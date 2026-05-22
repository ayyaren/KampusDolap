package com.kampusdolap.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.kampusdolap.app.data.AppDatabase
import com.kampusdolap.app.model.FavoriteItem

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private val favoritesDao = AppDatabase.getInstance(application).favoritesDao()

    val favorites: LiveData<List<FavoriteItem>> = favoritesDao.getFavorites()

    suspend fun addFavorite(item: FavoriteItem) {
        favoritesDao.addFavorite(item)
    }

    suspend fun removeFavorite(item: FavoriteItem) {
        favoritesDao.removeFavorite(item)
    }
}