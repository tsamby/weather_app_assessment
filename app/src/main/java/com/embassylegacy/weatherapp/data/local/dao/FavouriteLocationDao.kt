package com.embassylegacy.weatherapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.embassylegacy.weatherapp.model.FavouriteLocation
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteLocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend  fun insert(favouriteLocation : FavouriteLocation) : Long

    @Query("select * From favourites")
    fun  fetch() : Flow<List<FavouriteLocation>>

    @Query("DELETE FROM favourites")
    suspend fun deleteAll()

}