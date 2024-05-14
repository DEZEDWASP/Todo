package com.andmar.todo.ui.todo

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.ImeAction
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import com.andmar.todo.ui.navigation.NavigateDestination
import com.andmar.todo.ui.AppViewModelProvider
import com.andmar.todo.TodoTopAppBar
import com.andmar.todo.R

object TodoEntryDestination: NavigateDestination {
    override val route = "entryScreen"
    override val title = R.string.top_app_bar_entry_todo_title
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}"
}

@Composable
fun TodoEntryScreen(
    viewModel: TodoEntryViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onNavClickBack: () -> Unit
) {
val scope = rememberCoroutineScope()

    TodoEntryBody(
        titleText = stringResource(TodoEntryDestination.title),
        todoUiState = viewModel.todoUiState,
        updateUiState = viewModel::updateUiState,
        onSaveClick = {
            scope.launch {
                viewModel.saveTodo()
                onNavClickBack()
            }
        },
        onBackClick = {
            onNavClickBack()
        },
        categoryId = viewModel.categoryId,
        showActionsIcon = viewModel.todoUiState.isShowButton
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoEntryBody(
    titleText: String,
    todoUiState: TodoUiState,
    updateUiState: (TodoDetails) -> Unit,
    onSaveClick: () -> Unit,
    onBackClick: () -> Unit,
    categoryId: Int,
    showActionsIcon: Boolean
) {

    Scaffold(
        topBar = {
            TodoTopAppBar(
                title = titleText,
                onClickNavigateIcon = { onBackClick() },
                showNavigationIcon = ShowTopBarIcons.SHOW_NAVIGATION_ICON,
                showActionsIcon = showActionsIcon,
                actionsIcon = Icons.Filled.Done,
                onClickActionsIcon = {
                    onSaveClick()
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TodoEntryForm(
                todoDetails = todoUiState.todoDetails,
                onValueChange = updateUiState,
                categoryId = categoryId
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoEntryForm(
    todoDetails: TodoDetails,
    onValueChange: (TodoDetails) -> Unit,
    categoryId: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = todoDetails.todoTitle,
            onValueChange = {
                onValueChange(todoDetails.copy(todoTitle = it, todoCategoryId = categoryId))
            },
            label = {
                Text(text = stringResource(R.string.label_todo_title))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = todoDetails.todoInfo,
            onValueChange = {
                onValueChange(todoDetails.copy(todoInfo = it))
            },
            label = {
                Text(text = stringResource(R.string.label_todo_info))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
    }
}