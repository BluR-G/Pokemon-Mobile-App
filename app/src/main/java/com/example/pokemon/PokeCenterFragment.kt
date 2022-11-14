package com.example.pokemon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.pokemon.databinding.FragmentPokeCenterBinding


class PokeCenterFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPokeCenterBinding.inflate(layoutInflater)
        binding.pokeCenterGoToMainMenu.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_pokeCenterFragment_to_mainMenuFragment)
        }
        return binding.root
    }

}