package com.andmar.todo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedContent
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.launch
import com.andmar.todo.ui.navigation.TodoNavHost
import com.andmar.todo.ui.todo.ShowTopBarIcons

@Composable
fun TodoApp() {
    TodoNavHost()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoTopAppBar(
    title: String,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onClickNavigateIcon: () -> Unit = {},
    onClickActionsIcon: () -> Unit = {},
    showNavigationIcon: Boolean = false,
    showActionsIcon: Boolean = false,
    actionsIcon: ImageVector = Icons.Filled.MoreVert,
    isLongClick: Boolean = false,
    onLongClickDelete: () -> Unit = {},
    onLongClickImportante: () -> Unit = {},
    onLongClickClose: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = title
            )
        },
        navigationIcon = {
            if(showNavigationIcon) {
                IconButton(
                    onClick = {
                        onClickNavigateIcon()
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        },
        actions = {
            if(showActionsIcon) {
                AnimatedContent(targetState = isLongClick) { isLongClick ->
                    if(isLongClick) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = {
                                    onLongClickDelete()
                                }
                            ) {
                                Icon(
                                    Icons.Outlined.Delete,
                                    null
                                )
                            }
                            IconButton(
                                onClick = {
                                    onLongClickImportante()
                                }
                            ) {
                                Icon(
                                    Icons.Outlined.Star,
                                    null
                                )
                            }
                            IconButton(
                                onClick = {
                                    onLongClickClose()        
                                }
                            ) {
                                Icon(
                                    Icons.Outlined.Close,
                                    "Close"
                                )
                            }
                        }
                    } else {
                        IconButton(
                            onClick = {
                                onClickActionsIcon()        
                            }
                        ) {
                            Icon(
                                actionsIcon,
                                null
                            )
                        }
                    }
                }
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
fun TodoModalSheetItem(
    itemText: String,
    itemIcon: ImageVector,
    onClickItem: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                  onClickItem()
            }
    ) {
        Icon(
            itemIcon,
            null,
            modifier = Modifier
                .padding(10.dp)
        )
        Text(
            text = itemText,
            modifier = Modifier
                .padding(10.dp)
        )
    }
}