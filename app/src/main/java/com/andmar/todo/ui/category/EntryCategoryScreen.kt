package com.andmar.todo.ui.category

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

object EntryCategoryDestination: NavigateDestination {
    override val route = "todoEntryColumnScreen"
    override val title = R.string.top_app_bar_category_entry_settings_title
}

@Composable
fun EntryCategoryScreen(
    viewModel: EntryCategoryViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onNavClickBack: () -> Unit
) {
 

    EntryCategoryBody(
        topAppBarTitle = stringResource(EntryCategoryDestination.title),
        categoryState = viewModel.todoCategoryUiState,
        updateCategoryState = {
            viewModel.updateCategoryState(it)
        },
        saveCategory = {
            viewModel.saveCategory()
        },
        onNavClickBack = onNavClickBack
    )
    
    
    
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryCategoryBody(
    topAppBarTitle: String,
    categoryState: CategoryState,
    updateCategoryState: (CategoryDetails) -> Unit,
    saveCategory: () -> Unit,
    onNavClickBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TodoTopAppBar(
                title = topAppBarTitle,
                showNavigationIcon = true,
                onClickNavigateIcon = { onNavClickBack() },
                showActionsIcon = categoryState.isShowButton,
                onClickActionsIcon = {
                    saveCategory()
                    onNavClickBack()
                },
                actionsIcon = Icons.Filled.Done
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,    
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CategoryForm(
                categoryDetails = categoryState.categoryDetails,
                onValueChange = {
                    updateCategoryState(it)
                }
            )
        }
    }
}

@Composable
fun CategoryForm(
    categoryDetails: CategoryDetails,
    onValueChange: (CategoryDetails) -> Unit
) {
    OutlinedTextField(
        value = categoryDetails.nameCategory,
        onValueChange = {
            onValueChange(categoryDetails.copy(nameCategory = it))
        },
        label = {
            Text(stringResource(R.string.text_field_category_entry_settings_label))
        }
    )
}
