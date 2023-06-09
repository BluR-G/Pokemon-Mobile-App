package com.example.pokemon.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [
    PlayerPokemon::class,
    Move::class,
    PokemonWithMoves::class,
    PokemonWithCurrentMoves::class,
    Player::class
                     ], version = 1, exportSchema = false)
abstract class PokemonRoomDatabase : RoomDatabase() {

    abstract fun PokemonDAO(): PokemonDAO

    companion object {
        @Volatile
        private var INSTANCE: PokemonRoomDatabase? = null

        fun getDatabase(context: Context): PokemonRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PokemonRoomDatabase::class.java,
                    "pokemon_database")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}