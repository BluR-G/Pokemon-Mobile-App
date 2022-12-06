package com.example.pokemon.database

import androidx.room.*

@Dao
interface PokemonDAO {

    @Transaction
    @Query("SELECT * FROM PlayerPokemon WHERE id = :id")
    fun getPlayerPokemonById(id: Int) : PlayerPokemon

    @Transaction
    @Query("SELECT id FROM PlayerPokemon WHERE pokemon_id = :id AND name = :name")
    fun getPlayerPokemonByIdAndName(id: Int, name: String) : PlayerPokemon

    @Transaction
    @Query("SELECT * FROM PokemonTeam WHERE position_id = :pos_id")
    fun getPokemonTeamByPosition(pos_id: Int) : PokemonTeam

    @Transaction
    @Query("SELECT * FROM PokemonCollection WHERE position_id = :pos_id")
    fun getPokemonCollectionByPosition(pos_id: Int) : PokemonCollection

    @Transaction
    @Query("SELECT * FROM PokemonTeam")
    fun getPokemonTeam(): List<PokemonInTeam>

    @Transaction
    @Query("SELECT * FROM PokemonCollection")
    fun getPokemonCollection(): List<PokemonInCollection>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPokemon(pokemon: PlayerPokemon)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTeam(pokemonTeam: PokemonTeam)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonInTeam(pokemonInTeam: PokemonInTeam)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCollection(pokemonTeam: PokemonCollection)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonInCollection(pokemonInCollection: PokemonInCollection)

//    @Transaction
//    @Query("SELECT * FROM pokemon WHERE pokemon_id = :id")
//    fun getPokemonById(id: String) : Pokemon
//
//    @Transaction
//    @Query("SELECT * FROM pokemon WHERE species = :species")
//    fun getPokemonByName(species: String) : Pokemon
//
//    @Transaction
//    @Query("Select * FROM pokemon WHERE pokemon_id = :poke_id")
//    fun findMovesByPokemonId(poke_id: Int) : List<PokemonMovePair>
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun insertPokemon(pokemon : Pokemon)
//

    @Query("DELETE FROM PokemonTeam")
    fun clearTeam()

    @Query("DELETE FROM PokemonCollection")
    fun clearCollection()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMove(move: Move)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPokemonWithMoves(playerPokemonMovePair: PlayerPokemonMovePair)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPokemonWithCurrentMoves(playerPokemonCurrentMovePair: PlayerPokemonCurrentMovePair)

}