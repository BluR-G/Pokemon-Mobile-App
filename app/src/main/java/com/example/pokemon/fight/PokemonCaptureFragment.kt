package com.example.pokemon.fight

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.pokemon.R
import com.example.pokemon.databinding.FragmentPokemonCaptureBinding


class PokemonCaptureFragment : Fragment()  {
    lateinit var fightActivity: FightActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
          fightActivity = context as FightActivity
        val binding = FragmentPokemonCaptureBinding.inflate(layoutInflater)
        binding.pokemonNickname.setText(fightActivity.getEnemyPokemon().getName())
        binding.nameButton.setOnClickListener{
            val pokemonName = binding.pokemonNickname.text.toString()
            fightActivity.setCapturedName(pokemonName)
        }
        return binding.root
    }
}