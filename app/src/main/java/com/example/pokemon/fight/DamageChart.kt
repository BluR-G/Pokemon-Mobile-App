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
        // initialize the value of the 2D array
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