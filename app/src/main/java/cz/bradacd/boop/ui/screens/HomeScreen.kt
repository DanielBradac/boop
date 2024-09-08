package cz.bradacd.boop.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cz.bradacd.boop.ui.Headline
import cz.bradacd.boop.viewmodel.HomePageViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cz.bradacd.boop.model.Boop
import cz.bradacd.boop.ui.EditBoopDialog
import cz.bradacd.boop.ui.theme.Pink200
import cz.bradacd.boop.ui.theme.Purple200
import cz.bradacd.boop.ui.theme.Purple400
import cz.bradacd.boop.utils.convert


@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModel: HomePageViewModel = viewModel()
    val boops by viewModel.boops.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.reloadBoops(context)
    }

    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Headline(text = "My Boops")
        BoopList(
            navController,
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
        EditBoopDialog(
            onDismiss = { showDialog = false },
            onSave = { newBoop ->
                try {
                    viewModel.saveNewBoop(newBoop, context)
                    showDialog = false
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }
            },
            boop = null
        )
    }
}

@Composable
fun BoopList(navController: NavController, boops: List<Boop>, modifier: Modifier) {

    if (boops.isEmpty()) {
        Text("You have no Boops \uD83D\uDE1E Go make some!")
    }

    LazyColumn(
        modifier = modifier
    ) {
        itemsIndexed(boops) { index, boop ->
            ListItem(boop, index) {
                navController.navigate("details/${boop.name}")
            }
        }
    }
}

@Composable
fun ListItem(boop: Boop, index: Int, onClick: () -> Unit) {
    val backgroundColor = if (index % 2 == 0) Pink200 else Purple200

    Box(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .border(
                width = 2.dp,
                color = Purple400,
                shape = RoundedCornerShape(28.dp)
            )
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .background(color = backgroundColor, shape = RoundedCornerShape(28.dp))
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = boop.name, fontWeight = FontWeight.SemiBold, fontSize = 28.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row {
                Column(
                    modifier = Modifier.padding(end = 4.dp)
                ) {
                    Text(text = "Boops:", fontWeight = FontWeight.Bold)
                    Text(text = "Created:", fontWeight = FontWeight.Bold)
                    Text(text = "Modified:", fontWeight = FontWeight.Bold)
                }

                Column {
                    Text(text = "${boop.boopCount}")
                    Text(text = boop.createDT.convert())
                    Text(text = boop.modifyDT.convert())
                }
            }

            if (boop.note.isNotBlank()) {
                Text(
                    text = boop.note,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
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
