package com.andmar.todo.ui.additional

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
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

object EntryAdditionalDestination: NavigateDestination {
    override val route = "todoEntryAdditionalScreen"
    override val title = R.string.top_app_bar_entry_additional_title
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}"
}

@Composable
fun EntryAdditionalScreen(
    viewModel: EntryAdditionalViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onNavClickBack: () -> Unit
) {
    TodoEntryAdditionalBody(
        topAppBarTitle = stringResource(EntryAdditionalDestination.title),
        additionalDetails = viewModel.todoAdditionalState.additionalDetails,
        updateAdditionalState = {
            viewModel.updateAdditionalState(it)
        },
        insertAdditional = {
            viewModel.insertAdditional()
        },
        onNavClickBack = onNavClickBack,
        showActionsIcon = viewModel.todoAdditionalState.isShowButton
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoEntryAdditionalBody(
    topAppBarTitle: String,
    additionalDetails: TodoAdditionalDetails,
    updateAdditionalState: (TodoAdditionalDetails) -> Unit,
    insertAdditional: () -> Unit,
    onNavClickBack: () -> Unit,
    showActionsIcon: Boolean
) {

    Scaffold(
        topBar = {
            TodoTopAppBar(
                title =  topAppBarTitle,
                showNavigationIcon = true,
                onClickNavigateIcon = {
                    onNavClickBack()
                },
                showActionsIcon = showActionsIcon,
                actionsIcon = Icons.Filled.Done,
                onClickActionsIcon = {
                    insertAdditional()
                    onNavClickBack()
                },
                
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
            AdditionalForm(
                additionalDetails = additionalDetails,
                onValueChange = { updateAdditionalState(it) }
            )
        }
    }
}

@Composable
fun AdditionalForm(
    additionalDetails: TodoAdditionalDetails,
    onValueChange: (TodoAdditionalDetails) -> Unit
) { 
    OutlinedTextField(
        value = additionalDetails.todoAdditional,
        onValueChange = {
            onValueChange(additionalDetails.copy(todoAdditional = it))
        },
        label = {
            Text(
                text = stringResource(R.string.label_additional)
            )
        }
    )
}