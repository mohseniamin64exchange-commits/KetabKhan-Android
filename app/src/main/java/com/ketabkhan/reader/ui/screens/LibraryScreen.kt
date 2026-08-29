package com.ketabkhan.reader.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ketabkhan.reader.data.model.BookEntity
import com.ketabkhan.reader.data.model.BookStatus
import com.ketabkhan.reader.ui.components.AppBottomNav
import com.ketabkhan.reader.ui.components.ConfirmDeleteBottomSheet
import com.ketabkhan.reader.ui.components.SortBottomSheet
import com.ketabkhan.reader.ui.navigation.Screen
import com.ketabkhan.reader.ui.theme.*
import com.ketabkhan.reader.ui.viewmodel.BookReaderViewModel
import com.ketabkhan.reader.ui.viewmodel.MainTab

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    viewModel: BookReaderViewModel,
    modifier: Modifier = Modifier
) {
    val books by viewModel.displayBooks.collectAsState()
    val allBooks by viewModel.allBooks.collectAsState()
    val viewMode by viewModel.libraryViewMode.collectAsState()
    val sortBy by viewModel.librarySortBy.collectAsState()
    val showSortSheet by viewModel.showSortSheet.collectAsState()
    val isSearchActive by viewModel.isSearchActive.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val showDeleteDialog by viewModel.showDeleteConfirmDialog.collectAsState()
    val bookOptionsTarget by viewModel.bookOptionsTarget.collectAsState()

    var selectedFilter by remember { mutableStateOf("all") } // "all", "reading", "finished"
    var showOptionsMenu by remember { mutableStateOf(false) }

    val filteredBooks = remember(books, selectedFilter) {
        when (selectedFilter) {
            "reading" -> books.filter { it.status == BookStatus.READING.name }
            "finished" -> books.filter { it.progress >= 100 }
            else -> books
        }
    }

    val currentlyReadingBook = remember(allBooks) {
        allBooks.firstOrNull { it.status == BookStatus.READING.name }
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Background)
            ) {
                // Top App Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "کتابخانه من",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = TextPrimary
                        ),
                        modifier = Modifier.padding(start = 4.dp)
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Search Button
                        IconButton(
                            onClick = { viewModel.setSearchActive(!isSearchActive) },
                            modifier = Modifier
                                .size(44.dp)
                                .testTag("library_search_button")
                        ) {
                            Icon(
                                imageVector = if (isSearchActive) Icons.Filled.Close else Icons.Filled.Search,
                                contentDescription = "جستجو",
                                tint = if (isSearchActive) Primary else TextPrimary
                            )
                        }

                        // Grid / List Toggle
                        IconButton(
                            onClick = {
                                viewModel.setLibraryViewMode(if (viewMode == "grid") "list" else "grid")
                            },
                            modifier = Modifier
                                .size(44.dp)
                                .testTag("library_view_mode_toggle")
                        ) {
                            Icon(
                                imageVector = if (viewMode == "grid") Icons.Outlined.FormatListBulleted else Icons.Outlined.GridView,
                                contentDescription = "تغییر نمای نمایش",
                                tint = TextSecondary
                            )
                        }

                        // Sort Button
                        IconButton(
                            onClick = { viewModel.setShowSortSheet(true) },
                            modifier = Modifier
                                .size(44.dp)
                                .testTag("library_sort_button")
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Sort,
                                contentDescription = "مرتب‌سازی",
                                tint = TextSecondary
                            )
                        }

                        // More Menu
                        Box {
                            IconButton(
                                onClick = { showOptionsMenu = true },
                                modifier = Modifier
                                .size(44.dp)
                                .testTag("library_more_menu_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.MoreVert,
                                    contentDescription = "گزینه‌های بیشتر",
                                    tint = TextSecondary
                                )
                            }

                            DropdownMenu(
                                expanded = showOptionsMenu,
                                onDismissRequest = { showOptionsMenu = false },
                                modifier = Modifier.background(Surface)
                            ) {
                                DropdownMenuItem(
                                    text = { Text("واردکردن کتاب (.bookapp)", fontSize = 14.sp) },
                                    leadingIcon = { Icon(Icons.Outlined.FileDownload, contentDescription = null, tint = Primary) },
                                    onClick = {
                                        showOptionsMenu = false
                                        viewModel.navigateTo(Screen.ImportBook)
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("پشتیبان‌گیری و بازیابی", fontSize = 14.sp) },
                                    leadingIcon = { Icon(Icons.Outlined.Backup, contentDescription = null, tint = Primary) },
                                    onClick = {
                                        showOptionsMenu = false
                                        viewModel.navigateTo(Screen.BackupRestore)
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("حالت‌های سیستم", fontSize = 14.sp) },
                                    leadingIcon = { Icon(Icons.Outlined.Layers, contentDescription = null, tint = Primary) },
                                    onClick = {
                                        showOptionsMenu = false
                                        viewModel.navigateTo(Screen.SystemStates)
                                    }
                                )
                            }
                        }
                    }
                }

                // Search Input Field (when search is toggled)
                AnimatedVisibility(
                    visible = isSearchActive,
                    enter = expandVertically() + fadeIn(),
                    exit = shrinkVertically() + fadeOut()
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { viewModel.setSearchQuery(it) },
                        placeholder = { Text("جستجو در عنوان، نویسنده یا مترجم...", fontSize = 13.sp) },
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Surface,
                            unfocusedContainerColor = Surface,
                            focusedBorderColor = Primary,
                            unfocusedBorderColor = Border
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                            .testTag("library_search_input")
                    )
                }

                // Filter Chips
                if (allBooks.isNotEmpty()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChip(
                            selected = selectedFilter == "all",
                            onClick = { selectedFilter = "all" },
                            label = { Text("همه (${allBooks.size})", fontSize = 12.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Primary,
                                selectedLabelColor = Color.White
                            )
                        )
                        FilterChip(
                            selected = selectedFilter == "reading",
                            onClick = { selectedFilter = "reading" },
                            label = { Text("در حال مطالعه", fontSize = 12.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Primary,
                                selectedLabelColor = Color.White
                            )
                        )
                        FilterChip(
                            selected = selectedFilter == "finished",
                            onClick = { selectedFilter = "finished" },
                            label = { Text("خوانده شده", fontSize = 12.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Primary,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.navigateTo(Screen.SelectPdf) },
                containerColor = Primary,
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.testTag("fab_add_book")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "ساخت کتاب جدید")
                    Text("ساخت کتاب", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
            }
        },
        bottomBar = {
            AppBottomNav(
                currentTab = MainTab.LIBRARY,
                onTabSelected = { viewModel.selectTab(it) }
            )
        },
        containerColor = Background,
        modifier = modifier.testTag("library_screen")
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (filteredBooks.isEmpty()) {
                // Empty Library State
                EmptyLibraryView(
                    onCreateBook = { viewModel.navigateTo(Screen.SelectPdf) },
                    onImportBook = { viewModel.navigateTo(Screen.ImportBook) }
                )
            } else {
                if (viewMode == "grid") {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // Continue reading hero banner if applicable
                        if (currentlyReadingBook != null && selectedFilter == "all" && searchQuery.isBlank()) {
                            item(span = { GridItemSpan(2) }) {
                                ContinueReadingHeroCard(
                                    book = currentlyReadingBook,
                                    onContinue = { viewModel.openBook(currentlyReadingBook) }
                                )
                            }
                        }

                        items(filteredBooks, key = { it.id }) { book ->
                            BookGridCard(
                                book = book,
                                onClick = {
                                    when (book.status) {
                                        BookStatus.REVIEW.name -> viewModel.navigateTo(Screen.StructureReview)
                                        BookStatus.PROCESSING.name -> viewModel.navigateTo(Screen.Conversion)
                                        else -> viewModel.openBook(book)
                                    }
                                },
                                onOptionsClick = { viewModel.showBookOptions(book) }
                            )
                        }

                        item(span = { GridItemSpan(2) }) {
                            Spacer(modifier = Modifier.height(72.dp))
                        }
                    }
                } else {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // Continue reading hero banner if applicable
                        if (currentlyReadingBook != null && selectedFilter == "all" && searchQuery.isBlank()) {
                            item {
                                ContinueReadingHeroCard(
                                    book = currentlyReadingBook,
                                    onContinue = { viewModel.openBook(currentlyReadingBook) }
                                )
                            }
                        }

                        items(filteredBooks, key = { it.id }) { book ->
                            BookListRow(
                                book = book,
                                onClick = {
                                    when (book.status) {
                                        BookStatus.REVIEW.name -> viewModel.navigateTo(Screen.StructureReview)
                                        BookStatus.PROCESSING.name -> viewModel.navigateTo(Screen.Conversion)
                                        else -> viewModel.openBook(book)
                                    }
                                },
                                onOptionsClick = { viewModel.showBookOptions(book) }
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(72.dp))
                        }
                    }
                }
            }
        }
    }

    // Sort Bottom Sheet
    if (showSortSheet) {
        SortBottomSheet(
            currentSort = sortBy,
            onSortSelected = { viewModel.setLibrarySortBy(it) },
            onDismiss = { viewModel.setShowSortSheet(false) }
        )
    }

    // Delete confirmation sheet
    if (showDeleteDialog && bookOptionsTarget != null) {
        ConfirmDeleteBottomSheet(
            bookTitle = bookOptionsTarget!!.title,
            onConfirmDelete = { viewModel.confirmDeleteBook() },
            onDismiss = { viewModel.setShowDeleteConfirmDialog(false) }
        )
    }
}

@Composable
fun ContinueReadingHeroCard(
    book: BookEntity,
    onContinue: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = Surface,
        border = BorderStroke(1.dp, Border),
        shadowElevation = 1.dp,
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onContinue)
            .testTag("continue_reading_hero_card")
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Book cover thumbnail
            Surface(
                shape = RoundedCornerShape(10.dp),
                shadowElevation = 3.dp,
                modifier = Modifier
                    .width(52.dp)
                    .height(72.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    parseColorSafe(book.coverColor),
                                    parseColorSafe(book.coverAccent)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.MenuBook,
                        contentDescription = null,
                        tint = Color.White.copy(alpha = 0.85f),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            // Info & progress
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "ادامه مطالعه",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Primary
                )
                Text(
                    text = book.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = book.author,
                    fontSize = 12.sp,
                    color = TextSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    LinearProgressIndicator(
                        progress = { book.progress / 100f },
                        color = Primary,
                        trackColor = SecondarySurface,
                        modifier = Modifier
                            .weight(1f)
                            .height(6.dp)
                            .clip(CircleShape)
                    )
                    Text(
                        text = "${book.progress}٪",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Primary
                    )
                }
            }

            // Open button
            IconButton(
                onClick = onContinue,
                modifier = Modifier
                    .size(40.dp)
                    .background(SuccessBackground, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = "ادامه",
                    tint = Primary
                )
            }
        }
    }
}

@Composable
fun BookGridCard(
    book: BookEntity,
    onClick: () -> Unit,
    onOptionsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val statusInfo = getStatusInfo(book.status)

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Surface,
        border = BorderStroke(1.dp, Border),
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("book_card_${book.id}")
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            // Book Cover
            Surface(
                shape = RoundedCornerShape(12.dp),
                shadowElevation = 4.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    parseColorSafe(book.coverColor),
                                    parseColorSafe(book.coverAccent)
                                )
                            )
                        )
                        .padding(10.dp)
                ) {
                    // Status Badge Top
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = Color.Black.copy(alpha = 0.35f),
                        modifier = Modifier.align(Alignment.TopStart)
                    ) {
                        Text(
                            text = statusInfo.label,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }

                    // Options menu icon
                    IconButton(
                        onClick = onOptionsClick,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .size(32.dp)
                            .background(Color.Black.copy(alpha = 0.25f), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "گزینه‌ها",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    // Book Title On Cover
                    Column(
                        modifier = Modifier.align(Alignment.BottomStart)
                    ) {
                        Text(
                            text = book.title,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = book.author,
                            fontSize = 11.sp,
                            color = Color.White.copy(alpha = 0.8f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Title & Author below card
            Text(
                text = book.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = book.author,
                fontSize = 12.sp,
                color = TextSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            // Progress bar
            if (book.status == BookStatus.READING.name) {
                Spacer(modifier = Modifier.height(6.dp))
                LinearProgressIndicator(
                    progress = { book.progress / 100f },
                    color = Primary,
                    trackColor = SecondarySurface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(CircleShape)
                )
            }
        }
    }
}

@Composable
fun BookListRow(
    book: BookEntity,
    onClick: () -> Unit,
    onOptionsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val statusInfo = getStatusInfo(book.status)

    Surface(
        shape = RoundedCornerShape(14.dp),
        color = Surface,
        border = BorderStroke(1.dp, Border),
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("book_row_${book.id}")
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Book cover thumbnail
            Surface(
                shape = RoundedCornerShape(8.dp),
                shadowElevation = 2.dp,
                modifier = Modifier
                    .width(48.dp)
                    .height(64.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    parseColorSafe(book.coverColor),
                                    parseColorSafe(book.coverAccent)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.MenuBook,
                        contentDescription = null,
                        tint = Color.White.copy(alpha = 0.85f),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // Info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = book.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = book.author,
                    fontSize = 13.sp,
                    color = TextSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Text(
                        text = statusInfo.label,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = statusInfo.color
                    )
                    if (book.lastRead != null) {
                        Text(
                            text = "·  ${book.lastRead}",
                            fontSize = 11.sp,
                            color = TextSecondary
                        )
                    }
                }

                if (book.status == BookStatus.READING.name) {
                    Spacer(modifier = Modifier.height(6.dp))
                    LinearProgressIndicator(
                        progress = { book.progress / 100f },
                        color = Primary,
                        trackColor = SecondarySurface,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp)
                            .clip(CircleShape)
                    )
                }
            }

            // More Options Icon
            IconButton(
                onClick = onOptionsClick,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "گزینه‌های کتاب",
                    tint = TextSecondary
                )
            }
        }
    }
}

@Composable
fun EmptyLibraryView(
    onCreateBook: () -> Unit,
    onImportBook: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            shape = RoundedCornerShape(28.dp),
            color = SecondarySurface,
            modifier = Modifier.size(100.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    imageVector = Icons.Outlined.AutoStories,
                    contentDescription = null,
                    tint = TextSecondary,
                    modifier = Modifier.size(48.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "هنوز کتابی در کتابخانه شما نیست",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = TextPrimary
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "می‌توانید از یک فایل PDF کتاب جدید بسازید یا یک کتاب آماده را وارد کنید.",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = TextSecondary,
                lineHeight = 22.sp
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = onCreateBook,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Primary),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Icon(Icons.Filled.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("ساخت کتاب جدید", fontWeight = FontWeight.Bold, color = Color.White)
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(
            onClick = onImportBook,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Icon(Icons.Outlined.FileDownload, contentDescription = null, tint = Primary)
            Spacer(modifier = Modifier.width(8.dp))
            Text("واردکردن کتاب (.bookapp)", fontWeight = FontWeight.Bold, color = Primary)
        }
    }
}

data class StatusInfo(val label: String, val color: Color)

fun getStatusInfo(status: String): StatusInfo {
    return when (status) {
        BookStatus.READING.name -> StatusInfo("در حال مطالعه", Primary)
        BookStatus.READY.name -> StatusInfo("آماده مطالعه", StatusSuccess)
        BookStatus.PROCESSING.name -> StatusInfo("در حال پردازش", StatusWarning)
        BookStatus.REVIEW.name -> StatusInfo("نیاز به بررسی", StatusWarning)
        BookStatus.FAILED.name -> StatusInfo("خطا در تبدیل", StatusError)
        BookStatus.IMPORTED.name -> StatusInfo("وارد شده", Primary)
        else -> StatusInfo("آماده مطالعه", StatusSuccess)
    }
}

fun parseColorSafe(colorHex: String): Color {
    return try {
        Color(android.graphics.Color.parseColor(colorHex))
    } catch (e: Exception) {
        Primary
    }
}
