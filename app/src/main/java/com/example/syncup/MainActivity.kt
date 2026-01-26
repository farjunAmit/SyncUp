package com.example.syncup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.syncup.data.AppContainer
import com.example.syncup.data.repository.auth.AuthRepository
import com.example.syncup.data.repository.group.GroupsRepository
import com.example.syncup.data.session.SessionStore
import com.example.syncup.ui.navigation.SyncUpApp
import com.example.syncup.ui.theme.SyncUpTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * MainActivity
 *
 * Application entry point.
 * Responsible for:
 * - Creating the AppContainer (manual dependency container)
 * - Setting the Compose content tree
 * - Applying the app theme and top-level surface
 * - Launching the root composable (SyncUpApp) which owns navigation
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // App-wide dependencies are created once and passed down to the UI layer.
    private val appContainer = AppContainer()
    @Inject
    lateinit var sessionStore: SessionStore
    @Inject
    lateinit var authRepository: AuthRepository
    @Inject
    lateinit var groupsRepository: GroupsRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enables edge-to-edge drawing (status/navigation bars)
        enableEdgeToEdge()

        setContent {
            SyncUpTheme {
                // Top-level background container for the app content
                Surface(Modifier.fillMaxSize()) {
                    // Root composable that sets up navigation and screens
                    SyncUpApp(appContainer,sessionStore,authRepository, groupsRepository)
                }
            }
        }
    }
}
