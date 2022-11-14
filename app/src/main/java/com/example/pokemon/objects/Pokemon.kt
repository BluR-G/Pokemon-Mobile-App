package com.example.pokemon.objects

import android.graphics.drawable.Drawable
import kotlin.math.floor
import kotlin.math.pow

class Pokemon {
    private var species: String = ""
    private var name: String = ""
    private var level: Int = 0
    private var experience: Int = 0
    private lateinit var types: Array<String>
    private var currentHp: Int = 0

    private var maxHp: Int = 0
    private var attack: Int = 0
    private var defense: Int = 0
    private var specialAttack: Int = 0
    private var specialDefense: Int = 0
    private var speed: Int = 0

    private lateinit var moves: ArrayList<MoveData>
    private lateinit var images: Array<Drawable>

    constructor(species: String, name: String, level: Int, types: Array<String>, maxHp: Int, attack: Int, defense: Int, specialAttack: Int, specialDefense: Int, speed: Int, moves: ArrayList<MoveData>, images: Array<Drawable>){
        this.species = species
        this.name = name
        this.level = level
        this.experience = level.toDouble().pow(3.0).toInt()
        this.types = types
        this.currentHp = maxHp

        this.maxHp = maxHp * (50 + level) / 50
        this.attack = attack * (50 + level) / 50
        this.defense = defense * (50 + level) / 50
        this.specialAttack = specialAttack * (50 + level) / 50
        this.specialDefense = specialDefense * (50 + level) / 50
        this.speed = speed * (50 + level) / 50

        this.moves = moves
        this.images = images
    }

    fun getSpecies(): String{
        return this.species
    }

    fun getName(): String{
        return this.name
    }

    fun getLevel(): Int{
        return this.level
    }
    private fun setLevel(level: Int){
        this.level = level
    }

    fun getExperience(): Int{
        return this.experience
    }
    private fun setExperience(){
        this.experience = this.level.toDouble().pow(3.0).toInt()
    }
    fun addExperience(exp: Int){
        this.experience += exp
        val newLevel = floor(Math.cbrt(this.experience.toDouble())).toInt()
        if(newLevel > this.level){
            setExperience()
            setMaxHp()
            setAttack()
            setDefense()
            setSpecialAttack()
            setSpecialDefense()
            setSpeed()
            setLevel(newLevel)
        }
    }

    fun getTypes(): Array<String>{
        return this.types
    }

    fun getMaxHp(): Int{
        return this.maxHp
    }
    private fun setMaxHp(){
        this.maxHp = this.maxHp + (this.maxHp / (50 + this.level))
    }

    fun getCurrentHp(): Int{
        return this.currentHp
    }
    fun setCurrentHp(currentHp: Int){
        this.currentHp = currentHp
    }

    fun getAttack(): Int{
        return this.attack
    }
    private fun setAttack(){
        this.attack = this.attack + (this.attack / (50 + this.level))
    }

    fun getDefense(): Int{
        return this.defense
    }
    private fun setDefense(){
        this.defense = this.defense + (this.defense / (50 + this.level))
    }

    fun getSpecialAttack(): Int{
        return this.specialAttack
    }
    private fun setSpecialAttack(){
       this.specialAttack = this.specialAttack + (this.specialAttack / (50 + this.level))
    }

    fun getSpecialDefense(): Int{
        return this.specialDefense
    }
    private fun setSpecialDefense(){
        this.specialDefense = this.specialDefense + (this.specialDefense / (50 + this.level))
    }

    fun getSpeed(): Int{
        return this.speed
    }
    private fun setSpeed(){
        this.speed = this.speed + (this.speed / (50 + this.level))
    }

    fun getMoves(): ArrayList<MoveData>{
        return this.moves
    }
    fun addMove(move: MoveData): Boolean{
        if(this.moves.size < 4){
            this.moves.add(move)
            return true
        }
        return false
    }
    fun addMoves(moves: Array<MoveData>): Boolean{
        this.moves.clear()
        if (moves.size <= 4){
            for (move in moves){
                if (move.level_learned_at <= this.level){
                    this.moves.add(move)
                }else{
                    return false
                }
            }
            return true
        }
        return false
    }

    fun getImages(): Array<Drawable>{
        return this.images
    }
}