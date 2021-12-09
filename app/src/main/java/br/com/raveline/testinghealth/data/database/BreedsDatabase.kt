package br.com.raveline.testinghealth.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.raveline.testinghealth.data.database.dao.BreedsDao
import br.com.raveline.testinghealth.data.model.BreedBySearchItem
import br.com.raveline.testinghealth.data.model.BreedsItem
import br.com.raveline.testinghealth.utils.BreedTypeConverter

@Database(
    entities = [BreedsItem::class, BreedBySearchItem::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(BreedTypeConverter::class)
abstract class BreedsDatabase : RoomDatabase() {
    abstract fun theDogDao(): BreedsDao
}