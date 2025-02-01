package com.jonichi.forceahabit.di

import com.jonichi.forceahabit.data.FakeHabitRepository
import com.jonichi.habit.di.RepositoryModule
import com.jonichi.habit.domain.repository.HabitRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class],
)
@Module
abstract class FakeRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindHabitRepository(fakeHabitRepository: FakeHabitRepository): HabitRepository
}
