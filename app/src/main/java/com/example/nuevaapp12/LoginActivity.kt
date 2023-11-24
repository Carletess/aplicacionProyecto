package com.example.nuevaapp12

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.TextView
import android.view.View // Agrega la importación de View
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

class LoginActivity : AppCompatActivity() {

    private val baseUrl = "http://3.218.55.240:8081/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val loginService = retrofit.create(LoginService::class.java)

    private lateinit var etLoginUsername: EditText
    private lateinit var etLoginPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvErrorMessage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etLoginUsername = findViewById(R.id.etLoginUsername)
        etLoginPassword = findViewById(R.id.etLoginPassword)
        btnLogin = findViewById(R.id.btnLogin)

        // Agrega la referencia al TextView
        tvErrorMessage = findViewById(R.id.tvErrorMessage)

        btnLogin.setOnClickListener {
            val username = etLoginUsername.text.toString()
            val password = etLoginPassword.text.toString()

            // Llama a la función de inicio de sesión
            loginUser(username, password)
        }
    }

    private fun loginUser(username: String, password: String) {
        // Crea un objeto de datos para enviar al servidor Flask
        val userData = UserData(username, password)

        // Realiza una solicitud POST al servidor Flask para el inicio de sesión
        val call = loginService.loginUser(userData)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    // Inicio de sesión exitoso
                    val loginResponse = response.body()

                    if (loginResponse != null) {
                        if (loginResponse.success) {
                            // Usuario autenticado con éxito
                            // Limpia el mensaje de error si había alguno previamente
                            tvErrorMessage.text = "" // Establece el texto en blanco
                            tvErrorMessage.visibility = View.GONE
                            // Muestra "Conectado"
                            Toast.makeText(this@LoginActivity, "Conectado", Toast.LENGTH_SHORT).show()
                            // Resto de tu código
                        } else {
                            // Usuario o contraseña incorrectos
                            tvErrorMessage.text = "Usuario o contraseña incorrectos"
                            tvErrorMessage.visibility = View.VISIBLE
                        }
                    } else {
                        // Error en la respuesta del servidor
                        tvErrorMessage.text = "Error en el servidor"
                        tvErrorMessage.visibility = View.VISIBLE
                    }
                } else {
                    // Error en el inicio de sesión
                    val errorMessage = response.errorBody()?.string() ?: "Error en el inicio de sesión"
                    tvErrorMessage.text = errorMessage
                    tvErrorMessage.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                // Error de red
                tvErrorMessage.text = "Error de red"
                tvErrorMessage.visibility = View.VISIBLE
            }
        })
    }
}

// Define una interfaz de servicio para el inicio de sesión
interface LoginService {
    @POST("/login")
    fun loginUser(@Body userData: UserData): Call<LoginResponse>
}

// Define una clase de datos para el registro
data class UserData(val username: String, val password: String)

// Define una clase de datos para la respuesta del inicio de sesión
data class LoginResponse(val success: Boolean)
