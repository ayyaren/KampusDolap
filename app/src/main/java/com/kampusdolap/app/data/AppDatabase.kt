package com.kampusdolap.app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kampusdolap.app.model.CartItem
import com.kampusdolap.app.model.FavoriteItem

@Database(entities = [CartItem::class, FavoriteItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cartDao(): CartDao
    abstract fun favoritesDao(): FavoritesDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "kampusdolap_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}