package com.andmar.todo.ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.border
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.TabRow
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.style.TextDecoration
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import com.andmar.todo.ui.navigation.NavigateDestination
import com.andmar.todo.ui.AppViewModelProvider
import com.andmar.todo.TodoTopAppBar
import com.andmar.todo.TodoModalSheetItem
import com.andmar.todo.data.TodoItem
import com.andmar.todo.data.TodoCategory

import com.andmar.todo.R


object MainDestination: NavigateDestination {
    override val route = "mainScreen"
    override val title = R.string.top_app_bar_main_title
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}"
    val showNavigationIcon = false
    val showActionsIcon = true
    val actionsIcon = Icons.Filled.MoreVert
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onNavTodoUpdate: (Int) -> Unit,
    onNavTodoEntry: (Int) -> Unit,
    onNavCategorySettings: () -> Unit,
    onNavSettings: () -> Unit,
    onNavCategoryEntry: () -> Unit
) {
val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
val todoList = viewModel.mainUiState.collectAsState()
val todoCategoryList = viewModel.mainCategoryState.collectAsState()

val sheetState = rememberModalBottomSheetState()
val bottomSheetState = remember {
    mutableStateOf(false)
}
val categoryId = remember {
    mutableStateOf(0)
 }
 
val selectedTabIndex = rememberSaveable {
    mutableStateOf(0)
}

val longClick = remember {
    mutableStateOf(false)
}

val todoItemsList = mutableListOf<TodoItem>()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TodoTopAppBar(
                title = stringResource(MainDestination.title),
                scrollBehavior = scrollBehavior,
                showNavigationIcon = MainDestination.showNavigationIcon,
                showActionsIcon = MainDestination.showActionsIcon,
                actionsIcon = MainDestination.actionsIcon,
                onClickActionsIcon = { bottomSheetState.value = true },
                isLongClick = longClick.value,
                onLongClickDelete = {
                    viewModel.deleteTodoItems(todoItemsList)
                    longClick.value = false
                },
                onLongClickImportante = {
                    viewModel.importanteTodoItems(todoItemsList)
                    longClick.value = false
                },
                onLongClickClose = {
                    longClick.value = false
                }
            )
        },
        floatingActionButton = {
            if(todoCategoryList.value.todoCategoryList.size != 0) {
                FloatingActionButton(
                    onClick = {
                        onNavTodoEntry(categoryId.value)
                    },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = null
                    )
                }
            }
        }
   ) { padding ->            
        MainBody(
            scaffoldPadding = padding,
            todoList = todoList.value.todoList,
            todoCategoryList = todoCategoryList.value.todoCategoryList,
            onNavTodoUpdate = onNavTodoUpdate,
            selectedTabIndex = selectedTabIndex,
            categoryId = categoryId,
            testVibro = {
                viewModel.vibrateLongClick()
            },
            onClickUpdateTodo = {
                viewModel.updateTodo(it)
            },
            onNavCategoryEntry = onNavCategoryEntry,
            longClick = longClick,
            todoItemsList = todoItemsList
        )
        
        if(bottomSheetState.value) {
            ModalBottomSheet(
                onDismissRequest = {
                    bottomSheetState.value = false
                },
                sheetState = sheetState 
            ) {
                TodoModalSheetItem(
                    itemText = stringResource(R.string.category_settings),
                    itemIcon = Icons.Filled.Settings,
                    onClickItem = {
                        onNavCategorySettings()
                    }
                )
                TodoModalSheetItem(
                    itemText = stringResource(R.string.settings),
                    itemIcon = Icons.Filled.Settings,
                    onClickItem = {
                        onNavSettings()
                    }
                )
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
   }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainBody(
    scaffoldPadding: PaddingValues,
    todoList: List<TodoItem>,
    todoCategoryList: List<TodoCategory>,
    onNavTodoUpdate: (Int) -> Unit,
    selectedTabIndex: MutableState<Int>,
    categoryId: MutableState<Int>,
    testVibro: () -> Unit,
    onClickUpdateTodo: (TodoItem) -> Unit,
    onNavCategoryEntry: () -> Unit,
    longClick: MutableState<Boolean>,
    todoItemsList: MutableList<TodoItem>
) {
val pagerState = rememberPagerState(pageCount = { todoCategoryList.size })

LaunchedEffect(selectedTabIndex.value) {
    pagerState.animateScrollToPage(selectedTabIndex.value)
}

LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
    if(!pagerState.isScrollInProgress) {
        selectedTabIndex.value = pagerState.currentPage
    }
}

val sheetState = rememberModalBottomSheetState()
val menuSheet = remember {
    mutableStateOf(false)
}

if(menuSheet.value) {
    ModalBottomSheet(
        onDismissRequest = {
            menuSheet.value = false
        },
        sheetState = sheetState
    ) {
        TodoModalSheetItem(
            itemText = "Edit",
            itemIcon = Icons.Filled.Edit,
            onClickItem = {
            
            }
        )
        Spacer(modifier = Modifier.height(30.dp))
    }
}

    Column(
        modifier = Modifier
            .padding(scaffoldPadding)
    ) {
    
        val filteredCategoryList = todoCategoryList.sortedBy {
            it.isImportante == false
        }
    
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex.value
        ) {
            filteredCategoryList.forEachIndexed { index, item ->
                if(selectedTabIndex.value == index) {
                    categoryId.value = item.categoryId
                }
                Tab(
                    selected = index == selectedTabIndex.value,
                    onClick = {
                        if(selectedTabIndex.value == index) {
                            menuSheet.value = true
                        } else {
                            selectedTabIndex.value = index
                        }
                    }, 
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if(item.isImportante) {
                                Icon(
                                    Icons.Filled.Star,
                                    null
                                )
                            }
                            Text(item.nameCategory)
                        }
                    }
                )
            }
            TextButton(
                onClick = {
                    onNavCategoryEntry()
                }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Filled.Add,
                        null,
                        modifier = Modifier
                            .padding(3.dp)
                    )
                    Text(
                        text = stringResource(R.string.add_category_button)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        
        val filteredTodoList = remember(todoList, todoCategoryList) {
            todoCategoryList.associateWith { category ->
                todoList.filter { 
                    it.todoCategoryId == category.categoryId
                }
            }
        }
        
        HorizontalPager(
            state = pagerState
        ) { page -> 
            
            val todoListForPade = filteredTodoList[todoCategoryList[page]] ?: emptyList()
                    
            MainList(
                todoList = todoListForPade,
                onClickItem = { onNavTodoUpdate(it.id) },
                testVibro = testVibro,
                onClickUpdateTodo = onClickUpdateTodo,
                longClick = longClick,
                todoItemsList = todoItemsList
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainList(
    todoList: List<TodoItem>,
    onClickItem: (TodoItem) -> Unit,
    longClick: MutableState<Boolean>,
    testVibro: () -> Unit,
    onClickUpdateTodo: (TodoItem) -> Unit,
    todoItemsList: MutableList<TodoItem>
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
        
            if(todoList.size == 0) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.is_empty_todo)
                    )
                }
            }
        
        
            val todoItemList = remember(todoList) {
                todoList.filter {
                    it.isDone == false
                }
            }
            if(todoItemList.size != 0) {
                todoItemList.forEach { item ->
            
                    val color = if(item.isImportante) MaterialTheme.colorScheme.primary else Color.Transparent
                    
                    MainCard(
                        item = item,
                        onClickUpdateTodo = onClickUpdateTodo,
                        longClick = longClick,
                        todoItemsList = todoItemsList,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .border(2.dp, color , RoundedCornerShape(20.dp))
                            .combinedClickable (
                                onClick = {
                                    if(!longClick.value) onClickItem(item)
                                },
                                onLongClick = {
                                    longClick.value = true
                                    testVibro()
                                }
                            )
                    )
                }
            }
            
            val isDoneTodoItemList = remember(todoList) {
                todoList.filter {
                    it.isDone == true
                }
            }
            
            if(isDoneTodoItemList.size != 0) {
                Text(
                    text = stringResource(R.string.is_done),
                    modifier = Modifier
                        .padding(5.dp)
                )
                isDoneTodoItemList.forEach { item ->
            
                    var color = if(item.isImportante) MaterialTheme.colorScheme.primary else Color.Transparent
                    
                    MainCard(
                        item = item,
                        onClickUpdateTodo = onClickUpdateTodo,
                        longClick = longClick,
                        todoItemsList = todoItemsList,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, top = 10.dp, end = 10.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .border(2.dp, color , RoundedCornerShape(20.dp))
                            .combinedClickable (
                                onClick = {
                                   if(!longClick.value) onClickItem(item)
                                },
                                onLongClick = {
                                    longClick.value = true
                                    testVibro()
                                }
                            )
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainCard(
    item: TodoItem,
    modifier: Modifier,
    longClick: MutableState<Boolean>,
    onClickUpdateTodo: (TodoItem) -> Unit,
    todoItemsList: MutableList<TodoItem>
) {

val checked = remember {
    mutableStateOf(false)
}

if(checked.value) todoItemsList.add(item)

    Card(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = item.todoTitle,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(5.dp),
                    maxLines = 1,
                    textDecoration = if(item.isDone) TextDecoration.LineThrough else TextDecoration.None
                )
                Text(
                    text = item.todoInfo,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(5.dp),
                    maxLines = 1,
                    textDecoration = if(item.isDone) TextDecoration.LineThrough else TextDecoration.None
                )
            }
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
                                onClickUpdateTodo(item.copy(isImportante = it))
                            }
                        ) {
                            Icon(
                                Icons.Outlined.Star,
                                null
                            )
                        }
                        Checkbox(
                            modifier = Modifier
                                .padding(5.dp),
                            checked = item.isDone,
                            onCheckedChange = {
                            onClickUpdateTodo(item.copy(isDone = it))
                            }
                        )
                    }
                }
            }
        } 
    }
}