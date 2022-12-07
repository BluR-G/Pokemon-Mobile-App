package com.example.pokemon.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import com.example.pokemon.objects.Move
import com.example.pokemon.objects.MoveData
import com.example.pokemon.objects.Pokemon
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import java.io.ByteArrayOutputStream
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class PokemonCreation {

//    private var pokemonObject: JsonObject = JsonObject()
    private var moveObject: JsonObject = JsonObject()

    // search for pokemon on DB and if not there, search on the web
    private suspend fun searchPokemon(input : String): JsonObject {
        val GSON: Gson = GsonBuilder().setPrettyPrinting().create()
        val url = URL("https://pokeapi.co/api/v2/pokemon/${input.lowercase()}")
        val conn: HttpsURLConnection = url.openConnection() as HttpsURLConnection

        conn.requestMethod = "GET"
        conn.connect()

        if (conn.responseCode == HttpsURLConnection.HTTP_OK) {
            val response = conn.inputStream.bufferedReader()
            val json: JsonObject = GSON.fromJson(response, JsonObject::class.java)
            val pokemonObject = parsePokemonData(json);
            return pokemonObject
        }
        // return empty JsonObject if something went wrong
        return JsonObject()
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

    public suspend fun createPokemon(
        species: String,
        nickname: String,
        initialLevel: Int
    ): Pokemon {
        val pokemonObject = searchPokemon(species)
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
        val types = getType(pokemonObject)
        val moves = getMoves(pokemonObject)
        var pokemonNickname = nickname
        if (nickname == "") {
            pokemonNickname = species
        }
        return Pokemon(id, species, pokemonNickname, initialLevel, types, hp, attack, defense, spAttack, spDefense, speed, moves, images)
    }

    private fun getImages(pokemon: JsonObject): ArrayList<String> {
        val list = ArrayList<String>()
        val imageObject = pokemon.get("sprites").asJsonObject

        val imageFrontUrl = imageObject.get("front").asString
        val imageFrontStr = urlToBitmapStr(imageFrontUrl)

        val imageBackUrl = imageObject.get("back").asString
        val imageBackStr = urlToBitmapStr(imageBackUrl)
        list.add(imageFrontStr)
        list.add(imageBackStr)
        return list
    }

    private fun urlToBitmapStr(imageUrl: String): String {
        val imageURLObj = URL(imageUrl)
        val imageBitmap = BitmapFactory.decodeStream(imageURLObj.openConnection().getInputStream())
        val baos = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
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

    private fun getType(pokemon: JsonObject): ArrayList<String> {
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
            val parseMove = parseMoveData(moveObject)
            val accuracy = Integer.parseInt(parseMove.get("accuracy").asString)
            val power = Integer.parseInt(parseMove.get("power").asString)
            val damageClass = parseMove.get("damageClass").asString
            val heal = Integer.parseInt(parseMove.get("heal").asString)
            val target = parseMove.get("target").asString
            val type = parseMove.get("type").asString

            val moveDetails = Move(accuracy, power, damageClass, heal, target, type)
            list.add(MoveData(name, level, moveDetails))
        }
        return list
    }
}