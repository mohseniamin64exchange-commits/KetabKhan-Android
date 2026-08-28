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
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Chapter
import com.example.ui.components.AppTopBar
import com.example.ui.navigation.Screen
import com.example.ui.theme.*
import com.example.ui.viewmodel.BookReaderViewModel

val SAMPLE_STRUCTURE_CHAPTERS = listOf(
    Chapter(id = "1", title = "فصل اول: آشنایی با موضوع و مفاهیم", level = 0, confident = true, content = ""),
    Chapter(id = "2", title = "۱ـ۱ پیشینه تاریخی و اجتماعی", level = 1, confident = true, content = ""),
    Chapter(id = "3", title = "۱ـ۲ کاربردها (عنوان نامشخص)", level = 1, confident = false, content = ""),
    Chapter(id = "4", title = "فصل دوم: زمینه‌های تاریخی و جریان‌ها", level = 0, confident = true, content = ""),
    Chapter(id = "5", title = "۲ـ۱ تأثیر جنگ بر بافت شهری", level = 1, confident = true, content = ""),
    Chapter(id = "6", title = "۲ـ۲ تحولات ادبی دوره پهلوی", level = 1, confident = true, content = ""),
    Chapter(id = "7", title = "فصل سوم: شخصیت‌پردازی و تحلیل نمادها", level = 0, confident = false, content = ""),
    Chapter(id = "8", title = "فصل چهارم: بازتاب جامعه در رمان", level = 0, confident = true, content = "")
)

@Composable
fun StructureReviewScreen(
    viewModel: BookReaderViewModel,
    modifier: Modifier = Modifier
) {
    val reviewIssues by viewModel.reviewIssues.collectAsState()
    val unresolvedCount = reviewIssues.count { !it.isResolved }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "بررسی ساختار فصل‌ها",
                onBack = { viewModel.goBack() }
            )
        },
        bottomBar = {
            Surface(
                color = Background,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    if (unresolvedCount > 0) {
                        OutlinedButton(
                            onClick = { viewModel.navigateTo(Screen.IssueReview) },
                            shape = RoundedCornerShape(14.dp),
                            border = BorderStroke(1.5.dp, StatusWarning),
                            modifier = Modifier
                                .weight(1f)
                                .height(52.dp)
                                .testTag("resolve_issues_button")
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Warning,
                                contentDescription = null,
                                tint = StatusWarning,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "اصلاح موارد ($unresolvedCount)",
                                color = StatusWarning,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }

                    Button(
                        onClick = { viewModel.navigateTo(Screen.FinalPreview) },
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Primary),
                        modifier = Modifier
                            .weight(1.2f)
                            .height(52.dp)
                            .testTag("preview_book_button")
                    ) {
                        Text(
                            text = "پیش‌نمایش کتاب",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    }
                }
            }
        },
        containerColor = Background,
        modifier = modifier.testTag("structure_review_screen")
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // Statistics Summary Card
            Surface(
                shape = RoundedCornerShape(18.dp),
                color = Surface,
                border = BorderStroke(1.dp, Border),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "۸",
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            color = Primary
                        )
                        Text(
                            text = "فصل شناسایی‌شده",
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                    }

                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(36.dp)
                            .background(Border)
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "$unresolvedCount",
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            color = if (unresolvedCount > 0) StatusWarning else StatusSuccess
                        )
                        Text(
                            text = "مورد نیازمند بررسی",
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Hierarchy Chapter List
            Text(
                text = "سلسله‌مراتب و سرفصل‌ها",
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
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    SAMPLE_STRUCTURE_CHAPTERS.forEachIndexed { index, ch ->
                        val isIndented = ch.level > 0

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = if (isIndented) 32.dp else 16.dp,
                                    end = 16.dp,
                                    top = 10.dp,
                                    bottom = 10.dp
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            if (isIndented) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .background(Primary, CircleShape)
                                )
                            }

                            Text(
                                text = ch.title,
                                fontSize = if (isIndented) 13.sp else 14.sp,
                                fontWeight = if (isIndented) FontWeight.Normal else FontWeight.Bold,
                                color = TextPrimary,
                                modifier = Modifier.weight(1f)
                            )

                            if (ch.confident) {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = SuccessBackground
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Check,
                                            contentDescription = null,
                                            tint = StatusSuccess,
                                            modifier = Modifier.size(12.dp)
                                        )
                                        Text(
                                            text = "مطمئن",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = StatusSuccess
                                        )
                                    }
                                }
                            } else {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = Color(0xFFFEF5E7),
                                    modifier = Modifier.clickable { viewModel.navigateTo(Screen.IssueReview) }
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.Warning,
                                            contentDescription = null,
                                            tint = StatusWarning,
                                            modifier = Modifier.size(12.dp)
                                        )
                                        Text(
                                            text = "نیاز به بررسی",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = StatusWarning
                                        )
                                    }
                                }
                            }
                        }

                        if (index < SAMPLE_STRUCTURE_CHAPTERS.size - 1) {
                            HorizontalDivider(
                                color = Border.copy(alpha = 0.5f),
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
