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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AppTopBar
import com.example.ui.theme.*
import com.example.ui.viewmodel.BookReaderViewModel

enum class BackupTab {
    BACKUP,
    RESTORE
}

@Composable
fun BackupRestoreScreen(
    viewModel: BookReaderViewModel,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(BackupTab.BACKUP) }

    val includeReadingPos by viewModel.backupIncludeReadingPos.collectAsState()
    val includeBookmarks by viewModel.backupIncludeBookmarks.collectAsState()
    val includeSettings by viewModel.backupIncludeSettings.collectAsState()
    val backupCompleted by viewModel.backupCompleted.collectAsState()
    val restoreState by viewModel.restoreState.collectAsState()

    Scaffold(
        topBar = {
            Column(modifier = Modifier.background(Background)) {
                AppTopBar(
                    title = "پشتیبان‌گیری و بازیابی",
                    onBack = { viewModel.goBack() }
                )

                TabRow(
                    selectedTabIndex = selectedTab.ordinal,
                    containerColor = Surface,
                    contentColor = Primary,
                    divider = { HorizontalDivider(color = Border) }
                ) {
                    Tab(
                        selected = selectedTab == BackupTab.BACKUP,
                        onClick = { selectedTab = BackupTab.BACKUP },
                        text = { Text("پشتیبان‌گیری", fontWeight = FontWeight.Bold, fontSize = 13.sp) }
                    )

                    Tab(
                        selected = selectedTab == BackupTab.RESTORE,
                        onClick = { selectedTab = BackupTab.RESTORE },
                        text = { Text("بازیابی اطلاعات", fontWeight = FontWeight.Bold, fontSize = 13.sp) }
                    )
                }
            }
        },
        containerColor = Background,
        modifier = modifier.testTag("backup_restore_screen")
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(14.dp))

            when (selectedTab) {
                BackupTab.BACKUP -> {
                    // Backup Tab Content
                    Surface(
                        shape = RoundedCornerShape(18.dp),
                        color = Surface,
                        border = BorderStroke(1.dp, Border),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "موارد مشمول پشتیبان‌گیری",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = TextPrimary,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )

                            BackupCheckboxRow(
                                title = "وضعیت و درصد مطالعه کتاب‌ها",
                                subtitle = "آخرین فصل و جایگاه خوانده‌شده",
                                checked = includeReadingPos,
                                onCheckedChange = { viewModel.toggleBackupIncludeReadingPos() }
                            )

                            HorizontalDivider(color = Border.copy(alpha = 0.5f), modifier = Modifier.padding(vertical = 8.dp))

                            BackupCheckboxRow(
                                title = "نشانک‌ها و علائم",
                                subtitle = "تمام نشانک‌های ثبت‌شده در تمام کتاب‌ها",
                                checked = includeBookmarks,
                                onCheckedChange = { viewModel.toggleBackupIncludeBookmarks() }
                            )

                            HorizontalDivider(color = Border.copy(alpha = 0.5f), modifier = Modifier.padding(vertical = 8.dp))

                            BackupCheckboxRow(
                                title = "تنظیمات مطالعه و ظاهر",
                                subtitle = "قلم‌ها، اندازه فونت و پوسته‌های انتخابی",
                                checked = includeSettings,
                                onCheckedChange = { viewModel.toggleBackupIncludeSettings() }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    if (backupCompleted) {
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = SuccessBackground,
                            border = BorderStroke(1.dp, Primary.copy(alpha = 0.3f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(Primary, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Filled.Check, contentDescription = null, tint = Color.White)
                                }

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "فایل پشتیبان ketabkhan_backup.bak ایجاد شد",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = Primary
                                    )
                                    Text(
                                        text = "حجم: ۴۸ کیلوبایت · امروز",
                                        fontSize = 11.sp,
                                        color = TextSecondary
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    Button(
                        onClick = { viewModel.performBackup() },
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Primary),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                    ) {
                        Icon(Icons.Outlined.Backup, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("ایجاد و ذخیره فایل پشتیبان", fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }

                BackupTab.RESTORE -> {
                    // Restore Tab Content
                    when (restoreState) {
                        "idle" -> {
                            Surface(
                                shape = RoundedCornerShape(18.dp),
                                color = Surface,
                                border = BorderStroke(1.dp, Border),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { viewModel.setRestoreState("preview") }
                            ) {
                                Column(
                                    modifier = Modifier.padding(28.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(56.dp)
                                            .background(SecondarySurface, CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(Icons.Outlined.SettingsBackupRestore, contentDescription = null, tint = Primary, modifier = Modifier.size(28.dp))
                                    }

                                    Spacer(modifier = Modifier.height(14.dp))

                                    Text(
                                        text = "انتخاب فایل پشتیبان (.bak)",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        color = TextPrimary
                                    )

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Text(
                                        text = "برای بازیابی اطلاعات از فایل پشتیبان قبلی ضربه بزنید",
                                        fontSize = 12.sp,
                                        color = TextSecondary
                                    )
                                }
                            }
                        }

                        "preview" -> {
                            Surface(
                                shape = RoundedCornerShape(18.dp),
                                color = Surface,
                                border = BorderStroke(1.dp, Border),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = "محتوای فایل پشتیبان",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        color = TextPrimary,
                                        modifier = Modifier.padding(bottom = 10.dp)
                                    )

                                    Text(text = "· اطلاعات ۵ کتاب در کتابخانه", fontSize = 13.sp, color = TextPrimary)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(text = "· ۳ نشانک و محل مطالعه", fontSize = 13.sp, color = TextPrimary)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(text = "· تنظیمات شخصی فونت و پوسته", fontSize = 13.sp, color = TextPrimary)

                                    Spacer(modifier = Modifier.height(16.dp))

                                    Button(
                                        onClick = { viewModel.performRestore() },
                                        shape = RoundedCornerShape(12.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = Primary),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(48.dp)
                                    ) {
                                        Text("تأیید و اجرای بازیابی", fontWeight = FontWeight.Bold, color = Color.White)
                                    }
                                }
                            }
                        }

                        "success" -> {
                            Surface(
                                shape = RoundedCornerShape(18.dp),
                                color = SuccessBackground,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.padding(24.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = StatusSuccess, modifier = Modifier.size(48.dp))
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text(
                                        text = "اطلاعات با موفقیت بازیابی شد",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = StatusSuccess
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Button(
                                        onClick = { viewModel.setRestoreState("idle") },
                                        shape = RoundedCornerShape(10.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = Primary)
                                    ) {
                                        Text("بازگشت", color = Color.White)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun BackupCheckboxRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = TextPrimary)
            Text(text = subtitle, fontSize = 11.sp, color = TextSecondary)
        }
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(checkedColor = Primary)
        )
    }
}
