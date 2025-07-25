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

package org.giste.profiles.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import org.giste.profiles.IoDispatcher
import org.giste.profiles.domain.repositories.ProfileRepository
import org.giste.profiles.domain.repositories.SelectedProfileRepository
import org.giste.profiles.domain.repositories.SystemRepository
import javax.inject.Singleton

private const val DB_NAME = "profiles.db"

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Singleton
    @Provides
    fun provideProfilesDatabase(@ApplicationContext context: Context): ProfilesDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = ProfilesDatabase::class.java,
            name = DB_NAME,
        ).build()
    }

    @Singleton
    @Provides
    fun provideProfileRepository(profilesDatabase: ProfilesDatabase): ProfileRepository {
        return DaoProfileRepository(profilesDatabase.profileDao())
    }

    @Singleton
    @Provides
    fun provideSelectedProfileRepository(dataStore: DataStore<Preferences>): SelectedProfileRepository {
        return DataStoreSelectedProfileRepository(dataStore)
    }

    @Singleton
    @Provides
    fun provideSystemRepository(
        @ApplicationContext context: Context,
        @IoDispatcher dispatcher: CoroutineDispatcher,
    ): SystemRepository {
        return AndroidSystemRepository(context, dispatcher)
    }

}