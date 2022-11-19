package com.example.pokemon.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.pokemon.activities.FightActivity
import com.example.pokemon.R
import com.example.pokemon.databinding.FragmentMainMenuBinding


class MainMenuFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainMenuBinding.inflate(layoutInflater)
        binding.goToTeam.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_mainMenuFragment_to_teamFragment)
        }
        binding.goToPokeCenter.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_mainMenuFragment_to_pokeCenterFragment)
        }
        binding.goToTrainerBattle.setOnClickListener { view: View ->
            val intent = Intent(activity, FightActivity::class.java)
            startActivity(intent)
        }
        binding.goToWildBattle.setOnClickListener{ view: View ->
            val intent = Intent(activity, FightActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }




}