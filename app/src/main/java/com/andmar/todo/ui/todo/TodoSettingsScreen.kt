package com.andmar.todo.ui.todo

import android.content.Context
import android.widget.Toast
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TabRow
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import com.andmar.todo.ui.navigation.NavigateDestination
import com.andmar.todo.ui.AppViewModelProvider
import com.andmar.todo.TodoTopAppBar
import com.andmar.todo.data.TodoItem
import com.andmar.todo.data.TodoAdditional
import com.andmar.todo.R

object TodoSettingsDestination: NavigateDestination {
    override val route = "todoSettingsScreen"
    override val title = R.string.top_app_bar_string_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoSettingsScreen(
    viewModel: TodoSettingsViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onNavClickBack: () -> Unit
) {

val settingsAppState = viewModel.settingsAppState.collectAsState()

    Scaffold(
        topBar = {
            TodoTopAppBar(
                title = stringResource(TodoSettingsDestination.title),
                showNavigationIcon = true,
                onClickNavigateIcon = {
                    onNavClickBack()
                }
            )
        }
    ) { padding ->
        TodoSettingsBody(
            contentPadding = padding,
            settingsAppState = settingsAppState.value,
            updateSettingsAppState = {
                viewModel.saveSettings(it)
            }
        )
    }

}

@Composable
fun TodoSettingsBody(
    contentPadding: PaddingValues,
    settingsAppState: SettingsAppState,
    updateSettingsAppState: (SettingsAppState) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
    ) {
        item {
            
        }
    }
}