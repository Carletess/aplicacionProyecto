package com.example.nuevaapp12


import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

class RegisterActivity : AppCompatActivity() {

    private val baseUrl = "http://3.218.55.240:8081/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val registerService = retrofit.create(RegisterService::class.java)

    private lateinit var etCJPSRegisterUsername: EditText
    private lateinit var etCJPSRegisterPassword: EditText
    private lateinit var btnCJPSRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etCJPSRegisterUsername = findViewById(R.id.etRegisterUsername)
        etCJPSRegisterPassword = findViewById(R.id.etRegisterPassword)
        btnCJPSRegister = findViewById(R.id.btnRegister)

        btnCJPSRegister.setOnClickListener {
            val username = etCJPSRegisterUsername.text.toString()
            val password = etCJPSRegisterPassword.text.toString()

            // Verifica si el usuario ya existe antes de registrar
            checkIfUserExists(username, password)
        }
    }

    private fun checkIfUserExists(username: String, password: String) {
        // Crea un objeto de datos para enviar al servidor Flask
        val userData = UserData(username, password)

        // Realiza una solicitud POST al servidor Flask para verificar si el usuario existe
        val call = registerService.checkUserExists(userData)

        call.enqueue(object : Callback<CheckUserResponse> {
            override fun onResponse(call: Call<CheckUserResponse>, response: Response<CheckUserResponse>) {
                if (response.isSuccessful) {
                    // Verificación exitosa, verifica si el usuario ya existe según la respuesta del servidor
                    val checkUserResponse = response.body()

                    if (checkUserResponse != null) {
                        if (checkUserResponse.userExists) {
                            // El usuario ya existe, muestra un mensaje de error
                            Toast.makeText(this@RegisterActivity, "El usuario ya existe", Toast.LENGTH_SHORT).show()
                        } else {
                            // El usuario no existe, procede con el registro
                            registerUser(username, password)
                        }
                    } else {
                        // Error en la respuesta del servidor
                        Toast.makeText(this@RegisterActivity, "Error en el servidor", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Error en la verificación
                    val errorMessage = response.errorBody()?.string() ?: "Error en la verificación"
                    Toast.makeText(this@RegisterActivity, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CheckUserResponse>, t: Throwable) {
                // Error de red
                Toast.makeText(this@RegisterActivity, "Error de red", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun registerUser(username: String, password: String) {
        // Crea un objeto de datos para enviar al servidor Flask
        val userData = UserData(username, password)

        // Realiza una solicitud POST al servidor Flask para el registro
        val call = registerService.registerUser(userData)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // Registro exitoso
                    Toast.makeText(this@RegisterActivity, "Registro exitoso", Toast.LENGTH_SHORT).show()
                } else {
                    // Error en el registro
                    val errorMessage = response.errorBody()?.string() ?: "Error en el registro"
                    Toast.makeText(this@RegisterActivity, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Error de red
                Toast.makeText(this@RegisterActivity, "Error de red", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

// Define una interfaz de servicio para la verificación de usuario y el registro
interface RegisterService {
    @POST("/checkUserExists")
    fun checkUserExists(@Body userData: UserData): Call<CheckUserResponse>

    @POST("/register")
    fun registerUser(@Body userData: UserData): Call<Void>
}

// Define una clase de datos para la respuesta de verificación de usuario
data class CheckUserResponse(val userExists: Boolean)
