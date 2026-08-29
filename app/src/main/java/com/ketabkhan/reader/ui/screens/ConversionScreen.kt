package com.ketabkhan.reader.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
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
fun ConversionScreen(
    viewModel: BookReaderViewModel,
    modifier: Modifier = Modifier
) {
    val progress by viewModel.conversionProgress.collectAsState()
    val stages by viewModel.conversionStages.collectAsState()
    val isCompleted by viewModel.conversionCompleted.collectAsState()

    Scaffold(
        topBar = {
            AppTopBar(
                title = if (isCompleted) "تبدیل تکمیل شد" else "در حال تبدیل کتاب",
                onBack = {
                    if (isCompleted) {
                        viewModel.navigateTo(Screen.BookDetails)
                    } else {
                        viewModel.cancelConversion()
                    }
                }
            )
        },
        bottomBar = {
            Surface(
                color = Background,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                if (isCompleted) {
                    Button(
                        onClick = { viewModel.navigateTo(Screen.BookDetails) },
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Primary),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("continue_to_details_button")
                    ) {
                        Text("ادامه به بررسی مشخصات کتاب", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color.White)
                    }
                } else {
                    OutlinedButton(
                        onClick = { viewModel.cancelConversion() },
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Text("لغو تبدیل", color = StatusError, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        },
        containerColor = Background,
        modifier = modifier.testTag("conversion_screen")
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // Large Progress Card
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Surface,
                border = BorderStroke(1.dp, Border),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isCompleted) "آماده‌سازی نهایی انجام شد" else "پیشرفت پردازش هوشمند",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = TextPrimary
                        )
                        Text(
                            text = "${progress.toInt()}٪",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Primary
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    LinearProgressIndicator(
                        progress = { progress / 100f },
                        color = Primary,
                        trackColor = SecondarySurface,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(CircleShape)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Stages list card
            Text(
                text = "مراحل پردازش و بهینه‌سازی",
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
                Column(
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    stages.forEachIndexed { index, stage ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            // Stage Status Icon
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .background(
                                        when {
                                            stage.isComplete -> Primary
                                            stage.isCurrent -> SecondarySurface
                                            else -> Background
                                        },
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                when {
                                    stage.isComplete -> {
                                        Icon(
                                            imageVector = Icons.Filled.Check,
                                            contentDescription = "انجام شد",
                                            tint = Color.White,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                    stage.isCurrent -> {
                                        CircularProgressIndicator(
                                            color = Primary,
                                            strokeWidth = 2.dp,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                    else -> {
                                        Text(
                                            text = "${index + 1}",
                                            fontSize = 12.sp,
                                            color = TextSecondary
                                        )
                                    }
                                }
                            }

                            Text(
                                text = stage.name,
                                fontSize = 14.sp,
                                fontWeight = if (stage.isCurrent || stage.isComplete) FontWeight.Bold else FontWeight.Normal,
                                color = if (stage.isCurrent || stage.isComplete) TextPrimary else TextSecondary,
                                modifier = Modifier.weight(1f)
                            )

                            if (stage.isComplete) {
                                Text(
                                    text = "تکمیل شد",
                                    fontSize = 11.sp,
                                    color = StatusSuccess,
                                    fontWeight = FontWeight.SemiBold
                                )
                            } else if (stage.isCurrent) {
                                Text(
                                    text = "در حال پردازش...",
                                    fontSize = 11.sp,
                                    color = StatusWarning,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        if (index < stages.size - 1) {
                            HorizontalDivider(
                                color = Border.copy(alpha = 0.5f),
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Background note card
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = SuccessBackground,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = null,
                        tint = Primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "پردازش کتاب‌ها کاملاً محلی انجام می‌شود. در صورت خروج از این صفحه پردازش در پس‌زمینه ادامه خواهد یافت.",
                        fontSize = 12.sp,
                        color = Primary,
                        lineHeight = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
