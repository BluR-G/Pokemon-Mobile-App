package com.example.pokemon.database

import androidx.room.*

@Dao
interface PokemonDAO {

    @Transaction
    @Query("SELECT * FROM pokemon WHERE pokemon_id = :id")
    fun getPokemonById(id: String) : Pokemon

    @Transaction
    @Query("SELECT * FROM pokemon WHERE species = :species")
    fun getPokemonByName(species: String) : Pokemon

    @Transaction
    @Query("Select * FROM pokemon WHERE pokemon_id = :poke_id")
    fun findMovesByPokemonId(poke_id: Int) : List<PokemonMovePair>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPokemon(pokemon : Pokemon)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMove(move: Move)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPokemonWithMoves(pokemonWithMoves: PokemonWithMoves)

}