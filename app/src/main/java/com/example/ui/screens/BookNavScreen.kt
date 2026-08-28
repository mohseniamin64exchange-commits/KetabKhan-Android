package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.BookmarkRemove
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BookmarkEntity
import com.example.ui.components.AppTopBar
import com.example.ui.navigation.Screen
import com.example.ui.theme.*
import com.example.ui.viewmodel.BookReaderViewModel

enum class BookNavTab {
    TOC,
    BOOKMARKS,
    SEARCH
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookNavScreen(
    viewModel: BookReaderViewModel,
    modifier: Modifier = Modifier
) {
    val book by viewModel.selectedBook.collectAsState()
    val chapters by viewModel.selectedBookChapters.collectAsState()
    val currentChapterIndex by viewModel.currentChapterIndex.collectAsState()
    val bookmarks by viewModel.bookBookmarks.collectAsState()

    var selectedTab by remember { mutableStateOf(BookNavTab.TOC) }
    var inBookSearchQuery by remember { mutableStateOf("") }

    val searchResults = remember(inBookSearchQuery, chapters) {
        if (inBookSearchQuery.isBlank()) {
            emptyList()
        } else {
            chapters.mapIndexedNotNull { index, chapter ->
                if (chapter.title.contains(inBookSearchQuery, ignoreCase = true) || chapter.content.contains(inBookSearchQuery, ignoreCase = true)) {
                    Pair(index, chapter)
                } else null
            }
        }
    }

    Scaffold(
        topBar = {
            Column(modifier = Modifier.background(Background)) {
                AppTopBar(
                    title = book?.title ?: "ناوبری کتاب",
                    subtitle = "فهرست، نشانک‌ها و جستجو",
                    onBack = { viewModel.navigateTo(Screen.Reader) }
                )

                // Tabs: فهرست مطالب, نشانک‌ها, جستجو
                TabRow(
                    selectedTabIndex = selectedTab.ordinal,
                    containerColor = Surface,
                    contentColor = Primary,
                    divider = { HorizontalDivider(color = Border) }
                ) {
                    Tab(
                        selected = selectedTab == BookNavTab.TOC,
                        onClick = { selectedTab = BookNavTab.TOC },
                        text = {
                            Text(
                                text = "فهرست مطالب",
                                fontWeight = if (selectedTab == BookNavTab.TOC) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 13.sp
                            )
                        }
                    )

                    Tab(
                        selected = selectedTab == BookNavTab.BOOKMARKS,
                        onClick = { selectedTab = BookNavTab.BOOKMARKS },
                        text = {
                            Text(
                                text = "نشانک‌ها (${bookmarks.size})",
                                fontWeight = if (selectedTab == BookNavTab.BOOKMARKS) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 13.sp
                            )
                        }
                    )

                    Tab(
                        selected = selectedTab == BookNavTab.SEARCH,
                        onClick = { selectedTab = BookNavTab.SEARCH },
                        text = {
                            Text(
                                text = "جستجو",
                                fontWeight = if (selectedTab == BookNavTab.SEARCH) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 13.sp
                            )
                        }
                    )
                }
            }
        },
        containerColor = Background,
        modifier = modifier.testTag("book_nav_screen")
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                BookNavTab.TOC -> {
                    // Table of Contents List
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        itemsIndexed(chapters) { index, ch ->
                            val isCurrent = index == currentChapterIndex
                            val isIndented = ch.level > 0

                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = if (isCurrent) SecondarySurface else Surface,
                                border = BorderStroke(1.dp, if (isCurrent) Primary else Border),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { viewModel.selectChapter(index) }
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(
                                            start = if (isIndented) 32.dp else 16.dp,
                                            end = 16.dp,
                                            top = 12.dp,
                                            bottom = 12.dp
                                        ),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    if (isCurrent) {
                                        Box(
                                            modifier = Modifier
                                                .size(8.dp)
                                                .background(Primary, CircleShape)
                                        )
                                    }

                                    Text(
                                        text = ch.title,
                                        fontSize = 14.sp,
                                        fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isCurrent) Primary else TextPrimary,
                                        modifier = Modifier.weight(1f)
                                    )

                                    if (isCurrent) {
                                        Text(
                                            text = "در حال مطالعه",
                                            fontSize = 11.sp,
                                            color = Primary,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                BookNavTab.BOOKMARKS -> {
                    // Bookmarks List
                    if (bookmarks.isEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Bookmarks,
                                contentDescription = null,
                                tint = TextSecondary,
                                modifier = Modifier.size(56.dp)
                            )
                            Spacer(modifier = Modifier.height(14.dp))
                            Text(
                                text = "هنوز نشانکی برای این کتاب ثبت نشده است",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = TextPrimary
                                ),
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "هنگام مطالعه می‌توانید با لمس دکمه نشانک در بالای صفحه، جای خوانش خود را ذخیره کنید.",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = TextSecondary,
                                    lineHeight = 20.sp
                                ),
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(bookmarks, key = { it.id }) { bm ->
                                Surface(
                                    shape = RoundedCornerShape(14.dp),
                                    color = Surface,
                                    border = BorderStroke(1.dp, Border),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { viewModel.selectChapter(bm.chapterIndex) }
                                ) {
                                    Row(
                                        modifier = Modifier.padding(14.dp),
                                        verticalAlignment = Alignment.Top,
                                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Bookmark,
                                            contentDescription = null,
                                            tint = Primary,
                                            modifier = Modifier.size(24.dp)
                                        )

                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = bm.chapterTitle,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 14.sp,
                                                color = TextPrimary
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                text = bm.excerpt,
                                                fontSize = 12.sp,
                                                color = TextSecondary,
                                                maxLines = 2,
                                                overflow = TextOverflow.Ellipsis,
                                                lineHeight = 18.sp
                                            )
                                            Spacer(modifier = Modifier.height(6.dp))
                                            Text(
                                                text = bm.date,
                                                fontSize = 11.sp,
                                                color = Primary
                                            )
                                        }

                                        IconButton(
                                            onClick = { viewModel.deleteBookmark(bm.id) },
                                            modifier = Modifier.size(36.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Outlined.BookmarkRemove,
                                                contentDescription = "حذف نشانک",
                                                tint = StatusError
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                BookNavTab.SEARCH -> {
                    // In-Book Search View
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        OutlinedTextField(
                            value = inBookSearchQuery,
                            onValueChange = { inBookSearchQuery = it },
                            placeholder = { Text("عبارت مورد نظر برای جستجو در کتاب...", fontSize = 13.sp) },
                            singleLine = true,
                            shape = RoundedCornerShape(14.dp),
                            leadingIcon = {
                                Icon(Icons.Outlined.Search, contentDescription = null, tint = Primary)
                            },
                            trailingIcon = {
                                if (inBookSearchQuery.isNotEmpty()) {
                                    IconButton(onClick = { inBookSearchQuery = "" }) {
                                        Icon(Icons.Filled.Close, contentDescription = "پاک‌کردن")
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        if (inBookSearchQuery.isBlank()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(24.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "عبارت دلخواه خود را در کادر بالا بنویسید تا تمام موارد در متن کتاب یافته شود.",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = TextSecondary,
                                        textAlign = TextAlign.Center
                                    )
                                )
                            }
                        } else if (searchResults.isEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(24.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "موردی مطابق با «$inBookSearchQuery» در این کتاب یافت نشد.",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = TextSecondary,
                                        textAlign = TextAlign.Center
                                    )
                                )
                            }
                        } else {
                            Text(
                                text = "${searchResults.size} مورد یافت شد:",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Primary,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(searchResults) { (idx, ch) ->
                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = Surface,
                                        border = BorderStroke(1.dp, Border),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable { viewModel.selectChapter(idx) }
                                    ) {
                                        Column(modifier = Modifier.padding(12.dp)) {
                                            Text(
                                                text = ch.title,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 13.sp,
                                                color = Primary
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                text = ch.content.take(100) + "...",
                                                fontSize = 12.sp,
                                                color = TextPrimary,
                                                lineHeight = 18.sp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
