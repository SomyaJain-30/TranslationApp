package com.example.translationapp.home

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.window.isPopupLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.translationapp.ui.theme.TranslationAppTheme

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
                    MainView()
                }
            }
        }
    }
}

@Composable
fun MainView(viewModel: TranslationViewModel = viewModel()) {
    var firstLanguage by remember { mutableStateOf("") }
    var secondLanguage by remember { mutableStateOf("") }
    var enteredText by remember { mutableStateOf("") }
    val isLoading by viewModel.isLoading.collectAsState()
    val context = LocalContext.current

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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .align(Alignment.Center)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(bottom = 30.dp)
            ) {
                DropDownTextField(
                    modifier = Modifier.weight(1f),
                    label = "First Language",
                    onValueChange = { firstLanguage = it },
                    selectedText = firstLanguage
                )
                DropDownTextField(
                    modifier = Modifier.weight(1f),
                    label = "Second Language",
                    onValueChange = { secondLanguage = it },
                    selectedText = secondLanguage
                )
            }
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                enteredText = enteredText,
                onValueChange = { enteredText = it }
            )
            androidx.compose.material3.Button({
                if (firstLanguage.isBlank() || secondLanguage.isBlank() || enteredText.isBlank()) {
                    Toast.makeText(context, "Please Enter all Fields", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                viewModel.translate(
                    sourceLang = firstLanguage,
                    targetLang = secondLanguage,
                    text = enteredText
                )
            }, modifier = Modifier.padding(bottom = 20.dp).size(width = 100.dp, height = 40.dp), enabled = !isLoading) {
                if(isLoading){
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                }else{
                    Text(text = "Submit", color = Color.White)
                }
            }
            Text(
                text = "Result: ",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 10.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.DarkGray, RectangleShape)
            ) {
                val translatedText by viewModel.translatedText.collectAsState()
                Text(text = translatedText, modifier = Modifier.padding(10.dp))
            }
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
fun TextField(modifier: Modifier = Modifier, enteredText: String, onValueChange: (String) -> Unit) {

    OutlinedTextField(
        value = enteredText,
        onValueChange = onValueChange,
        label = { Text("Enter the Text") },
        modifier = modifier,
        minLines = 5
    )
}

@Composable
fun DropDownTextField(
    modifier: Modifier = Modifier,
    label: String = "",
    selectedText: String,
    onValueChange: (String) -> Unit
) {

    var isExpanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    var languages = listOf("Hindi", "English", "German", "Arabic", "French")
    val icon = if (isExpanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }

    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = {},
            trailingIcon = {
                Icon(icon, "DropDown Icon", Modifier.clickable {
                    isExpanded = !isExpanded
                })
            },
            label = { Text(text = label, maxLines = 1, overflow = TextOverflow.Ellipsis) },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                }
        )

        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { !isExpanded },
            properties = PopupProperties(dismissOnClickOutside = true),
            modifier = modifier
                .width(with(LocalDensity.current) {
                    textFieldSize.width.toDp()
                })
        ) {
            languages.forEach { language ->
                DropdownMenuItem(text = { Text(text = language) }, onClick = {
                    onValueChange(language)
                    isExpanded = false
                })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ViewPreview() {
    MainView()
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TranslationAppTheme {
        Greeting()
    }
}
