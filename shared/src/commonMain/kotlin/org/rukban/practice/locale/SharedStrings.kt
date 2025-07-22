package org.rukban.practice.locale

data class LocalizedString(val en: String, val fr: String)

expect fun getLocalizedString(key: String, localized: LocalizedString): String

object SharedStrings {
    val appName get() = getLocalizedString("app_name", LocalizedString("KMP App", "Application KMP"))
    val labelTitle get() = getLocalizedString("label_title", LocalizedString("Title", "Titre"))
    val labelArtist get() = getLocalizedString("label_artist", LocalizedString("Artist", "Artiste"))
    val labelDate get() = getLocalizedString("label_date", LocalizedString("Date", "Date"))
    val labelDimensions get() = getLocalizedString("label_dimensions", LocalizedString("Dimensions", "Dimensions"))
    val labelMedium get() = getLocalizedString("label_medium", LocalizedString("Medium", "Médium"))
    val labelDepartment get() = getLocalizedString("label_department", LocalizedString("Department", "Département"))
    val labelRepository get() = getLocalizedString("label_repository", LocalizedString("Repository", "Référentiel"))
    val labelCredits get() = getLocalizedString("label_credits", LocalizedString("Credits", "Crédits"))
    val back get() = getLocalizedString("back", LocalizedString("Back", "Retour"))
    val noDataAvailable get() = getLocalizedString("no_data_available", LocalizedString("No data available", "Aucune donnée disponible"))
}

