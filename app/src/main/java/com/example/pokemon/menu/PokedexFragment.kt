package com.example.pokemon.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.pokemon.R
import com.example.pokemon.databinding.ActivityMenuBinding
import com.example.pokemon.databinding.FragmentPokedexBinding
import com.example.pokemon.databinding.FragmentTeamBinding
import com.example.pokemon.objects.Pokemon

class PokedexFragment : Fragment() {
    lateinit var pokemon : Pokemon
    lateinit var menuActivity: MenuActivity
    lateinit var binding : FragmentPokedexBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPokedexBinding.inflate(layoutInflater)
        binding.goBack.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_pokedexFragment_to_teamFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        menuActivity = context as MenuActivity
        if(menuActivity.getPokemon() == null){
            throw NullPointerException()
        }
        pokemon = menuActivity.getPokemon()!!
        printPokemon()
    }

    private fun printPokemon() {
        binding.
    }

}