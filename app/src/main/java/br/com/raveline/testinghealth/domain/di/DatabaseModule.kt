package br.com.raveline.testinghealth.domain.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import br.com.raveline.testinghealth.data.database.BreedsDatabase
import br.com.raveline.testinghealth.data.database.dao.BreedsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun providesBreedsDatabase(context: Application): BreedsDatabase =
        Room.databaseBuilder(context, BreedsDatabase::class.java, "Breeds_db")
            .build()

    @Singleton
    @Provides
    fun providesBreedsDao(breedsDatabase: BreedsDatabase): BreedsDao =
        breedsDatabase.theDogDao()
}