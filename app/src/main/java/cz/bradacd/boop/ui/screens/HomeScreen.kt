package cz.bradacd.boop.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cz.bradacd.boop.ui.Headline
import cz.bradacd.boop.viewmodel.HomePageViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import cz.bradacd.boop.model.Boop

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val viewModel: HomePageViewModel = viewModel()
    val boops by viewModel.boops.collectAsState()

    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Headline(text = "My Boops")
        BoopList(
            boops ?: emptyList(),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        NewBoopButton(
            { showDialog = true },
            context
        )
    }

    // Modal dialog
    if (showDialog) {
        EditDialog(
            onDismiss = { showDialog = false },
            onSave = { newBoop ->
                try {
                    viewModel.saveNewBoop(newBoop, context)
                    showDialog = false
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        )
    }
}

@Composable
fun BoopList(boops: List<Boop>, modifier: Modifier) {

    if (boops.isEmpty()) {
        Text("You have no Boops \uD83D\uDE1E Go make some!")
    }

    LazyColumn(
        modifier = modifier
    ) {
        items(boops) { boop ->
            ListItem(boop)
        }
    }
}

@Composable
fun ListItem(boop: Boop) {
    Text(text = boop.name)
}

@Composable
fun NewBoopButton(onClick: () -> Unit, context: Context) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = { onClick() }
    ) {
        Text(text = "New Boop")
    }
}


@Composable
fun EditDialog(onDismiss: () -> Unit, onSave: (Boop) -> Unit) {
    var boopName by remember { mutableStateOf("") }
    var boopCount by remember { mutableIntStateOf(0) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("New Boop") },
        text = {
            Column {
                OutlinedTextField(
                    label = { Text("Boop name") },
                    value = boopName,
                    onValueChange = { newValue -> boopName = newValue },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )

                OutlinedTextField(
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    label = { Text("Boop count") },
                    value = boopCount.toString(),
                    onValueChange = { newValue ->
                        if (newValue.isNotBlank()) {
                            val intValue = newValue.toIntOrNull()
                            if (intValue != null) {
                                boopCount = intValue
                            }
                        } else {
                            boopCount = 0
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onSave(
                        Boop(
                            name = boopName,
                            boopCount = boopCount
                        )
                    )
                }
            ) {
                Text("Save Boop")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text("Cancel")
            }
        }
    )
}