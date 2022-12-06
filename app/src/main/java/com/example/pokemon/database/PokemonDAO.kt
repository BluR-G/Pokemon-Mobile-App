package com.example.pokemon.database

import androidx.room.*

@Dao
interface PokemonDAO {

    @Transaction
    @Query("SELECT * FROM PlayerPokemon")
    fun getPlayerPokemons() : List<PlayerPokemon>

    @Transaction
    @Query("SELECT id FROM PlayerPokemon WHERE position = :position " +
            "AND isTeam = :isTeam " +
            "AND pokemon_id = :pokemon_id " +
            "AND name = :name")
    fun getPlayerPokemonId(position: Int, isTeam: Int, pokemon_id: Int, name : String) : Int
//
//    @Transaction
//    @Query("SELECT * FROM PokemonTeam WHERE position_id = :pos_id")
//    fun getPokemonTeamByPosition(pos_id: Int) : PokemonTeam
//
//    @Transaction
//    @Query("SELECT * FROM PokemonCollection WHERE position_id = :pos_id")
//    fun getPokemonCollectionByPosition(pos_id: Int) : PokemonCollection
//
//    @Transaction
//    @Query("SELECT * FROM PokemonTeam")
//    fun getPokemonTeam(): List<PokemonInTeam>
//
//    @Transaction
//    @Query("SELECT * FROM PokemonCollection")
//    fun getPokemonCollection(): List<PokemonInCollection>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPokemon(pokemon: PlayerPokemon)

//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun insertTeam(pokemonTeam: PokemonTeam)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertPokemonInTeam(pokemonInTeam: PokemonInTeam)
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun insertCollection(pokemonTeam: PokemonCollection)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertPokemonInCollection(pokemonInCollection: PokemonInCollection)

//    @Transaction
//    @Query("SELECT * FROM pokemon WHERE pokemon_id = :id")
//    fun getPokemonById(id: String) : Pokemon
//
//    @Transaction
//    @Query("SELECT * FROM pokemon WHERE species = :species")
//    fun getPokemonByName(species: String) : Pokemon
//
    @Transaction
    @Query("Select * FROM PlayerPokemon WHERE id = :poke_id")
    fun findMovesByPokemonId(poke_id: Int) : List<PlayerPokemonMovePair>
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun insertPokemon(pokemon : Pokemon)
//

//    @Query("DELETE FROM PokemonTeam")
//    fun clearTeam()
//
//    @Query("DELETE FROM PokemonCollection")
//    fun clearCollection()

    @Query("DELETE FROM playerpokemon")
    fun clearPlayerPokemons()

    @Query("DELETE FROM pokemonwithmoves")
    fun clearPokemonWithMoves()

    @Query("DELETE FROM pokemonwithcurrentmoves")
    fun clearPokemonWithCurrentMoves()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMove(move: Move)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemonWithMoves(pokemonWithMoves: PokemonWithMoves)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemonWithCurrentMoves(pokemonWithCurrentMoves: PokemonWithCurrentMoves)

}