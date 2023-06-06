package co.talesbruno.drinkwater

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import co.talesbruno.drinkwater.domain.utils.formatTime
import co.talesbruno.drinkwater.presentation.viewmodel.WaterReminderViewModel
import co.talesbruno.drinkwater.ui.theme.DrinkWaterTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val waterReminderViewModel by viewModels<WaterReminderViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(waterReminderViewModel)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainScreen(waterReminderViewModel: WaterReminderViewModel) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var searchText by remember { mutableStateOf("") }

    val state by waterReminderViewModel.isReminderScheduled.collectAsStateWithLifecycle()
    val startTime = waterReminderViewModel.startTime.collectAsStateWithLifecycle()

    val elapsedTime = System.currentTimeMillis() - startTime.value
    val remainingTimeMillis = searchText.toLong() * 60 * 1000L - elapsedTime

    val remainingTimeString = remember(remainingTimeMillis) {
        mutableStateOf(formatTime(remainingTimeMillis))
    }

    LaunchedEffect(remainingTimeMillis) {
        while (remainingTimeMillis > 0) {
            remainingTimeString.value = formatTime(remainingTimeMillis)
            delay(1000)
        }
    }

    DrinkWaterTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            if (state) {
                Column() {
                    Text(text = "Você tem um lembrete de beba água")
                    Text(text = remainingTimeString.value, style = MaterialTheme.typography.h4)
                    Button(
                        onClick = { waterReminderViewModel.cancelReminder() },
                    ) {
                        Text("Cancelar lembrete")
                    }
                }
            } else {
                Column() {
                    TextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Search
                        ),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                waterReminderViewModel.scheduleReminder(searchText.toLong() * 60 * 1000)
                                keyboardController?.hide()
                            }
                        )
                    )
                    Button(onClick = {
                        waterReminderViewModel.scheduleReminder(searchText.toLong() * 60 * 1000)
                    }
                    ) {
                        Text(text = "Agendar lembrete")
                    }
                }
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DrinkWaterTheme {

    }
}