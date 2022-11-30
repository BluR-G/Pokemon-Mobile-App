package com.example.pokemon.data

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive

/**
 * For our application, there are 4 endpoints we use from the PokeAPI,
 *
 * - **Generation** (https://pokeapi.co/docs/v2#generations) : Describes the rules that games from
 *   a given generation follow. For example, in the later editions of the pokemon games (a.k.a in
 *   later generations of the game) new pokemon types were added. But, older generations (like
 *   generation-i) does not use those types.
 *
 * - **Move** (https://pokeapi.co/docs/v2#moves) : Describes the details of a given move.
 *
 * - **Pokemon** (https://pokeapi.co/docs/v2#pokemon and https://pokeapi.co/docs/v2#pokemon-species)
 *   : Both describe a given pokemon's various attributes, but each contains different attributes.
 *   For example, the pokemon endpoint contains the list of moves a pokemon can learn and the
 *   pokemon-species endpoint contains the growth rate of the pokemon. So, in order to build a
 *   complete PokemonData object, you need both.
 *
 *
 *  NOTE: This simplifier is intended for retrieving data about GEN-1 pokemon and moves only
 *  It should not be expected to work when retrieving data about pokemon outside GEN-1
 */
const val POKE_API_BASE_URL = "https://pokeapi.co/api/v2"
const val GEN_BASE_URL = "$POKE_API_BASE_URL/generation"
const val MOVE_BASE_URL = "$POKE_API_BASE_URL/move"
const val POKEMON_BASE_URL = "$POKE_API_BASE_URL/pokemon"
const val SPECIES_BASE_URL = "$POKE_API_BASE_URL/pokemon-species"
const val EVOLUTION_BASE_URL = "$POKE_API_BASE_URL/evolution-chain"

/* List of pokemon properties not needed for the project*/
private val REMOVED_POKEMON_PROPERTIES = listOf(
    "abilities", "forms", "game_indices", "height", "held_items",
    "is_default","location_area_encounters", "order", "past_types",
    "weight"
)

/* You can ignore the following 4 constants */
private const val VERSION = "red-blue"


/**
 * Parses the list of existing pokemon species in a given generation.
 *
 * @param generationResponse Comes from https://pokeapi.co/docs/v2#generations. In our Android
 *                           game, we use "generation-i"
 *
 * @return a JsonArray of all pokemon species (e.g. {"bulbasaur", "pikachu", "caterpie", etc.})
 */
fun parsePokemonGenSpecies(generationResponse: JsonObject): JsonArray =
    generationResponse["pokemon_species"].asJsonArray

/**
 * Parses the list of move names that a given pokemon could learn
 *
 * @param pokemonResponse Comes from https://pokeapi.co/docs/v2#pokemon
 *
 * @return a list JsonArry of pokemon moves names (e.g. "growl", "scratch", etc.) and the level they're
 *         learned at
 */
fun getMoveList(pokemonResponse: JsonObject): JsonArray {
    val moveList = JsonArray()

    pokemonResponse["moves"].asJsonArray.forEach { move ->
        move.asJsonObject["version_group_details"].asJsonArray.forEach { version ->
            run {
                val moveName = move.asJsonObject["move"].asJsonObject["name"]
                val moveDetails = move.asJsonObject["move"].asJsonObject["url"]
                val versionName =
                    version.asJsonObject["version_group"].asJsonObject["name"].asString
                val learnMethod =
                    version.asJsonObject["move_learn_method"].asJsonObject["name"].asString
                val learnLevel = version.asJsonObject["level_learned_at"]
                if (VERSION == versionName && learnMethod.equals("level-up")) {
                    val moveOut = JsonObject()
                    moveOut.add("move", moveName)
                    moveOut.add("level_learned_at", learnLevel)
                    moveOut.add("url", moveDetails)
                    moveList.add(moveOut)
                }
            }
        }
    }
    return moveList
}

/**
 * Parses the possible sprites of a given pokemon. Note if you want to use a different
 * set of sprites, you can alter this method to choose a different sprite for the front
 * and back of the pokemon.
 *
 * @param pokemonResponse Comes from https://pokeapi.co/docs/v2#pokemon
 *
 * @return a JsonObject containing a link to the sprite for the front and back of a pokemon.
 */
fun getPokemonSprites(pokemonResponse: JsonObject): JsonObject{
    val sprites = JsonObject()
    sprites.add("front", pokemonResponse["sprites"].asJsonObject["front_default"])
    sprites.add("back", pokemonResponse["sprites"].asJsonObject["back_default"])


    /* EXAMPLE: Using GEN 1 grayscale sprites instead of default:
    sprites.add("front", pokemonResponse["sprites"]
        .asJsonObject["versions"]
        .asJsonObject["generation-i"]
        .asJsonObject["red-blue"]
        .asJsonObject["front_gray"])
    sprites.add("front", pokemonResponse["sprites"]
        .asJsonObject["versions"]
        .asJsonObject["generation-i"]
        .asJsonObject["red-blue"]
        .asJsonObject["back_gray"])
     */

    return sprites
}


/**
 * Parses a pokemon's types
 *
 * @param pokemonResponse Comes from https://pokeapi.co/docs/v2#pokemon
 *
 * @return a JsonArray containing a pokemon's types.
 */
fun getPokemonTypes(pokemonResponse: JsonObject): JsonArray {
    val types = JsonArray()
    pokemonResponse["types"].asJsonArray.forEach { type ->
        types.add(type.asJsonObject["type"].asJsonObject["name"].asString)
    }
    return types
}


/**
 * Parses the list of move names that a given pokemon could learn
 *
 * @param pokemonResponse Comes from https://pokeapi.co/docs/v2#pokemon
 *
 * @return a simplified JsonObject containing only the necessary and relevant fields for working with a pokemon
 */
fun parsePokemonData( pokemonResponse: JsonObject): JsonObject {
    REMOVED_POKEMON_PROPERTIES.forEach { property ->
        pokemonResponse.remove(property)
    }
    pokemonResponse.add("moves", getMoveList(pokemonResponse))
    pokemonResponse.add("sprites", getPokemonSprites(pokemonResponse))
    pokemonResponse.add("types", getPokemonTypes(pokemonResponse))

    pokemonResponse.add("species", pokemonResponse["name"])
    pokemonResponse.remove("name")
    pokemonResponse.add("pokemonNumber", pokemonResponse["id"])
    pokemonResponse.remove("id")
    pokemonResponse.add("baseExperienceReward", pokemonResponse["base_experience"])
    pokemonResponse.remove("base_experience")

    /* Gives us a mapping 'statName -> baseStatValue' */
    val baseStats = pokemonResponse["stats"].asJsonArray.associate {
        it.asJsonObject.run {
            (this["stat"].asJsonObject["name"].asString) to (this["base_stat"].asInt)
        }
    }
    /* Add each statistic, then remove the stats object entirely */
    pokemonResponse.add("baseStateAttack", JsonPrimitive(baseStats.getValue("attack")))
    pokemonResponse.add("baseStatDefense", JsonPrimitive(baseStats.getValue("defense")))
    pokemonResponse.add("baseStateMaxHp", JsonPrimitive(baseStats.getValue("hp")))
    pokemonResponse.add(
        "baseStatSpecialAttack",
        JsonPrimitive(baseStats.getValue("special-attack"))
    )
    pokemonResponse.add(
        "baseStatSpecialDefense",
        JsonPrimitive(baseStats.getValue("special-defense"))
    )
    pokemonResponse.add("baseStatSpeed", JsonPrimitive(baseStats.getValue("speed")))
    pokemonResponse.remove("stats")


    return pokemonResponse
}



/**
 * Parses the target of a move
 *
 * Helper function for [parseMoveData]
 */
private fun getMoveTarget(moveResponse: JsonObject): String =
    when (moveResponse["target"].asJsonObject["name"].asString) {
        in listOf(
            "selected-pokemon-me-first",
            "random-opponent",
            "all-other-pokemon",
            "selected-pokemon",
            "all-opponents"
        ) -> "OPPONENT"
        in listOf("user-or-ally", "user", "user-and-allies", "all-allies") -> "SELF"
        "all-pokemon" -> "BOTH"
        else -> "OTHER"
    }


/**
 * Parses a single move data
 *
 * @param moveResponse Comes from https://pokeapi.co/docs/v2#moves
 *
 * @return simplified JsonObject containing only fields of interest
 *
 */
fun parseMoveData(moveResponse: JsonObject): JsonObject {
    val outMove = JsonObject()


    outMove.add("name", moveResponse["name"])
    outMove.add("maxPP", moveResponse["pp"])
    outMove.add("target", JsonPrimitive(getMoveTarget(moveResponse)))
    outMove.add("type", moveResponse["type"].asJsonObject["name"])

    outMove.add("accuracy", JsonPrimitive(
        if (moveResponse["accuracy"].isJsonNull) 100 else moveResponse["accuracy"].asInt
    ))
    outMove.add("power", JsonPrimitive(
        if (moveResponse["power"].isJsonNull) 0 else moveResponse["power"].asInt
    ))


    outMove.add("damageClass", JsonPrimitive(
        when (moveResponse["damage_class"].asJsonObject["name"].asString) {
        "physical" -> "PHYSICAL"
        /* There is also a status damage class, but we ignore it */
        else -> "SPECIAL"
    }))
    outMove.remove("damage_class")


    /* A portion of the API response containing a lot of metadata */
    val meta = moveResponse["meta"].asJsonObject
    outMove.add("ailmentChance", meta["ailment_chance"])
    outMove.add("ailment", JsonPrimitive(when (meta["ailment"].asJsonObject["name"].asString) {
        "paralysis" -> "PARALYSIS"
        "sleep" -> "SLEEP"
        "freeze" -> "FREEZE"
        "burn" -> "BURN"
        "poison" -> "POISON"
        "confusion" -> "CONFUSION"
        else -> "NONE"
    } ))

    outMove.add("heal", meta["healing"])

    /*category is the sort of attack*/
    outMove.add("category", JsonPrimitive(when (meta["category"].asJsonObject["name"].asString) {
        "damage" -> "DAMAGE"
        "ailment" -> "AILMENT"
        "heal" -> "HEAL"
        "damage+ailment" -> "DAMAGE_AND_AILMENT"
        else -> "OTHER"
    }))

    return outMove

}
