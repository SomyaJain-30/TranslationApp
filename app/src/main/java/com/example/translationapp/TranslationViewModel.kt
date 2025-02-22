package com.example.translationapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class TranslationViewModel : ViewModel() {

    private val _translatedText = MutableStateFlow("")
    val translatedText: StateFlow<String> = _translatedText

    fun translate(sourceLang: String, targetLang: String, text: String) {
        val sourceLanguage = getLanguageCode(sourceLang)
        val targetLanguage = getLanguageCode(targetLang)
        viewModelScope.launch {
            try {
                val translator = createTranslator(sourceLanguage, targetLanguage)
                downloadIfNeeded(translator)
                performTranslation(translator, text)
            } catch (e: Exception) {
                throw Exception("Translation Failed: ${e.message}")
            }
        }
    }

    private fun performTranslation(translator: Translator, text: String) {
        try {
            translator.translate(text)
                .addOnSuccessListener { translatedText ->
                    Log.e("CHECK-->", translatedText)
                    _translatedText.value = translatedText
                }
                .addOnFailureListener { exception ->
                    Log.e("CHECK-->", "Something Went wrong")
                    throw Exception("Translation Failed: ${exception.message}")
                }
        } catch (e: Exception) {
            throw Exception("Translation Failed: ${e.message}")
        }
    }

    private fun createTranslator(sourceLang: String, targetLang: String): Translator {
        return Translation.getClient(
            TranslatorOptions.Builder().setSourceLanguage(sourceLang).setTargetLanguage(targetLang)
                .build()
        )
    }

    private suspend fun downloadIfNeeded(translator: Translator) {
        try {
            translator.downloadModelIfNeeded(
                DownloadConditions.Builder()
                    .requireWifi()
                    .build()
            ).await()
        } catch (e: Exception) {
            throw Exception("Failed to download language model: ${e.message}")
        }
    }


    private fun getLanguageCode(language: String): String {
        return when (language) {
            "English" -> TranslateLanguage.ENGLISH
            "Hindi" -> TranslateLanguage.HINDI
            "German" -> TranslateLanguage.GERMAN
            "Arabic" -> TranslateLanguage.ARABIC
            "French" -> TranslateLanguage.FRENCH
            else -> TranslateLanguage.ENGLISH
        }
    }

    /*private suspend fun <TResult> Task<TResult>.await(): TResult {
        return suspendCoroutine { continuation ->
            addOnSuccessListener { result ->
                continuation.resume(result)
            }.addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
        }
    }*/

}