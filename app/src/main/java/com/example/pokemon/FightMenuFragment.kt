package com.example.pokemon

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.pokemon.databinding.FragmentFightMenuBinding


class FightMenuFragment : Fragment()  {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFightMenuBinding.inflate(layoutInflater)
        binding.selectFight.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_fightMenuFragment_to_fightFragment)
        }
        binding.selectBag.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_fightMenuFragment_to_bagFragment)
        }
        binding.selectTeam.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_fightMenuFragment_to_fightPokemonTeamFragment)
        }
        binding.selectRun.setOnClickListener{ view: View ->
        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent)
        }
        return binding.root
    }
}