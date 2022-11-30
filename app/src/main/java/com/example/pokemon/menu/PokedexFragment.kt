package com.example.pokemon.menu

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokemon.R
import com.example.pokemon.databinding.FragmentPokedexBinding
import com.example.pokemon.objects.Pokemon

class PokedexFragment : Fragment() {
    lateinit var pokemon : Pokemon
    lateinit var menuActivity: MenuActivity
    lateinit var binding : FragmentPokedexBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPokedexBinding.inflate(layoutInflater)
        binding.goBack.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_pokedexFragment_to_teamFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        menuActivity = context as MenuActivity
        if(menuActivity.getPokemon() == null){
            throw NullPointerException()
        }
        pokemon = menuActivity.getPokemon()!!
        printPokemon()
    }

    @SuppressLint("SetTextI18n")
    private fun printPokemon() {
        val img = pokemon.getImages()
        val imgFront = img[0]
        val imageFrontBytes = Base64.decode(imgFront, 0)
        val imageFront = BitmapFactory.decodeByteArray(imageFrontBytes, 0, imageFrontBytes.size)
        binding.pokemonFrontImage.setImageBitmap(imageFront)
        val imgBack = img[1]
        val imageBackBytes = Base64.decode(imgBack, 0)
        val imageBack = BitmapFactory.decodeByteArray(imageBackBytes, 0, imageBackBytes.size)

        binding.pokemonBackImage.setImageBitmap(imageBack)
        binding.pokemonName.text = "Name: ${pokemon.getSpecies()}"
        binding.pokemonId.text = "ID: ${pokemon.getId()}"
        val types = pokemon.getTypes()
        var pokemonType = ""
        for (type in types) {
            pokemonType += if (type == types[types.size - 1]) {
                type
            } else {
                "$type, "
            }
        }
        if(types.size > 1){
            binding.pokemonType.text = "Types: $pokemonType"
        } else {
            binding.pokemonType.text = "Type: $pokemonType"
        }

        binding.pokemonHp.text = "HP: ${pokemon.getMaxHp()}"
        binding.pokemonAttack.text = "ATK: ${pokemon.getAttack()}"
        binding.pokemonDefense.text = "DEF: ${pokemon.getDefense()}"
        binding.pokemonSpecialAttack.text = "S_ATK: ${pokemon.getSpecialAttack()}"
        binding.pokemonSpecialDefense.text = "S_DEF: ${pokemon.getSpecialDefense()}"
        binding.pokemonSpeed.text = "SPEED: ${pokemon.getSpeed()}"

        val moveList = pokemon.getMoves()
        val recyclerView = binding.moveList
        recyclerView.layoutManager = LinearLayoutManager(menuActivity)
        recyclerView.adapter = PokedexMoveAdapter(moveList)
    }

}