package com.example.util

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

class TtsManager(context: Context) {
    private var tts: TextToSpeech? = null
    private var isInitialized = false

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _currentParagraphIndex = MutableStateFlow<Int?>(null)
    val currentParagraphIndex: StateFlow<Int?> = _currentParagraphIndex.asStateFlow()

    private var paragraphsToSpeak: List<String> = emptyList()
    private var currentIndex = 0
    private var speechRate = 1.0f

    init {
        tts = TextToSpeech(context.applicationContext) { status ->
            if (status == TextToSpeech.SUCCESS) {
                isInitialized = true
                val persianLocale = Locale("fa")
                val result = tts?.setLanguage(persianLocale)
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    tts?.setLanguage(Locale.getDefault())
                }
                tts?.setSpeechRate(speechRate)
            }
        }

        tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                _isPlaying.value = true
            }

            override fun onDone(utteranceId: String?) {
                currentIndex++
                if (currentIndex < paragraphsToSpeak.size && _isPlaying.value) {
                    _currentParagraphIndex.value = currentIndex
                    speakParagraph(currentIndex)
                } else {
                    _isPlaying.value = false
                    _currentParagraphIndex.value = null
                }
            }

            @Deprecated("Deprecated in Java")
            override fun onError(utteranceId: String?) {
                _isPlaying.value = false
                _currentParagraphIndex.value = null
            }
        })
    }

    fun startSpeaking(paragraphs: List<String>, startIndex: Int = 0) {
        if (!isInitialized || paragraphs.isEmpty()) return
        paragraphsToSpeak = paragraphs
        currentIndex = startIndex.coerceIn(0, paragraphs.size - 1)
        _isPlaying.value = true
        _currentParagraphIndex.value = currentIndex
        speakParagraph(currentIndex)
    }

    private fun speakParagraph(index: Int) {
        if (index in paragraphsToSpeak.indices) {
            val text = paragraphsToSpeak[index]
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "PARAGRAPH_$index")
        }
    }

    fun pauseOrResume() {
        if (_isPlaying.value) {
            tts?.stop()
            _isPlaying.value = false
        } else if (paragraphsToSpeak.isNotEmpty()) {
            _isPlaying.value = true
            speakParagraph(currentIndex)
        }
    }

    fun stop() {
        tts?.stop()
        _isPlaying.value = false
        _currentParagraphIndex.value = null
        currentIndex = 0
    }

    fun setSpeechRate(rate: Float) {
        speechRate = rate
        tts?.setSpeechRate(rate)
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        tts = null
    }
}
