package com.example.pokemon.objects

import android.graphics.drawable.Drawable
import java.io.Serializable
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.pow

class Pokemon : Serializable {
    private var species: String = ""
    private var name: String = ""
    private var level: Int = 0
    private var experience: Double = 0.0
    private var baseExperience: Double = 0.0
    private lateinit var types: ArrayList<String>
    private var currentHp: Int = 0

    private var maxHp: Int = 0
    private var attack: Int = 0
    private var defense: Int = 0
    private var specialAttack: Int = 0
    private var specialDefense: Int = 0
    private var speed: Int = 0
    private var statusAfflicted : String = ""

    private lateinit var moves: ArrayList<MoveData>
    private lateinit var currentMoves: ArrayList<MoveData>
    private lateinit var images: ArrayList<String>

    constructor(species: String, name: String, level: Int, baseExperience: Double, types: ArrayList<String>, maxHp: Int, attack: Int, defense: Int, specialAttack: Int, specialDefense: Int, speed: Int, moves: ArrayList<MoveData>, images: ArrayList<String>, statusAfflicted : String = ""){
        this.species = species
        this.name = name
        this.level = level
        this.experience = level.toDouble().pow(3.0)
        this.baseExperience = baseExperience
        this.types = types
        this.maxHp = maxHp * (50 + level) / 50
        this.currentHp = this.maxHp
        this.attack = attack * (50 + level) / 50
        this.defense = defense * (50 + level) / 50
        this.specialAttack = specialAttack * (50 + level) / 50
        this.specialDefense = specialDefense * (50 + level) / 50
        this.speed = speed * (50 + level) / 50
        this.statusAfflicted = statusAfflicted
        this.moves = moves
        this.currentMoves = ArrayList<MoveData>()
        setCurrentMoves()
        this.images = images
    }

    fun getStatusAfflicted(): String {
        return this.statusAfflicted
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

    fun getExperience(): Double{
        return this.experience
    }

    fun addExperience(exp: Double){
        this.experience += exp
        val newLevel = floor(Math.cbrt(this.experience.toDouble())).toInt()
        if(newLevel > this.level){
            val numberOfLevels = newLevel - this.level
            for (i in 0 until numberOfLevels){
                setMaxHp()
                setAttack()
                setDefense()
                setSpecialAttack()
                setSpecialDefense()
                setSpeed()
                setCurrentMoves()
                setLevel(this.level + 1)
            }
        }
    }

    fun getTypes(): ArrayList<String>{
        return this.types
    }

    fun getMaxHp(): Int{
        return this.maxHp
    }
    private fun setMaxHp(){
        this.maxHp = ceil(this.maxHp + (this.maxHp / (50 + this.level).toDouble())).toInt()
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
        this.attack = ceil(this.attack + (this.attack / (50 + this.level).toDouble())).toInt()
    }

    fun getDefense(): Int{
        return this.defense
    }
    private fun setDefense(){
        this.defense = ceil(this.defense + (this.defense / (50 + this.level).toDouble())).toInt()
    }

    fun getSpecialAttack(): Int{
        return this.specialAttack
    }
    private fun setSpecialAttack(){
       this.specialAttack = ceil(this.specialAttack + (this.specialAttack / (50 + this.level).toDouble())).toInt()
    }

    fun getSpecialDefense(): Int{
        return this.specialDefense
    }
    private fun setSpecialDefense(){
        this.specialDefense = ceil(this.specialDefense + (this.specialDefense / (50 + this.level).toDouble())).toInt()
    }

    fun getSpeed(): Int{
        return this.speed
    }
    private fun setSpeed(){
        this.speed = ceil(this.speed + (this.speed / (50 + this.level).toDouble())).toInt()
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

    private fun setCurrentMoves(){
        currentMoves.clear()
        for(elem in moves){
            if(elem.level_learned_at <= this.level){
                currentMoves.add(elem)
            }
            if(currentMoves.size == 4){
                return
            }
        }
    }

    fun getCurrentMoves(): ArrayList<MoveData> {
        return this.currentMoves
    }

    fun isAlive():Boolean{
        if(this.getCurrentHp()==0){
            return false
        }
        return true
    }

    fun getImages(): ArrayList<String>{
        return this.images
    }

    fun getBaseExperience(): Double {
        return this.baseExperience
    }
}