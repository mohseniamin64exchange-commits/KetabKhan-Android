package com.ketabkhan.reader.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatAlignJustify
import androidx.compose.material.icons.filled.FormatAlignRight
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ketabkhan.reader.data.model.ReaderSettings
import com.ketabkhan.reader.ui.theme.*

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

            // Theme selector (Light, Sepia, Dark)
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
                ThemeCard(
                    title = "روشن",
                    bgColor = Color(0xFFFAF6F0),
                    textColor = Color(0xFF1A1A18),
                    borderColor = Border,
                    isSelected = settings.theme == "light",
                    onClick = { onSettingsChanged(settings.copy(theme = "light")) },
                    modifier = Modifier.weight(1f)
                )

                ThemeCard(
                    title = "سپیا / کاهی",
                    bgColor = Color(0xFFF2E8D5),
                    textColor = Color(0xFF3A2E22),
                    borderColor = Color(0xFFE2D6C0),
                    isSelected = settings.theme == "sepia",
                    onClick = { onSettingsChanged(settings.copy(theme = "sepia")) },
                    modifier = Modifier.weight(1f)
                )

                ThemeCard(
                    title = "تاریک",
                    bgColor = Color(0xFF1E2020),
                    textColor = Color(0xFFD8D4CC),
                    borderColor = Color(0xFF3E4444),
                    isSelected = settings.theme == "dark",
                    onClick = { onSettingsChanged(settings.copy(theme = "dark")) },
                    modifier = Modifier.weight(1f)
                )
            }

            // Warm night eye protection slider
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = if (settings.theme == "dark") Color(0xFF252218) else SecondarySurface,
                border = if (settings.theme == "dark") BorderStroke(1.dp, NightAccent) else null,
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
                                tint = if (settings.theme == "dark") NightAccent else TextSecondary
                            )
                            Text(
                                text = "شدت نور ملایم و فیلتر کهربایی",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = if (settings.theme == "dark") NightText else TextPrimary
                            )
                        }
                        Text(
                            text = "${(settings.nightIntensity * 100).toInt()}٪",
                            fontWeight = FontWeight.Bold,
                            color = Primary
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Slider(
                        value = settings.nightIntensity,
                        onValueChange = { onSettingsChanged(settings.copy(nightIntensity = it)) },
                        valueRange = 0.1f..1.0f,
                        colors = SliderDefaults.colors(
                            thumbColor = Primary,
                            activeTrackColor = Primary,
                            inactiveTrackColor = SecondarySurface
                        )
                    )
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
                    text = "${settings.fontSize.toInt()} pt",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Primary
                    )
                )
            }

            Slider(
                value = settings.fontSize,
                onValueChange = { onSettingsChanged(settings.copy(fontSize = it)) },
                valueRange = 13f..28f,
                steps = 15,
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
                    text = String.format("%.1f", settings.lineSpacing),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Primary
                    )
                )
            }

            Slider(
                value = settings.lineSpacing,
                onValueChange = { onSettingsChanged(settings.copy(lineSpacing = it)) },
                valueRange = 1.3f..2.5f,
                steps = 12,
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
                                onClick = { onSettingsChanged(settings.copy(textAlignment = "justify")) },
                                modifier = Modifier
                                    .size(38.dp)
                                    .background(
                                        if (settings.textAlignment == "justify") Primary else Surface,
                                        RoundedCornerShape(8.dp)
                                    )
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.FormatAlignJustify,
                                    contentDescription = "دوطرفه",
                                    tint = if (settings.textAlignment == "justify") Color.White else TextPrimary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            IconButton(
                                onClick = { onSettingsChanged(settings.copy(textAlignment = "right")) },
                                modifier = Modifier
                                    .size(38.dp)
                                    .background(
                                        if (settings.textAlignment == "right") Primary else Surface,
                                        RoundedCornerShape(8.dp)
                                    )
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.FormatAlignRight,
                                    contentDescription = "راست‌چین",
                                    tint = if (settings.textAlignment == "right") Color.White else TextPrimary,
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
                                color = if (settings.pageTransition == "scroll") Primary else Surface,
                                modifier = Modifier
                                    .height(38.dp)
                                    .clickable { onSettingsChanged(settings.copy(pageTransition = "scroll")) }
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.padding(horizontal = 10.dp)
                                ) {
                                    Text(
                                        text = "پیوسته",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (settings.pageTransition == "scroll") Color.White else TextPrimary
                                    )
                                }
                            }

                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (settings.pageTransition == "slide") Primary else Surface,
                                modifier = Modifier
                                    .height(38.dp)
                                    .clickable { onSettingsChanged(settings.copy(pageTransition = "slide")) }
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.padding(horizontal = 10.dp)
                                ) {
                                    Text(
                                        text = "صفحه‌ای",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (settings.pageTransition == "slide") Color.White else TextPrimary
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Inline Footnotes Toggle
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "نمایش پاورقی‌ها در انتهای هر بند",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = TextPrimary
                    )
                )
                Switch(
                    checked = settings.showFootnotesInline,
                    onCheckedChange = { onSettingsChanged(settings.copy(showFootnotesInline = it)) },
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
