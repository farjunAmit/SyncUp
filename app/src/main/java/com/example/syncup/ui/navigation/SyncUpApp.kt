package com.example.syncup.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.syncup.Routes
import com.example.syncup.data.AppContainer
import com.example.syncup.ui.event.screens.CreateEventScreen
import com.example.syncup.ui.group.screens.GroupDetailScreen
import com.example.syncup.ui.group.vm.GroupDetailViewModel
import com.example.syncup.ui.group.screens.GroupsScreen
import com.example.syncup.ui.group.vm.CreateEventViewModel
import com.example.syncup.ui.group.vm.GroupsViewModel

/**
 * SyncUpApp
 *
 * Root composable of the application.
 * Responsible for:
 * - Creating and holding the NavController
 * - Defining the navigation graph (NavHost)
 * - Wiring screen dependencies using AppContainer (manual DI)
 *
 * Navigation arguments (e.g., groupId) are passed via routes and extracted
 * from the backStackEntry.
 */
@Composable
fun SyncUpApp(appContainer: AppContainer) {

    // NavController is remembered so it survives recompositions
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.GROUPS
    ) {

        /**
         * Groups screen route.
         * Creates the required ViewModel and passes navigation callback.
         */
        composable(Routes.GROUPS) {

            // Manual ViewModel creation (lightweight alternative to Hilt for MVP stage)
            val groupsViewModel = remember {
                GroupsViewModel(appContainer.groupsRepository)
            }

            GroupsScreen(
                viewModel = groupsViewModel,
                onGroupClick = { groupId ->
                    navController.navigate(Routes.groupDetail(groupId))
                }
            )
        }

        /**
         * Group details route.
         * Expects groupId as a mandatory navigation argument.
         */
        composable(
            Routes.GROUP_DETAIL,
            arguments = listOf(
                navArgument("groupId") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            // Extract groupId from navigation arguments
            val groupId = backStackEntry.arguments?.getString("groupId") ?: return@composable
            val groupsDetailViewModel = remember {
                GroupDetailViewModel(appContainer.groupsRepository, appContainer.eventRepository)
            }
            GroupDetailScreen(
                viewModel = groupsDetailViewModel,
                groupId = groupId,
                onGroupSelected = { newGroupId ->
                    if (newGroupId != groupId) {
                        navController.navigate(Routes.groupDetail(newGroupId)) {
                            popUpTo(Routes.GROUP_DETAIL) { inclusive = true }
                        }
                    }
                },
                onCreateEvent = {
                    navController.navigate(Routes.createEvent(groupId))
                },
                onBack = { navController.popBackStack() }
            )
        }

        /**
         * Create event route.
         * Expects groupId as a mandatory navigation argument.
         */
        composable(
            Routes.CREATE_EVENT,
            arguments = listOf(
                navArgument("groupId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            // Extract groupId from navigation arguments
            val groupId = backStackEntry.arguments?.getString("groupId") ?: return@composable
            val createEventViewModel = remember {
                CreateEventViewModel(appContainer.eventRepository)
            }
            CreateEventScreen(
                viewModel = createEventViewModel,
                groupId = groupId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
