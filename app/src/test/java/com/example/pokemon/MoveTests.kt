package com.example.pokemon

import com.example.pokemon.objects.Move
import org.junit.Assert
import org.junit.Test

class MoveTests {

    @Test
    fun testCreateMove() {
        val move = Move(100, 40, "PHYSICAL", 0, "OPPONENT", "normal")
        Assert.assertEquals(100, move.getAccuracy())
        Assert.assertEquals(40, move.getPower())
        Assert.assertEquals("PHYSICAL", move.getDamageClass())
        Assert.assertEquals(0, move.getHeal())
        Assert.assertEquals("OPPONENT", move.getTarget())
        Assert.assertEquals("normal", move.getTypes())
    }
}