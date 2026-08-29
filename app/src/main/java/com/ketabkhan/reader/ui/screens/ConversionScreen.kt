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
    val stages by viewModel.conversionStages.collectAsState()

    Scaffold(
        topBar = {
            AppTopBar(
                title = "تبدیل فایل PDF",
                subtitle = "زیرساخت پردازش پس‌زمینه",
                onBack = { viewModel.cancelConversion() }
            )
        },
        bottomBar = {
            Surface(
                color = Background,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                OutlinedButton(
                    onClick = { viewModel.cancelConversion() },
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text("بازگشت به کتابخانه", color = Primary, fontWeight = FontWeight.SemiBold)
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

            // Notice Card
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
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = null,
                        tint = Primary,
                        modifier = Modifier.size(36.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "موتور تبدیل PDF در حال توسعه است",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = TextPrimary
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "زیرساخت معماری WorkManager آماده شده است. در فاز بعدی موتور بومی استخراج متن، شناسایی فصول و تولید بسته .bookapp فعال خواهد شد.",
                        fontSize = 12.sp,
                        color = TextSecondary,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        lineHeight = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Stages list card
            Text(
                text = "مراحل معماری تبدیل کتاب",
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
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .background(
                                        if (stage.isCurrent) SecondarySurface else Background,
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${index + 1}",
                                    fontSize = 12.sp,
                                    color = if (stage.isCurrent) Primary else TextSecondary,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Text(
                                text = stage.name,
                                fontSize = 13.sp,
                                fontWeight = if (stage.isCurrent) FontWeight.Bold else FontWeight.Normal,
                                color = if (stage.isCurrent) TextPrimary else TextSecondary,
                                modifier = Modifier.weight(1f)
                            )
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

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
