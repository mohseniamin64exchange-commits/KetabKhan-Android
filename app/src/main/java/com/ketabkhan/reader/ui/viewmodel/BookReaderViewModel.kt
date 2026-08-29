package com.ketabkhan.reader.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ketabkhan.reader.data.db.AppDatabase
import com.ketabkhan.reader.data.model.*
import com.ketabkhan.reader.data.preferences.ReaderPreferencesRepository
import com.ketabkhan.reader.data.repository.BookRepository
import com.ketabkhan.reader.data.sample.SampleData
import com.ketabkhan.reader.ui.navigation.Screen
import com.ketabkhan.reader.util.AppConstants
import com.ketabkhan.reader.work.WorkManagerHelper
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

enum class MainTab {
    LIBRARY,
    CONVERT,
    SETTINGS
}

data class ConversionStage(
    val name: String,
    val isComplete: Boolean = false,
    val isCurrent: Boolean = false
)

class BookReaderViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val preferencesRepository = ReaderPreferencesRepository(application)
    private val repository = BookRepository(
        database.bookDao(),
        database.bookmarkDao(),
        database.settingsDao(),
        preferencesRepository
    )

    // Navigation Stack State
    private val _currentScreen = MutableStateFlow<Screen>(Screen.Splash)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val screenBackStack = mutableListOf<Screen>()

    // Main Tab
    private val _currentTab = MutableStateFlow(MainTab.LIBRARY)
    val currentTab: StateFlow<MainTab> = _currentTab.asStateFlow()

    // Library State
    val allBooks: StateFlow<List<BookEntity>> = repository.allBooks.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _isSearchActive = MutableStateFlow(false)
    val isSearchActive: StateFlow<Boolean> = _isSearchActive.asStateFlow()

    private val _libraryViewMode = MutableStateFlow("grid") // "grid" or "list"
    val libraryViewMode: StateFlow<String> = _libraryViewMode.asStateFlow()

    private val _librarySortBy = MutableStateFlow("lastRead") // "lastRead", "addedDate", "title", "author"
    val librarySortBy: StateFlow<String> = _librarySortBy.asStateFlow()

    private val _showSortSheet = MutableStateFlow(false)
    val showSortSheet: StateFlow<Boolean> = _showSortSheet.asStateFlow()

    // Filtered & Sorted Books
    val displayBooks: StateFlow<List<BookEntity>> = combine(
        allBooks,
        searchQuery,
        librarySortBy
    ) { books, query, sortBy ->
        var list = if (query.isBlank()) {
            books
        } else {
            books.filter {
                it.title.contains(query, ignoreCase = true) ||
                it.author.contains(query, ignoreCase = true) ||
                it.translator.contains(query, ignoreCase = true)
            }
        }

        when (sortBy) {
            "title" -> list.sortedBy { it.title }
            "author" -> list.sortedBy { it.author }
            "addedDate" -> list.sortedByDescending { it.addedDate }
            else -> list.sortedWith(compareByDescending<BookEntity> { it.lastRead != null }.thenByDescending { it.lastRead })
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Active Book & Reader State
    private val _selectedBook = MutableStateFlow<BookEntity?>(null)
    val selectedBook: StateFlow<BookEntity?> = _selectedBook.asStateFlow()

    private val _selectedBookChapters = MutableStateFlow<List<Chapter>>(emptyList())
    val selectedBookChapters: StateFlow<List<Chapter>> = _selectedBookChapters.asStateFlow()

    private val _currentChapterIndex = MutableStateFlow(0)
    val currentChapterIndex: StateFlow<Int> = _currentChapterIndex.asStateFlow()

    private val _readerControlsVisible = MutableStateFlow(true)
    val readerControlsVisible: StateFlow<Boolean> = _readerControlsVisible.asStateFlow()

    private val _activeFootnote = MutableStateFlow<Footnote?>(null)
    val activeFootnote: StateFlow<Footnote?> = _activeFootnote.asStateFlow()

    // Reader Settings
    private val _readerSettings = MutableStateFlow(ReaderSettings())
    val readerSettings: StateFlow<ReaderSettings> = _readerSettings.asStateFlow()

    private val _showReaderSettingsSheet = MutableStateFlow(false)
    val showReaderSettingsSheet: StateFlow<Boolean> = _showReaderSettingsSheet.asStateFlow()

    // Bookmarks for selected book
    private val _bookBookmarks = MutableStateFlow<List<BookmarkEntity>>(emptyList())
    val bookBookmarks: StateFlow<List<BookmarkEntity>> = _bookBookmarks.asStateFlow()

    // All Bookmarks
    val allBookmarks: StateFlow<List<BookmarkEntity>> = repository.allBookmarks.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    // Book Options Modal / Screen
    private val _bookOptionsTarget = MutableStateFlow<BookEntity?>(null)
    val bookOptionsTarget: StateFlow<BookEntity?> = _bookOptionsTarget.asStateFlow()

    private val _showDeleteConfirmDialog = MutableStateFlow(false)
    val showDeleteConfirmDialog: StateFlow<Boolean> = _showDeleteConfirmDialog.asStateFlow()

    // Conversion Process State
    private val _conversionProgress = MutableStateFlow(0f)
    val conversionProgress: StateFlow<Float> = _conversionProgress.asStateFlow()

    private val _conversionStages = MutableStateFlow<List<ConversionStage>>(emptyList())
    val conversionStages: StateFlow<List<ConversionStage>> = _conversionStages.asStateFlow()

    private val _conversionCompleted = MutableStateFlow(false)
    val conversionCompleted: StateFlow<Boolean> = _conversionCompleted.asStateFlow()

    private var conversionJob: Job? = null

    // Selected PDF File info for import/conversion
    private val _selectedPdfName = MutableStateFlow("sample_document_demo.pdf")
    val selectedPdfName: StateFlow<String> = _selectedPdfName.asStateFlow()

    private val _selectedPdfSize = MutableStateFlow("۲.۴ مگابایت")
    val selectedPdfSize: StateFlow<String> = _selectedPdfSize.asStateFlow()

    private val _selectedPdfPages = MutableStateFlow("۱۲۰ صفحه")
    val selectedPdfPages: StateFlow<String> = _selectedPdfPages.asStateFlow()

    // New Book Draft Metadata
    private val _draftTitle = MutableStateFlow("کتاب نمونه ۱ (آزمایشی)")
    val draftTitle: StateFlow<String> = _draftTitle.asStateFlow()

    private val _draftAuthor = MutableStateFlow("نویسنده آزمایشی")
    val draftAuthor: StateFlow<String> = _draftAuthor.asStateFlow()

    private val _draftTranslator = MutableStateFlow("")
    val draftTranslator: StateFlow<String> = _draftTranslator.asStateFlow()

    private val _draftPublisher = MutableStateFlow("نشر آزمایشی")
    val draftPublisher: StateFlow<String> = _draftPublisher.asStateFlow()

    private val _draftPublishYear = MutableStateFlow("۱۴۰۳")
    val draftPublishYear: StateFlow<String> = _draftPublishYear.asStateFlow()

    private val _draftLanguage = MutableStateFlow("فارسی")
    val draftLanguage: StateFlow<String> = _draftLanguage.asStateFlow()

    private val _draftDirection = MutableStateFlow("RTL")
    val draftDirection: StateFlow<String> = _draftDirection.asStateFlow()

    private val _draftCoverColor = MutableStateFlow("#2B5329")
    val draftCoverColor: StateFlow<String> = _draftCoverColor.asStateFlow()

    private val _draftCoverAccent = MutableStateFlow("#4A7C47")
    val draftCoverAccent: StateFlow<String> = _draftCoverAccent.asStateFlow()

    // Issues Review State
    private val _reviewIssues = MutableStateFlow<List<ReviewIssue>>(SampleData.getSampleReviewIssues())
    val reviewIssues: StateFlow<List<ReviewIssue>> = _reviewIssues.asStateFlow()

    private val _currentIssueIndex = MutableStateFlow(0)
    val currentIssueIndex: StateFlow<Int> = _currentIssueIndex.asStateFlow()

    private val _issueTab = MutableStateFlow("original") // "original" or "converted"
    val issueTab: StateFlow<String> = _issueTab.asStateFlow()

    // Import Book Flow State
    private val _importState = MutableStateFlow("empty") // "empty", "selected", "duplicate", "invalid", "success"
    val importState: StateFlow<String> = _importState.asStateFlow()

    // Backup & Restore State
    private val _backupIncludeReadingPos = MutableStateFlow(true)
    val backupIncludeReadingPos: StateFlow<Boolean> = _backupIncludeReadingPos.asStateFlow()

    private val _backupIncludeBookmarks = MutableStateFlow(true)
    val backupIncludeBookmarks: StateFlow<Boolean> = _backupIncludeBookmarks.asStateFlow()

    private val _backupIncludeSettings = MutableStateFlow(true)
    val backupIncludeSettings: StateFlow<Boolean> = _backupIncludeSettings.asStateFlow()

    private val _backupCompleted = MutableStateFlow(false)
    val backupCompleted: StateFlow<Boolean> = _backupCompleted.asStateFlow()

    private val _restoreState = MutableStateFlow("idle") // "idle", "preview", "success"
    val restoreState: StateFlow<String> = _restoreState.asStateFlow()

    // Toast / Snackbar message
    private val _snackbarMessage = MutableStateFlow<String?>(null)
    val snackbarMessage: StateFlow<String?> = _snackbarMessage.asStateFlow()

    init {
        viewModelScope.launch {
            repository.checkAndSeedDatabase()
        }
        viewModelScope.launch {
            preferencesRepository.readerSettingsFlow.collect { settings ->
                _readerSettings.value = settings
            }
        }
    }

    // Navigation Methods
    fun navigateTo(screen: Screen) {
        if (_currentScreen.value != screen) {
            screenBackStack.add(_currentScreen.value)
            _currentScreen.value = screen
        }
    }

    fun goBack(): Boolean {
        if (screenBackStack.isNotEmpty()) {
            _currentScreen.value = screenBackStack.removeAt(screenBackStack.size - 1)
            return true
        } else if (_currentScreen.value != Screen.Library) {
            _currentScreen.value = Screen.Library
            return true
        }
        return false
    }

    fun selectTab(tab: MainTab) {
        _currentTab.value = tab
        when (tab) {
            MainTab.LIBRARY -> navigateTo(Screen.Library)
            MainTab.CONVERT -> navigateTo(Screen.SelectPdf)
            MainTab.SETTINGS -> navigateTo(Screen.GeneralSettings)
        }
    }

    // Library Methods
    fun setLibraryViewMode(mode: String) {
        _libraryViewMode.value = mode
    }

    fun setLibrarySortBy(sort: String) {
        _librarySortBy.value = sort
        _showSortSheet.value = false
    }

    fun setShowSortSheet(show: Boolean) {
        _showSortSheet.value = show
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setSearchActive(active: Boolean) {
        _isSearchActive.value = active
        if (!active) _searchQuery.value = ""
    }

    // Reader Methods
    fun openBook(book: BookEntity) {
        _selectedBook.value = book
        val chapters = SampleData.jsonToChapters(book.chaptersJson)
        _selectedBookChapters.value = chapters
        _currentChapterIndex.value = book.currentChapterIndex.coerceIn(0, (chapters.size - 1).coerceAtLeast(0))
        _readerControlsVisible.value = true

        // load bookmarks for this book
        viewModelScope.launch {
            repository.getBookmarksForBook(book.id).collect { bookmarks ->
                _bookBookmarks.value = bookmarks
            }
        }

        navigateTo(Screen.Reader)
    }

    fun closeReader() {
        saveReadingProgress()
        _selectedBook.value = null
        goBack()
    }

    fun toggleReaderControls() {
        _readerControlsVisible.value = !_readerControlsVisible.value
    }

    fun nextChapter() {
        if (_currentChapterIndex.value < _selectedBookChapters.value.size - 1) {
            _currentChapterIndex.value++
            saveReadingProgress()
        }
    }

    fun previousChapter() {
        if (_currentChapterIndex.value > 0) {
            _currentChapterIndex.value--
            saveReadingProgress()
        }
    }

    fun selectChapter(index: Int) {
        if (index in _selectedBookChapters.value.indices) {
            _currentChapterIndex.value = index
            saveReadingProgress()
            if (_currentScreen.value == Screen.BookNav) {
                navigateTo(Screen.Reader)
            }
        }
    }

    fun updateProgressPercent(percent: Int) {
        val book = _selectedBook.value ?: return
        val updated = book.copy(progress = percent.coerceIn(0, 100))
        _selectedBook.value = updated
        viewModelScope.launch {
            repository.updateReadingProgress(
                id = book.id,
                progress = percent,
                chapterIndex = _currentChapterIndex.value,
                offset = 0,
                lastRead = "امروز"
            )
        }
    }

    private fun saveReadingProgress() {
        val book = _selectedBook.value ?: return
        viewModelScope.launch {
            val totalChapters = (_selectedBookChapters.value.size).coerceAtLeast(1)
            val computedProgress = (((_currentChapterIndex.value + 1).toFloat() / totalChapters) * 100).toInt()
            val progress = computedProgress.coerceIn(book.progress, 100)
            repository.updateReadingProgress(
                id = book.id,
                progress = progress,
                chapterIndex = _currentChapterIndex.value,
                offset = 0,
                lastRead = "امروز"
            )
        }
    }

    // Footnotes
    fun showFootnote(footnote: Footnote) {
        _activeFootnote.value = footnote
    }

    fun dismissFootnote() {
        _activeFootnote.value = null
    }

    // Reader Settings
    fun updateReaderSettings(update: ReaderSettings.() -> ReaderSettings) {
        val newSettings = _readerSettings.value.update()
        _readerSettings.value = newSettings
        viewModelScope.launch {
            preferencesRepository.updateSettings(newSettings)
        }
    }

    fun setShowReaderSettingsSheet(show: Boolean) {
        _showReaderSettingsSheet.value = show
    }

    // Bookmarks
    fun addBookmarkForCurrentLocation() {
        val book = _selectedBook.value ?: return
        val currentChapter = _selectedBookChapters.value.getOrNull(_currentChapterIndex.value)
        val title = currentChapter?.title ?: "فصل ${_currentChapterIndex.value + 1}"
        val snippet = currentChapter?.content?.take(80)?.plus("...") ?: ""

        val newBookmark = BookmarkEntity(
            bookId = book.id,
            chapterTitle = title,
            excerpt = snippet,
            date = "امروز",
            chapterIndex = _currentChapterIndex.value,
            scrollOffset = 0
        )

        viewModelScope.launch {
            repository.addBookmark(newBookmark)
            showSnackbar("نشانک با موفقیت ذخیره شد")
        }
    }

    fun deleteBookmark(id: Long) {
        viewModelScope.launch {
            repository.deleteBookmarkById(id)
            showSnackbar("نشانک حذف شد")
        }
    }

    // Book Options & Delete
    fun showBookOptions(book: BookEntity) {
        _bookOptionsTarget.value = book
        navigateTo(Screen.BookOptions)
    }

    fun setShowDeleteConfirmDialog(show: Boolean) {
        _showDeleteConfirmDialog.value = show
    }

    fun confirmDeleteBook() {
        val book = _bookOptionsTarget.value ?: return
        viewModelScope.launch {
            repository.deleteBookById(book.id)
            _showDeleteConfirmDialog.value = false
            _bookOptionsTarget.value = null
            showSnackbar("کتاب از کتابخانه حذف شد (فایل اصلی PDF دست‌نخورده باقی ماند)")
            navigateTo(Screen.Library)
        }
    }

    // Conversion Process handling
    fun startConversion() {
        _conversionProgress.value = 0f
        _conversionCompleted.value = false
        _conversionStages.value = listOf(
            ConversionStage("ثبت درخواست در WorkManager", isCurrent = true, isComplete = true),
            ConversionStage("استخراج ساختار و متون PDF (${AppConstants.STATUS_IN_DEVELOPMENT})"),
            ConversionStage("تشخیص هوشمند عناوین و پاورقی‌ها (${AppConstants.STATUS_IN_DEVELOPMENT})"),
            ConversionStage("آماده‌سازی پیش‌نمایش و داده‌ها (${AppConstants.STATUS_IN_DEVELOPMENT})")
        )
        
        // Enqueue real background worker via WorkManager
        WorkManagerHelper.scheduleBookProcessing(getApplication(), _selectedPdfName.value)
        
        navigateTo(Screen.Conversion)
    }

    fun cancelConversion() {
        conversionJob?.cancel()
        goBack()
    }

    // Draft Metadata Editor
    fun setDraftMetadata(
        title: String,
        author: String,
        translator: String,
        publisher: String,
        publishYear: String,
        language: String,
        direction: String
    ) {
        _draftTitle.value = title
        _draftAuthor.value = author
        _draftTranslator.value = translator
        _draftPublisher.value = publisher
        _draftPublishYear.value = publishYear
        _draftLanguage.value = language
        _draftDirection.value = direction
    }

    fun setDraftCover(color: String, accent: String) {
        _draftCoverColor.value = color
        _draftCoverAccent.value = accent
    }

    // Issues Review
    fun setIssueIndex(index: Int) {
        _currentIssueIndex.value = index.coerceIn(0, _reviewIssues.value.size - 1)
    }

    fun setIssueTab(tab: String) {
        _issueTab.value = tab
    }

    fun resolveCurrentIssue(action: String) {
        val index = _currentIssueIndex.value
        val list = _reviewIssues.value.toMutableList()
        if (index in list.indices) {
            list[index] = list[index].copy(isResolved = true)
            _reviewIssues.value = list
        }
        if (index < _reviewIssues.value.size - 1) {
            _currentIssueIndex.value = index + 1
        } else {
            navigateTo(Screen.StructureReview)
        }
    }

    // Add Converted Book To Library
    fun finalizeAndAddBookToLibrary() {
        val newId = System.currentTimeMillis().toString()
        val sampleChapters = SampleData.getSampleBooks().first().chaptersJson

        val newBook = BookEntity(
            id = newId,
            title = _draftTitle.value.ifBlank { "کتاب جدید" },
            author = _draftAuthor.value.ifBlank { "نویسنده نامشخص" },
            translator = _draftTranslator.value,
            publisher = _draftPublisher.value,
            publishYear = _draftPublishYear.value,
            progress = 0,
            status = BookStatus.READY.name,
            coverColor = _draftCoverColor.value,
            coverAccent = _draftCoverAccent.value,
            lastRead = null,
            addedDate = "امروز",
            chaptersCount = 14,
            currentChapterIndex = 0,
            currentScrollOffset = 0,
            language = _draftLanguage.value,
            direction = _draftDirection.value,
            isFavorite = false,
            chaptersJson = sampleChapters
        )

        viewModelScope.launch {
            repository.insertBook(newBook)
            showSnackbar("کتاب با موفقیت به کتابخانه اضافه شد")
            navigateTo(Screen.Library)
        }
    }

    // Import Flow
    fun setImportState(state: String) {
        _importState.value = state
    }

    fun handleIncomingBookUri(uriString: String) {
        _importState.value = "selected"
        navigateTo(Screen.ImportBook)
        showSnackbar("فایل بسته کتاب دریافت شد. برای بررسی ساختار آماده است.")
    }

    fun performImportBook() {
        showSnackbar(AppConstants.MSG_IMPORT_BOOKAPP_DEV)
    }

    // Backup & Restore
    fun toggleBackupIncludeReadingPos() {
        _backupIncludeReadingPos.value = !_backupIncludeReadingPos.value
    }

    fun toggleBackupIncludeBookmarks() {
        _backupIncludeBookmarks.value = !_backupIncludeBookmarks.value
    }

    fun toggleBackupIncludeSettings() {
        _backupIncludeSettings.value = !_backupIncludeSettings.value
    }

    fun performBackup() {
        showSnackbar(AppConstants.MSG_BACKUP_RESTORE_DEV)
    }

    fun setRestoreState(state: String) {
        _restoreState.value = state
    }

    fun performRestore() {
        showSnackbar(AppConstants.MSG_BACKUP_RESTORE_DEV)
    }

    // Snackbar
    fun showSnackbar(message: String) {
        _snackbarMessage.value = message
    }

    fun dismissSnackbar() {
        _snackbarMessage.value = null
    }
}
