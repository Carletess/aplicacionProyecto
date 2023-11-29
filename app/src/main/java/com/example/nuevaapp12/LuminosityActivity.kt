package com.example.nuevaapp12

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LuminosityActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_luminosity)

        // Configurar Retrofit con la URL correcta ("/dataluz")
        val retrofit = Retrofit.Builder()
            .baseUrl("http://3.218.55.240:8081/dataluz/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Crear la instancia de la interfaz ApiService
        val apiService = retrofit.create(ApiService::class.java)

        // Hacer la solicitud para obtener todos los JSON
        val call = apiService.getDataLuz()
        call.enqueue(object : Callback<List<YourDataLuz>> {
            override fun onResponse(call: Call<List<YourDataLuz>>, response: Response<List<YourDataLuz>>) {
                if (response.isSuccessful) {
                    // Procesar la respuesta y mostrar el dato en tu interfaz de usuario
                    val data = response.body()

                    // Obtener el último elemento de la lista
                    val ultimoDato = data?.lastOrNull()

                    // Asignar los datos al TextView
                    if (ultimoDato != null) {
                        findViewById<TextView>(R.id.tvData1).text = "Luminosity Data: ${ultimoDato.luminosity}"
                    }
                } else {
                    Toast.makeText(this@LuminosityActivity, "Error en la solicitud", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<YourDataLuz>>, t: Throwable) {
                Toast.makeText(this@LuminosityActivity, "Error de red", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
