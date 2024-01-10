package com.project.quiz_application

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Utils {
    inline fun <reified T> loadJsonFile(context: Context, fileName: String): List<T> {
        val json = context.assets.open(fileName)
            .bufferedReader()
            .use {
                it.readText()
            }

        val gson = Gson()
        val type = object : TypeToken<List<T>>() {}.type
        return gson.fromJson(json, type)
    }
}