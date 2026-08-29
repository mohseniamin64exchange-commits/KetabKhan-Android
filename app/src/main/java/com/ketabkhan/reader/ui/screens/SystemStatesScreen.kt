package com.ketabkhan.reader.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
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
import com.ketabkhan.reader.ui.theme.*
import com.ketabkhan.reader.ui.viewmodel.BookReaderViewModel

@Composable
fun SystemStatesScreen(
    viewModel: BookReaderViewModel,
    modifier: Modifier = Modifier
) {
    var showPasswordDialog by remember { mutableStateOf(false) }
    var passwordInput by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "حالت‌های سیستم و پیام‌ها",
                subtitle = "کاتالوگ وضعیت‌ها و دیالوگ‌ها",
                onBack = { viewModel.goBack() }
            )
        },
        containerColor = Background,
        modifier = modifier.testTag("system_states_screen")
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // 1. Loading State
            StateCard(title = "۱. در حال بارگذاری و پردازش") {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CircularProgressIndicator(color = Primary, modifier = Modifier.size(28.dp))
                    Text(text = "در حال استخراج ساختار فایل PDF...", fontSize = 13.sp, color = TextPrimary)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 2. Storage Full Alert
            StateCard(title = "۲. هشدار تکمیل ظرفیت حافظه") {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFFDECEA),
                    border = BorderStroke(1.dp, Color(0xFFF5C6CB)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(Icons.Outlined.SdCardAlert, contentDescription = null, tint = StatusError)
                        Column {
                            Text(text = "حافظه دستگاه برای ذخیره کتاب کافی نیست", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = StatusError)
                            Text(text = "لطفاً فضای خالی حافظه را بررسی فرمایید.", fontSize = 11.sp, color = TextSecondary)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 3. Damaged PDF State
            StateCard(title = "۳. فایل PDF ناقص یا آسیب‌دیده") {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFFDECEA),
                    border = BorderStroke(1.dp, Color(0xFFF5C6CB)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(Icons.Outlined.BrokenImage, contentDescription = null, tint = StatusError)
                        Column {
                            Text(text = "فایل PDF ناقص است", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = StatusError)
                            Text(text = "امکان استخراج صفحات بدون خطا وجود ندارد.", fontSize = 11.sp, color = TextSecondary)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 4. Password Protected PDF
            StateCard(title = "۴. فایل دارای رمز عبور") {
                Button(
                    onClick = { showPasswordDialog = true },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary)
                ) {
                    Icon(Icons.Outlined.Lock, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("نمایش دیالوگ رمز عبور PDF", fontSize = 12.sp, color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 5. Interactive Dialogs & Snackbars
            StateCard(title = "۵. پیام‌ها و اطلاع‌رسانی‌ها (پیش‌نمایش رابط کاربری)") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = { viewModel.showSnackbar("این پیام صرفاً پیش‌نمایش گرافیکی رابط کاربری است") },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("نمونه اعلان راهنما", fontSize = 12.sp)
                    }

                    Button(
                        onClick = { viewModel.showSnackbar("اعلان وضعیت سیستم: بدون عملیات در پس‌زمینه") },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Primary),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("تست نمای پیام", fontSize = 12.sp, color = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    if (showPasswordDialog) {
        AlertDialog(
            onDismissRequest = { showPasswordDialog = false },
            title = { Text("فایل PDF رمزگذاری شده است (پیش‌نمایش)", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
            text = {
                Column {
                    Text("برای بازکردن و استخراج محتوا، رمز عبور فایل را وارد کنید (این قابلیت همراه موتور PDF فعال خواهد شد):", fontSize = 13.sp, color = TextSecondary)
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = passwordInput,
                        onValueChange = { passwordInput = it },
                        placeholder = { Text("رمز عبور فایل") },
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showPasswordDialog = false
                        viewModel.showSnackbar("بازگشایی فایل‌های دارای رمز در فاز پیاده‌سازی موتور PDF فعال می‌شود")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Primary)
                ) {
                    Text("تأیید و بستن", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showPasswordDialog = false }) {
                    Text("لغو")
                }
            }
        )
    }
}

@Composable
private fun StateCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Surface,
        border = BorderStroke(1.dp, Border),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = TextSecondary,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            content()
        }
    }
}
