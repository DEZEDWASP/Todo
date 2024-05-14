package com.andmar.todo.ui.category

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import com.andmar.todo.ui.navigation.NavigateDestination
import com.andmar.todo.ui.AppViewModelProvider
import com.andmar.todo.R

object EditCategoryDestination: NavigateDestination {
    override val route = "categoryEditScreen"
    override val title = R.string.top_app_bar_category_edit_settings_title
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}"
}

@Composable
fun EditCategoryScreen(
    viewModel: EditCategoryViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onNavClickBack: () -> Unit
) {

    EntryCategoryBody(
        topAppBarTitle = stringResource(EditCategoryDestination.title),
        categoryState = viewModel.categoryUiState,
        updateCategoryState = {
            viewModel.updateCategoryState(it)
        },
        saveCategory = {
            viewModel.seveCategory()
        },
        onNavClickBack = onNavClickBack
    )
}