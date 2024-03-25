package com.example.pokedex

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {
    var species = ""
    var type = ""
    var pokemonImageURL = ""
    val button = findViewById<Button>(R.id.buttonNext)
    val textSpecies = findViewById<TextView>(R.id.textSpecies)
    val imageView = findViewById<ImageView>(R.id.imageView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        getPokemon("pikachu")
    }

    private fun getPokemon(s: String) {
        val client = AsyncHttpClient()
        client["https://pokeapi.co/api/v2/pokemon/$s", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.d("Pokemon", "response successful$json")
                species = json.jsonObject.getString("name")
                Log.d("Pokemon Name", species)
                textSpecies.text = species
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
//                Log.d("Pokemon Error", errorResponse)
            }
        }]
    }
}