package com.example.apidog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.apidog.api.Endpoint
import com.example.apidog.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchButton.setOnClickListener {
            getImage()
        }
    }

    private fun getImage() {
        val url = "https://dog.ceo/"
        val retrofitClient = retrofitInstance(url)
        val endpoint = retrofitClient.create(Endpoint::class.java)
        val raca = binding.searchRaca.text.toString()

        endpoint.getDog(raca).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                val urlImage = response.body()?.get("message")?.asString
                Picasso.get().load(urlImage).into(binding.imageDog)
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Toast.makeText(applicationContext,"Erro ao acessar", Toast.LENGTH_LONG)
            }

        })


    }

    private fun retrofitInstance(url: String): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
}


