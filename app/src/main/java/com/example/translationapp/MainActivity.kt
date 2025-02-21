package com.example.translationapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.translationapp.ui.theme.TranslationAppTheme
import kotlinx.coroutines.selects.select

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TranslationAppTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(WindowInsets.systemBars)
                ) {
                    View()
                }
            }
        }
    }
}

@Composable
fun View() {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 20.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Greeting()
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(bottom = 30.dp)
                ) {
                    DropDownTextField(
                        modifier = Modifier.weight(1f),
                        label = "First Language"
                    )
                    DropDownTextField(
                        modifier = Modifier.weight(1f),
                        label = "Second Language"
                    )
                }
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                )

                Button(modifier = Modifier)
            }
        }
        Column(modifier = Modifier) {
            Text(text = "Result: ", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }
    }
}


@Composable
fun Greeting(modifier: Modifier = Modifier) {
    Text(
        text = "Translate In Different Languages",
        fontWeight = FontWeight.Bold,
        color = Color.DarkGray,
        modifier = modifier,
        fontSize = 20.sp
    )
}


@Composable
fun TextField(modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { it -> text = it },
        label = { Text("Enter the Text") },
        modifier = modifier,
        minLines = 5
    )
}

@Composable
fun DropDownTextField(modifier: Modifier = Modifier, label: String = "") {
    var isExpanded by remember { mutableStateOf(false) }

    var languages = listOf("Hindi", "English", "German", "Arabic", "French")

    var selectedText by remember { mutableStateOf("") }

    val icon = if (isExpanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }

    Column(modifier = modifier) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { selectedText = it },
            trailingIcon = {
                Icon(icon, "DropDown Icon", Modifier.clickable {
                    isExpanded = !isExpanded
                })
            },
            label = { Text(text = label, maxLines = 1, overflow = TextOverflow.Ellipsis) },
        )
    }
}

@Composable
fun Button(modifier: Modifier = Modifier) {
    androidx.compose.material3.Button({

    }, modifier = modifier) {
        Text(text = "Submit", color = Color.White)
    }
}


@Preview(showBackground = true)
@Composable
fun ViewPreview() {
    View()
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TranslationAppTheme {
        Greeting()
    }
}

@Preview(showBackground = true)
@Composable
fun TextFieldPreview() {
    TranslationAppTheme {
        TextField(modifier = Modifier)
    }
}

@Preview(showBackground = true)
@Composable
fun DropDownTextFieldPreview() {
    TranslationAppTheme {
        DropDownTextField()
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonPreview() {
    TranslationAppTheme {
        Button()
    }
}