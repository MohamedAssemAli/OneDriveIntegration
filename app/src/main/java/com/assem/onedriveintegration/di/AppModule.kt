package com.assem.onedriveintegration.di

import com.assem.onedriveintegration.data.repository.OneDriveRepoImpl
import com.assem.onedriveintegration.domain.repository.OneDriveRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindOneDriveRepository(oneDriveRepoImpl: OneDriveRepoImpl): OneDriveRepo
}