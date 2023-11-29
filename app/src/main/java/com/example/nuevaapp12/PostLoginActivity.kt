package com.example.nuevaapp12

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class PostLoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_login)

        val btnFiveLatestData = findViewById<Button>(R.id.btnFiveLatestData2)
        val btnLuminosityActivity = findViewById<Button>(R.id.btnLuminosity2)
        val btnWebViewActivity = findViewById<Button>(R.id.btnWebView)

        btnFiveLatestData.setOnClickListener {
            // Redirige a la pantalla five_latest_data.xml
            val intent = Intent(this, FiveLatestDataActivity::class.java)
            startActivity(intent)
        }

        btnLuminosityActivity.setOnClickListener {
            val intent = Intent(this, LuminosityActivity::class.java)
            startActivity(intent)
        }

        btnWebViewActivity.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            startActivity(intent)
        }
    }
}
