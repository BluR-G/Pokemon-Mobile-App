package com.example.pokemon.data

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.pokemon.objects.Move
import com.example.pokemon.objects.MoveData
import com.example.pokemon.objects.Pokemon
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class PokemonCreation {

    private lateinit var pokemonObject: JsonObject
    private lateinit var moveObject: JsonObject
    private lateinit var effectObject: JsonObject

    // search for pokemon on DB and if not there, search on the web
    private suspend fun searchPokemon(input : String){

        val GSON: Gson = GsonBuilder().setPrettyPrinting().create()
        val url = URL("https://pokeapi.co/api/v2/pokemon/$input")
        val conn: HttpsURLConnection = url.openConnection() as HttpsURLConnection

        conn.requestMethod = "GET"
        conn.connect()

        if (conn.responseCode == HttpsURLConnection.HTTP_OK) {
            val response = conn.inputStream.bufferedReader()
            val json: JsonObject = GSON.fromJson(response, JsonObject::class.java)
            pokemonObject = parsePokemonData(json);
        }
    }

    private suspend fun searchMove(url : String){

        val GSON: Gson = GsonBuilder().setPrettyPrinting().create()
        val url = URL(url)
        val conn: HttpsURLConnection = url.openConnection() as HttpsURLConnection

        conn.requestMethod = "GET"
        conn.connect()

        if (conn.responseCode == HttpsURLConnection.HTTP_OK) {
            val response = conn.inputStream.bufferedReader()
            val json: JsonObject = GSON.fromJson(response, JsonObject::class.java)
            moveObject = json
        }
    }

    private suspend fun searchEffect(url : String){

        val GSON: Gson = GsonBuilder().setPrettyPrinting().create()
        val url = URL(url)
        val conn: HttpsURLConnection = url.openConnection() as HttpsURLConnection

        conn.requestMethod = "GET"
        conn.connect()

        if (conn.responseCode == HttpsURLConnection.HTTP_OK) {
            val response = conn.inputStream.bufferedReader()
            val json: JsonObject = GSON.fromJson(response, JsonObject::class.java)
            effectObject = json
        }
    }

    public suspend fun createPokemon(nickname: String, initialLevel: Int): Pokemon {
        val images = getImages(pokemonObject)
        val species = getName(pokemonObject)
        val id = getId(pokemonObject)
        val hp = getHp(pokemonObject)
        val attack = getAttack(pokemonObject)
        val defense = getDefense(pokemonObject)
        val spAttack = getSpecialAttack(pokemonObject)
        val spDefense = getSpecialDefense(pokemonObject)
        val speed = getSpeed(pokemonObject)
        val exp = getExperience(pokemonObject)
        val types = getTypes(pokemonObject)
        val moves = getMoves(pokemonObject)
        return Pokemon(species, nickname, initialLevel, types, hp, attack, defense, spAttack, spDefense, speed, moves, images)
    }

    private fun getImages(pokemon: JsonObject): ArrayList<String> {
        val list = ArrayList<String>()
        val imageObject = pokemon.get("sprites").asJsonObject
        val imageFrontUrl = URL(imageObject.get("front").asString)
        val imageBackUrl = URL(imageObject.get("back").asString)
        list.add(imageFrontUrl.toString())
        list.add(imageBackUrl.toString())
        return list
    }

    private fun getName(pokemon: JsonObject): String {
        return pokemon.get("species").asString
    }

    private fun getId(pokemon: JsonObject): Int {
        val id = pokemon.get("pokemonNumber").asString
        return Integer.parseInt(id)
    }

    private fun getHp(pokemon: JsonObject): Int {
        val hp = pokemon.get("baseStateMaxHp").asString
        return Integer.parseInt(hp)
    }

    private fun getAttack(pokemon: JsonObject): Int {
        val attack = pokemon.get("baseStateAttack").asString
        return Integer.parseInt(attack)
    }

    private fun getDefense(pokemon: JsonObject): Int {
        val defense = pokemon.get("baseStatDefense").asString
        return Integer.parseInt(defense)
    }

    private fun getSpecialAttack(pokemon: JsonObject): Int {
        val spAttack = pokemon.get("baseStatSpecialAttack").asString
        return Integer.parseInt(spAttack)
    }

    private fun getSpecialDefense(pokemon: JsonObject): Int {
        val spDefense = pokemon.get("baseStatSpecialDefense").asString
        return Integer.parseInt(spDefense)
    }

    private fun getSpeed(pokemon: JsonObject): Int {
        val speed = pokemon.get("baseStatSpeed").asString
        return Integer.parseInt(speed)
    }

    private fun getExperience(pokemon: JsonObject): Int {
        val exp = pokemon.get("baseExperienceReward").asString
        return Integer.parseInt(exp)
    }

    private fun getTypes(pokemon: JsonObject): ArrayList<String> {
        val list = ArrayList<String>()
        val types = pokemon.get("types").asJsonArray
        for (i in 0 until types.size()) {
            val elem = types[i].asString
            list.add(elem)
        }
        return list
    }

    private suspend fun getMoves(pokemon: JsonObject): ArrayList<MoveData> {
        val list = ArrayList<MoveData>()
        val moves = pokemon.get("moves").asJsonArray
        for (i in 0 until moves.size()) {
            val name = moves[i].asJsonObject.get("move").asString
            val level = Integer.parseInt(moves[i].asJsonObject.get("level_learned_at").asString)
            val moveUrl = moves[i].asJsonObject.get("url").asString
            searchMove(moveUrl)
            val accuracy = Integer.parseInt(moveObject.get("accuracy").asString)
            val power = Integer.parseInt(moveObject.get("power").asString)
            val damageClass = moveObject.get("damage_class").asJsonObject.get("name").asString
            val target = moveObject.get("target").asJsonObject.get("name").asString
            val type = moveObject.get("type").asJsonObject.get("name").asString

            val effectUrl = moveObject.get("contest_effect").asJsonObject.get("url").asString
            searchEffect(effectUrl)
            val effect = effectObject.get("effect_entries").asJsonArray[0].asJsonObject.get("effect").asString
            val effectChance = Integer.parseInt(moveObject.get("effect_chance").asString)

            val moveDetails = Move(accuracy, power, damageClass, target, effect, effectChance, type)
            list.add(MoveData(name, level, moveDetails))
        }
        return list
    }
}