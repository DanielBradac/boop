package cz.bradacd.boop.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cz.bradacd.boop.model.Boop
import cz.bradacd.boop.ui.Headline
import cz.bradacd.boop.viewmodel.BoopScreenViewModel

import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import cz.bradacd.boop.ui.DeleteBoopDialog
import cz.bradacd.boop.ui.EditBoopDialog
import cz.bradacd.boop.ui.theme.Red500


@Composable
fun BoopScreen(navController: NavController, boopName: String) {
    val context = LocalContext.current
    val viewModel: BoopScreenViewModel = viewModel()
    val boop by viewModel.boop.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadBoop(context, boopName)
    }

    var showDeleteDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .clickable { viewModel.incrementBoop(context) }
    ) {
        Headline(text = boopName)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            boop?.let {
                BoopData(boop = it)
            }

            ControlPanel(
                onMinus = { viewModel.decrementBoop(context) },
                onEdit = { showEditDialog = true },
                onDelete = { showDeleteDialog = true }
            )
        }
    }

    // Dialogs
    if (showDeleteDialog) {
        DeleteBoopDialog(
            onDismiss = { showDeleteDialog = false },
            onDelete = {
                showDeleteDialog = false
                viewModel.deleteBoop(context, navController)
            },
            boopName
        )
    }

    if (showEditDialog) {
        EditBoopDialog(
            onDismiss = { showEditDialog = false },
            onSave = { newBoop ->
                showEditDialog = false
                viewModel.editBoop(context, newBoop)
            },
            boop
        )
    }
}

@Composable
fun BoopData(boop: Boop) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "${boop.boopCount}", fontSize = 200.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )

        if (boop.note.isNotBlank()) {
            Text(text = "Note:", fontWeight = FontWeight.Bold)
            Text(text = boop.note, fontStyle = FontStyle.Italic)
        }
    }
}

@Composable
fun ControlPanel(
    onMinus: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    var locked by remember { mutableStateOf(true) }

    Column {
        Button(
            onClick = { locked = !locked },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            // Display icon based on the 'locked' state
            Icon(
                imageVector = if (locked) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                contentDescription = if (locked) "Locked" else "Unlocked"
            )
        }
        if (!locked) {

            Button(onClick = { onMinus() }) {
                Text(text = "-1")
            }

            Button(onClick = { onEdit() }) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Edit",
                    modifier = Modifier.padding(end = 16.dp)
                )
                Text("Edit")
            }

            Button(
                onClick = { onDelete() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Red500
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete",
                    modifier = Modifier.padding(end = 16.dp)
                )
                Text("Delete")
            }
        }
    }
}


