package com.example.pokemon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.pokemon.R
import com.example.pokemon.databinding.FragmentTeamBinding


class TeamFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTeamBinding.inflate(layoutInflater)
        binding.TeamGoToMainMenu.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_teamFragment_to_mainMenuFragment)
        }
        return binding.root
    }
}