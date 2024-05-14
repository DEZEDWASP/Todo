package com.andmar.todo.ui.category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.TabRow
import androidx.compose.material3.Card
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.viewModel
import com.andmar.todo.ui.navigation.NavigateDestination
import com.andmar.todo.ui.AppViewModelProvider
import com.andmar.todo.TodoTopAppBar
import com.andmar.todo.TodoModalSheetItem
import com.andmar.todo.data.TodoCategory
import com.andmar.todo.R

object CategorySettingsDestination: NavigateDestination {
    override val route = "columnSettingsScreen"
    override val title = R.string.top_app_bar_column_settings_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySettings(
    viewModel: CategorySettingsViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onNavClickBack: () -> Unit,
    onNavCategoryEntry: () -> Unit,
    onNavCategoryEdit: (Int) -> Unit
) {
val categoryList = viewModel.categoryList.collectAsState()
val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())    


    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection), 
        topBar = {
            TodoTopAppBar(
                title = stringResource(CategorySettingsDestination.title),
                scrollBehavior = scrollBehavior,
                onClickNavigateIcon = { onNavClickBack() },
                showNavigationIcon = true
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onNavCategoryEntry()
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    Icons.Filled.Add,
                    null
                )
            }
        }
    ) { padding ->
        CategorySettingsBody(
            contentPadding = padding,
            categoryList = categoryList.value.categoryList,
            onClickDeleteColumn = { viewModel.deleteCategory(it) },
            onNavCategoryEdit = onNavCategoryEdit,
            onClickUpdateCategory = {
                viewModel.updateCategory(it)
            }
        )
    }
}

@Composable
fun CategorySettingsBody(
    contentPadding: PaddingValues,
    categoryList: List<TodoCategory>,
    onClickDeleteColumn: (TodoCategory) -> Unit,
    onNavCategoryEdit: (Int) -> Unit,
    onClickUpdateCategory: (TodoCategory) -> Unit
) {
val sortedCategoryList = categoryList.sortedBy {
    it.isImportante == false
}
    
    if(categoryList.size == 0) {
        CategorySettingsInfo(
            contentPadding = contentPadding
        )
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
    ) {
        items(sortedCategoryList) { item ->
            CategoryCard(
                item,
                onClickDeleteColumn = { onClickDeleteColumn(item) },
                onNavCategoryEdit = { onNavCategoryEdit(item.categoryId) },
                onClickUpdateCategory = onClickUpdateCategory
            )
        }
    }
}    

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryCard(
    item: TodoCategory,
    onClickDeleteColumn: () -> Unit,
    onNavCategoryEdit: () -> Unit,
    onClickUpdateCategory: (TodoCategory) -> Unit
) {
val sheetMenuState = rememberModalBottomSheetState()
val sheetMenu = remember {
    mutableStateOf(false)
}

if(sheetMenu.value) {
    ModalBottomSheet(
        onDismissRequest = {
            sheetMenu.value = false
        },
        sheetState = sheetMenuState
    ) {
        TodoModalSheetItem(
            itemText = stringResource(R.string.category_settings_menu_edit_category),
            itemIcon = Icons.Filled.Edit,
            onClickItem = {
                sheetMenu.value = false
                onNavCategoryEdit()
            }
        ) 
        TodoModalSheetItem(
            itemText = stringResource(R.string.category_settings_menu_delete_category),
            itemIcon = Icons.Filled.Delete,
            onClickItem = {
                sheetMenu.value = false
                onClickDeleteColumn()
            }
        ) 
        TodoModalSheetItem(
            itemText = "Importante",
            itemIcon = Icons.Filled.Star,
            onClickItem = {
                sheetMenu.value = false
                onClickUpdateCategory(item.copy(isImportante = true))
            }
        ) 
        Spacer(modifier = Modifier.height(30.dp))
    }
}
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, top = 10.dp, end = 10.dp)
            .clip(RoundedCornerShape(20.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(5.dp),
                text = item.nameCategory
            )
            IconButton(
                onClick = {
                    sheetMenu.value = true
                }
            ) {
                Icon(
                    Icons.Filled.MoreVert,
                    null
                )
            }
        }
    }
}

@Composable
fun CategorySettingsInfo(
    contentPadding: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.is_empty_category),
            modifier = Modifier
                .padding(20.dp)
        )
    }
}