package com.example.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Library : Screen("library")
    object SelectPdf : Screen("select_pdf")
    object Conversion : Screen("conversion")
    object BookDetails : Screen("book_details")
    object StructureReview : Screen("structure_review")
    object IssueReview : Screen("issue_review")
    object FinalPreview : Screen("final_preview")
    object Reader : Screen("reader")
    object BookNav : Screen("book_nav")
    object ReadingSettings : Screen("reading_settings")
    object BookOptions : Screen("book_options")
    object ExportBook : Screen("export_book")
    object ImportBook : Screen("import_book")
    object BackupRestore : Screen("backup_restore")
    object GeneralSettings : Screen("general_settings")
    object SystemStates : Screen("system_states")
}
