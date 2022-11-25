package com.example.pokemon

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.pokemon.databinding.FragmentIntroBinding

class IntroFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentIntroBinding.inflate(layoutInflater)

        var isUserNameValid : Boolean = false;
        var isStarterPicked : Boolean = false;
        var username : String = "";
        var starterPokemon : String = "";
        var nickname : String = "";

        binding.IntroGoToMainMenu.setOnClickListener {
            val intent = Intent(activity, MenuActivity::class.java)
            startActivity(intent)
        }

        // Enable button if username is provided and a radiobutton is checked
        binding.usernameInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().trim() != "") {
                    isUserNameValid = true;
                    username = binding.usernameInput.text.toString()
                    if (isStarterPicked) {
                        binding.IntroGoToMainMenu.isEnabled = true;
                    }
                }
            }
        })

        // Enable button if a radiobutton is checked and username is provided
        binding.bulbasaurRBtn.setOnClickListener {
            isStarterPicked = true;
            starterPokemon = "Bulbasaur";
            if (isUserNameValid) {
                binding.IntroGoToMainMenu.isEnabled = true;
            }
        }
        binding.charmanderRBtn.setOnClickListener {
            isStarterPicked = true;
            starterPokemon = "Charmander";
            if (isUserNameValid) {
                binding.IntroGoToMainMenu.isEnabled = true;
            }
        }
        binding.squirtleRBtn.setOnClickListener {
            isStarterPicked = true;
            starterPokemon = "Squirtle";
            if (isUserNameValid) {
                binding.IntroGoToMainMenu.isEnabled = true;
            }
        }

        // Save pokemon nickname if provided
        binding.nicknameInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().trim() != "") {
                    nickname = binding.nicknameInput.text.toString()
                }
            }
        })

        return binding.root
    }

}