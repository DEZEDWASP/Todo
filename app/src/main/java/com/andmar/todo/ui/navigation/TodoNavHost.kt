package com.andmar.todo.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.NavHostController
import com.andmar.todo.ui.todo.TodoEntryDestination
import com.andmar.todo.ui.todo.TodoEntryScreen
import com.andmar.todo.ui.todo.TodoEditDestination
import com.andmar.todo.ui.todo.TodoEditScreen
import com.andmar.todo.ui.todo.TodoDetailsDestination
import com.andmar.todo.ui.todo.TodoDetailsScreen
import com.andmar.todo.ui.todo.TodoSettingsDestination
import com.andmar.todo.ui.todo.TodoSettingsScreen
import com.andmar.todo.ui.todo.TodoReminderDestination
import com.andmar.todo.ui.todo.TodoReminderScreen
import com.andmar.todo.ui.category.CategorySettingsDestination
import com.andmar.todo.ui.category.CategorySettings
import com.andmar.todo.ui.category.EntryCategoryDestination
import com.andmar.todo.ui.category.EntryCategoryScreen
import com.andmar.todo.ui.category.EditCategoryDestination
import com.andmar.todo.ui.category.EditCategoryScreen
import com.andmar.todo.ui.additional.EntryAdditionalDestination
import com.andmar.todo.ui.additional.EntryAdditionalScreen
import com.andmar.todo.ui.additional.EditAdditionalDestination
import com.andmar.todo.ui.additional.EditAdditionalScreen
import com.andmar.todo.ui.main.MainDestination
import com.andmar.todo.ui.main.MainScreen

@Composable
fun TodoNavHost(
    navController: NavHostController = rememberNavController()
) {

    NavHost(
        navController = navController,
        startDestination = MainDestination.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(route = MainDestination.route) {
            MainScreen(
                onNavTodoUpdate = {
                    navController.navigate("${TodoDetailsDestination.route}/${it}")
                },
                onNavTodoEntry = {
                    navController.navigate("${TodoEntryDestination.route}/$it")
                },
                onNavCategorySettings = {
                    navController.navigate(CategorySettingsDestination.route)
                },
                onNavSettings = {
                    navController.navigate(TodoSettingsDestination.route)
                },
                onNavCategoryEntry = {
                    navController.navigate(EntryCategoryDestination.route)
                }
            )
        }
        composable(route = TodoEntryDestination.routeWithArgs,
            arguments = listOf(navArgument(TodoEntryDestination.itemIdArg) {
                type = NavType.IntType
            }),
            enterTransition = {
                fadeIn(
                    animationSpec = tween(300, easing = LinearEasing),
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(300, easing = LinearEasing)
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) {
            TodoEntryScreen(
                onNavClickBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = TodoDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(TodoDetailsDestination.itemIdArg) {
                type = NavType.IntType
            }),
            enterTransition = {
                fadeIn(
                    animationSpec = tween(300, easing = LinearEasing),
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(300, easing = LinearEasing)
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) {
            TodoDetailsScreen(
                onNavTodoEdit = {
                    navController.navigate("${TodoEditDestination.route}/$it")
                },
                onNavClickBack = {
                    navController.popBackStack()
                },
                onNavEntryAddition = {
                    navController.navigate("${EntryAdditionalDestination.route}/$it")
                },
                onNavEditAdditional = {
                    navController.navigate("${EditAdditionalDestination.route}/$it")
                },
                onNavClickReminder = {
                    navController.navigate("${TodoReminderDestination.route}/$it")
                }
            )
        }
        composable(
            route = TodoEditDestination.routeWithArgs,
            arguments = listOf(navArgument(TodoEditDestination.itemIdArg) {
                type = NavType.IntType
            }),
            enterTransition = {
                fadeIn(
                    animationSpec = tween(300, easing = LinearEasing),
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(300, easing = LinearEasing)
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) {
            TodoEditScreen(
                onNavClickBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(
            route = CategorySettingsDestination.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(300, easing = LinearEasing),
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(300, easing = LinearEasing)
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        
        ) {
            CategorySettings(
                onNavClickBack = {
                    navController.popBackStack()
                },
                onNavCategoryEntry = {
                    navController.navigate(EntryCategoryDestination.route)
                },
                onNavCategoryEdit = {
                    navController.navigate("${EditCategoryDestination.route}/$it")
                }
            )
        }
        
        composable(
            route = EntryCategoryDestination.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(300, easing = LinearEasing),
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(300, easing = LinearEasing)
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) {
            EntryCategoryScreen(
                onNavClickBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(
            route = EditCategoryDestination.routeWithArgs,
            arguments = listOf(navArgument(EditCategoryDestination.itemIdArg) {
                type = NavType.IntType
            }),
            enterTransition = {
                fadeIn(
                    animationSpec = tween(300, easing = LinearEasing),
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(300, easing = LinearEasing)
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) {
            EditCategoryScreen(
                onNavClickBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(
            route = EntryAdditionalDestination.routeWithArgs,
            arguments = listOf(navArgument(EntryAdditionalDestination.itemIdArg) {
                type = NavType.IntType
            }),
            enterTransition = {
                fadeIn(
                    animationSpec = tween(300, easing = LinearEasing),
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(300, easing = LinearEasing)
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        
        ) {
            EntryAdditionalScreen(
                onNavClickBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(
            route = EditAdditionalDestination.routeWithArgs,
            arguments = listOf(navArgument(EditAdditionalDestination.itemIdArg) {
                type = NavType.IntType
            }),
            enterTransition = {
                fadeIn(
                    animationSpec = tween(300, easing = LinearEasing),
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(300, easing = LinearEasing)
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) {
            EditAdditionalScreen(
                onNavClickBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(
            route = TodoSettingsDestination.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(300, easing = LinearEasing),
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(300, easing = LinearEasing)
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) {
            TodoSettingsScreen(
                onNavClickBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(
            route = TodoReminderDestination.routeWithArgs,
            arguments = listOf(navArgument(TodoReminderDestination.itemIdArg) {
                type = NavType.IntType
            }),
            enterTransition = {
                fadeIn(
                    animationSpec = tween(300, easing = LinearEasing),
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(300, easing = LinearEasing)
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) {
            TodoReminderScreen(
                onNavClickBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
