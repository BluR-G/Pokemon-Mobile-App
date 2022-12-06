package com.example.pokemon.database

import androidx.room.*

//@Entity
//data class Pokemon(
//    @PrimaryKey val pokemon_id: Int,
//    @ColumnInfo(name = "species") val species : String,
//    @ColumnInfo(name = "images") val images : String,
//    @ColumnInfo(name = "experience") val experience: String,
//    @ColumnInfo(name = "types") val types: String,
//    @ColumnInfo(name = "maxHp") val maxHp: Int,
//    @ColumnInfo(name = "attack") val attack: Int,
//    @ColumnInfo(name = "defense") val defense: Int,
//    @ColumnInfo(name = "special_attack") val specialAttack: Int,
//    @ColumnInfo(name = "special_defense") val specialDefense: Int,
//    @ColumnInfo(name = "Speed") val speed: Int
//)

@Entity
data class PlayerPokemon(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "pokemon_id") val pokemon_id : Int,
    @ColumnInfo(name = "species") val species : String,
    @ColumnInfo(name = "name") val name : String,
    @ColumnInfo(name = "images") val images : String,
    @ColumnInfo(name = "currentXP") val currentXP: Int,
    @ColumnInfo(name = "level") val level: Int,
    @ColumnInfo(name = "types") val types: String,
    @ColumnInfo(name = "maxHp") val maxHp: Int,
    @ColumnInfo(name = "currentHp") val currentHp: Int,
    @ColumnInfo(name = "attack") val attack: Int,
    @ColumnInfo(name = "defense") val defense: Int,
    @ColumnInfo(name = "special_attack") val specialAttack: Int,
    @ColumnInfo(name = "special_defense") val specialDefense: Int,
    @ColumnInfo(name = "Speed") val speed: Int
)
@Entity
data class PokemonTeam(
    @PrimaryKey val position_id : Int
)

data class PokemonInTeam(
    @Embedded val pokemon: PlayerPokemon,
    @Relation(
        parentColumn = "position_id",
        entityColumn = "id"
    )
    val position: PokemonTeam
)

@Entity
data class PokemonCollection(
    @PrimaryKey val position_id : Int
)

data class PokemonInCollection(
    @Embedded val pokemon: PlayerPokemon,
    @Relation(
        parentColumn = "position_id",
        entityColumn = "id"
    )
    val position: PokemonCollection
)

@Entity
data class Move(
    @PrimaryKey val move: String,
    @ColumnInfo val accuracy: Int,
    @ColumnInfo val power: Int,
    @ColumnInfo val damageClass: String,
    @ColumnInfo val heal: Int,
    @ColumnInfo val target: String,
    @ColumnInfo val type: String,
)


@Entity(primaryKeys = ["pokemon_id", "move"])
data class PokemonWithMoves(
    val pokemon_id: Int,
    val move: String,
    val level_learned_at: Int
)

@Entity(primaryKeys = ["pokemon_id", "move"])
data class PokemonWithCurrentMoves(
    val pokemon_id: Int,
    val move: String,
    val level_learned_at: Int
)

data class PlayerPokemonCurrentMovePair(
    @Embedded
    var pokemon: PlayerPokemon,

    @Relation(
        parentColumn = "pokemon_id",
        entity = Move::class,
        entityColumn = "move",
        associateBy = Junction(
            value = PokemonWithMoves::class,
            parentColumn = "pokemon_id",
            entityColumn = "move"
        )
    )
    var move: List<Move>
)
data class PlayerPokemonMovePair(
    @Embedded
    var pokemon: PlayerPokemon,

    @Relation(
        parentColumn = "pokemon_id",
        entity = Move::class,
        entityColumn = "move",
        associateBy = Junction(
            value = PokemonWithCurrentMoves::class,
            parentColumn = "pokemon_id",
            entityColumn = "move"
        )
    )
    var move: List<Move>
)