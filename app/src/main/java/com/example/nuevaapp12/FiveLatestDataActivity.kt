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

class FiveLatestDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_five_latest_data)

        // Configurar Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("http://3.218.55.240:8081")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Crear la instancia de la interfaz ApiService
        val apiService = retrofit.create(ApiService::class.java)

        // Hacer la solicitud para obtener todos los JSON
        val call = apiService.getLatestData()
        call.enqueue(object : Callback<List<YourDataModel>> {
            override fun onResponse(call: Call<List<YourDataModel>>, response: Response<List<YourDataModel>>) {
                if (response.isSuccessful) {
                    // Procesar la respuesta y mostrar los últimos 5 datos en tu interfaz de usuario
                    val data = response.body()

                    // Obtener los últimos 5 elementos de la lista
                    val ultimos5Datos = data?.takeLast(5) as List<YourDataModel>

                    // Asignar los datos a los campos de texto
                    if (ultimos5Datos.size == 5) {

                        findViewById<TextView>(R.id.tvData1).text = "Campo 1: ${ultimos5Datos[0].distance_m}, ${ultimos5Datos[0].unit}"
                        findViewById<TextView>(R.id.tvData2).text = "Campo 2: ${ultimos5Datos[1].distance_m}, ${ultimos5Datos[1].unit}"
                        findViewById<TextView>(R.id.tvData3).text = "Campo 3: ${ultimos5Datos[2].distance_m}, ${ultimos5Datos[2].unit}"
                        findViewById<TextView>(R.id.tvData4).text = "Campo 4: ${ultimos5Datos[3].distance_m}, ${ultimos5Datos[3].unit}"
                        findViewById<TextView>(R.id.tvData5).text = "Campo 5: ${ultimos5Datos[4].distance_m}, ${ultimos5Datos[4].unit}"
                    }
                } else {
                    Toast.makeText(this@FiveLatestDataActivity, "Error en la solicitud", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<YourDataModel>>, t: Throwable) {
                Toast.makeText(this@FiveLatestDataActivity, "Error de red", Toast.LENGTH_SHORT).show()
            }
        })

    }

}
