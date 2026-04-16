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
        webView.settings.databaseEnabled = true
        webView.settings.mixedContentMode = android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        webView.settings.userAgentString = "Mozilla/5.0 (Linux; Android 10; Mobile) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Mobile Safari/537.36"
        
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                // Если это первый запуск (нет сохраненного пина), можно сделать редирект через JS или проверить тут
                // Но лучше управлять этим на стороне веб-приложения
            }
        }
        
        // Загружаем публичную ссылку
        webView.loadUrl("https://ais-pre-ehhpukwzjqxnwrvn73fvkk-366435121233.europe-west1.run.app") 

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
