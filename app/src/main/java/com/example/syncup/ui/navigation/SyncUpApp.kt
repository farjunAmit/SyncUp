package com.example.syncup.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.syncup.Routes
import com.example.syncup.data.session.SessionStore
import com.example.syncup.ui.event.screens.CreateEventScreen
import com.example.syncup.ui.event.screens.EventDetailScreen
import com.example.syncup.ui.group.screens.GroupDetailScreen
import com.example.syncup.ui.group.vm.GroupDetailViewModel
import com.example.syncup.ui.group.screens.GroupsScreen
import com.example.syncup.ui.event.vm.CreateEventViewModel
import com.example.syncup.ui.group.vm.GroupsViewModel
import com.example.syncup.ui.login.LoginScreen
import com.example.syncup.ui.login.LoginViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.syncup.ui.event.vm.EventDetailViewModel

/**
 * SyncUpApp
 *
 * Root composable of the application.
 * Responsible for:
 * - Creating and holding the NavController
 * - Defining the navigation graph (NavHost)
 *
 * Navigation arguments (e.g., groupId) are passed via routes and extracted
 * from the backStackEntry.
 */
@Composable
fun SyncUpApp(sessionStore: SessionStore) {

    // NavController is remembered so it survives recompositions
    val navController = rememberNavController()
    val loginViewModel : LoginViewModel = hiltViewModel()
    NavHost(
        navController = navController,
        startDestination = Routes.GATE
    ) {
        composable(Routes.GATE) {
            GateScreen(
                sessionStore = sessionStore,
                onGoHome = {
                    navController.navigate(Routes.GROUPS) {
                        popUpTo(Routes.GATE) { inclusive = true }
                    }
                },
                onGoLogin = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.GATE) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                loginViewModel = loginViewModel,
                onLoggedIn = {
                    navController.navigate(Routes.GROUPS) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }
        /**
         * Groups screen route.
         * Creates the required ViewModel and passes navigation callback.
         */
        composable(Routes.GROUPS) {

            // Manual ViewModel creation (lightweight alternative to Hilt for MVP stage)
            val groupsViewModel : GroupsViewModel = hiltViewModel()
            GroupsScreen(
                viewModel = groupsViewModel,
                onGroupClick = { groupId ->
                    navController.navigate(Routes.groupDetail(groupId))
                },
                onLogout = {
                    loginViewModel.logout()
                    navController.navigate(Routes.GATE) {
                        popUpTo(Routes.GROUPS) { inclusive = true }
                    }
                },
            )
        }

        /**
         * Group details route.
         */
        composable(
            Routes.GROUP_DETAIL,
            arguments = listOf(
                navArgument("groupId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val groupId = backStackEntry.arguments?.getLong("groupId") ?: return@composable
            val groupsDetailViewModel : GroupDetailViewModel = hiltViewModel()
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
                    // CHANGED: Pass groupId as Long, not .toString()
                    navController.navigate(Routes.createEvent(groupId))
                },
                onEventSelected = { eventId ->
                    navController.navigate(Routes.eventDetail(eventId))
                },
                onBack = { navController.popBackStack() }
            )
        }

        /**
         * Create event route.
         */
        composable(
            Routes.CREATE_EVENT,
            arguments = listOf(
                navArgument("groupId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val groupId = backStackEntry.arguments?.getLong("groupId") ?: return@composable

            val createEventViewModel : CreateEventViewModel = hiltViewModel()

            CreateEventScreen(
                viewModel = createEventViewModel,
                groupId = groupId,
                eventId = null,
                onBack = { navController.popBackStack() },
            )
        }

        composable(
            Routes.CREATE_EVENT,
            arguments = listOf(
                navArgument("groupId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val groupId = backStackEntry.arguments?.getLong("groupId") ?: return@composable

            val createEventViewModel : CreateEventViewModel = hiltViewModel()

            CreateEventScreen(
                viewModel = createEventViewModel,
                groupId = groupId,
                eventId = null,
                onBack = { navController.popBackStack() },
            )
        }

        composable(
            Routes.EDIT_EVENT,
            arguments = listOf(
                navArgument("groupId") { type = NavType.LongType },
                navArgument("eventId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val groupId = backStackEntry.arguments?.getLong("groupId") ?: return@composable
            val eventId = backStackEntry.arguments?.getLong("eventId") ?: return@composable
            val createEventViewModel : CreateEventViewModel = hiltViewModel()

            CreateEventScreen(
                viewModel = createEventViewModel,
                groupId = groupId,
                eventId = eventId,
                onBack = { navController.popBackStack() }
            )
        }

        /**
         * Event detail route.
         */
        composable(
            Routes.EVENT_DETAIL,
            arguments = listOf(
                // CHANGED: Set type to LongType
                navArgument("eventId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            // CHANGED: Use getLong
            val eventId = backStackEntry.arguments?.getLong("eventId") ?: return@composable

            val eventDetailViewModel : EventDetailViewModel = hiltViewModel()
            EventDetailScreen(
                viewModel = eventDetailViewModel,
                eventId = eventId,
                onClick = { eventId, groupId ->
                    navController.navigate(Routes.editEvent(eventId, groupId))
                },
                onBack = { navController.popBackStack() }
            )
        }
    }
}
