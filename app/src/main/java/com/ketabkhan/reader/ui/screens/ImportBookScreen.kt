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
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ketabkhan.reader.ui.components.AppTopBar
import com.ketabkhan.reader.ui.navigation.Screen
import com.ketabkhan.reader.ui.theme.*
import com.ketabkhan.reader.ui.viewmodel.BookReaderViewModel

@Composable
fun ImportBookScreen(
    viewModel: BookReaderViewModel,
    modifier: Modifier = Modifier
) {
    val importState by viewModel.importState.collectAsState()

    Scaffold(
        topBar = {
            AppTopBar(
                title = "واردکردن کتاب",
                subtitle = "فایل‌های فرمت .bookapp",
                onBack = { viewModel.goBack() }
            )
        },
        containerColor = Background,
        modifier = modifier.testTag("import_book_screen")
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            when (importState) {
                "empty" -> {
                    // Drop zone for .bookapp
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = Surface,
                        border = BorderStroke(1.5.dp, Border),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.setImportState("selected") }
                            .testTag("import_bookapp_drop_zone")
                    ) {
                        Column(
                            modifier = Modifier.padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(64.dp)
                                    .background(SuccessBackground, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.FileDownload,
                                    contentDescription = null,
                                    tint = Primary,
                                    modifier = Modifier.size(32.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "انتخاب فایل کتاب (.bookapp)",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = TextPrimary
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "برای انتخاب بسته کتاب از حافظه دستگاه ضربه بزنید",
                                fontSize = 12.sp,
                                color = TextSecondary,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                else -> {
                    // Selected file preview / in-development info
                    Surface(
                        shape = RoundedCornerShape(18.dp),
                        color = Surface,
                        border = BorderStroke(1.dp, Border),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(14.dp)
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = Primary.copy(alpha = 0.1f),
                                    modifier = Modifier
                                        .width(56.dp)
                                        .height(76.dp)
                                ) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(Icons.Filled.MenuBook, contentDescription = null, tint = Primary)
                                    }
                                }

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "فایل بسته کتاب (.bookapp)",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        color = TextPrimary
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "دریافت مسیر از سیستم‌عامل با موفقیت انجام شد",
                                        fontSize = 12.sp,
                                        color = TextSecondary
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = SuccessBackground,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(Icons.Filled.Info, contentDescription = null, tint = Primary, modifier = Modifier.size(18.dp))
                                    Text(
                                        text = "موتور اعتبارسنجی و استخراج بسته .bookapp در فاز بعدی فعال خواهد شد.",
                                        fontSize = 12.sp,
                                        color = TextPrimary
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = { viewModel.performImportBook() },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Primary),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                            ) {
                                Text("پردازش و واردکردن کتاب", fontWeight = FontWeight.Bold, color = Color.White)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
