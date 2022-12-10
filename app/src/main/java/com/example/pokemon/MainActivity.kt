package com.example.pokemon

import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.pokemon.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var mediaPlayer: MediaPlayer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launch(Dispatchers.IO){
            playAudio()
        }
    }

    fun playAudio() {
        val audioURL = "https://vgmsite.com/soundtracks/pokemon-game-boy-pok-mon-sound-complete-set-play-cd/vfywpihuos/1-01.%20Opening.mp3"
        mediaPlayer = MediaPlayer()
        mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
        try {
            mediaPlayer!!.setDataSource(audioURL)
            mediaPlayer!!.prepare()
            mediaPlayer!!.start()
            while(true) {
                if(!mediaPlayer!!.isPlaying){
                    mediaPlayer!!.stop()
                    mediaPlayer!!.reset()
                    mediaPlayer!!.setDataSource(audioURL)
                    mediaPlayer!!.prepare()
                    mediaPlayer!!.start()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}