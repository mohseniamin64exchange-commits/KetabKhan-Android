package com.ketabkhan.reader.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.ketabkhan.reader.data.model.ReaderSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "reader_preferences")

class ReaderPreferencesRepository(private val context: Context) {

    private object PreferencesKeys {
        val FONT_SIZE = floatPreferencesKey("font_size")
        val LINE_SPACING = floatPreferencesKey("line_spacing")
        val PARAGRAPH_SPACING = floatPreferencesKey("paragraph_spacing")
        val THEME = stringPreferencesKey("theme")
        val NIGHT_INTENSITY = floatPreferencesKey("night_intensity")
        val TEXT_ALIGNMENT = stringPreferencesKey("text_alignment")
        val READING_DIRECTION = stringPreferencesKey("reading_direction")
        val AUTO_SCROLL = booleanPreferencesKey("auto_scroll")
        val PAGE_TRANSITION = stringPreferencesKey("page_transition")
        val SHOW_FOOTNOTES_INLINE = booleanPreferencesKey("show_footnotes_inline")
    }

    val readerSettingsFlow: Flow<ReaderSettings> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            ReaderSettings(
                fontSize = preferences[PreferencesKeys.FONT_SIZE] ?: 18f,
                lineSpacing = preferences[PreferencesKeys.LINE_SPACING] ?: 1.6f,
                paragraphSpacing = preferences[PreferencesKeys.PARAGRAPH_SPACING] ?: 12f,
                theme = preferences[PreferencesKeys.THEME] ?: "light",
                nightIntensity = preferences[PreferencesKeys.NIGHT_INTENSITY] ?: 0.5f,
                textAlignment = preferences[PreferencesKeys.TEXT_ALIGNMENT] ?: "justify",
                readingDirection = preferences[PreferencesKeys.READING_DIRECTION] ?: "RTL",
                autoScroll = preferences[PreferencesKeys.AUTO_SCROLL] ?: false,
                pageTransition = preferences[PreferencesKeys.PAGE_TRANSITION] ?: "scroll",
                showFootnotesInline = preferences[PreferencesKeys.SHOW_FOOTNOTES_INLINE] ?: false
            )
        }

    suspend fun updateSettings(settings: ReaderSettings) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.FONT_SIZE] = settings.fontSize
            preferences[PreferencesKeys.LINE_SPACING] = settings.lineSpacing
            preferences[PreferencesKeys.PARAGRAPH_SPACING] = settings.paragraphSpacing
            preferences[PreferencesKeys.THEME] = settings.theme
            preferences[PreferencesKeys.NIGHT_INTENSITY] = settings.nightIntensity
            preferences[PreferencesKeys.TEXT_ALIGNMENT] = settings.textAlignment
            preferences[PreferencesKeys.READING_DIRECTION] = settings.readingDirection
            preferences[PreferencesKeys.AUTO_SCROLL] = settings.autoScroll
            preferences[PreferencesKeys.PAGE_TRANSITION] = settings.pageTransition
            preferences[PreferencesKeys.SHOW_FOOTNOTES_INLINE] = settings.showFootnotesInline
        }
    }

    suspend fun updateFontSize(fontSize: Float) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.FONT_SIZE] = fontSize
        }
    }

    suspend fun updateLineSpacing(lineSpacing: Float) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.LINE_SPACING] = lineSpacing
        }
    }

    suspend fun updateTheme(theme: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.THEME] = theme
        }
    }

    suspend fun updateNightIntensity(intensity: Float) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.NIGHT_INTENSITY] = intensity
        }
    }
}
