package com.embassylegacy.weatherapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nullable

@Entity(tableName = "favourites")
data class FavouriteLocation (
    val id : String,
    @PrimaryKey
    val name : String,
    val address : String,
    val longitude:Double,
    val latitude:Double,
    val phoneNumber:String?,
    val isOpen: String?,
    val rating: Double?,
    val userRating:Int?




)