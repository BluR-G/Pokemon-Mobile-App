package com.example.pokemon.database

import androidx.room.*

@Entity
data class Pokemon(
    @PrimaryKey val pokemon_id: Int,
    @ColumnInfo(name = "species") val species : String,
    @ColumnInfo(name = "images") val images : String,
    @ColumnInfo(name = "experience") val experience: String,
    @ColumnInfo(name = "types") val types: String,
    @ColumnInfo(name = "maxHp") val maxHp: Int,
    @ColumnInfo(name = "attack") val attack: Int,
    @ColumnInfo(name = "defense") val defense: Int,
    @ColumnInfo(name = "special_attack") val specialAttack: Int,
    @ColumnInfo(name = "special_defense") val sDef: Int,
    @ColumnInfo(name = "Speed") val Spd: Int
)

@Entity
data class Move(
    @PrimaryKey(autoGenerate = true) val move_id: Int,
    @ColumnInfo val accuracy: Int,
    @ColumnInfo val power: Int,
    @ColumnInfo val damageClass: String,
    @ColumnInfo val heal: Int,
    @ColumnInfo val target: String,
    @ColumnInfo val type: String,
)


@Entity(primaryKeys = ["pokemon_id", "move_id"])
data class PokemonWithMoves(
    val pokemon_id: Int,
    val move_id: Int,
    //TODO: How does adding extra column in bridging table works in SQLite?
)

data class PokemonMovePair(
    @Embedded
    var pokemon: Pokemon,

    @Relation(
        parentColumn = "pokemon_id",
        entity = Move::class,
        entityColumn = "move_id",
        associateBy = Junction(
            value = PokemonWithMoves::class,
            parentColumn = "pokemon_id",
            entityColumn = "move_id"
        )
    )
    var move: List<Move>
)