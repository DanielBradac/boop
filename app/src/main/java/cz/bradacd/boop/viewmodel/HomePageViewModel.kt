package cz.bradacd.boop.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import cz.bradacd.boop.model.Boop
import cz.bradacd.boop.model.BoopEntitityManager
import cz.bradacd.boop.utils.InvalidBoopInput
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomePageViewModel(application: Application) : AndroidViewModel(application) {

    private val _boops: MutableStateFlow<List<Boop>> = MutableStateFlow(emptyList())
    val boops: StateFlow<List<Boop>?> = _boops

    init {
        loadBoops(application.applicationContext)
    }

    private fun loadBoops(context: Context) {
        _boops.value = BoopEntitityManager.getSavedBoops(context)
    }

    fun saveNewBoop(newBoop: Boop, context: Context) {
        validateNewBoop(newBoop, context)
        BoopEntitityManager.saveBoop(context, newBoop)
        loadBoops(context)
    }

    private fun validateNewBoop(newBoop: Boop, context: Context) {
        if (newBoop.name.isBlank()) {
            throw InvalidBoopInput("Boop must have a name.")
        }

        if (BoopEntitityManager.getSavedBoops(context).contains(newBoop)) {
            throw InvalidBoopInput("Boop with name ${newBoop.name} already exists.")
        }
    }

}