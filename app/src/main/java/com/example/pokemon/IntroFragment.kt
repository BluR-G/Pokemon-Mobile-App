package com.example.pokemon

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.pokemon.databinding.FragmentIntroBinding
import com.example.pokemon.menu.MenuActivity
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.pokemon.data.PokemonCreation
import com.example.pokemon.databinding.ActivityMenuBinding
import com.example.pokemon.objects.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IntroFragment : Fragment() {

    private var starterPokemon : String = ""
    private var nickname : String = ""
    private var username : String = "";
    private lateinit var pokemon: Pokemon
    lateinit var binding: FragmentIntroBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIntroBinding.inflate(layoutInflater)

        var isUserNameValid : Boolean = false;
        var isStarterPicked : Boolean = false;

        // Enable button if username is provided and a radiobutton is checked
        binding.usernameInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().trim() != "") {
                    isUserNameValid = true;
                    username = binding.usernameInput.text.toString()
                    if (isStarterPicked) {
                        binding.IntroGoToMainMenu.isEnabled = true;
                    }
                }
            }
        })

        // Enable button if a radiobutton is checked and username is provided
        binding.bulbasaurRBtn.setOnClickListener {
            isStarterPicked = true;
            starterPokemon = "Bulbasaur";
            if (isUserNameValid) {
                binding.IntroGoToMainMenu.isEnabled = true;
            }
        }
        binding.charmanderRBtn.setOnClickListener {
            isStarterPicked = true;
            starterPokemon = "Charmander";
            if (isUserNameValid) {
                binding.IntroGoToMainMenu.isEnabled = true;
            }
        }
        binding.squirtleRBtn.setOnClickListener {
            isStarterPicked = true;
            starterPokemon = "Squirtle";
            if (isUserNameValid) {
                binding.IntroGoToMainMenu.isEnabled = true;
            }
        }

        // Save pokemon nickname if provided
        binding.nicknameInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().trim() != "") {
                    nickname = binding.nicknameInput.text.toString()
                }
            }
        })

        binding.IntroGoToMainMenu.setOnClickListener {
            handleThreading()
        }

        return binding.root
    }

    private fun handleThreading() {
        val activity: MainActivity =  context as MainActivity
        binding.IntroGoToMainMenu.isEnabled = false;
        Toast.makeText(activity, "Loading...", Toast.LENGTH_SHORT).show()
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                pokemon = PokemonCreation().createPokemon(starterPokemon, nickname, 5)
                PokemonCreation().setURLToBitMapImages(pokemon)
                lifecycleScope.launch(Dispatchers.Main){
                    val intent = Intent(activity, MenuActivity::class.java)
                    intent.putExtra("pokemon", pokemon)
                    startActivity(intent)
                }
            }catch (e: Exception){
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(activity, "Pokemon Data not found", Toast.LENGTH_SHORT).show()
                }
                binding.IntroGoToMainMenu.isEnabled = true;
            }
        }
    }

}