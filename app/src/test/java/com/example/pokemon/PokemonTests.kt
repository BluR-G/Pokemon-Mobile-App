package com.example.pokemon

import com.example.pokemon.objects.Move
import com.example.pokemon.objects.MoveData
import com.example.pokemon.objects.Pokemon
import org.junit.Assert
import org.junit.Test

class PokemonTests {

    @Test
    fun createPokemon() {
        val types = ArrayList<String>()
        types.add("fire")

        val moves = ArrayList<MoveData>()
        moves.add(MoveData("scratch", 1, Move(100, 40, "PHYSICAL", 0, "OPPONENT", "NONE", 0, "normal")))
        moves.add(MoveData("leer", 15, Move(100, 0, "SPECIAL", 0, "OPPONENT", "NONE", 0, "normal")))
        moves.add(MoveData("growl", 1, Move(100, 0, "SPECIAL", 0, "OPPONENT", "NONE", 0, "normal")))
        moves.add(MoveData("ember", 9, Move(100, 40, "SPECIAL", 0, "OPPONENT", "BURN", 10, "fire")))
        moves.add(MoveData("flamethrower", 38, Move(100, 90, "SPECIAL", 0, "OPPONENT", "BURN", 10, "fire")))
        moves.add(MoveData("fire-spin", 46, Move(85, 35, "SPECIAL", 0, "OPPONENT", "NONE", 100, "fire")))
        moves.add(MoveData("rage", 22, Move(100, 20, "PHYSICAL", 0, "OPPONENT", "NONE", 0, "normal")))
        moves.add(MoveData("slash", 30, Move(100, 70, "PHYSICAL", 0, "OPPONENT", "NONE", 0, "normal")))

        val images = ArrayList<String>()
        images.add("iVBORw0KGgoAAAANSUhEUgAAAGAAAABgCAYAAADimHc4AAAAAXNSR0IArs4c6QAAAARzQklUCAgI\n" +
                "CHwIZIgAAATGSURBVHic7ZsrdONGFIb/9CyQi8asChOUmcO0bAVTFrOkqCfMsF4UFzVGDjULTZhp\n" +
                "oMrWLEZdsYhFZRaL2BQoY41kSX5lPTfp/c7JSTx6Te4/9zEzMsAwDMMwDMMwDMMwDMMwDMMwDMMw\n" +
                "DMMwDMMwDMMwDMMwzJtwZLoDTTydeRIApuFs2fY1pN3nbSH5zyjDO761cuxm8jeAjyMEuX/i6cyT\n" +
                "zh8uMI8az2sP50iShFz/t+Un0x3QWRofALqO2c4cCFICbMPiugshhDTdj30hI8DTmScd38pDT0UI\n" +
                "ioL0wL368ZARoECN8ctJ+dF3370XkBGgE59tNMJbd6do3Z1+GG8gI4A4cxuPO76F1t3p8rM+N3jP\n" +
                "kBEgHvxaWffrvHwbwApHa8V6T5ARoN1ub3Re6l7Bdj7v/bxnDyRyBwkBxi5k6l6tPe+mc7Qc/T3X\n" +
                "2/u58h/LuAifTHdA8XITAnidfNXMggf9L+gFI+BjhH8ARDygwKvxq6oc1bZvAn72IEV/r1u8GWTW\n" +
                "UsYu5KD/pfZ4FKRLw6vwcxKEtetBYzeL8VWLds8epH2bJfyXeYqffzNnBzICqAnV4rpbaFern6NY\n" +
                "YLFYIOrlCVgXQBlc0XO9WoGUB7S6mQjxZYrjmRlbGM0B+ihVhmoP5wVDfl9WnEnB+ABwZSeAnd2j\n" +
                "7D3rJmqtroX4MoV9a0H0YSyvGPUAIYR89N1CTB/FAo9+ZnW9XQ876njdvEE3ftkLyuWnCkWmvICE\n" +
                "ALohoyDFSRACyMOR3qaMr1DXlke8Or8cgpQAog8kk6zNvrXwMk+RTHBwEYyFIGV8YHWhTbW3h/OV\n" +
                "tjJVoaYpOQP5qE+QLv9udS0kOPz6knEPAOqNuy11o16nnIB1TIQho0lYGeokCCuFmIYz9FwP1nm+\n" +
                "O5beRyvnAFnu2GaL8mWejXYlhPp8aMiUocCqR0zDGS7+Ol85T4mgwta2+8PKC5JJlguAPB/8rzyg\n" +
                "jO4RV3aCnuvBdlf3huNzwP433vk5xzMcPSNLxpMRcCHy9p1vuiP0liKQC6GHHp2yKLvsjB3PcHSX\n" +
                "FD9v28+3gKQAiqrRX8W6fYQmBre/oxOKna/fF9ICbMPOb0msef/oR0NSgLELWZV8deJf7L2f0bT4\n" +
                "dyhIJWGddeHHdh1gh0Ss1p+U8U1v7pMToLyq2UR5Br247qI9nMtySarfUx/1aonD5CuOpARQYSEK\n" +
                "UsRh1OgFcVgdux99d1nGKqpCTRSkWZX1Ons2BSkBFI5vIbqPsnq/JIIyfHofVVY/jm8BQXHPWPcU\n" +
                "ZXjrHOhcPhh/wZeMAEIIOejnmzHTcIYLOLUjvYlXLygsbahYr+YWFIwPEK2CgCxsdC4ftr6uLqlm\n" +
                "o56W8QFCHlDFo++ic/mA77enK8em4Qw9eCt7CTrlmbQSlIrxAUICJEly1B7Opb4JA+QiAKgUYpMy\n" +
                "8u7P+9dn0Fp8BIithgLFjZoyJ6WKRS3YVZ2nV0GUv85ExgOaqNto+ZoAwExWiUDZ6DqkBGga/U1x\n" +
                "uykPUIdMFSSEkIvFAtNwBmf6bdm+yUxVN7rjWx/iixtGEELI8s+m10kp5dhF4fd7EOFdxMlNqDI2\n" +
                "pXKTYRiGYRiGYRiGYRiGYRiGYRiGYRiGYRiGYRiGORD/AWl8LuO/SjYoAAAAAElFTkSuQmCC\n")
        images.add("iVBORw0KGgoAAAANSUhEUgAAAGAAAABgCAYAAADimHc4AAAAAXNSR0IArs4c6QAAAARzQklUCAgI\n" +
                "CHwIZIgAAAPzSURBVHic7dohdNNAGAfwf3mIzl0dnSvu5lYXUItEUjcm52qHKg6mCo46kOCC4k12\n" +
                "CuJaR9zqWlzPrS6I7LY0a0qSJpeD/X/v7b11a19u33f35e7LACIiIiIiIiIiIiIiIiIiIiIiIiIi\n" +
                "IqL/QaPuAZTt6qUTJn/29Jtv7d/5uO4BlGUoEfakg47bvPe7K0RJsTER1g2oiG3Bj5uNV9YlwarB\n" +
                "FJE1+JptSXhU9wAeun86AXr2A9HMTjMbr26/Om4TQ4l7N+q61HoTnjsI9/3yyuC2JGitN1MM2mVd\n" +
                "cXfGV8DcWZ99yddV8gIfy+XS1OUyqaUElRH0ePnJo+s94ATowO/7aOyahPOFKPS54MNnnPWPrLkP\n" +
                "1LoCRH/9ddV60kEzODdxqcyMJ0AHHQD2Dptrr02YuBKz8cqaVWA0AXrHI/p3idg7zHaAqkLRMlam\n" +
                "Wu4BwHrgRT9/GVJKNbzAz/z+5Htn4xUGbZXnkpUwXoL2Dpulzfo8M/h8IdAdB+h4P0u5dlmMHcTm\n" +
                "DkJddq6nq9skXE9XUKPqrz9xJbrjAK1W6/ZnStXfCzO+Aq6nfz+tZjGUCCeuzPWZiSsxaCsopRpK\n" +
                "qdqDDxhcAfs+GnMg/HJTdvuDaBXo2V+kJaE7oGktCC/woQ9r3XEAwI5ZH2d0MEOJ8Kx/hNabKeYf\n" +
                "o0yoUf7gX710wmT7eVsSAOB1YFfgNWODigf/l4yCX2TWbwq+liUJeu9vS0KMlCAdfB2goh3QbcHf\n" +
                "5q5n5Idn/aPo29FlaEMSjLaju+MARW9+Q4lCwdc6bhNnbhT896PLmy3sAzgH6NlftXj5+dsBrUgX\n" +
                "tSpGV8CgrfC6wKTLm8SedG53PXqrqhN0txt6INvQ84VA7+ZRYDTz/NJr76ab7/JHdOprPV8/5dkS\n" +
                "eM3YQUwHKW8DTAgR9qSzMcj6OW9S/F4RP3jZFnyghmfCNy2BsEgwsjzzBQAcPQMALIJZ3ksYV/kK\n" +
                "UEo1dN3VJq6stBe/CGb/RPABgyUouYXsSQdCiFAIsTER+nd5+z1xB6cX1hy40hgbnBAiXL473FhG\n" +
                "kisEuNu9FOEFPl69PcbB6YV1N90kown49ekF2r8X2Wv5DrzAt372A4abcUzCfUafByilGgenF1g8\n" +
                "aaN53Mn8D7X/s1pmh14JcVWtCttXQW0D07ufZCLiykjKLg1AE2ofmE7ExJVoHncyfab9e4H3o8tM\n" +
                "TTUmIKP4eSAtGauvM3iBv9bOGLRVaiJsDz5gUQLi0g5nWjyo2w5rTMAONgU27YaalgQmYEdCiDD+\n" +
                "32tpuxn9vngp8gIfcvgdJycnVifB2oHllVa2bA4+ERERERERERERERERERERERERVeAP4h7TX90L\n" +
                "uXMAAAAASUVORK5CYII=\n")

        val expected = Pokemon(4, "charmander", "flame", 0, types, 39, 52, 43, 60, 50, 65, moves, images)

        Assert.assertEquals(4, expected.getId())
        Assert.assertEquals("charmander", expected.getSpecies())
        Assert.assertEquals("flame", expected.getName())
        Assert.assertEquals(0, expected.getLevel())
        Assert.assertEquals("fire", expected.getTypes()[0])
        Assert.assertEquals(39, expected.getMaxHp())
        Assert.assertEquals(52, expected.getAttack())
        Assert.assertEquals(43, expected.getDefense())
        Assert.assertEquals(60, expected.getSpecialAttack())
        Assert.assertEquals(50, expected.getSpecialDefense())
        Assert.assertEquals(65, expected.getSpeed())
    }
}