package com.example.avaloncharacters

import com.google.gson.Gson
import org.junit.Test

import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashMap

class GsonTest {
    private val defaultGson = Gson()

    private class CustomClass(
        val publicField: Any,
        private val privateField: Any
    ) {
        override fun toString(): String {
            return "publicField: ${publicField.toString()}\n" +
                    "privateField: ${privateField.toString()}\n"
        }
    }

    @Test
    fun GsonShowCase() {

        // serialization
        val simpleArray = arrayOf(1, 2)
        val nestedArray = ArrayList<ArrayList<Int>>(3).apply {
            for (i in 0 until 3) {
                add(ArrayList())
                for (j in 0 until 5)
                    this[i].add(i * 5 + j)
            }
        }
        val serializationShowCase = LinkedHashMap<String, Any>().apply {
            put("Simple Array", simpleArray)
            put("Nested Array", nestedArray)
            put("Object with array field", CustomClass(simpleArray, nestedArray))
        }

        serializationShowCase.forEach { (useCase, obj) ->
            println("$useCase:")
            println("\t${defaultGson.toJson(obj)}")
        }

        println()

        // Deserialization
        println("Deserialization:")
        val customClassJsonString = defaultGson.toJson(CustomClass(simpleArray, nestedArray))
        val customClassFromJsonString =
            defaultGson.fromJson(customClassJsonString, CustomClass::class.java)
        println(customClassFromJsonString.toString())
    }


}
