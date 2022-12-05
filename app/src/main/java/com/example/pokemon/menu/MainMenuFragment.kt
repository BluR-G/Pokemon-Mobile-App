package com.example.pokemon.menu

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.pokemon.fight.FightActivity
import com.example.pokemon.R
import com.example.pokemon.databinding.FragmentMainMenuBinding



class MainMenuFragment : Fragment() {
    private lateinit var menuActivity : MenuActivity
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
        binding.goToTrainerBattle.setOnClickListener { switchTrainerBattle() }
        binding.goToWildBattle.setOnClickListener{ switchWildBattle() }
        return binding.root
    }
    private fun switchBattle(type:String){
        val intent = Intent(menuActivity, FightActivity::class.java)
        intent.putExtra("pokemonTeam", menuActivity.getTeam())
        intent.putExtra("pokemonCollection", menuActivity.getCollect())
        intent.putExtra("battleType", type)
        menuActivity.getResult.launch(intent)
    }
    private fun switchTrainerBattle() {
        if(!menuActivity.getTeam().isTeamDead()){
            switchBattle("trainer")
        }
    }
    private fun switchWildBattle() {
        if(!menuActivity.getTeam().isTeamDead()) {
            switchBattle("wild")
        }
    }



}