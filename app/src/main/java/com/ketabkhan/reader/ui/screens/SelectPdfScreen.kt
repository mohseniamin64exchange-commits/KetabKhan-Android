package com.ketabkhan.reader.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ketabkhan.reader.ui.components.AppTopBar
import com.ketabkhan.reader.ui.theme.*
import com.ketabkhan.reader.ui.viewmodel.BookReaderViewModel

@Composable
fun SelectPdfScreen(
    viewModel: BookReaderViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val selectedPdfInfo by viewModel.selectedPdfInfo.collectAsState()

    val pdfLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        viewModel.onPdfUriSelected(context, uri)
    }

    val hasSelectedFile = selectedPdfInfo != null

    Scaffold(
        topBar = {
            AppTopBar(
                title = "انتخاب فایل PDF",
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
                Button(
                    onClick = {
                        if (hasSelectedFile) {
                            viewModel.startConversion()
                        }
                    },
                    enabled = hasSelectedFile,
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("start_conversion_button")
                ) {
                    Text(
                        text = "شروع تبدیل و استخراج ساختار",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }
        },
        containerColor = Background,
        modifier = modifier.testTag("select_pdf_screen")
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // File selection drop box
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = if (hasSelectedFile) Surface else SecondarySurface,
                border = BorderStroke(1.5.dp, if (hasSelectedFile) Primary else Border),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        pdfLauncher.launch(arrayOf("application/pdf"))
                    }
                    .testTag("pdf_file_drop_zone")
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(SuccessBackground, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PictureAsPdf,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = if (hasSelectedFile) "فایل PDF انتخاب شد" else "برای انتخاب فایل PDF ضربه بزنید",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = TextPrimary
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "پردازش و تبدیل ساختاریافته به صورت کاملاً محلی و آفلاین",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Selected file preview card
            selectedPdfInfo?.let { pdfInfo ->
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Surface,
                    border = BorderStroke(1.dp, Border),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .background(SuccessBackground, RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Description,
                                contentDescription = null,
                                tint = Primary,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = pdfInfo.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = TextPrimary,
                                maxLines = 1
                            )
                            Text(
                                text = "${pdfInfo.formattedSize} · ${pdfInfo.mimeType}",
                                fontSize = 12.sp,
                                color = TextSecondary
                            )
                        }

                        IconButton(
                            onClick = { viewModel.clearSelectedPdf() },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "حذف فایل",
                                tint = TextSecondary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            // PDF Types Info Cards
            Text(
                text = "وضعیت روش‌های پردازش PDF",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = TextPrimary,
                modifier = Modifier.padding(bottom = 10.dp)
            )

            PdfTypeInfoCard(
                icon = Icons.Outlined.TextFields,
                title = "PDF متنی (Text-based)",
                description = "استخراج متن و تشخیص ساختار در مرحله بعد توسعه داده خواهد شد."
            )

            Spacer(modifier = Modifier.height(8.dp))

            PdfTypeInfoCard(
                icon = Icons.Outlined.DocumentScanner,
                title = "PDF اسکن‌شده (OCR)",
                description = "قابلیت OCR در نسخه فعلی فعال نیست و در حال توسعه است."
            )

            Spacer(modifier = Modifier.height(8.dp))

            PdfTypeInfoCard(
                icon = Icons.Outlined.Layers,
                title = "PDF ترکیبی (Hybrid)",
                description = "پردازش تصاویر، جدول‌ها و اجزای ترکیبی در نسخه فعلی فعال نیست."
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun PdfTypeInfoCard(
    icon: ImageVector,
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = Surface,
        border = BorderStroke(1.dp, Border),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(SecondarySurface, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier.size(20.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = TextPrimary
                )
                Text(
                    text = description,
                    fontSize = 12.sp,
                    color = TextSecondary,
                    lineHeight = 18.sp
                )
            }
        }
    }
}
