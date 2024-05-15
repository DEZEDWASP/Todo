package com.andmar.todo.ui.todo

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Card
import androidx.compose.material3.AssistChip
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.style.TextDecoration
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import com.andmar.todo.R
import com.andmar.todo.ui.navigation.NavigateDestination
import com.andmar.todo.ui.AppViewModelProvider
import com.andmar.todo.TodoTopAppBar
import com.andmar.todo.TodoModalSheetItem
import com.andmar.todo.data.TodoItem
import com.andmar.todo.data.AlarmItem
import com.andmar.todo.data.TodoAdditional

object TodoDetailsDestination: NavigateDestination {
    override val route = "detailsScreen"
    override val title = R.string.top_app_bar_details_title
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoDetailsScreen(
    viewModel: TodoDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onNavTodoEdit: (Int) -> Unit,
    onNavClickBack: () -> Unit,
    onNavEntryAddition: (Int) -> Unit,
    onNavEditAdditional: (Int) -> Unit,
    onNavClickReminder: (Int) -> Unit
) {
val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
val uiState = viewModel.uiState.collectAsState()
val todoAdditionalList = viewModel.todoAdditioanlList.collectAsState()
val alarmList = viewModel.alarmList.collectAsState()

val sheetState = rememberModalBottomSheetState()
val isShowSheet = remember {
    mutableStateOf(false)
}

val longClick = remember {
    mutableStateOf(false)
}

val additionalsList = mutableListOf<TodoAdditional>()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TodoTopAppBar(
                title = stringResource(TodoDetailsDestination.title),
                scrollBehavior = scrollBehavior,
                showNavigationIcon = ShowTopBarIcons.SHOW_NAVIGATION_ICON,
                onClickNavigateIcon = {
                    onNavClickBack()
                },
                showActionsIcon = ShowTopBarIcons.SHOW_ACTIONS_ICON,
                actionsIcon = Icons.Filled.MoreVert,
                onClickActionsIcon = {
                    isShowSheet.value = true
                },
                isLongClick = longClick.value,
                onLongClickDelete = {
                    viewModel.deleteTodoAdditionals(additionalsList)
                    longClick.value = false
                },
                onLongClickImportante = {
                    viewModel.importanteTodoAdditionals(additionalsList)
                    longClick.value = false
                },
                onLongClickClose = {
                    longClick.value = false
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                   onNavEntryAddition(uiState.value.todoDetails.id)
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null
                )
            }
        }
    ) { padding ->
        TodoDetailsBody(
            uiState = uiState.value,
            todoAdditionalList = todoAdditionalList.value.todoAdditionalList,
            alarmList = alarmList.value.alarmList,
            scaffoldPadding = padding,
            longClick = longClick,
            additionalsList = additionalsList,
            editAdditional = { 
                onNavEditAdditional(it)
            },
            deleteAdditional = { 
                viewModel.deleteTodoAdditional(it)
            },
            onClickDeleteReminder = {
                viewModel.deleteAlarm(it)
            },
            onClickUpdateAdditional = {
                viewModel.updateAdditional(it)
            },
            vibrate = {
                viewModel.vibrate()
            }
        )
        if(isShowSheet.value) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = {
                    isShowSheet.value = false
                }
            ) {
                TodoModalSheetItem(
                    itemText = stringResource(R.string.details_menu_add_remonder),
                    itemIcon = Icons.Filled.DateRange,
                    onClickItem = {
                        isShowSheet.value = false
                        onNavClickReminder(uiState.value.todoDetails.id)
                    }
                )
                TodoModalSheetItem(
                    itemText = stringResource(R.string.details_menu_edit_todo),
                    itemIcon = Icons.Filled.Edit,
                    onClickItem = {
                        isShowSheet.value = false
                        onNavTodoEdit(uiState.value.todoDetails.id)
                    }
                )
                TodoModalSheetItem(
                    itemText = stringResource(R.string.details_menu_delete_todo),
                    itemIcon = Icons.Filled.Delete,
                    onClickItem = {
                        isShowSheet.value = false
                        viewModel.deleteTodo(uiState.value.todoDetails.toTodoItem())
                    }
                )
                TodoModalSheetItem(
                    itemText = stringResource(R.string.details_menu_is_done_todo),
                    itemIcon = Icons.Filled.Done,
                    onClickItem = {
                        isShowSheet.value = false
                        viewModel.updateTodo(uiState.value.todoDetails.copy(isDone = true).toTodoItem())
                    }
                )
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}

@Composable
fun TodoDetailsBody(
    uiState: TodoDetailsState,
    todoAdditionalList: List<TodoAdditional>,
    alarmList: List<AlarmItem>,
    scaffoldPadding: PaddingValues,
    longClick: MutableState<Boolean>,
    additionalsList: MutableList<TodoAdditional>,
    editAdditional: (Int) -> Unit,
    deleteAdditional: (TodoAdditional) -> Unit,
    onClickDeleteReminder: (AlarmItem) -> Unit,
    onClickUpdateAdditional: (TodoAdditional) -> Unit,
    vibrate: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(scaffoldPadding)
    ) {
        item {    
            TodoDetailsItem(
                item = uiState.todoDetails.toTodoItem(),
                alarmList = alarmList,
                onClickDeleteReminder = onClickDeleteReminder
            )
            Spacer(modifier = Modifier.height(30.dp))
            
            val filteredAdditionalList = remember(todoAdditionalList) {
                todoAdditionalList.filter { 
                    it.todoItemId == uiState.todoDetails.id
                }
            }
            
            if(filteredAdditionalList.size != 0) {
                TodoAdditional(
                    todoAdditionalList = filteredAdditionalList,
                    longClick = longClick,
                    additionalsList = additionalsList,
                    editAdditional = {
                        editAdditional(it)
                    },
                    deleteAdditional = {
                        deleteAdditional(it)
                    },
                    onClickUpdateAdditional = onClickUpdateAdditional,
                    vibrate = vibrate
                )
            } 
        }
    }
}

@Composable
fun TodoDetailsItem(
    item: TodoItem,
    alarmList: List<AlarmItem>,
    onClickDeleteReminder: (AlarmItem) -> Unit
) {
    Column {
        Text(
            modifier = Modifier
                .padding(horizontal = 10.dp),
            text = item.todoTitle,
            textDecoration = if(item.isDone) TextDecoration.LineThrough else TextDecoration.None,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        if(item.todoInfo.isNotBlank()) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier
                    .padding(horizontal = 10.dp),
                text = item.todoInfo,
                textDecoration = if(item.isDone) TextDecoration.LineThrough else TextDecoration.None
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        
        alarmList.forEach { alarmItem ->
            if(alarmItem.todoItemId == item.id) {
                ReminderChip(
                    alarmItem = alarmItem,
                    onClickDeleteReminder = onClickDeleteReminder
                )
            }
        }
    }
}

@Composable
fun ReminderChip(
    alarmItem: AlarmItem,
    onClickDeleteReminder: (AlarmItem) -> Unit
) {
    AssistChip(
        onClick = {
            onClickDeleteReminder(alarmItem)
        },
        label = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.text_reminder)
                )
            }
            Text(" ${alarmItem.timeReminderMessage}")
        },
        trailingIcon = {
            Icon(
                Icons.Filled.Close,
                null
            )
        },
        modifier = Modifier
            .padding(10.dp)
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodoAdditional(
    todoAdditionalList: List<TodoAdditional>,
    longClick: MutableState<Boolean>,
    additionalsList: MutableList<TodoAdditional>,
    editAdditional: (Int) -> Unit,
    deleteAdditional: (TodoAdditional) -> Unit,
    onClickUpdateAdditional: (TodoAdditional) -> Unit,
    vibrate: () -> Unit
) {


    Column {
        Text(
            modifier = Modifier
                .padding(horizontal = 10.dp),
            text = stringResource(R.string.additional_todo_text)
        )
        
        
        val notDoneAdditionalList = remember(todoAdditionalList) {
            todoAdditionalList.filter {
                it.isDone == false
            }
        }
        
        notDoneAdditionalList.forEach { item -> 
            
            
            TodoAdditionalCard(
                item = item,
                longClick = longClick,
                vibrate = vibrate,
                additionalsList = additionalsList,
                editAdditional = {
                    editAdditional(item.additionalId)
                },    
                deleteAdditional = {
                    deleteAdditional(item)
                },
                onClickUpdateAdditional = onClickUpdateAdditional
            )
        }
        
        val doneAdditionalList = remember(todoAdditionalList) {
            todoAdditionalList.filter {
                it.isDone == true
            }
        }
        
        if(doneAdditionalList.size > 0) {
            Text(
                text = stringResource(R.string.is_done),
                modifier = Modifier.padding(10.dp)
            )
        }
        
        doneAdditionalList.forEach { item -> 
            
            
            TodoAdditionalCard(
                item = item,
                longClick = longClick,
                vibrate = vibrate,
                additionalsList = additionalsList,
                editAdditional = {
                    editAdditional(item.additionalId)
                },    
                deleteAdditional = {
                    deleteAdditional(item)
                },
                onClickUpdateAdditional = onClickUpdateAdditional
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TodoAdditionalCard(
    item: TodoAdditional,
    longClick: MutableState<Boolean>,
    additionalsList: MutableList<TodoAdditional>,
    editAdditional: () -> Unit,
    deleteAdditional: () -> Unit,
    onClickUpdateAdditional: (TodoAdditional) -> Unit,
    vibrate: () -> Unit
) {
val borderColor = if(item.isImportante) MaterialTheme.colorScheme.primary else Color.Transparent
val sheetState = rememberModalBottomSheetState()

val checked = remember {
    mutableStateOf(false)
}

if(checked.value) additionalsList.add(item)
if(!checked.value) additionalsList.removeIf { it == item }

val isShowAdditionalSheet = remember {
    mutableStateOf(false)
}

if(isShowAdditionalSheet.value) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            isShowAdditionalSheet.value = false
        }
    ) {
        TodoModalSheetItem(
            itemText = stringResource(R.string.details_menu_edit_additional),
            itemIcon = Icons.Filled.Edit,
            onClickItem = {
                editAdditional()
                isShowAdditionalSheet.value = false
            }
        )
        TodoModalSheetItem(
            itemText = stringResource(R.string.details_menu_delete_additional),
            itemIcon = Icons.Filled.Delete,
            onClickItem = {
                deleteAdditional()
                isShowAdditionalSheet.value = false
            }
        )
        if(item.isDone) {
            TodoModalSheetItem(
                itemText = stringResource(R.string.details_menu_is_not_done_additional),
                itemIcon = Icons.Filled.Done,
                onClickItem = {
                    onClickUpdateAdditional(item.copy(isDone = false))
                    isShowAdditionalSheet.value = false
                }
            )
        } else {
            TodoModalSheetItem(
                itemText = stringResource(R.string.details_menu_is_done_additional),
                itemIcon = Icons.Outlined.Done,
                onClickItem = {
                    onClickUpdateAdditional(item.copy(isDone = true))
                    isShowAdditionalSheet.value = false
                }
            )
        }
        if(item.isImportante) {
            TodoModalSheetItem(
                itemText = stringResource(R.string.details_menu_is_not_importante_additional),
                itemIcon = Icons.Filled.Star,
                onClickItem = {
                    onClickUpdateAdditional(item.copy(isImportante = false))
                    isShowAdditionalSheet.value = false
                }
            )
        } else {
            TodoModalSheetItem(
                itemText = stringResource(R.string.details_menu_is_importante_additional),
                itemIcon = Icons.Outlined.Star,
                onClickItem = {
                    onClickUpdateAdditional(item.copy(isImportante = true))
                    isShowAdditionalSheet.value = false
                }
            )
        }
        
        Spacer(modifier = Modifier.height(30.dp))
    }
}
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, top = 10.dp, end = 10.dp)
            .clip(RoundedCornerShape(20.dp))
            .border(2.dp, borderColor, RoundedCornerShape(20.dp))
            .combinedClickable (
                onClick = {
                    isShowAdditionalSheet.value = true
                },
                onLongClick = {
                   longClick.value = true
                   vibrate()
                }
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(10.dp),
                text = item.todoAdditional,
                textDecoration = if(item.isDone) TextDecoration.LineThrough else TextDecoration.None
            )
            AnimatedContent(targetState = longClick.value) { isLongClick ->
                if(isLongClick) {
                    Checkbox(
                        modifier = Modifier
                            .padding(5.dp),
                        checked = checked.value,
                        onCheckedChange = {
                            checked.value = it
                        }
                    )
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconToggleButton(
                            checked = item.isImportante,
                            onCheckedChange = {
                                onClickUpdateAdditional(item.copy(isImportante = it))
                            }
                        ) {
                            Icon(
                                Icons.Filled.Star,
                                null
                            )
                        }
                        Checkbox(
                            modifier = Modifier
                                .padding(5.dp),
                            checked = item.isDone,
                            onCheckedChange = {
                                onClickUpdateAdditional(item.copy(isDone = it))
                            }
                        )
                    }
                }
            }
        }
    }
}