package com.ketabkhan.reader.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ketabkhan.reader.ui.components.AppTopBar
import com.ketabkhan.reader.ui.navigation.Screen
import com.ketabkhan.reader.ui.theme.*
import com.ketabkhan.reader.ui.viewmodel.BookReaderViewModel

@Composable
fun StructureReviewScreen(
    viewModel: BookReaderViewModel,
    modifier: Modifier = Modifier
) {
    val reviewIssues by viewModel.reviewIssues.collectAsState()
    val detectedStructure by viewModel.detectedStructure.collectAsState()
    val unresolvedCount = reviewIssues.count { !it.isResolved }
    val chapterCount = detectedStructure?.chapters?.size ?: 0

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
                        onClick = {
                            viewModel.showSnackbar("پیش‌نمایش کتاب تا زمان استخراج واقعی ساختار فعال نیست")
                        },
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
                            text = "$chapterCount",
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
                val structure = detectedStructure
                if (structure == null) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "هنوز ساختاری از PDF استخراج نشده است",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = TextPrimary
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "استخراج متن و شناسایی فصل‌ها در حال توسعه است.",
                            fontSize = 13.sp,
                            color = TextSecondary
                        )
                    }
                } else if (structure.chapters.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "عنوان فصلی شناسایی نشد",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = TextPrimary
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "متن PDF استخراج شده است، اما عنوان فصل مشخصی در آن تشخیص داده نشد.",
                            fontSize = 13.sp,
                            color = TextSecondary,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        structure.chapters.forEachIndexed { index, chapter ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(14.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(28.dp)
                                        .background(SecondarySurface, androidx.compose.foundation.shape.CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "${index + 1}",
                                        fontSize = 12.sp,
                                        color = Primary,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Text(
                                    text = chapter.title,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = TextPrimary,
                                    modifier = Modifier.weight(1f)
                                )
                            }

                            if (index < structure.chapters.size - 1) {
                                HorizontalDivider(
                                    color = Border.copy(alpha = 0.5f),
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
