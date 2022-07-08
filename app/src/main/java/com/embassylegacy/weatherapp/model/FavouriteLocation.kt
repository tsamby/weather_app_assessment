package com.embassylegacy.weatherapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nullable

@Entity(tableName = "favourites")
data class FavouriteLocation (
    @PrimaryKey
    val id : String,
    val name : String,
    val address : String,
    val longitude:Double,
    val latitude:Double,
    @Nullable
    val phoneNumber : String,
    @Nullable
    val rating : Double,
    @Nullable
    val userRatings : Int
)