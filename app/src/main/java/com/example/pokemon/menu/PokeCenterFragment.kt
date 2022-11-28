package com.example.pokemon.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.pokemon.R
import com.example.pokemon.databinding.FragmentPokeCenterBinding


class PokeCenterFragment : Fragment() {
    lateinit var menuActivity: MenuActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPokeCenterBinding.inflate(layoutInflater)
        binding.pokeCenterGoToMainMenu.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_pokeCenterFragment_to_mainMenuFragment)
        }
        menuActivity = context as MenuActivity
        val pokemonTeam = menuActivity.getTeam()

        binding.healButton.setOnClickListener {
            pokemonTeam.healAllPokemons()
            Toast.makeText(context, "All pokemon in your team successfully healed", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }
}