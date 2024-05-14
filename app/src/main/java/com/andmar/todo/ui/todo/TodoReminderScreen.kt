package com.andmar.todo.ui.todo

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TabRow
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.andmar.todo.ui.navigation.NavigateDestination
import com.andmar.todo.ui.AppViewModelProvider
import com.andmar.todo.TodoTopAppBar
import com.andmar.todo.R
import java.time.LocalDateTime

object TodoReminderDestination {
    val route = "todoReminderScreen"
    val title = R.string.top_app_bar_reminder_title
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}" 
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoReminderScreen(
    viewModel: TodoReminderViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onNavClickBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TodoTopAppBar(
                title = stringResource(TodoReminderDestination.title),
                onClickNavigateIcon = {
                    onNavClickBack()
                },
                showNavigationIcon = true
            )
        }
    ) { padding ->
        TodoReminderBody(
            contentPadding = padding,
            onClickAddReminder = {
                viewModel.setReminder()
                onNavClickBack()
            },
            todoReminderDate = viewModel.todoReminderState.todoReminderDate,
            updateTodoReminderState = {
                viewModel.updateTodoReminderState(it)
            }
        )
    }
}

@Composable
fun TodoReminderBody(
    contentPadding: PaddingValues,
    onClickAddReminder: () -> Unit,
    todoReminderDate: TodoReminderDate,
    updateTodoReminderState: (TodoReminderDate) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
    ) {
        item {
            
            TodoDateForm(
                todoReminderDate = todoReminderDate,
                onValueChange = updateTodoReminderState
            ) 
            TodoHoursForm(
                todoReminderDate = todoReminderDate,
                onValueChange = updateTodoReminderState
            )
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                onClick = {
                    onClickAddReminder()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    text = stringResource(R.string.set_reminder_button)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoHoursForm(
    todoReminderDate: TodoReminderDate,
    onValueChange: (TodoReminderDate) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = todoReminderDate.hour,
            onValueChange = {
                onValueChange(todoReminderDate.copy(hour = it))
            },
            label = {
                Text(
                    text = stringResource(R.string.hours_text)
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .weight(1f)
        )
        Text(
            text = ":",
            fontSize = 30.sp,
            modifier = Modifier.padding(10.dp)
        )
        OutlinedTextField(
            value = todoReminderDate.minute,
            onValueChange = {
                onValueChange(todoReminderDate.copy(minute = it))
            },
            label = {
                Text(
                    text = stringResource(R.string.minutes_text)
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .weight(1f)
            
        )
    }
}

@Composable
fun TodoDateForm(
    todoReminderDate: TodoReminderDate,
    onValueChange: (TodoReminderDate) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        OutlinedTextField(
            value = todoReminderDate.year,
            onValueChange = {
                onValueChange(todoReminderDate.copy(year = it))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = {
                Text(
                    text = stringResource(R.string.year_text)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        )
        OutlinedTextField(
            value = todoReminderDate.month,
            onValueChange = {
                onValueChange(todoReminderDate.copy(month = it))
            },
            label = {
                Text(
                    text = stringResource(R.string.month_text)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        )
        OutlinedTextField(
            value = todoReminderDate.day,
            onValueChange = {
                onValueChange(todoReminderDate.copy(day = it))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = {
                Text(
                    text = stringResource(R.string.day_text)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        )
    }
}