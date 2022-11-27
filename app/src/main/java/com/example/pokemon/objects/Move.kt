package com.example.pokemon.objects

import java.io.Serializable

class Move : Serializable {
    private var accuracy: Int = 0
    private var power: Int = 0
    private var damageClass: String = ""
    private var target: String = ""
    private var type: String = ""
    private var effect: String = ""
    private var effectChance: Int = 0

    constructor(accuracy: Int, power: Int, damageClass: String, target: String, effect: String, effectChance: Int, type: String){
        this.accuracy = accuracy
        this.power = power
        this.damageClass = damageClass
        this.target = target
        this.effect = effect
        this.effectChance = effectChance
        this.type = type
    }

    fun getAccuracy(): Int{
        return this.accuracy
    }

    fun getPower(): Int{
        return this.power
    }

    fun getDamageClass(): String{
        return this.damageClass
    }

    fun getTarget(): String{
        return this.target
    }

    fun getEffect(): String{
        return this.effect
    }

    fun getEffectChance(): Int{
        return this.effectChance
    }

    fun getTypes(): String{
        return this.type
    }
}