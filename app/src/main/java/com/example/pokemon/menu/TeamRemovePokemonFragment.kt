package com.example.pokemon.menu

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.R
import com.example.pokemon.databinding.FragmentTeamRemovePokemonBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL


class TeamRemovePokemonFragment : Fragment() {
    lateinit var menuActivity: MenuActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTeamRemovePokemonBinding.inflate(layoutInflater)
        binding.removePokemonBack.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_teamRemovePokemonFragment_to_teamFragment)
        }
        menuActivity = context as MenuActivity
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pokemonTeam = menuActivity.getTeam()
        val recyclerView = view.findViewById<RecyclerView>(R.id.remove_pokemon_recycler_view)
        recyclerView.apply {
            layoutManager = GridLayoutManager(activity, 3)
            recyclerView.adapter = PokemonTeamAdapter(pokemonTeam, view.context)
        }
        //val pokemonImageBitmaps = ArrayList<Bitmap>(pokemonTeam.getSize())
//        lifecycleScope.launch(Dispatchers.IO){
//            for(i in 0 until pokemonTeam.getSize()){
//                val sprites : ArrayList<String> = pokemonTeam.getPokemon(i).getImages()
//                val frontSprite = sprites[0]
//                val imageURL = URL(frontSprite)
//                val imageBitmap = BitmapFactory.decodeStream(imageURL.openConnection() .getInputStream());
//                pokemonImageBitmaps.add(imageBitmap)
//            }
//            lifecycleScope.launch(Dispatchers.Main){
//                val recyclerView = view.findViewById<RecyclerView>(R.id.remove_pokemon_recycler_view)
//                recyclerView.apply {
//                    layoutManager = GridLayoutManager(activity, 3)
//                    recyclerView.adapter = PokemonTeamAdapter(pokemonTeam, pokemonImageBitmaps, view.context)
//                }
//            }
//        }

    }
}