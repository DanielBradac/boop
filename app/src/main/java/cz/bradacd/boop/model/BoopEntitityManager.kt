package cz.bradacd.boop.model

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import java.util.Calendar

object BoopEntitityManager {
    private const val BOOPS_STORAGE_NAME = "boops"
    private val gson = Gson()

    fun getSavedBoops(context: Context): List<Boop> {
        val boopsJson = getSharedPreferences(context).getString(BOOPS_STORAGE_NAME, null)
        return if (boopsJson != null) {
            gson.fromJson(boopsJson, SavedBoops::class.java).boops
        } else {
            emptyList()
        }
    }

    private fun saveBoops(context: Context, newBoops: List<Boop>) {
        val editor = getSharedPreferences(context).edit()
        gson.toJson(newBoops)
        editor.putString(BOOPS_STORAGE_NAME, Gson().toJson(SavedBoops(newBoops)))
        editor.apply()
    }

    fun saveBoop(context: Context, boop: Boop) {
        val existingBoops = getSavedBoops(context).toMutableList()
        // Remove old state of boop
        existingBoops.removeIf { it.name == boop.name }
        // Add new state of boop
        existingBoops.add(boop.copy(modifyDT = Calendar.getInstance().time))

        val editor = getSharedPreferences(context).edit()
        gson.toJson(existingBoops)
        editor.putString(BOOPS_STORAGE_NAME, Gson().toJson(SavedBoops(existingBoops)))
        editor.apply()
        println(boop)
    }

    fun deleteBoop(context: Context, boop: Boop) {
        val existingBoops = getSavedBoops(context).toMutableList()
        existingBoops.removeIf { it.name == boop.name }
        saveBoops(context, existingBoops)
    }

    private fun getSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(BOOPS_STORAGE_NAME, Context.MODE_PRIVATE)
}