package br.com.raveline.testinghealth.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.raveline.testinghealth.data.model.BreedBySearchItem
import br.com.raveline.testinghealth.data.model.Breeds
import br.com.raveline.testinghealth.data.model.BreedsBySearch
import br.com.raveline.testinghealth.data.model.BreedsItem

@Dao
interface BreedsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBreeds(breeds: List<BreedsItem>)

    @Query("SELECT * FROM BREED_TABLE")
    suspend fun getAllBreeds(): List<BreedsItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBreedsBySearch(breeds: List<BreedBySearchItem>)

    @Query("SELECT * FROM BREAD_BY_SEARCH_TABLE")
    suspend fun getBreedsBySearch(): List<BreedBySearchItem>


}