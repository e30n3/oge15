package ru.involta.composesample3

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.involta.composesample3.ui.theme.ButtonBottom
import ru.involta.composesample3.ui.theme.ButtonTop
import ru.involta.composesample3.ui.theme.ComposeSample3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeSample3Theme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        BodyContent(Modifier.padding(horizontal = 12.dp))
    }
}

@Composable
fun BodyContent(modifier: Modifier) {
    val scrollState = rememberScrollState()
    Column(modifier = modifier.verticalScroll(scrollState)) {
        Spacer(modifier = Modifier.height(12.dp))
        Header("Регистрация")
        DataCard("Тип пользователя") {
            ExpandableMenu("Тип профиля")
            Spacer(modifier = Modifier.height(20.dp))
            AccentButton("Сделать")
        }
        Spacer(modifier = Modifier.height(20.dp))
        DataCard("Персональные данные") {
            Text("How are you")
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
fun AccentButton(text: String) {
    GradientButton(
        text = text,
        gradient = Brush.verticalGradient(listOf(ButtonTop, ButtonBottom)),
        textColor = Color.Black,
        modifier = Modifier.fillMaxWidth().height(54.dp)
    )
}

@Composable
fun GradientButton(
    text: String,
    textColor: Color,
    gradient: Brush,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        ),
        contentPadding = PaddingValues(),
        onClick = { onClick() },
        modifier = modifier
    )
    {
        Box(
            modifier = modifier
                .background(gradient)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = text, color = textColor, style = MaterialTheme.typography.h4)
        }
    }
}

@Composable
fun Header(topicName: String) {
    Text(
        topicName,
        style = MaterialTheme.typography.h1,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 18.dp),
        textAlign = TextAlign.Center
    )
}

@Composable
fun DataCard(cardName: String = "", content: @Composable ColumnScope.() -> Unit) {
    Card(
        Modifier
            .fillMaxWidth(), elevation = 24.dp
    ) {
        Column(Modifier.padding(20.dp)) {
            if (cardName.isNotBlank() and cardName.isNotEmpty()) {
                Text(
                    cardName,
                    style = MaterialTheme.typography.h2,
                    modifier = Modifier.padding(bottom = 20.dp)
                )
            }
            content.invoke(this)
        }
    }
}

@Composable
fun ExpandableMenu(
    menuName: String = "",
    hint: String = "Выберите из списка",
    elements: List<String> = listOf("a", "b", "c", "d"),
    onSelect: (String) -> Unit = {},
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    var selected by rememberSaveable { mutableStateOf(hint) }


    @Composable
    fun ExpandableMenuElement(name: String, modifier: Modifier = Modifier) {
        Column(modifier = modifier) {
            Divider()
            Text(
                name, modifier = Modifier
                    .padding(19.dp)
                    .fillMaxWidth()
            )
        }
    }

    Column(Modifier.animateContentSize()) {
        Text(text = menuName, fontSize = 12.sp, modifier = Modifier.padding(bottom = 4.dp))
        Column(
            Modifier
                .fillMaxWidth()
                .border(2.dp, color = Color.LightGray, shape = RoundedCornerShape(5.dp))
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Text(
                    text = selected,
                    modifier = Modifier.padding(start = 14.dp),
                    style = MaterialTheme.typography.h3
                )
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                        contentDescription = ""
                    )
                }
            }
            if (expanded) elements.filter { it != selected }.forEach {
                ExpandableMenuElement(it, modifier = Modifier.clickable {
                    selected = it
                    expanded = false
                    onSelect(selected)
                })
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeSample3Theme {
        MyApp()
    }
}

