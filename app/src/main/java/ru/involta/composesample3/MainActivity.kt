package ru.involta.composesample3

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
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
import ru.involta.composesample3.model.*
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
        var task by rememberSaveable { mutableStateOf(Task()) }
        var code by rememberSaveable { mutableStateOf(task.generateCode()) }



        Spacer(modifier = Modifier.height(12.dp))
        Header("ОГЭ 15.2")
        DataCard("Шаблон задачи", modifier = Modifier.padding(horizontal = 12.dp)) {
            ExpandableMenu(menuName = "Тип ввода данных", elements = InputType.titles,
                onSelect = {
                    task = task.copy(inputType = InputType.get(it))
                    code = task.generateCode()
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            ExpandableMenu(menuName = "Тип искомого значения", elements = CalculationType.titles,
                onSelect = {
                    task = task.copy(calculationType = CalculationType.get(it))
                    code = task.generateCode()
                }
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        DataCard("Условие", Modifier.padding(horizontal = 12.dp)) {
            ExpandableMenu(menuName = "Тип условия", elements = ConditionType.titles,
                onSelect = {
                    task = task.copy(conditionType = ConditionType.get(it))
                    code = task.generateCode()
                }
            )
            if (task.conditionType.needEditText) {
                Spacer(modifier = Modifier.height(16.dp))
                EditText("Значение условия", editTextValue = task.conditionValue.toString()) {
                    task = task.copy(conditionValue = it.toIntOrNull() ?: 0)
                    code = task.generateCode()
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            RadioButtonMenu("Логическая операция между условиями", LogicOperationType.titles) {
                task = task.copy(conditionOperationType = LogicOperationType.get(it))
                if (LogicOperationType.get(it)== LogicOperationType.DISABLE)
                    task = task.copy(conditionType2 = ConditionType.ALIQUOT, conditionValue2 = 3)
                code = task.generateCode()
            }
            AnimatedVisibility(task.conditionOperationType != LogicOperationType.DISABLE) {
                Column {
                    Spacer(
                        modifier = Modifier
                            .height(16.dp)
                    )
                    ExpandableMenu(
                        menuName = "Тип условия", elements = ConditionType.titles,
                        onSelect = {
                            task = (task.copy(conditionType2 = ConditionType.get(it)))
                            code = task.generateCode()
                        },
                        modifier = Modifier
                    )
                    if (task.conditionType2.needEditText) {
                        Spacer(modifier = Modifier.height(16.dp))
                        EditText(
                            "Значение условия",
                            editTextValue = task.conditionValue.toString(),
                            modifier = Modifier.animateContentSize()
                        ) {
                            task = (task.copy(conditionValue2 = it.toIntOrNull() ?: 0))
                            code = task.generateCode()
                        }
                    }
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
                    fontSize = 10.sp
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
            text = "Иван Зайцев, e30n3z@gmail.com",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .clickable {
                    val emailSelectorIntent = Intent(Intent.ACTION_SENDTO)
                    emailSelectorIntent.data = Uri.parse("mailto:")

                    val emailIntent = Intent(Intent.ACTION_SEND)
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("e30n3z@gmail.com"))
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Из приложения ОГЭ 15.2")
                    emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    emailIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                    emailIntent.selector = emailSelectorIntent


                    /*if (emailIntent.resolveActivity(context.packageManager) != null)*/
                    context.startActivity(
                        emailIntent
                    )
                }
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            color = Color.Gray,
            fontSize = 10.sp

        )

    }
}

@Composable
fun RadioButtonMenu(menuName: String, elements: List<String>, onChange: (String) -> Unit) {
    @Composable
    fun RadioButtonMenuElement(
        title: String,
        isSelected: Boolean = false,
        modifier: Modifier = Modifier,

        ) {
        Row(
            modifier
                .background(
                    if (isSelected) Brush.verticalGradient(listOf(ButtonTop, ButtonBottom))
                    else Brush.verticalGradient(listOf(Color.Transparent, Color.Transparent)),
                    shape = RoundedCornerShape(5.dp),
                )
                .border(
                    width = 2.dp,
                    shape = RoundedCornerShape(5.dp),
                    color = Color.LightGray
                ),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = title, Modifier.padding(18.dp))
        }
    }

    Column(Modifier.fillMaxWidth()) {
        Text(text = menuName, fontSize = 12.sp, modifier = Modifier.padding(bottom = 4.dp))
        var selectedElement by rememberSaveable { mutableStateOf(elements.first()) }
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            elements.forEachIndexed { i, it ->
                RadioButtonMenuElement(
                    title = it,
                    it == selectedElement,
                    Modifier
                        .weight(1f)
                        .clickable {
                            selectedElement = it
                            onChange(it)
                        },
                )
                if (i != elements.lastIndex) Spacer(modifier = Modifier.width(4.dp))
            }
        }
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
    modifier: Modifier = Modifier,
    editTextValue: String = "3",
    onValueChange: (newString: String) -> Unit
) {
    var text by rememberSaveable { mutableStateOf(editTextValue) }

    Text(text = editTextName, fontSize = 12.sp, modifier = Modifier.padding(bottom = 4.dp))
    TextField(
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            disabledTextColor = Color.Transparent,
            backgroundColor =  Color.Transparent,
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
    modifier: Modifier = Modifier,
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

