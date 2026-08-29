package com.ketabkhan.reader.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ketabkhan.reader.data.model.Footnote
import com.ketabkhan.reader.ui.components.AppTopBar
import com.ketabkhan.reader.ui.components.FootnoteBottomSheet
import com.ketabkhan.reader.ui.navigation.Screen
import com.ketabkhan.reader.ui.theme.*
import com.ketabkhan.reader.ui.viewmodel.BookReaderViewModel

@Composable
fun FinalPreviewScreen(
    viewModel: BookReaderViewModel,
    modifier: Modifier = Modifier
) {
    val draftTitle by viewModel.draftTitle.collectAsState()
    val reviewIssues by viewModel.reviewIssues.collectAsState()
    val activeFootnote by viewModel.activeFootnote.collectAsState()

    val unresolvedCount = reviewIssues.count { !it.isResolved }
    val sampleFootnote = Footnote(id = "fn1", number = "۱", text = "برای مطالعه بیشتر پیرامون زمینه‌های تاریخی به فصل سوم مراجعه فرمایید.")

    Scaffold(
        topBar = {
            AppTopBar(
                title = "پیش‌نمایش کتاب",
                subtitle = draftTitle,
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
                    OutlinedButton(
                        onClick = { viewModel.navigateTo(Screen.StructureReview) },
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                    ) {
                        Text("بازگشت و اصلاح", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    }

                    Button(
                        onClick = { viewModel.finalizeAndAddBookToLibrary() },
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Primary),
                        modifier = Modifier
                            .weight(1.3f)
                            .height(52.dp)
                            .testTag("finalize_add_to_library_button")
                    ) {
                        Text("تأیید و افزودن به کتابخانه", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White)
                    }
                }
            }
        },
        containerColor = Background,
        modifier = modifier.testTag("final_preview_screen")
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // Warning banner if issues remain
            if (unresolvedCount > 0) {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = Color(0xFFFEF5E7),
                    border = BorderStroke(1.dp, Color(0xFFF9E4B7)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Warning,
                            contentDescription = null,
                            tint = StatusWarning,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "$unresolvedCount مورد نیازمند بازبینی هنوز باقی مانده است. می‌توانید بعداً نیز ساختار را ویرایش کنید.",
                            fontSize = 12.sp,
                            color = TextPrimary,
                            lineHeight = 18.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
            }

            // Reader simulated book paper view
            Surface(
                shape = RoundedCornerShape(18.dp),
                color = Surface,
                border = BorderStroke(1.dp, Border),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "فصل اول: آشنایی با موضوع",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Primary
                        ),
                        modifier = Modifier.padding(bottom = 14.dp)
                    )

                    Text(
                        text = "در آغاز هر داستانی، دنیایی پدید می‌آید که خواننده را به درون خود می‌کشد. این دنیا نه تنها از کلمات ساخته شده، بلکه از احساسی است که میان سطرها جاری است.",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            lineHeight = 28.sp,
                            color = TextPrimary
                        ),
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    // Embedded Image Sample Card
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = SecondarySurface,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Image,
                                contentDescription = null,
                                tint = TextSecondary,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "تصویر ۱-۱: نمودار ساختار روایی اثر",
                                fontSize = 12.sp,
                                color = TextSecondary,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "تحلیل عمیق این رخدادها نشان می‌دهد که چگونه شخصیت در کشاکش میان سنت و نوگرایی، راهی به سوی خودآگاهی می‌جوید",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            lineHeight = 28.sp,
                            color = TextPrimary
                        )
                    )

                    // Clickable Footnote badge
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .clickable { viewModel.showFootnote(sampleFootnote) }
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.padding(end = 6.dp)
                        ) {
                            Text(
                                text = "پاورقی [۱]",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Primary,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                        Text(
                            text = "برای مشاهده متن پاورقی کلیک کنید",
                            fontSize = 11.sp,
                            color = Primary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    // Footnote Sheet
    if (activeFootnote != null) {
        FootnoteBottomSheet(
            footnote = activeFootnote!!,
            onDismiss = { viewModel.dismissFootnote() }
        )
    }
}
