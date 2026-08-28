package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AppTopBar
import com.example.ui.components.ConfirmDeleteBottomSheet
import com.example.ui.navigation.Screen
import com.example.ui.theme.*
import com.example.ui.viewmodel.BookReaderViewModel

@Composable
fun BookOptionsScreen(
    viewModel: BookReaderViewModel,
    modifier: Modifier = Modifier
) {
    val book by viewModel.bookOptionsTarget.collectAsState()
    val showDeleteDialog by viewModel.showDeleteConfirmDialog.collectAsState()

    if (book == null) {
        LaunchedEffect(Unit) {
            viewModel.goBack()
        }
        return
    }

    val currentBook = book!!

    Scaffold(
        topBar = {
            AppTopBar(
                title = "گزینه‌های کتاب",
                onBack = { viewModel.goBack() }
            )
        },
        containerColor = Background,
        modifier = modifier.testTag("book_options_screen")
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // Book Header Preview Card
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Surface,
                border = BorderStroke(1.dp, Border),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Cover
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        shadowElevation = 4.dp,
                        modifier = Modifier
                            .width(64.dp)
                            .height(88.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.linearGradient(
                                        listOf(
                                            parseColorSafe(currentBook.coverColor),
                                            parseColorSafe(currentBook.coverAccent)
                                        )
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.MenuBook,
                                contentDescription = null,
                                tint = Color.White.copy(alpha = 0.85f),
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = currentBook.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = TextPrimary
                        )
                        Text(
                            text = currentBook.author,
                            fontSize = 13.sp,
                            color = TextSecondary
                        )
                        if (currentBook.translator.isNotBlank()) {
                            Text(
                                text = "مترجم: ${currentBook.translator}",
                                fontSize = 11.sp,
                                color = TextSecondary
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "${currentBook.chaptersCount} فصل",
                                fontSize = 11.sp,
                                color = Primary,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "·  پیشرفت: ${currentBook.progress}٪",
                                fontSize = 11.sp,
                                color = TextSecondary
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Actions Section
            Text(
                text = "عملیات و مدیریت",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = TextPrimary,
                modifier = Modifier.padding(bottom = 10.dp)
            )

            Surface(
                shape = RoundedCornerShape(18.dp),
                color = Surface,
                border = BorderStroke(1.dp, Border),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    OptionRowItem(
                        icon = Icons.Filled.PlayArrow,
                        title = "بازکردن و مطالعه کتاب",
                        subtitle = "ادامه مطالعه از آخرین بخش خوانده‌شده",
                        iconColor = Primary,
                        onClick = { viewModel.openBook(currentBook) }
                    )

                    HorizontalDivider(color = Border.copy(alpha = 0.5f), modifier = Modifier.padding(horizontal = 16.dp))

                    OptionRowItem(
                        icon = Icons.Outlined.UploadFile,
                        title = "ساخت و خروجی فایل کتاب (.bookapp)",
                        subtitle = "خروجی قابل انتقال و اشتراک‌گذاری برای دیگران",
                        iconColor = Primary,
                        onClick = { viewModel.navigateTo(Screen.ExportBook) }
                    )

                    HorizontalDivider(color = Border.copy(alpha = 0.5f), modifier = Modifier.padding(horizontal = 16.dp))

                    OptionRowItem(
                        icon = Icons.Outlined.Edit,
                        title = "ویرایش مشخصات و جلد",
                        subtitle = "تغییر عنوان، نویسنده، ناشر یا رنگ جلد",
                        iconColor = Primary,
                        onClick = { viewModel.navigateTo(Screen.BookDetails) }
                    )

                    HorizontalDivider(color = Border.copy(alpha = 0.5f), modifier = Modifier.padding(horizontal = 16.dp))

                    OptionRowItem(
                        icon = Icons.Outlined.AccountTree,
                        title = "بررسی ساختار فصل‌ها",
                        subtitle = "مشاهده و ویرایش سلسله‌مراتب فهرست و عناوین",
                        iconColor = Primary,
                        onClick = { viewModel.navigateTo(Screen.StructureReview) }
                    )

                    HorizontalDivider(color = Border.copy(alpha = 0.5f), modifier = Modifier.padding(horizontal = 16.dp))

                    OptionRowItem(
                        icon = Icons.Outlined.Delete,
                        title = "حذف از کتابخانه",
                        subtitle = "فایل PDF اصلی در حافظه بدون تغییر باقی می‌ماند",
                        iconColor = StatusError,
                        isDestructive = true,
                        onClick = { viewModel.setShowDeleteConfirmDialog(true) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))
        }
    }

    // Delete confirmation sheet
    if (showDeleteDialog) {
        ConfirmDeleteBottomSheet(
            bookTitle = currentBook.title,
            onConfirmDelete = { viewModel.confirmDeleteBook() },
            onDismiss = { viewModel.setShowDeleteConfirmDialog(false) }
        )
    }
}

@Composable
fun OptionRowItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    iconColor: Color,
    onClick: () -> Unit,
    isDestructive: Boolean = false,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    if (isDestructive) Color(0xFFFDECEA) else SecondarySurface,
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(20.dp)
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = if (isDestructive) StatusError else TextPrimary
            )
            Text(
                text = subtitle,
                fontSize = 11.sp,
                color = TextSecondary,
                lineHeight = 16.sp
            )
        }

        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            tint = TextSecondary,
            modifier = Modifier.size(16.dp)
        )
    }
}
