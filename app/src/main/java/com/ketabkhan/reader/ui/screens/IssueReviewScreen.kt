package com.ketabkhan.reader.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Edit
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
import com.ketabkhan.reader.ui.components.AppTopBar
import com.ketabkhan.reader.ui.navigation.Screen
import com.ketabkhan.reader.ui.theme.*
import com.ketabkhan.reader.ui.viewmodel.BookReaderViewModel

@Composable
fun IssueReviewScreen(
    viewModel: BookReaderViewModel,
    modifier: Modifier = Modifier
) {
    val reviewIssues by viewModel.reviewIssues.collectAsState()
    val currentIndex by viewModel.currentIssueIndex.collectAsState()
    val issueTab by viewModel.issueTab.collectAsState()

    val currentIssue = reviewIssues.getOrNull(currentIndex)

    Scaffold(
        topBar = {
            AppTopBar(
                title = "اصلاح موارد مشکوک",
                subtitle = "مورد ${currentIndex + 1} از ${reviewIssues.size}",
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
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = {
                            if (currentIndex > 0) viewModel.setIssueIndex(currentIndex - 1)
                        },
                        enabled = currentIndex > 0,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.height(48.dp)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "قبلی")
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("قبلی", fontSize = 13.sp)
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        reviewIssues.forEachIndexed { idx, issue ->
                            Box(
                                modifier = Modifier
                                    .size(if (idx == currentIndex) 10.dp else 8.dp)
                                    .background(
                                        if (idx == currentIndex) Primary else if (issue.isResolved) StatusSuccess else Border,
                                        CircleShape
                                    )
                            )
                        }
                    }

                    Button(
                        onClick = {
                            if (currentIndex < reviewIssues.size - 1) {
                                viewModel.setIssueIndex(currentIndex + 1)
                            } else {
                                viewModel.navigateTo(Screen.StructureReview)
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Primary),
                        modifier = Modifier.height(48.dp)
                    ) {
                        Text(if (currentIndex < reviewIssues.size - 1) "بعدی" else "پایان بازبینی", fontSize = 13.sp, color = Color.White)
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "بعدی")
                    }
                }
            }
        },
        containerColor = Background,
        modifier = modifier.testTag("issue_review_screen")
    ) { innerPadding ->
        if (currentIssue != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                // Issue Title Card
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFFEF5E7),
                    border = BorderStroke(1.dp, Color(0xFFF9E4B7)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Warning,
                            contentDescription = null,
                            tint = StatusWarning,
                            modifier = Modifier.size(24.dp)
                        )
                        Column {
                            Text(
                                text = currentIssue.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = TextPrimary
                            )
                            Text(
                                text = currentIssue.desc,
                                fontSize = 12.sp,
                                color = TextSecondary,
                                lineHeight = 18.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Comparison Tabs
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = if (issueTab == "original") Primary else Surface,
                        border = BorderStroke(1.dp, if (issueTab == "original") Primary else Border),
                        modifier = Modifier
                            .weight(1f)
                            .height(42.dp)
                            .clickable { viewModel.setIssueTab("original") }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "PDF اصلی",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (issueTab == "original") Color.White else TextPrimary
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = if (issueTab == "converted") Primary else Surface,
                        border = BorderStroke(1.dp, if (issueTab == "converted") Primary else Border),
                        modifier = Modifier
                            .weight(1f)
                            .height(42.dp)
                            .clickable { viewModel.setIssueTab("converted") }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "نسخه تبدیل‌شده",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (issueTab == "converted") Color.White else TextPrimary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Text Content Preview Box
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Surface,
                    border = BorderStroke(1.dp, Border),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = if (issueTab == "original") currentIssue.original else currentIssue.converted,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                lineHeight = 24.sp,
                                color = TextPrimary
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Actions: Confirm, Edit, Ignore
                Text(
                    text = "اقدام پیشنهادی",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = TextSecondary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { viewModel.resolveCurrentIssue("confirm") },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Primary),
                        modifier = Modifier
                            .weight(1f)
                            .height(46.dp)
                    ) {
                        Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("تأیید ساختار", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }

                    OutlinedButton(
                        onClick = { viewModel.resolveCurrentIssue("edit") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(46.dp)
                    ) {
                        Icon(Icons.Outlined.Edit, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("ویرایش دستی", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    TextButton(
                        onClick = { viewModel.resolveCurrentIssue("ignore") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.height(46.dp)
                    ) {
                        Text("نادیده‌گرفتن", fontSize = 12.sp, color = TextSecondary)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
