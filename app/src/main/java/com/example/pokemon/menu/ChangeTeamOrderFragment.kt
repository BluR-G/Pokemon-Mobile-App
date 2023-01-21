package com.example.pokemon.menu

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.R
import com.example.pokemon.databinding.FragmentChangeTeamOrderBinding
import com.example.pokemon.objects.Pokemon
import com.example.pokemon.objects.PokemonTeam

class ChangeTeamOrderFragment : Fragment() {
    lateinit var menuActivity: MenuActivity
    lateinit var recyclerView: RecyclerView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentChangeTeamOrderBinding.inflate(layoutInflater)
        binding.apply.setOnClickListener { view : View ->
            val indexList = getPositions()
            if(indexList.size == indexList.distinct().count()){
                if(invalidIndexCheck(indexList)){
                    changeOrder(indexList)
                    view.findNavController().navigate(R.id.action_changeTeamOrderFragment_to_teamFragment)
                } else {
                    Toast.makeText(context, "invalid numbers found",Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "duplicate values found",Toast.LENGTH_SHORT).show()
            }

        }
        binding.cancelButton.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_changeTeamOrderFragment_to_teamFragment)
        }
        menuActivity = context as MenuActivity
        return binding.root
    }

    private fun changeOrder(indexList: ArrayList<Int>) {
        val pokemonTeam = menuActivity.getTeam()
        val hMap: MutableMap<Int, Pokemon> = HashMap()
        for(i in 0 until pokemonTeam.getSize()){
            hMap[indexList[i]] = pokemonTeam.getPokemon(i)
        }
        menuActivity.getTeam().setNewTeamOrder(hMap)
    }

    private fun invalidIndexCheck(indexList: ArrayList<Int>): Boolean {
        for(i in indexList){
            if(i >= indexList.size){
                return false
            }
        }
        return true
    }

    private fun getPositions() : ArrayList<Int> {
        val indexList = ArrayList<Int>()
        for(i in 0 until menuActivity.getTeam().getSize()){
            val view : View = recyclerView.getChildAt(i)
            val pokemonPosition : EditText = view.findViewById(R.id.orderNumber)
            val position : Int = Integer.parseInt(pokemonPosition.text.toString())
            indexList.add(position)
        }
        return indexList
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pokemonTeam = menuActivity.getTeam()
        recyclerView = view.findViewById(R.id.change_team_order_recycler_view)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            recyclerView.adapter = ChangeTeamOrderAdapter(pokemonTeam)
        }

    }

}