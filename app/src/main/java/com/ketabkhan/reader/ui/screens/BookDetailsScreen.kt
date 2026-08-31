package com.ketabkhan.reader.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ketabkhan.reader.ui.components.AppTopBar
import com.ketabkhan.reader.ui.theme.*
import com.ketabkhan.reader.ui.viewmodel.BookReaderViewModel

val COVER_PRESETS = listOf(
    Pair("#3D5A47", "#6B8F71"),
    Pair("#5C3D2E", "#8B6350"),
    Pair("#2E4A5C", "#50788B"),
    Pair("#4A3D5C", "#75608B"),
    Pair("#5A3D3D", "#8F6B6B"),
    Pair("#2D4B4B", "#487575")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailsScreen(
    viewModel: BookReaderViewModel,
    modifier: Modifier = Modifier
) {
    val draftTitle by viewModel.draftTitle.collectAsState()
    val draftAuthor by viewModel.draftAuthor.collectAsState()
    val draftTranslator by viewModel.draftTranslator.collectAsState()
    val draftPublisher by viewModel.draftPublisher.collectAsState()
    val draftPublishYear by viewModel.draftPublishYear.collectAsState()
    val draftLanguage by viewModel.draftLanguage.collectAsState()
    val draftDirection by viewModel.draftDirection.collectAsState()
    val draftCoverColor by viewModel.draftCoverColor.collectAsState()
    val draftCoverAccent by viewModel.draftCoverAccent.collectAsState()

    Scaffold(
        topBar = {
            AppTopBar(
                title = "مشخصات و جلد کتاب",
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
                        if (draftTitle.isNotBlank() && draftAuthor.isNotBlank()) {
                            viewModel.startConversion()
                        } else {
                            viewModel.showSnackbar("لطفاً عنوان و نویسنده کتاب را وارد کنید")
                        }
                    },
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("continue_to_structure_button")
                ) {
                    Text("استخراج و بررسی ساختار", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color.White)
                }
            }
        },
        containerColor = Background,
        modifier = modifier.testTag("book_details_screen")
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // Cover Preview & Color Selection Card
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Surface,
                border = BorderStroke(1.dp, Border),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Preview
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        shadowElevation = 6.dp,
                        modifier = Modifier
                            .width(110.dp)
                            .height(150.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(
                                            parseColorSafe(draftCoverColor),
                                            parseColorSafe(draftCoverAccent)
                                        )
                                    )
                                )
                                .padding(12.dp),
                            contentAlignment = Alignment.BottomStart
                        ) {
                            Column {
                                Text(
                                    text = draftTitle.ifBlank { "عنوان کتاب" },
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = Color.White,
                                    maxLines = 2
                                )
                                Text(
                                    text = draftAuthor.ifBlank { "نویسنده" },
                                    fontSize = 10.sp,
                                    color = Color.White.copy(alpha = 0.8f),
                                    maxLines = 1
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "رنگ‌بندی جلد کتاب",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                        color = TextSecondary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Preset Color Circles
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        COVER_PRESETS.forEach { (color, accent) ->
                            val isSelected = draftCoverColor == color
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(
                                        Brush.linearGradient(
                                            listOf(parseColorSafe(color), parseColorSafe(accent))
                                        )
                                    )
                                    .clickable { viewModel.setDraftCover(color, accent) }
                                    .then(
                                        if (isSelected) Modifier.border(2.5.dp, Primary, CircleShape) else Modifier
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isSelected) {
                                    Icon(
                                        imageVector = Icons.Filled.Check,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Metadata Form Fields
            Text(
                text = "اطلاعات کتاب‌شناختی",
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
                Column(modifier = Modifier.padding(16.dp)) {
                    // Title
                    OutlinedTextField(
                        value = draftTitle,
                        onValueChange = {
                            viewModel.setDraftMetadata(it, draftAuthor, draftTranslator, draftPublisher, draftPublishYear, draftLanguage, draftDirection)
                        },
                        label = { Text("عنوان کتاب *", fontSize = 13.sp) },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Author
                    OutlinedTextField(
                        value = draftAuthor,
                        onValueChange = {
                            viewModel.setDraftMetadata(draftTitle, it, draftTranslator, draftPublisher, draftPublishYear, draftLanguage, draftDirection)
                        },
                        label = { Text("نام نویسنده / پدیدآور *", fontSize = 13.sp) },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Translator
                    OutlinedTextField(
                        value = draftTranslator,
                        onValueChange = {
                            viewModel.setDraftMetadata(draftTitle, draftAuthor, it, draftPublisher, draftPublishYear, draftLanguage, draftDirection)
                        },
                        label = { Text("مترجم (اختیاری)", fontSize = 13.sp) },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Publisher & Year
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedTextField(
                            value = draftPublisher,
                            onValueChange = {
                                viewModel.setDraftMetadata(draftTitle, draftAuthor, draftTranslator, it, draftPublishYear, draftLanguage, draftDirection)
                            },
                            label = { Text("ناشر", fontSize = 13.sp) },
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1.3f)
                        )

                        OutlinedTextField(
                            value = draftPublishYear,
                            onValueChange = {
                                viewModel.setDraftMetadata(draftTitle, draftAuthor, draftTranslator, draftPublisher, it, draftLanguage, draftDirection)
                            },
                            label = { Text("سال نشر", fontSize = 13.sp) },
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Text Direction (RTL vs LTR)
                    Text(
                        text = "جهت متن و صفحه",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextSecondary,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (draftDirection == "RTL") Primary else SecondarySurface,
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                                .clickable {
                                    viewModel.setDraftMetadata(draftTitle, draftAuthor, draftTranslator, draftPublisher, draftPublishYear, draftLanguage, "RTL")
                                }
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "راست‌به‌چپ (فارسی / RTL)",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (draftDirection == "RTL") Color.White else TextPrimary
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (draftDirection == "LTR") Primary else SecondarySurface,
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                                .clickable {
                                    viewModel.setDraftMetadata(draftTitle, draftAuthor, draftTranslator, draftPublisher, draftPublishYear, draftLanguage, "LTR")
                                }
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "چپ‌به‌راست (LTR)",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (draftDirection == "LTR") Color.White else TextPrimary
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
