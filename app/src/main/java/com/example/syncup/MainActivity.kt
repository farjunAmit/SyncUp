package com.example.syncup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.syncup.data.repository.auth.AuthRepository
import com.example.syncup.data.repository.event.EventRepository
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
 * - Setting the Compose content tree
 * - Applying the app theme and top-level surface
 * - Launching the root composable (SyncUpApp) which owns navigation
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sessionStore: SessionStore
    @Inject
    lateinit var authRepository: AuthRepository
    @Inject
    lateinit var groupsRepository: GroupsRepository
    @Inject
    lateinit var eventRepository: EventRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enables edge-to-edge drawing (status/navigation bars)
        enableEdgeToEdge()

        setContent {
            SyncUpTheme {
                // Top-level background container for the app content
                Surface(Modifier.fillMaxSize()) {
                    // Root composable that sets up navigation and screens
                    SyncUpApp(sessionStore,authRepository, groupsRepository, eventRepository)
                }
            }
        }
    }
}
