package com.example.pokemon.fight

class DamageChart {
    private val arr = Array(15) { DoubleArray(15) }
    private val typesMap = mapOf(
        "normal" to 0,
        "fire" to 1,
        "water" to 2,
        "electric" to 3,
        "grass" to 4,
        "ice" to 5,
        "fighting" to 6,
        "poison" to 7,
        "ground" to 8,
        "flying" to 9,
        "psychic" to 10,
        "bug" to 11,
        "rock" to 12,
        "ghost" to 13,
        "dragon" to 14
    )

    constructor(){
        // initialize the values of the 2D array
        arr[getIndexOfType("normal")][getIndexOfType("normal")] = 1.0
        arr[getIndexOfType("normal")][getIndexOfType("fire")] = 1.0
        arr[getIndexOfType("normal")][getIndexOfType("water")] = 1.0
        arr[getIndexOfType("normal")][getIndexOfType("electric")] = 1.0
        arr[getIndexOfType("normal")][getIndexOfType("grass")] = 1.0
        arr[getIndexOfType("normal")][getIndexOfType("ice")] = 1.0
        arr[getIndexOfType("normal")][getIndexOfType("fighting")] = 1.0
        arr[getIndexOfType("normal")][getIndexOfType("poison")] = 1.0
        arr[getIndexOfType("normal")][getIndexOfType("ground")] = 1.0
        arr[getIndexOfType("normal")][getIndexOfType("flying")] = 1.0
        arr[getIndexOfType("normal")][getIndexOfType("psychic")] = 1.0
        arr[getIndexOfType("normal")][getIndexOfType("bug")] = 1.0
        arr[getIndexOfType("normal")][getIndexOfType("rock")] = 0.5
        arr[getIndexOfType("normal")][getIndexOfType("ghost")] = 0.0
        arr[getIndexOfType("normal")][getIndexOfType("dragon")] = 1.0

        arr[getIndexOfType("fire")][getIndexOfType("normal")] = 1.0
        arr[getIndexOfType("fire")][getIndexOfType("fire")] = 0.5
        arr[getIndexOfType("fire")][getIndexOfType("water")] = 0.5
        arr[getIndexOfType("fire")][getIndexOfType("electric")] = 1.0
        arr[getIndexOfType("fire")][getIndexOfType("grass")] = 2.0
        arr[getIndexOfType("fire")][getIndexOfType("ice")] = 2.0
        arr[getIndexOfType("fire")][getIndexOfType("fighting")] = 1.0
        arr[getIndexOfType("fire")][getIndexOfType("poison")] = 1.0
        arr[getIndexOfType("fire")][getIndexOfType("ground")] = 1.0
        arr[getIndexOfType("fire")][getIndexOfType("flying")] = 1.0
        arr[getIndexOfType("fire")][getIndexOfType("psychic")] = 1.0
        arr[getIndexOfType("fire")][getIndexOfType("bug")] = 2.0
        arr[getIndexOfType("fire")][getIndexOfType("rock")] = 0.5
        arr[getIndexOfType("fire")][getIndexOfType("ghost")] = 1.0
        arr[getIndexOfType("fire")][getIndexOfType("dragon")] = 0.5

        arr[getIndexOfType("water")][getIndexOfType("normal")] = 1.0
        arr[getIndexOfType("water")][getIndexOfType("fire")] = 2.0
        arr[getIndexOfType("water")][getIndexOfType("water")] = 0.5
        arr[getIndexOfType("water")][getIndexOfType("electric")] = 1.0
        arr[getIndexOfType("water")][getIndexOfType("grass")] = 0.5
        arr[getIndexOfType("water")][getIndexOfType("ice")] = 1.0
        arr[getIndexOfType("water")][getIndexOfType("fighting")] = 1.0
        arr[getIndexOfType("water")][getIndexOfType("poison")] = 1.0
        arr[getIndexOfType("water")][getIndexOfType("ground")] = 2.0
        arr[getIndexOfType("water")][getIndexOfType("flying")] = 1.0
        arr[getIndexOfType("water")][getIndexOfType("psychic")] = 1.0
        arr[getIndexOfType("water")][getIndexOfType("bug")] = 1.0
        arr[getIndexOfType("water")][getIndexOfType("rock")] = 2.0
        arr[getIndexOfType("water")][getIndexOfType("ghost")] = 1.0
        arr[getIndexOfType("water")][getIndexOfType("dragon")] = 0.5

        arr[getIndexOfType("electric")][getIndexOfType("normal")] = 1.0
        arr[getIndexOfType("electric")][getIndexOfType("fire")] = 1.0
        arr[getIndexOfType("electric")][getIndexOfType("water")] = 2.0
        arr[getIndexOfType("electric")][getIndexOfType("electric")] = 0.5
        arr[getIndexOfType("electric")][getIndexOfType("grass")] = 0.5
        arr[getIndexOfType("electric")][getIndexOfType("ice")] = 1.0
        arr[getIndexOfType("electric")][getIndexOfType("fighting")] = 1.0
        arr[getIndexOfType("electric")][getIndexOfType("poison")] = 1.0
        arr[getIndexOfType("electric")][getIndexOfType("ground")] = 0.0
        arr[getIndexOfType("electric")][getIndexOfType("flying")] = 2.0
        arr[getIndexOfType("electric")][getIndexOfType("psychic")] = 1.0
        arr[getIndexOfType("electric")][getIndexOfType("bug")] = 1.0
        arr[getIndexOfType("electric")][getIndexOfType("rock")] = 1.0
        arr[getIndexOfType("electric")][getIndexOfType("ghost")] = 1.0
        arr[getIndexOfType("electric")][getIndexOfType("dragon")] = 0.5

        arr[getIndexOfType("grass")][getIndexOfType("normal")] = 1.0
        arr[getIndexOfType("grass")][getIndexOfType("fire")] = 0.5
        arr[getIndexOfType("grass")][getIndexOfType("water")] = 2.0
        arr[getIndexOfType("grass")][getIndexOfType("electric")] = 1.0
        arr[getIndexOfType("grass")][getIndexOfType("grass")] = 0.5
        arr[getIndexOfType("grass")][getIndexOfType("ice")] = 1.0
        arr[getIndexOfType("grass")][getIndexOfType("fighting")] = 1.0
        arr[getIndexOfType("grass")][getIndexOfType("poison")] = 0.5
        arr[getIndexOfType("grass")][getIndexOfType("ground")] = 2.0
        arr[getIndexOfType("grass")][getIndexOfType("flying")] = 0.5
        arr[getIndexOfType("grass")][getIndexOfType("psychic")] = 1.0
        arr[getIndexOfType("grass")][getIndexOfType("bug")] = 0.5
        arr[getIndexOfType("grass")][getIndexOfType("rock")] = 2.0
        arr[getIndexOfType("grass")][getIndexOfType("ghost")] = 1.0
        arr[getIndexOfType("grass")][getIndexOfType("dragon")] = 0.5

        arr[getIndexOfType("ice")][getIndexOfType("normal")] = 1.0
        arr[getIndexOfType("ice")][getIndexOfType("fire")] = 0.5
        arr[getIndexOfType("ice")][getIndexOfType("water")] = 0.5
        arr[getIndexOfType("ice")][getIndexOfType("electric")] = 1.0
        arr[getIndexOfType("ice")][getIndexOfType("grass")] = 2.0
        arr[getIndexOfType("ice")][getIndexOfType("ice")] = 0.5
        arr[getIndexOfType("ice")][getIndexOfType("fighting")] = 1.0
        arr[getIndexOfType("ice")][getIndexOfType("poison")] = 1.0
        arr[getIndexOfType("ice")][getIndexOfType("ground")] = 2.0
        arr[getIndexOfType("ice")][getIndexOfType("flying")] = 2.0
        arr[getIndexOfType("ice")][getIndexOfType("psychic")] = 1.0
        arr[getIndexOfType("ice")][getIndexOfType("bug")] = 1.0
        arr[getIndexOfType("ice")][getIndexOfType("rock")] = 1.0
        arr[getIndexOfType("ice")][getIndexOfType("ghost")] = 1.0
        arr[getIndexOfType("ice")][getIndexOfType("dragon")] = 2.0

        arr[getIndexOfType("fighting")][getIndexOfType("normal")] = 2.0
        arr[getIndexOfType("fighting")][getIndexOfType("fire")] = 1.0
        arr[getIndexOfType("fighting")][getIndexOfType("water")] = 1.0
        arr[getIndexOfType("fighting")][getIndexOfType("electric")] = 1.0
        arr[getIndexOfType("fighting")][getIndexOfType("grass")] = 1.0
        arr[getIndexOfType("fighting")][getIndexOfType("ice")] = 2.0
        arr[getIndexOfType("fighting")][getIndexOfType("fighting")] = 1.0
        arr[getIndexOfType("fighting")][getIndexOfType("poison")] = 0.5
        arr[getIndexOfType("fighting")][getIndexOfType("ground")] = 1.0
        arr[getIndexOfType("fighting")][getIndexOfType("flying")] = 0.5
        arr[getIndexOfType("fighting")][getIndexOfType("psychic")] = 0.5
        arr[getIndexOfType("fighting")][getIndexOfType("bug")] = 0.5
        arr[getIndexOfType("fighting")][getIndexOfType("rock")] = 2.0
        arr[getIndexOfType("fighting")][getIndexOfType("ghost")] = 0.0
        arr[getIndexOfType("fighting")][getIndexOfType("dragon")] = 1.0

        arr[getIndexOfType("poison")][getIndexOfType("normal")] = 1.0
        arr[getIndexOfType("poison")][getIndexOfType("fire")] = 1.0
        arr[getIndexOfType("poison")][getIndexOfType("water")] = 1.0
        arr[getIndexOfType("poison")][getIndexOfType("electric")] = 1.0
        arr[getIndexOfType("poison")][getIndexOfType("grass")] = 2.0
        arr[getIndexOfType("poison")][getIndexOfType("ice")] = 1.0
        arr[getIndexOfType("poison")][getIndexOfType("fighting")] = 1.0
        arr[getIndexOfType("poison")][getIndexOfType("poison")] = 0.5
        arr[getIndexOfType("poison")][getIndexOfType("ground")] = 0.5
        arr[getIndexOfType("poison")][getIndexOfType("flying")] = 1.0
        arr[getIndexOfType("poison")][getIndexOfType("psychic")] = 1.0
        arr[getIndexOfType("poison")][getIndexOfType("bug")] = 1.0
        arr[getIndexOfType("poison")][getIndexOfType("rock")] = 0.5
        arr[getIndexOfType("poison")][getIndexOfType("ghost")] = 0.5
        arr[getIndexOfType("poison")][getIndexOfType("dragon")] = 1.0

        arr[getIndexOfType("ground")][getIndexOfType("normal")] = 1.0
        arr[getIndexOfType("ground")][getIndexOfType("fire")] = 2.0
        arr[getIndexOfType("ground")][getIndexOfType("water")] = 1.0
        arr[getIndexOfType("ground")][getIndexOfType("electric")] = 2.0
        arr[getIndexOfType("ground")][getIndexOfType("grass")] = 0.5
        arr[getIndexOfType("ground")][getIndexOfType("ice")] = 1.0
        arr[getIndexOfType("ground")][getIndexOfType("fighting")] = 1.0
        arr[getIndexOfType("ground")][getIndexOfType("poison")] = 2.0
        arr[getIndexOfType("ground")][getIndexOfType("ground")] = 1.0
        arr[getIndexOfType("ground")][getIndexOfType("flying")] = 0.0
        arr[getIndexOfType("ground")][getIndexOfType("psychic")] = 1.0
        arr[getIndexOfType("ground")][getIndexOfType("bug")] = 0.5
        arr[getIndexOfType("ground")][getIndexOfType("rock")] = 2.0
        arr[getIndexOfType("ground")][getIndexOfType("ghost")] = 1.0
        arr[getIndexOfType("ground")][getIndexOfType("dragon")] = 1.0

        arr[getIndexOfType("flying")][getIndexOfType("normal")] = 1.0
        arr[getIndexOfType("flying")][getIndexOfType("fire")] = 1.0
        arr[getIndexOfType("flying")][getIndexOfType("water")] = 1.0
        arr[getIndexOfType("flying")][getIndexOfType("electric")] = 0.5
        arr[getIndexOfType("flying")][getIndexOfType("grass")] = 2.0
        arr[getIndexOfType("flying")][getIndexOfType("ice")] = 1.0
        arr[getIndexOfType("flying")][getIndexOfType("fighting")] = 2.0
        arr[getIndexOfType("flying")][getIndexOfType("poison")] = 1.0
        arr[getIndexOfType("flying")][getIndexOfType("ground")] = 1.0
        arr[getIndexOfType("flying")][getIndexOfType("flying")] = 1.0
        arr[getIndexOfType("flying")][getIndexOfType("psychic")] = 1.0
        arr[getIndexOfType("flying")][getIndexOfType("bug")] = 2.0
        arr[getIndexOfType("flying")][getIndexOfType("rock")] = 0.5
        arr[getIndexOfType("flying")][getIndexOfType("ghost")] = 1.0
        arr[getIndexOfType("flying")][getIndexOfType("dragon")] = 1.0

        arr[getIndexOfType("psychic")][getIndexOfType("normal")] = 1.0
        arr[getIndexOfType("psychic")][getIndexOfType("fire")] = 1.0
        arr[getIndexOfType("psychic")][getIndexOfType("water")] = 1.0
        arr[getIndexOfType("psychic")][getIndexOfType("electric")] = 1.0
        arr[getIndexOfType("psychic")][getIndexOfType("grass")] = 1.0
        arr[getIndexOfType("psychic")][getIndexOfType("ice")] = 1.0
        arr[getIndexOfType("psychic")][getIndexOfType("fighting")] = 2.0
        arr[getIndexOfType("psychic")][getIndexOfType("poison")] = 2.0
        arr[getIndexOfType("psychic")][getIndexOfType("ground")] = 1.0
        arr[getIndexOfType("psychic")][getIndexOfType("flying")] = 1.0
        arr[getIndexOfType("psychic")][getIndexOfType("psychic")] = 0.5
        arr[getIndexOfType("psychic")][getIndexOfType("bug")] = 1.0
        arr[getIndexOfType("psychic")][getIndexOfType("rock")] = 1.0
        arr[getIndexOfType("psychic")][getIndexOfType("ghost")] = 1.0
        arr[getIndexOfType("psychic")][getIndexOfType("dragon")] = 1.0

        arr[getIndexOfType("bug")][getIndexOfType("normal")] = 1.0
        arr[getIndexOfType("bug")][getIndexOfType("fire")] = 0.5
        arr[getIndexOfType("bug")][getIndexOfType("water")] = 1.0
        arr[getIndexOfType("bug")][getIndexOfType("electric")] = 1.0
        arr[getIndexOfType("bug")][getIndexOfType("grass")] = 2.0
        arr[getIndexOfType("bug")][getIndexOfType("ice")] = 1.0
        arr[getIndexOfType("bug")][getIndexOfType("fighting")] = 0.5
        arr[getIndexOfType("bug")][getIndexOfType("poison")] = 0.5
        arr[getIndexOfType("bug")][getIndexOfType("ground")] = 1.0
        arr[getIndexOfType("bug")][getIndexOfType("flying")] = 0.5
        arr[getIndexOfType("bug")][getIndexOfType("psychic")] = 2.0
        arr[getIndexOfType("bug")][getIndexOfType("bug")] = 1.0
        arr[getIndexOfType("bug")][getIndexOfType("rock")] = 1.0
        arr[getIndexOfType("bug")][getIndexOfType("ghost")] = 0.5
        arr[getIndexOfType("bug")][getIndexOfType("dragon")] = 1.0

        arr[getIndexOfType("rock")][getIndexOfType("normal")] = 1.0
        arr[getIndexOfType("rock")][getIndexOfType("fire")] = 2.0
        arr[getIndexOfType("rock")][getIndexOfType("water")] = 1.0
        arr[getIndexOfType("rock")][getIndexOfType("electric")] = 1.0
        arr[getIndexOfType("rock")][getIndexOfType("grass")] = 1.0
        arr[getIndexOfType("rock")][getIndexOfType("ice")] = 2.0
        arr[getIndexOfType("rock")][getIndexOfType("fighting")] = 0.5
        arr[getIndexOfType("rock")][getIndexOfType("poison")] = 1.0
        arr[getIndexOfType("rock")][getIndexOfType("ground")] = 0.5
        arr[getIndexOfType("rock")][getIndexOfType("flying")] = 2.0
        arr[getIndexOfType("rock")][getIndexOfType("psychic")] = 1.0
        arr[getIndexOfType("rock")][getIndexOfType("bug")] = 2.0
        arr[getIndexOfType("rock")][getIndexOfType("rock")] = 1.0
        arr[getIndexOfType("rock")][getIndexOfType("ghost")] = 1.0
        arr[getIndexOfType("rock")][getIndexOfType("dragon")] = 1.0

        arr[getIndexOfType("ghost")][getIndexOfType("normal")] = 0.0
        arr[getIndexOfType("ghost")][getIndexOfType("fire")] = 1.0
        arr[getIndexOfType("ghost")][getIndexOfType("water")] = 1.0
        arr[getIndexOfType("ghost")][getIndexOfType("electric")] = 1.0
        arr[getIndexOfType("ghost")][getIndexOfType("grass")] = 1.0
        arr[getIndexOfType("ghost")][getIndexOfType("ice")] = 1.0
        arr[getIndexOfType("ghost")][getIndexOfType("fighting")] = 1.0
        arr[getIndexOfType("ghost")][getIndexOfType("poison")] = 1.0
        arr[getIndexOfType("ghost")][getIndexOfType("ground")] = 1.0
        arr[getIndexOfType("ghost")][getIndexOfType("flying")] = 1.0
        arr[getIndexOfType("ghost")][getIndexOfType("psychic")] = 2.0
        arr[getIndexOfType("ghost")][getIndexOfType("bug")] = 1.0
        arr[getIndexOfType("ghost")][getIndexOfType("rock")] = 1.0
        arr[getIndexOfType("ghost")][getIndexOfType("ghost")] = 2.0
        arr[getIndexOfType("ghost")][getIndexOfType("dragon")] = 1.0

        arr[getIndexOfType("dragon")][getIndexOfType("normal")] = 1.0
        arr[getIndexOfType("dragon")][getIndexOfType("fire")] = 1.0
        arr[getIndexOfType("dragon")][getIndexOfType("water")] = 1.0
        arr[getIndexOfType("dragon")][getIndexOfType("electric")] = 1.0
        arr[getIndexOfType("dragon")][getIndexOfType("grass")] = 1.0
        arr[getIndexOfType("dragon")][getIndexOfType("ice")] = 1.0
        arr[getIndexOfType("dragon")][getIndexOfType("fighting")] = 1.0
        arr[getIndexOfType("dragon")][getIndexOfType("poison")] = 1.0
        arr[getIndexOfType("dragon")][getIndexOfType("ground")] = 1.0
        arr[getIndexOfType("dragon")][getIndexOfType("flying")] = 1.0
        arr[getIndexOfType("dragon")][getIndexOfType("psychic")] = 1.0
        arr[getIndexOfType("dragon")][getIndexOfType("bug")] = 1.0
        arr[getIndexOfType("dragon")][getIndexOfType("rock")] = 1.0
        arr[getIndexOfType("dragon")][getIndexOfType("ghost")] = 1.0
        arr[getIndexOfType("dragon")][getIndexOfType("dragon")] = 2.0
    }

    private fun getIndexOfType(type: String): Int {
        return typesMap[type]!!
    }

    fun getDamageMultiplier(attackerType: String, defenderType: String): Double {
        return try{
            val x = getIndexOfType(attackerType)
            val y = getIndexOfType(defenderType)
            arr[x][y]
        }catch(e: NullPointerException) {
            1.0;
        }
    }
}