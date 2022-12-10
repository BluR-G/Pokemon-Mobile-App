package com.example.pokemon.database

import androidx.room.*

@Dao
interface PokemonDAO {

    @Transaction
    @Query("SELECT * FROM Player")
    fun getPlayer() : String

    @Transaction
    @Query("SELECT * FROM PlayerPokemon")
    fun getPlayerPokemons() : List<PlayerPokemon>

    @Transaction
    @Query("SELECT * FROM Move where move = :move")
    fun getMove(move: String) : Move

    @Transaction
    @Query("SELECT * FROM PokemonWithMoves where id = :id")
    fun getPokemonMoves(id : Int) : List<PokemonWithMoves>

    @Transaction
    @Query("SELECT * FROM PokemonWithCurrentMoves where id = :id")
    fun getPokemonCurrentMoves(id : Int) : List<PokemonWithCurrentMoves>

    @Query("DELETE FROM playerpokemon")
    fun clearPlayerPokemons()

    @Query("DELETE FROM pokemonwithmoves")
    fun clearPokemonWithMoves()

    @Query("DELETE FROM pokemonwithcurrentmoves")
    fun clearPokemonWithCurrentMoves()

    @Query("DELETE FROM player")
    fun clearPlayer()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPokemon(pokemon: PlayerPokemon)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMove(move: Move)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemonWithMoves(pokemonWithMoves: PokemonWithMoves)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemonWithCurrentMoves(pokemonWithCurrentMoves: PokemonWithCurrentMoves)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPlayer(player: Player)

}