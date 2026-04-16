package com.learn2unlock.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Проверка разрешений при запуске
        checkPermissions()

        webView = WebView(this)
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.webViewClient = WebViewClient()
        
        // Загружаем наш веб-интерфейс
        webView.loadUrl("https://ais-dev-ehhpukwzjqxnwrvn73fvkk-366435121233.europe-west1.run.app") 

        setContentView(webView)
    }

    private fun checkPermissions() {
        // Проверка разрешения на отображение поверх других окон
        if (!Settings.canDrawOverlays(this)) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            startActivity(intent)
        }
        
        // Здесь также нужно будет добавить проверку Accessibility Service
    }

    override fun onBackPressed() {
        // Блокируем кнопку "Назад", чтобы ребенок не мог выйти из заданий
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            // Если мы на главной - ничего не делаем (блокируем выход)
        }
    }
}
