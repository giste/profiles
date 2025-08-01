/*
 * Copyright 2025 Giste Trappiste
 *
 * This program is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.giste.profiles.ui

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.giste.profiles.ui.theme.ProfilesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ProfilesPreview() {
    ProfilesTheme {
        Profiles()
    }
}

@Composable
fun Profiles() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            DynamicTopBar(navController)
        },
        floatingActionButton = {
            DynamicFab(navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ProfilesDestinations.ProfileManager,
            modifier = Modifier
                .padding(innerPadding)
                // Consume this insets so that it's not applied again
                // when using safeDrawing in the hierarchy below
                .consumeWindowInsets(innerPadding),
        ) {
            managerDestination(
                onNavigateToProfileDetails = { profileId ->
                    navController.navigateToProfileDetails(profileId = profileId)
                },
                onNavigateToNewProfileDialog = {
                    navController.navigateToNewProfileDialog()
                },
            )
            newProfileDestination(
                onNewProfile = { profileId ->
                    navController.navigateToProfileDetails(profileId = profileId)
                },
                onDismiss = { navController.popBackStack() },
            )
            profileDetailsDestination()
        }
    }
}
