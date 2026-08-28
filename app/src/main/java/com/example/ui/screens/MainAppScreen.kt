package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.navigation.Screen
import com.example.ui.theme.Primary
import com.example.ui.theme.TextPrimary
import com.example.ui.viewmodel.BookReaderViewModel
import kotlinx.coroutines.delay

@Composable
fun MainAppScreen(
    viewModel: BookReaderViewModel,
    modifier: Modifier = Modifier
) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    val snackbarMessage by viewModel.snackbarMessage.collectAsState()

    // Enforce RTL Layout Direction
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        // Back press handling
        BackHandler(enabled = currentScreen != Screen.Library && currentScreen != Screen.Splash) {
            viewModel.goBack()
        }

        Box(modifier = modifier.fillMaxSize()) {
            AnimatedContent(
                targetState = currentScreen,
                transitionSpec = {
                    fadeIn() togetherWith fadeOut()
                },
                label = "screen_transition"
            ) { targetScreen ->
                when (targetScreen) {
                    Screen.Splash -> SplashScreen(viewModel = viewModel)
                    Screen.Library -> LibraryScreen(viewModel = viewModel)
                    Screen.SelectPdf -> SelectPdfScreen(viewModel = viewModel)
                    Screen.Conversion -> ConversionScreen(viewModel = viewModel)
                    Screen.BookDetails -> BookDetailsScreen(viewModel = viewModel)
                    Screen.StructureReview -> StructureReviewScreen(viewModel = viewModel)
                    Screen.IssueReview -> IssueReviewScreen(viewModel = viewModel)
                    Screen.FinalPreview -> FinalPreviewScreen(viewModel = viewModel)
                    Screen.Reader -> ReaderScreen(viewModel = viewModel)
                    Screen.BookNav -> BookNavScreen(viewModel = viewModel)
                    Screen.BookOptions -> BookOptionsScreen(viewModel = viewModel)
                    Screen.ExportBook -> ExportBookScreen(viewModel = viewModel)
                    Screen.ImportBook -> ImportBookScreen(viewModel = viewModel)
                    Screen.BackupRestore -> BackupRestoreScreen(viewModel = viewModel)
                    Screen.GeneralSettings -> GeneralSettingsScreen(viewModel = viewModel)
                    Screen.SystemStates -> SystemStatesScreen(viewModel = viewModel)
                    else -> LibraryScreen(viewModel = viewModel)
                }
            }

            // Global Feedback Toast / Snackbar Overlay
            if (snackbarMessage != null) {
                LaunchedEffect(snackbarMessage) {
                    delay(3000)
                    viewModel.dismissSnackbar()
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFF2C3E50),
                    shadowElevation = 8.dp,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 76.dp)
                        .padding(horizontal = 24.dp)
                        .testTag("app_snackbar")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = null,
                            tint = Color(0xFF2ECC71),
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = snackbarMessage!!,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 13.sp
                            )
                        )
                    }
                }
            }
        }
    }
}
