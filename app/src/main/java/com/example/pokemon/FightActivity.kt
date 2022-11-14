package com.example.pokemon

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pokemon.databinding.ActivityFightBinding
import com.example.pokemon.databinding.ActivityMainBinding

class FightActivity : AppCompatActivity(){
    lateinit var binding: ActivityFightBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFightBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}