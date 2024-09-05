package cz.bradacd.boop.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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