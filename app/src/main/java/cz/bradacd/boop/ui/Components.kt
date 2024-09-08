package cz.bradacd.boop.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cz.bradacd.boop.model.Boop

@Composable
fun Headline(text: String) {
    val primaryColor = MaterialTheme.colorScheme.primary
    Text(
        text = text,
        fontSize = 32.sp,
        color = primaryColor,
        modifier = Modifier.padding(bottom = 16.dp)
    )
}

@Composable
fun EditBoopDialog(onDismiss: () -> Unit, onSave: (Boop) -> Unit, boop: Boop?) {
    var boopName by remember { mutableStateOf(boop?.name ?: "") }
    var boopCount by remember { mutableIntStateOf(boop?.boopCount ?: 0) }
    var note by remember { mutableStateOf(boop?.note ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(boop?.name ?: "New Boop") },
        text = {
            Column {
                if (boop == null) {
                    OutlinedTextField(
                        label = { Text("Boop name") },
                        value = boopName,
                        onValueChange = { newValue -> boopName = newValue },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }

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

                OutlinedTextField(
                    label = { Text("Note") },
                    value = note,
                    onValueChange = { newValue -> note = newValue },
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
                            boopCount = boopCount,
                            note = note
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

@Composable
fun DeleteBoopDialog(onDismiss: () -> Unit, onDelete: () -> Unit, boopName: String) {

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("New Boop") },
        text = {
            Text(text = "Do you really want to delete $boopName?")
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDelete()
                }
            ) {
                Text("Confirm")
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