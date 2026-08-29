package com.ketabkhan.reader.ui.navigation

sealed class Screen {
    data object Splash : Screen()
    data object Library : Screen()
    data object Reader : Screen()
    data object BookNav : Screen()
    data object BookDetails : Screen()
    data object BookOptions : Screen()
    data object SelectPdf : Screen()
    data object Conversion : Screen()
    data object IssueReview : Screen()
    data object StructureReview : Screen()
    data object FinalPreview : Screen()
    data object ExportBook : Screen()
    data object ImportBook : Screen()
    data object BackupRestore : Screen()
    data object GeneralSettings : Screen()
    data object SystemStates : Screen()
}
