package com.example.pokemon.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.pokemon.R
import com.example.pokemon.databinding.FragmentTitleBinding


class TitleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTitleBinding.inflate(layoutInflater)
        binding.goToIntro.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_titleFragment_to_introFragment)
        }
        return binding.root
    }
}