package com.example.ui.components

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
import androidx.compose.material.icons.filled.FormatAlignJustify
import androidx.compose.material.icons.filled.FormatAlignRight
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ReaderSettings
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadingSettingsSheet(
    settings: ReaderSettings,
    onSettingsChanged: (ReaderSettings) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Surface,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        dragHandle = {
            Surface(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .width(40.dp)
                    .height(4.dp),
                shape = RoundedCornerShape(2.dp),
                color = MaterialTheme.colorScheme.outline
            ) {}
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 32.dp)
                .verticalScroll(rememberScrollState())
                .testTag("reading_settings_sheet")
        ) {
            Text(
                text = "تنظیمات مطالعه",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = TextPrimary
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Themes Selection (Light, Cream, Dark)
            Text(
                text = "پوسته صفحه",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = TextSecondary
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Light Theme
                ThemeCard(
                    title = "روشن",
                    bgColor = Color(0xFFFAF6F0),
                    textColor = Color(0xFF1A1A18),
                    borderColor = Border,
                    isSelected = settings.theme == "light" && !settings.nightMode,
                    onClick = { onSettingsChanged(settings.copy(theme = "light", nightMode = false)) },
                    modifier = Modifier.weight(1f)
                )

                // Cream Theme
                ThemeCard(
                    title = "سپیا / کرم",
                    bgColor = Color(0xFFF2E8D5),
                    textColor = Color(0xFF3A2E22),
                    borderColor = Color(0xFFE2D6C0),
                    isSelected = settings.theme == "cream" && !settings.nightMode,
                    onClick = { onSettingsChanged(settings.copy(theme = "cream", nightMode = false)) },
                    modifier = Modifier.weight(1f)
                )

                // Dark Theme
                ThemeCard(
                    title = "تاریک",
                    bgColor = Color(0xFF1E2020),
                    textColor = Color(0xFFD8D4CC),
                    borderColor = Color(0xFF3E4444),
                    isSelected = settings.theme == "dark" && !settings.nightMode,
                    onClick = { onSettingsChanged(settings.copy(theme = "dark", nightMode = false)) },
                    modifier = Modifier.weight(1f)
                )
            }

            // Night Mode Warm Eye-Protection System
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = if (settings.nightMode) Color(0xFF252218) else SecondarySurface,
                border = if (settings.nightMode) BorderStroke(1.5.dp, NightAccent) else null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(
                                imageVector = Icons.Filled.NightsStay,
                                contentDescription = null,
                                tint = if (settings.nightMode) NightAccent else TextSecondary
                            )
                            Column {
                                Text(
                                    text = "حالت شب محافظ چشم (گرم)",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = if (settings.nightMode) NightText else TextPrimary
                                )
                                Text(
                                    text = "فیلتر نور آبی و کاهش خستگی چشم در تاریکی",
                                    fontSize = 12.sp,
                                    color = if (settings.nightMode) NightText.copy(alpha = 0.7f) else TextSecondary
                                )
                            }
                        }
                        Switch(
                            checked = settings.nightMode,
                            onCheckedChange = { onSettingsChanged(settings.copy(nightMode = it)) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Surface,
                                checkedTrackColor = NightAccent
                            )
                        )
                    }

                    if (settings.nightMode) {
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "روشنایی حالت شب: ${(settings.nightBrightness).toInt()}٪",
                            fontSize = 12.sp,
                            color = NightText
                        )
                        Slider(
                            value = settings.nightBrightness,
                            onValueChange = { onSettingsChanged(settings.copy(nightBrightness = it)) },
                            valueRange = 10f..100f,
                            colors = SliderDefaults.colors(
                                thumbColor = NightAccent,
                                activeTrackColor = NightAccent,
                                inactiveTrackColor = Color(0xFF3A3528)
                            )
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "گرمی رنگ و فیلتر کهربایی: ${(settings.nightWarmth).toInt()}٪",
                            fontSize = 12.sp,
                            color = NightText
                        )
                        Slider(
                            value = settings.nightWarmth,
                            onValueChange = { onSettingsChanged(settings.copy(nightWarmth = it)) },
                            valueRange = 0f..100f,
                            colors = SliderDefaults.colors(
                                thumbColor = Color(0xFFE58F3B),
                                activeTrackColor = Color(0xFFE58F3B),
                                inactiveTrackColor = Color(0xFF3A3528)
                            )
                        )
                    }
                }
            }

            // Font Families Selection
            Text(
                text = "قلم متن",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = TextSecondary
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("وزیرمتن", "نوتو نسخ", "نوتو سنس").forEach { fontName ->
                    val isSelected = settings.font == fontName
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected) Primary else Surface,
                        border = BorderStroke(1.dp, if (isSelected) Primary else Border),
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                            .clickable { onSettingsChanged(settings.copy(font = fontName)) }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = fontName,
                                fontSize = 13.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) Color.White else TextPrimary
                            )
                        }
                    }
                }
            }

            // Font Size Slider
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "اندازه قلم",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = TextSecondary
                    )
                )
                Text(
                    text = "${settings.fontSizeSp.toInt()} pt",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Primary
                    )
                )
            }

            Slider(
                value = settings.fontSizeSp,
                onValueChange = { onSettingsChanged(settings.copy(fontSizeSp = it)) },
                valueRange = 13f..26f,
                steps = 12,
                colors = SliderDefaults.colors(
                    thumbColor = Primary,
                    activeTrackColor = Primary,
                    inactiveTrackColor = SecondarySurface
                ),
                modifier = Modifier.padding(bottom = 14.dp)
            )

            // Line Spacing Slider
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "فاصله خطوط",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = TextSecondary
                    )
                )
                Text(
                    text = String.format("%.1f", settings.lineHeightMultiplier),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Primary
                    )
                )
            }

            Slider(
                value = settings.lineHeightMultiplier,
                onValueChange = { onSettingsChanged(settings.copy(lineHeightMultiplier = it)) },
                valueRange = 1.5f..2.6f,
                steps = 10,
                colors = SliderDefaults.colors(
                    thumbColor = Primary,
                    activeTrackColor = Primary,
                    inactiveTrackColor = SecondarySurface
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Alignment and Navigation Mode
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Alignment Card
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = SecondarySurface,
                    modifier = Modifier.weight(1f)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "چینش متن",
                            fontSize = 12.sp,
                            color = TextSecondary,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            IconButton(
                                onClick = { onSettingsChanged(settings.copy(align = "justify")) },
                                modifier = Modifier
                                    .size(38.dp)
                                    .background(
                                        if (settings.align == "justify") Primary else Surface,
                                        RoundedCornerShape(8.dp)
                                    )
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.FormatAlignJustify,
                                    contentDescription = "دوطرفه",
                                    tint = if (settings.align == "justify") Color.White else TextPrimary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            IconButton(
                                onClick = { onSettingsChanged(settings.copy(align = "right")) },
                                modifier = Modifier
                                    .size(38.dp)
                                    .background(
                                        if (settings.align == "right") Primary else Surface,
                                        RoundedCornerShape(8.dp)
                                    )
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.FormatAlignRight,
                                    contentDescription = "راست‌چین",
                                    tint = if (settings.align == "right") Color.White else TextPrimary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }

                // Nav Mode Card
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = SecondarySurface,
                    modifier = Modifier.weight(1f)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "حالت پیمایش",
                            fontSize = 12.sp,
                            color = TextSecondary,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (settings.navMode == "scroll") Primary else Surface,
                                modifier = Modifier
                                    .height(38.dp)
                                    .clickable { onSettingsChanged(settings.copy(navMode = "scroll")) }
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.padding(horizontal = 10.dp)
                                ) {
                                    Text(
                                        text = "اسکرول",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (settings.navMode == "scroll") Color.White else TextPrimary
                                    )
                                }
                            }

                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (settings.navMode == "page") Primary else Surface,
                                modifier = Modifier
                                    .height(38.dp)
                                    .clickable { onSettingsChanged(settings.copy(navMode = "page")) }
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.padding(horizontal = 10.dp)
                                ) {
                                    Text(
                                        text = "صفحه‌ای",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (settings.navMode == "page") Color.White else TextPrimary
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Keep Screen On Toggle
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "روشن ماندن صفحه هنگام مطالعه",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = TextPrimary
                    )
                )
                Switch(
                    checked = settings.screenOn,
                    onCheckedChange = { onSettingsChanged(settings.copy(screenOn = it)) },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Surface,
                        checkedTrackColor = Primary
                    )
                )
            }
        }
    }
}

@Composable
private fun ThemeCard(
    title: String,
    bgColor: Color,
    textColor: Color,
    borderColor: Color,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = bgColor,
        border = BorderStroke(if (isSelected) 2.dp else 1.dp, if (isSelected) Primary else borderColor),
        modifier = modifier
            .height(56.dp)
            .clickable(onClick = onClick)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = textColor
            )
        }
    }
}
