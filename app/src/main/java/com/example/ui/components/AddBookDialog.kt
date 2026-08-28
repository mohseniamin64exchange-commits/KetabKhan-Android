package com.example.ui.components

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun AddBookDialog(
    onDismiss: () -> Unit,
    onAddBook: (title: String, author: String, category: String, description: String, text: String, startColor: Long, endColor: Long) -> Unit
) {
    val context = LocalContext.current
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("کتاب‌های من") }
    var description by remember { mutableStateOf("") }
    var bookText by remember { mutableStateOf("") }
    var selectedGradientIndex by remember { mutableStateOf(0) }

    val colorOptions = listOf(
        Pair(0xFF2C3E50, 0xFF1A252F), // Slate Dark
        Pair(0xFF8B1E0F, 0xFF2B0A06), // Crimson Burgundy
        Pair(0xFF1B4931, 0xFF0B2115), // Forest Green
        Pair(0xFF4A148C, 0xFF12005E), // Royal Violet
        Pair(0xFF6B4226, 0xFF2A1608)  // Antique Leather
    )

    // File picker launcher for text files
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            try {
                val inputStream = context.contentResolver.openInputStream(uri)
                val content = inputStream?.bufferedReader()?.use { it.readText() } ?: ""
                if (content.isNotBlank()) {
                    bookText = content
                    if (title.isBlank()) {
                        title = uri.lastPathSegment?.substringBeforeLast(".")?.take(30) ?: "کتاب وارد شده"
                    }
                    Toast.makeText(context, "فایل با موفقیت بارگذاری شد (${content.length} کاراکتر)", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "خطا در خواندن فایل: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .padding(vertical = 12.dp)
                .testTag("add_book_dialog"),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "افزودن کتاب جدید",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "بستن")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // File Import Button
                    OutlinedButton(
                        onClick = {
                            filePickerLauncher.launch("text/*")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.UploadFile, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("وارد کردن از فایل متنی (TXT)")
                    }

                    // Title
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("عنوان کتاب *") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )

                    // Author
                    OutlinedTextField(
                        value = author,
                        onValueChange = { author = it },
                        label = { Text("نام نویسنده") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )

                    // Category
                    OutlinedTextField(
                        value = category,
                        onValueChange = { category = it },
                        label = { Text("دسته‌بندی (مثلاً: رمان، فلسفه، شعر)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )

                    // Color Gradient Selector
                    Text(
                        text = "رنگ جلد کتاب",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        colorOptions.forEachIndexed { index, (start, end) ->
                            val isSelected = selectedGradientIndex == index
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(Color(start))
                                    .border(
                                        width = if (isSelected) 3.dp else 1.dp,
                                        color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                                        shape = CircleShape
                                    )
                                    .clickable { selectedGradientIndex = index },
                                contentAlignment = Alignment.Center
                            ) {
                                if (isSelected) {
                                    Icon(
                                        Icons.Default.Check,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }

                    // Book Text Content
                    OutlinedTextField(
                        value = bookText,
                        onValueChange = { bookText = it },
                        label = { Text("متن کتاب یا مقاله *") },
                        placeholder = { Text("متن کتاب یا بخش‌های مختلف را اینجا جای‌گذاری کنید...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        shape = RoundedCornerShape(12.dp),
                        maxLines = 10
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Submit Button
                Button(
                    onClick = {
                        if (title.isBlank()) {
                            Toast.makeText(context, "لطفاً عنوان کتاب را وارد کنید", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        if (bookText.isBlank()) {
                            Toast.makeText(context, "لطفاً متن کتاب را وارد یا از فایل بارگذاری کنید", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        val (sColor, eColor) = colorOptions[selectedGradientIndex]
                        onAddBook(title, author, category, description, bookText, sColor, eColor)
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Icon(Icons.Default.Book, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("ذخیره و شروع مطالعه", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
