package br.com.raveline.testinghealth.domain.di

import br.com.raveline.testinghealth.data.database.dao.BreedsDao
import br.com.raveline.testinghealth.data.datasources.BreedLocalDataSource
import br.com.raveline.testinghealth.data.datasources.BreedLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {

    @Provides
    @Singleton
    fun providesLocalDataSource(breedsDao: BreedsDao): BreedLocalDataSource =
        BreedLocalDataSourceImpl(breedsDao)
}