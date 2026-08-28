package com.example.ui.screens

import androidx.compose.animation.*
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
import com.example.ui.components.AppTopBar
import com.example.ui.navigation.Screen
import com.example.ui.theme.*
import com.example.ui.viewmodel.BookReaderViewModel

@Composable
fun ImportBookScreen(
    viewModel: BookReaderViewModel,
    modifier: Modifier = Modifier
) {
    val importState by viewModel.importState.collectAsState()
    var showDuplicateDialog by remember { mutableStateOf(false) }

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
                                text = "برای انتخاب فایل آماده کتاب از حافظه دستگاه ضربه بزنید",
                                fontSize = 12.sp,
                                color = TextSecondary,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Demo test buttons for duplicate and corrupted states
                    Text(
                        text = "آزمایش حالت‌های ورود فایل",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextSecondary,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                viewModel.setImportState("selected")
                                showDuplicateDialog = true
                            },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("کتاب تکراری", fontSize = 12.sp)
                        }

                        OutlinedButton(
                            onClick = { viewModel.setImportState("invalid") },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("فایل نامعتبر", fontSize = 12.sp, color = StatusError)
                        }
                    }
                }

                "selected" -> {
                    // Selected file preview card
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
                                    shadowElevation = 3.dp,
                                    modifier = Modifier
                                        .width(56.dp)
                                        .height(76.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(
                                                Brush.linearGradient(listOf(Color(0xFF5A3D3D), Color(0xFF8F6B6B)))
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(Icons.Filled.MenuBook, contentDescription = null, tint = Color.White)
                                    }
                                }

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "صد سال تنهایی",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = TextPrimary
                                    )
                                    Text(
                                        text = "گابریل گارسیا مارکز · مترجم: محمد مجلسی",
                                        fontSize = 12.sp,
                                        color = TextSecondary
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "۲۰ فصل · حجم: ۲.۱ مگابایت",
                                        fontSize = 11.sp,
                                        color = Primary
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
                                    modifier = Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = StatusSuccess, modifier = Modifier.size(18.dp))
                                    Text(
                                        text = "ساختار فایل سالم و مورد تأیید است.",
                                        fontSize = 12.sp,
                                        color = StatusSuccess,
                                        fontWeight = FontWeight.SemiBold
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
                                Text("تأیید و افزودن به کتابخانه", fontWeight = FontWeight.Bold, color = Color.White)
                            }
                        }
                    }
                }

                "invalid" -> {
                    // Invalid file error card
                    Surface(
                        shape = RoundedCornerShape(18.dp),
                        color = Color(0xFFFDECEA),
                        border = BorderStroke(1.dp, Color(0xFFF5C6CB)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(Icons.Filled.ErrorOutline, contentDescription = null, tint = StatusError, modifier = Modifier.size(44.dp))
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "فایل انتخاب‌شده نامعتبر یا آسیب‌دیده است",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = StatusError
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "ساختار بسته .bookapp ناقص است یا در هنگام دانلود دچار مشکل شده است.",
                                fontSize = 12.sp,
                                color = TextSecondary,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            OutlinedButton(
                                onClick = { viewModel.setImportState("empty") },
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("انتخاب فایل دیگر")
                            }
                        }
                    }
                }

                "success" -> {
                    // Success card
                    Surface(
                        shape = RoundedCornerShape(18.dp),
                        color = SuccessBackground,
                        border = BorderStroke(1.dp, Primary.copy(alpha = 0.3f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .background(Primary, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Filled.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(32.dp))
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            Text(
                                text = "کتاب با موفقیت به کتابخانه اضافه شد",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Primary
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            Button(
                                onClick = {
                                    viewModel.setImportState("empty")
                                    viewModel.navigateTo(Screen.Library)
                                },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Primary),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                            ) {
                                Text("مشاهده در کتابخانه", fontWeight = FontWeight.Bold, color = Color.White)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    // Duplicate Book Conflict Dialog
    if (showDuplicateDialog) {
        AlertDialog(
            onDismissRequest = { showDuplicateDialog = false },
            title = { Text("کتاب از قبل در کتابخانه موجود است", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
            text = {
                Text(
                    text = "کتاب «صد سال تنهایی» از قبل در کتابخانه شما وجود دارد. مایلید نسخه جدید جایگزین شود یا هر دو نگهداری شوند؟",
                    fontSize = 13.sp,
                    lineHeight = 20.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDuplicateDialog = false
                        viewModel.performImportBook()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Primary)
                ) {
                    Text("جایگزینی نسخه فعلی", color = Color.White, fontSize = 12.sp)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDuplicateDialog = false }) {
                    Text("نگهداری هر دو نسخه", fontSize = 12.sp)
                }
            }
        )
    }
}
