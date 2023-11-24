package com.example.nuevaapp12

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class AuthSelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_selection)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val btnFiveLatestData = findViewById<Button>(R.id.btnFiveLatestData)

        btnLogin.setOnClickListener {
            // Redirige a la pantalla de inicio de sesión
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        btnRegister.setOnClickListener {
            // Redirige a la pantalla de registro
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        btnFiveLatestData.setOnClickListener {
            // Redirige a la pantalla five_latest_data.xml
            val intent = Intent(this, FiveLatestDataActivity::class.java)
            startActivity(intent)
        }
    }
}
