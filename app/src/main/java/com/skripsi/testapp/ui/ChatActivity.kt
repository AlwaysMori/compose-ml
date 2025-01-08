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

        _binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = getColor(R.color.white)
        hideNavigationBar()

        val chatContextText = getChatContext()

        binding.composeView.setContent {
            GeminiChatView(
                apiKey = "",
                appThemColor = colorResource(R.color.greensky),
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
        _binding = null
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
