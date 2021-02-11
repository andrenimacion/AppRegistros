package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.myapplication.interfaces.LaboresAPI
import com.example.myapplication.models.Jornalero
import com.example.myapplication.models.Labor
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://alp-cloud.com:8449"
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getContent()
    }
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getContent(){
        //val texto:TextView = findViewById(R.id.texto)
        /*val call = getRetrofit().create(LaboresAPI::class.java).getLabores().enqueue(object:Callback<List<Labor>>{
            override fun onResponse(call: Call<List<Labor>>, response: Response<List<Labor>>) {
                val labores = response.body() as List<Labor>
                labores.forEach {
                    texto.text = it.codLab
                }
            }

            override fun onFailure(call: Call<List<Labor>>, t: Throwable) {
                texto.text = t.message
            }
        })*/

        GlobalScope.launch(Dispatchers.IO){
            val response = getRetrofit().create(LaboresAPI::class.java).getLabores().awaitResponse()
            if(response.isSuccessful){
                val data = response.body()!!
                Log.d("MainActivity",data[0]. codlab)
                withContext(Dispatchers.Main){
                    data.forEach{
                        texto.text = it.toString()
                    }
                    //texto.text = data[0].codlab
                    print(texto.text)
                }
            }
        }

    }
}





