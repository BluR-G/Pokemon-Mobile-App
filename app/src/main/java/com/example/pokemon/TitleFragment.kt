package com.example.pokemon

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.pokemon.database.PokemonRoomDatabase
import com.example.pokemon.databinding.FragmentTitleBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.pokemon.database.*
import com.example.pokemon.menu.MenuActivity


class TitleFragment : Fragment() {
    lateinit var mainActivity: MainActivity
    private val database by lazy { PokemonRoomDatabase.getDatabase(mainActivity)}
    private lateinit var playerPokemon : List<PlayerPokemon>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = context as MainActivity
        val binding = FragmentTitleBinding.inflate(layoutInflater)
        binding.goToIntro.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_titleFragment_to_introFragment)
        }
        lifecycleScope.launch(Dispatchers.IO) {
            val isAvailable = isSaveAvailable()
            lifecycleScope.launch(Dispatchers.Main){
                binding.continueGame.isEnabled = isAvailable
            }
        }
        binding.continueGame.setOnClickListener {
            val intent = Intent(activity, MenuActivity::class.java)
            intent.putExtra("fromContinue", true)
            startActivity(intent)
        }
        return binding.root
    }

    private fun isSaveAvailable(): Boolean {
        playerPokemon = database.PokemonDAO().getPlayerPokemons()
        return playerPokemon.isNotEmpty()
    }
}