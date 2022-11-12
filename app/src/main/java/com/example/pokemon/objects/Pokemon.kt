package com.example.pokemon.objects

class Pokemon {
    private var species: String = ""
    private var name: String = ""
    private var level: Int = 0
    private var experience: Int = 0
    private lateinit var types: Array<String>
    private var maxHp: Int = 0
    private var currentHp: Int = 0
    private var attack: Int = 0
    private var defense: Int = 0
    private var specialAttack: Int = 0
    private var specialDefense: Int = 0
    private var speed: Int = 0
    private lateinit var moves: ArrayList<Move>

    constructor(species: String, name: String, level: Int, experience: Int, types: Array<String>, maxHp: Int, currentHp: Int, attack: Int, defense: Int, specialAttack: Int, specialDefense: Int, speed: Int, moves: ArrayList<Move>){
        this.species = species
        this.name = name
        this.level = level
        this.experience = experience
        this.types = types
        this.maxHp - maxHp
        this.currentHp = currentHp
        this.attack = attack
        this.defense = defense
        this.specialAttack = specialAttack
        this.specialDefense = specialDefense
        this.speed = speed
        this.moves = moves
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
    fun setLevel(level: Int){
        this.level = level
    }

    fun getExperience(): Int{
        return this.experience
    }
    fun setExperience(experience: Int){
        this.experience = experience
    }

    fun getTypes(): Array<String>{
        return this.types
    }

    fun getMaxHp(): Int{
        return this.maxHp
    }
    fun setMaxHp(maxHp: Int){
        this.maxHp = maxHp
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
    fun setAttack(attack: Int){
        this.attack = attack
    }

    fun getDefense(): Int{
        return this.defense
    }
    fun setDefense(defense: Int){
        this.defense = defense
    }

    fun getSpecialAttack(): Int{
        return this.specialAttack
    }
    fun setSpecialAttack(specialAttack: Int){
       this.specialAttack = specialAttack
    }

    fun getSpecialDefense(): Int{
        return this.specialDefense
    }
    fun setSpecialDefense(specialDefense: Int){
        this.specialDefense = specialDefense
    }

    fun getSpeed(): Int{
        return this.speed
    }
    fun setSpeed(speed: Int){
        this.speed = speed
    }

    fun getMoves(): ArrayList<Move>{
        return this.moves
    }
    fun addMove(move: Move){
        this.moves.add(move)
    }

}