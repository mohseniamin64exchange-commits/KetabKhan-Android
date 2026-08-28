package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Chapter
import com.example.data.model.Footnote
import com.example.data.model.ReaderSettings
import com.example.ui.components.FootnoteBottomSheet
import com.example.ui.components.ReadingSettingsSheet
import com.example.ui.navigation.Screen
import com.example.ui.theme.*
import com.example.ui.viewmodel.BookReaderViewModel

@Composable
fun ReaderScreen(
    viewModel: BookReaderViewModel,
    modifier: Modifier = Modifier
) {
    val book by viewModel.selectedBook.collectAsState()
    val chapters by viewModel.selectedBookChapters.collectAsState()
    val currentChapterIndex by viewModel.currentChapterIndex.collectAsState()
    val controlsVisible by viewModel.readerControlsVisible.collectAsState()
    val settings by viewModel.readerSettings.collectAsState()
    val activeFootnote by viewModel.activeFootnote.collectAsState()
    val showSettingsSheet by viewModel.showReaderSettingsSheet.collectAsState()

    val currentChapter = chapters.getOrNull(currentChapterIndex)
    val scrollState = rememberScrollState()

    // Dynamic background and text colors based on theme & night mode
    val readerBackground = remember(settings.theme, settings.nightMode, settings.nightWarmth) {
        if (settings.nightMode) {
            // Warm eye-friendly dark amber
            Color(0xFF1C1917)
        } else {
            when (settings.theme) {
                "cream" -> Color(0xFFF7F0E3)
                "dark" -> Color(0xFF1E2020)
                else -> Color(0xFFFAF6F0)
            }
        }
    }

    val readerTextColor = remember(settings.theme, settings.nightMode) {
        if (settings.nightMode) {
            Color(0xFFD6C7A1)
        } else {
            when (settings.theme) {
                "cream" -> Color(0xFF2C241B)
                "dark" -> Color(0xFFE2DFD8)
                else -> Color(0xFF1A1A18)
            }
        }
    }

    val readerSecondaryColor = remember(settings.theme, settings.nightMode) {
        if (settings.nightMode) {
            Color(0xFF9E9275)
        } else {
            when (settings.theme) {
                "cream" -> Color(0xFF6B5D4E)
                "dark" -> Color(0xFF9A9996)
                else -> Color(0xFF5A5852)
            }
        }
    }

    val readerFontFamily = remember(settings.font) {
        when (settings.font) {
            "نوتو نسخ" -> NotoNaskhArabic
            "نوتو سنس" -> NotoSansArabic
            else -> Vazirmatn
        }
    }

    Scaffold(
        topBar = {
            AnimatedVisibility(
                visible = controlsVisible,
                enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut()
            ) {
                Surface(
                    color = readerBackground,
                    shadowElevation = 3.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(
                            onClick = { viewModel.closeReader() },
                            modifier = Modifier
                                .size(48.dp)
                                .testTag("reader_back_button")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "بازگشت به کتابخانه",
                                tint = readerTextColor
                            )
                        }

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 8.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = book?.title ?: "کتاب",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = readerTextColor
                                ),
                                maxLines = 1
                            )
                            Text(
                                text = currentChapter?.title ?: "فصل ${currentChapterIndex + 1}",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 11.sp,
                                    color = readerSecondaryColor
                                ),
                                maxLines = 1
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // Bookmark Button
                            IconButton(
                                onClick = { viewModel.addBookmarkForCurrentLocation() },
                                modifier = Modifier
                                    .size(44.dp)
                                    .testTag("reader_bookmark_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.BookmarkAdd,
                                    contentDescription = "افزودن نشانک",
                                    tint = readerTextColor
                                )
                            }

                            // TOC & Search Navigation Button
                            IconButton(
                                onClick = { viewModel.navigateTo(Screen.BookNav) },
                                modifier = Modifier
                                    .size(44.dp)
                                    .testTag("reader_toc_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.List,
                                    contentDescription = "فهرست مطالب",
                                    tint = readerTextColor
                                )
                            }

                            // Settings Button
                            IconButton(
                                onClick = { viewModel.setShowReaderSettingsSheet(true) },
                                modifier = Modifier
                                    .size(44.dp)
                                    .testTag("reader_settings_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Tune,
                                    contentDescription = "تنظیمات مطالعه",
                                    tint = readerTextColor
                                )
                            }
                        }
                    }
                }
            }
        },
        bottomBar = {
            AnimatedVisibility(
                visible = controlsVisible,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
            ) {
                Surface(
                    color = readerBackground,
                    shadowElevation = 6.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        // Progress Slider & Info
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "فصل ${currentChapterIndex + 1} از ${chapters.size.coerceAtLeast(1)}",
                                fontSize = 12.sp,
                                color = readerSecondaryColor
                            )
                            Text(
                                text = "${book?.progress ?: 0}٪",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Primary
                            )
                        }

                        Slider(
                            value = (book?.progress ?: 0).toFloat(),
                            onValueChange = { viewModel.updateProgressPercent(it.toInt()) },
                            valueRange = 0f..100f,
                            colors = SliderDefaults.colors(
                                thumbColor = Primary,
                                activeTrackColor = Primary,
                                inactiveTrackColor = readerSecondaryColor.copy(alpha = 0.3f)
                            )
                        )

                        // Chapter Previous / Next / Settings Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedButton(
                                onClick = { viewModel.previousChapter() },
                                enabled = currentChapterIndex > 0,
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.height(42.dp)
                            ) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "فصل قبلی", modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("فصل قبل", fontSize = 12.sp)
                            }

                            IconButton(
                                onClick = { viewModel.setShowReaderSettingsSheet(true) },
                                modifier = Modifier.size(44.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.FormatSize,
                                    contentDescription = "اندازه قلم و فونت",
                                    tint = Primary
                                )
                            }

                            Button(
                                onClick = { viewModel.nextChapter() },
                                enabled = currentChapterIndex < chapters.size - 1,
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Primary),
                                modifier = Modifier.height(42.dp)
                            ) {
                                Text("فصل بعد", fontSize = 12.sp, color = Color.White)
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "فصل بعدی", modifier = Modifier.size(16.dp))
                            }
                        }
                    }
                }
            }
        },
        containerColor = readerBackground,
        modifier = modifier.testTag("reader_screen")
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    viewModel.toggleReaderControls()
                }
        ) {
            // Main Reading Content Area
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 24.dp, vertical = 20.dp)
            ) {
                // Chapter Header
                if (currentChapter != null) {
                    Text(
                        text = currentChapter.title,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontFamily = readerFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = (settings.fontSizeSp + 4).sp,
                            color = if (settings.nightMode) NightAccent else Primary,
                            lineHeight = ((settings.fontSizeSp + 4) * settings.lineHeightMultiplier).sp
                        ),
                        modifier = Modifier.padding(bottom = 20.dp)
                    )

                    // Body Content paragraphs
                    val paragraphs = remember(currentChapter.content) {
                        currentChapter.content.split("\n\n").filter { it.isNotBlank() }
                    }

                    paragraphs.forEachIndexed { pIdx, paragraph ->
                        Text(
                            text = paragraph.trim(),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontFamily = readerFontFamily,
                                fontSize = settings.fontSizeSp.sp,
                                lineHeight = (settings.fontSizeSp * settings.lineHeightMultiplier).sp,
                                textAlign = if (settings.align == "justify") TextAlign.Justify else TextAlign.Right,
                                color = readerTextColor
                            ),
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        // Sample illustration if exists for chapter
                        if (pIdx == 1 && currentChapter.imageCaption != null) {
                            Surface(
                                shape = RoundedCornerShape(14.dp),
                                color = if (settings.nightMode) Color(0xFF2B2620) else SecondarySurface,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 12.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Image,
                                        contentDescription = null,
                                        tint = readerSecondaryColor,
                                        modifier = Modifier.size(56.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = currentChapter.imageCaption,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            fontFamily = readerFontFamily,
                                            fontSize = (settings.fontSizeSp - 2).sp,
                                            color = readerSecondaryColor,
                                            textAlign = TextAlign.Center
                                        )
                                    )
                                }
                            }
                        }
                    }

                    // Footnotes Section at chapter end
                    if (currentChapter.footnotes.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(24.dp))
                        HorizontalDivider(
                            color = readerSecondaryColor.copy(alpha = 0.3f),
                            modifier = Modifier.padding(vertical = 12.dp)
                        )

                        Text(
                            text = "پاورقی‌ها و یادداشت‌ها",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontFamily = readerFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = readerSecondaryColor
                            ),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        currentChapter.footnotes.forEach { fn ->
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = if (settings.nightMode) Color(0xFF26231C) else SecondarySurface,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clickable { viewModel.showFootnote(fn) }
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.Top,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = if (settings.nightMode) NightAccent else Primary
                                    ) {
                                        Text(
                                            text = fn.number,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }

                                    Text(
                                        text = fn.text,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            fontFamily = readerFontFamily,
                                            fontSize = (settings.fontSizeSp - 2).sp,
                                            lineHeight = 20.sp,
                                            color = readerTextColor
                                        ),
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(64.dp))
                }
            }

            // Warm Night Mode Tint Overlay if enabled
            if (settings.nightMode && settings.nightWarmth > 0) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Color(0xFFE58F3B).copy(alpha = (settings.nightWarmth / 100f) * 0.12f)
                        )
                )
            }
        }
    }

    // Footnote Sheet
    if (activeFootnote != null) {
        FootnoteBottomSheet(
            footnote = activeFootnote!!,
            onDismiss = { viewModel.dismissFootnote() }
        )
    }

    // Reader Settings Bottom Sheet
    if (showSettingsSheet) {
        ReadingSettingsSheet(
            settings = settings,
            onSettingsChanged = { viewModel.updateReaderSettings { it } },
            onDismiss = { viewModel.setShowReaderSettingsSheet(false) }
        )
    }
}
