package cz.bradacd.boop.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import cz.bradacd.boop.model.Boop
import cz.bradacd.boop.model.BoopEntitityManager
import cz.bradacd.boop.utils.InvalidStateException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class BoopScreenViewModel() : ViewModel() {
    private val _boop: MutableStateFlow<Boop?> = MutableStateFlow(null)
    val boop: StateFlow<Boop?> = _boop

    fun loadBoop(context: Context, boopName: String) {
        _boop.value = BoopEntitityManager.getSavedBoops(context)
            .find { it.name == boopName } ?: throw InvalidStateException("Boop $boopName is null")
    }

    private fun saveBoop(context: Context) {
        BoopEntitityManager.saveBoop(context, boop.value!!)
    }

    fun incrementBoop(context: Context) {
        val currentBoop = _boop.value ?: return
        _boop.value = currentBoop.copy(boopCount = currentBoop.boopCount + 1)
        saveBoop(context)
    }

    fun decrementBoop(context: Context) {
        val currentBoop = _boop.value ?: return
        _boop.value = currentBoop.copy(boopCount = currentBoop.boopCount - 1)
        saveBoop(context)
    }

    fun deleteBoop(context: Context, navController: NavController) {
        BoopEntitityManager.deleteBoop(context, boop.value!!)
        navController.navigate("home") {
            popUpTo("home") { inclusive = true }
        }
    }

    fun editBoop(context: Context, newBoopState: Boop) {
        _boop.value = newBoopState
        saveBoop(context)
    }

}