package com.andmar.todo.ui.additional

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import com.andmar.todo.ui.navigation.NavigateDestination
import com.andmar.todo.ui.AppViewModelProvider
import com.andmar.todo.R

object EditAdditionalDestination: NavigateDestination {
    override val route = "editAdditionalScreen"
    override val title = R.string.top_app_bar_edit_additional_title
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}"
}

@Composable
fun EditAdditionalScreen(
    viewModel: EditAdditionalViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onNavClickBack: () -> Unit
) {

    TodoEntryAdditionalBody(
        topAppBarTitle = stringResource(EditAdditionalDestination.title),
        additionalDetails = viewModel.additionalUiState.additionalDetails,
        updateAdditionalState = {
            viewModel.updateAdditionalUiState(it)
        },
        insertAdditional = {
            viewModel.insertAdditional()
        },
        onNavClickBack = onNavClickBack,
        showActionsIcon = true
    )
}