package com.example.pokemon

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.pokemon.databinding.FragmentMainMenuBinding
import com.example.pokemon.fight.FightActivity


class MainMenuFragment : Fragment() {

    lateinit var menuActivity: MenuActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        menuActivity = activity as MenuActivity
        val binding = FragmentMainMenuBinding.inflate(layoutInflater)
        binding.goToTeam.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_mainMenuFragment_to_teamFragment)
        }
        binding.goToPokeCenter.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_mainMenuFragment_to_pokeCenterFragment)
        }
        binding.goToTrainerBattle.setOnClickListener { switchFight() }
        binding.goToWildBattle.setOnClickListener{ switchFight() }

        return binding.root
    }

    private fun switchFight() {
        val intent = Intent(menuActivity, FightActivity::class.java)
        intent.putExtra("pokemonTeam", menuActivity.getPokemonTeam())
        menuActivity.getResult.launch(intent)
    }


}