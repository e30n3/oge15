package ru.involta.composesample3

import android.R.attr.background
import android.R.attr.button
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import ru.involta.composesample3.model.CalculationType
import ru.involta.composesample3.model.ConditionType
import ru.involta.composesample3.model.InputType
import ru.involta.composesample3.model.Task
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
        BodyContent(Modifier.padding())
    }
}

@Composable
fun BodyContent(modifier: Modifier) {
    val scrollState = rememberScrollState()

    val clipboard = LocalClipboardManager.current
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    ) {
        var task by remember { mutableStateOf(Task()) }
        var code by rememberSaveable { mutableStateOf(task.generateCode()) }



        Spacer(modifier = Modifier.height(12.dp))
        Header("ОГЭ 15.2")
        DataCard("Шаблон задачи", modifier = Modifier.padding(horizontal = 12.dp)) {
            ExpandableMenu("Тип ввода данных", elements = InputType.titles,
                onSelect = {
                    task = task.copy(inputType = InputType.get(it))
                    code = task.generateCode()
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            ExpandableMenu("Тип искомого значения", elements = CalculationType.titles,
                onSelect = {
                    task = task.copy(calculationType = CalculationType.get(it))
                    code = task.generateCode()
                }
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        DataCard("Условие", Modifier.padding(horizontal = 12.dp)) {
            ExpandableMenu("Тип условия", elements = ConditionType.titles,
                onSelect = {
                    task = task.copy(conditionType = ConditionType.get(it))
                    code = task.generateCode()
                }
            )
            if (task.conditionType.needEditText) {
                Spacer(modifier = Modifier.height(16.dp))
                EditText("Значение условия", task.conditionValue.toString()) {
                    task = task.copy(conditionValue = it.toIntOrNull() ?: 0)
                    code = task.generateCode()
                }
            }

        }
        Spacer(modifier = Modifier.height(20.dp))
        DataCard("Код задачи", Modifier.padding(horizontal = 12.dp)) {
            SelectionContainer {
                Text(
                    code,
                    modifier = Modifier.animateContentSize(),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 14.sp
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            AccentButton("Скопировать") {
                clipboard.setText(AnnotatedString(code))
                Toast.makeText(context, "Скопировано!", Toast.LENGTH_SHORT).show()
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        DataCard("Дополнительно", modifier = Modifier.padding(horizontal = 12.dp)) {
            AccentButton("Python online") {
                val viewIntent = Intent(
                    "android.intent.action.VIEW",
                    Uri.parse("https://www.onlinegdb.com/online_python_compiler")
                )
                context.startActivity(viewIntent)
            }
            Spacer(modifier = Modifier.height(16.dp))
            AccentButton("Python android") {
                val viewIntent = Intent(
                    "android.intent.action.VIEW",
                    Uri.parse("https://drive.google.com/file/d/1I_Y496CS_YBdqZa0BkEPoKYWJmcrO3vk/view")
                )
                context.startActivity(viewIntent)
            }
        }
        Text(
            text = "Иван Зайцев, 2022",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            color = Color.Gray,
            fontSize = 10.sp
        )

    }
}

@Composable
fun AccentButton(text: String, onClick: () -> Unit) {
    GradientButton(
        text = text,
        gradient = Brush.verticalGradient(listOf(ButtonTop, ButtonBottom)),
        textColor = Color.Black,
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp),
        onClick = onClick
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
fun DataCard(
    cardName: String = "",
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier
            .fillMaxWidth(),
        elevation = 24.dp,
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
fun EditText(
    editTextName: String,
    editTextValue: String = "3",
    onValueChange: (newString: String) -> Unit
) {
    var text by rememberSaveable { mutableStateOf(editTextValue) }

    Text(text = editTextName, fontSize = 12.sp, modifier = Modifier.padding(bottom = 4.dp))
    TextField(
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            disabledTextColor = Color.Transparent,
            backgroundColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 2.dp, shape = RoundedCornerShape(5.dp), color = Color.LightGray),
        value = text,
        onValueChange = { newString ->
            text = newString.filter { it.isDigit() }
            onValueChange(newString)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

    )
}


@Composable
fun ExpandableMenu(
    menuName: String = "",
    elements: List<String> = listOf("a", "b", "c", "d"),
    onSelect: (String) -> Unit = {},
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    var selected by rememberSaveable { mutableStateOf(elements.first()) }


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

