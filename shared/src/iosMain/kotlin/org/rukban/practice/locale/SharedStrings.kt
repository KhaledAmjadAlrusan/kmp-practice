package org.rukban.practice.locale

import platform.Foundation.NSLocale
import platform.Foundation.preferredLanguages

actual fun getLocalizedString(key: String, localized: LocalizedString): String {
    val languages = NSLocale.preferredLanguages
    val languageCode = (languages.firstOrNull() as? String)?.substring(0, 2) ?: "en"
    return if (languageCode == "fr") localized.fr else localized.en
}


