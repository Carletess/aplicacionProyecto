package com.example.nuevaapp12

import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        val webView: WebView = findViewById(R.id.webView)

        // Configurar la configuración de WebView
        val webSettings: WebSettings = webView.settings

        // Habilitar el soporte para zoom en la página
        webSettings.setSupportZoom(true)

        // Establecer el nivel de zoom inicial al 100%
        webView.settings.textZoom = 100

        // Habilitar el control de zoom en la página
        webSettings.setBuiltInZoomControls(false)

        // Deshabilitar los gestos de zoom, si es necesario
        webSettings.setDisplayZoomControls(true)

        // Habilita la ejecución de scripts en la página web (opcional, según tus necesidades)
        webSettings.javaScriptEnabled = true

        // Configura un WebViewClient para que las páginas web se carguen dentro de la aplicación
        webView.webViewClient = WebViewClient()

        // Carga la URL de la página web que deseas mostrar
        val url = "http://34.239.209.214:8085/data"
        //val url = "https://www.google.cl"
        webView.loadUrl(url)
    }
}
