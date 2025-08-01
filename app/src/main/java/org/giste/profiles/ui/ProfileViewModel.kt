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

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.giste.profiles.domain.Profile
import org.giste.profiles.domain.SystemProperties
import org.giste.profiles.domain.usecases.FindProfileByIdUseCase
import org.giste.profiles.domain.usecases.FindSystemPropertiesUseCase
import org.giste.profiles.domain.usecases.UpdateProfileUseCase

private const val TAG = "ProfileViewModel"

@HiltViewModel(assistedFactory = ProfileViewModel.Factory::class)
class ProfileViewModel @AssistedInject constructor(
    @Assisted profileId: Long,
    private val updateProfileUseCase: UpdateProfileUseCase,
    findProfileByIdUseCase: FindProfileByIdUseCase,
    findSystemPropertiesUseCase: FindSystemPropertiesUseCase,
) : ViewModel() {

    val uiState: StateFlow<UiState> = combine(
        findProfileByIdUseCase(profileId),
        findSystemPropertiesUseCase()
    ) { profile, systemProperties ->
        Log.d(TAG, "New state: ($profile, $systemProperties)")
        UiState(
            profile = if (profile.alarmVolume.value < systemProperties.streamAlarmMinValue) {
                profile.copy(
                    alarmVolume = profile.alarmVolume.copy(
                        value = systemProperties.streamAlarmMinValue
                    )
                )
            } else {
                profile
            },
            systemProperties = systemProperties,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = UiState(),
    )

    fun onUiAction(uiAction: UiAction) {
        when (uiAction) {
            is UiAction.OnValueChange -> onValueChange(uiAction.profile)
        }
    }

    private fun onValueChange(profile: Profile) {
        viewModelScope.launch { updateProfileUseCase(profile) }
    }

    data class UiState(
        val profile: Profile = Profile(),
        val systemProperties: SystemProperties = SystemProperties(),
    )

    sealed class UiAction {
        data class OnValueChange(val profile: Profile) : UiAction()
    }

    @AssistedFactory
    interface Factory {
        fun create(id: Long): ProfileViewModel
    }
}