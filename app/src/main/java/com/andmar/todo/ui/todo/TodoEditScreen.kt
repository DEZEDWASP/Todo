package com.andmar.todo.ui.todo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import com.andmar.todo.ui.navigation.NavigateDestination
import com.andmar.todo.ui.AppViewModelProvider
import com.andmar.todo.R

object TodoEditDestination: NavigateDestination {
    override val route = "editScreen"
    override val title = R.string.top_app_bar_edit_todo_title
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}"
}

@Composable
fun TodoEditScreen(
    viewModel: TodoEditViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onNavClickBack: () -> Unit
) {
val scope = rememberCoroutineScope()

    TodoEntryBody(
        titleText = stringResource(TodoEditDestination.title),
        todoUiState = viewModel.itemUiState,
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
        categoryId = viewModel.itemUiState.todoDetails.todoCategoryId,
        showActionsIcon = viewModel.itemUiState.isShowButton
    )
}