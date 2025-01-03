package cz.bradacd.boop.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import cz.bradacd.boop.model.Boop
import cz.bradacd.boop.model.BoopEntitityManager
import cz.bradacd.boop.utils.InvalidBoopInput
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomePageViewModel : ViewModel() {

    private val _boops: MutableStateFlow<List<Boop>> = MutableStateFlow(emptyList())
    val boops: StateFlow<List<Boop>?> = _boops

    fun reloadBoops(context: Context) {
        _boops.value = BoopEntitityManager.getSavedBoops(context)
    }

    fun saveNewBoop(newBoop: Boop, context: Context) {
        validateNewBoop(newBoop, context)
        BoopEntitityManager.saveBoop(context, newBoop)
        reloadBoops(context)
    }

    private fun validateNewBoop(newBoop: Boop, context: Context) {
        if (newBoop.name.isBlank()) {
            throw InvalidBoopInput("Boop must have a name.")
        }

        if (BoopEntitityManager.getSavedBoops(context).any { it.name == newBoop.name }) {
            throw InvalidBoopInput("Boop with name ${newBoop.name} already exists.")
        }
    }
}