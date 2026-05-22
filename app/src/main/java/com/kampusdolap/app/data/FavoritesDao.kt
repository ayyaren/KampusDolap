package com.kampusdolap.app.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kampusdolap.app.model.FavoriteItem

@Dao
interface FavoritesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(item: FavoriteItem)

    @Delete
    suspend fun removeFavorite(item: FavoriteItem)

    @Query("SELECT * FROM favorites")
    fun getFavorites(): LiveData<List<FavoriteItem>>

    @Query("SELECT * FROM favorites WHERE productId = :productId")
    suspend fun getFavoriteById(productId: String): FavoriteItem?
}