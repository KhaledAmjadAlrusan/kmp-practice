package org.rukban.practice.locale

import android.content.Context
import java.util.Locale

actual fun getLocalizedString(key: String, localized: LocalizedString): String {
    return when (Locale.getDefault().language) {
        "fr" -> localized.fr
        else -> localized.en
    }
}