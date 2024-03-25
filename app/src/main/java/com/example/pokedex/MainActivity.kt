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
import com.example.pokedex.databinding.ActivityMainBinding
import okhttp3.Headers
import java.util.Locale
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var imagePokemon: ImageView
    private lateinit var textSpecies: TextView
    private lateinit var textId : TextView
    private lateinit var buttonNext: Button

    var species = ""
    var id = ""
    var pokemonImageURL = ""

//    val imageView = findViewById<ImageView>(R.id.imageView)
//    val button = findViewById<Button>(R.id.buttonNext)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        textSpecies = binding.textSpecies
        textId = binding.textId
        imagePokemon = binding.imagePokemon
        buttonNext = binding.buttonNext
        getPokemon(25)
        setButtonNextClickListener()
    }

    private fun setButtonNextClickListener() {
        buttonNext.setOnClickListener {
            val randId = Random.nextInt(1025)
            getPokemon(randId)
        }
    }

    private fun getPokemon(pokemonId: Int) {
        val client = AsyncHttpClient()
        client["https://pokeapi.co/api/v2/pokemon/$pokemonId", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.d("Pokemon", "response successful$json")
                species = json.jsonObject.getString("name")
                textSpecies.text = species.capitalize(Locale.ROOT)

                id = json.jsonObject.getInt("id").toString()
                textId.text = id

                pokemonImageURL = json.jsonObject.getJSONObject("sprites").getString("front_default")
                loadPokemonImage(pokemonImageURL)
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Pokemon Error", errorResponse)
            }
        }]
    }

    private fun loadPokemonImage(imageURL: String) {
        Glide.with(this)
            .load(imageURL)
            .fitCenter()
            .into(imagePokemon)
    }
}