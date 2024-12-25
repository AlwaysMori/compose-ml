package com.skripsi.testapp.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.res.colorResource
import com.skripsi.testapp.R
import com.skripsi.testapp.databinding.ActivityChatBinding
import java.util.Locale

class ChatActivity : AppCompatActivity() {
    private var _binding: ActivityChatBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inisialisasi binding sebelum setContentView
        _binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Konfigurasi UI
        window.statusBarColor = getColor(R.color.white)
        hideNavigationBar()

        // Tentukan konteks percakapan berdasarkan bahasa
        val chatContextText = getChatContext()

        // Setel ComposeView
        binding.composeView.setContent {
            GeminiChatView(
                apiKey = "AIzaSyDQKICc9Ij_UMKa32lAJnv7SbEjY-8ihyI",
                appThemColor = colorResource(R.color.green_light),
                chatContext = listOf(
                    GeminiContent(
                        role = "user",
                        text = chatContextText
                    )
                )
            )
        }
    }

    private fun hideNavigationBar() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                )
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideNavigationBar()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null // Bersihkan binding untuk mencegah memory leak
    }

    private fun getChatContext(): String {
        val currentLocale = Locale.getDefault().language
        return if (currentLocale == "en") {
            """
                You are a lettuce expert on the HICARE app named Kiana, female...
                [Content Bahasa Inggris di sini]
            """.trimIndent()
        } else {
            """
                Anda adalah seorang ahli selada di aplikasi HICARE bernama Kiana...
                [Content Bahasa Indonesia di sini]
            """.trimIndent()
        }
    }
}
