package com.stash.shopeklobek.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.stash.shopeklobek.model.entities.room.RoomCart
import com.stash.shopeklobek.model.entities.room.RoomFavorite
import com.stash.shopeklobek.model.entities.room.RoomOrder
import com.stash.shopeklobek.model.room.daw.CartDao
import com.stash.shopeklobek.model.room.daw.FavoritesDao
import com.stash.shopeklobek.model.room.daw.OrdersDao
import com.stash.shopeklobek.model.room.utils.Converters
import com.stash.shopeklobek.utils.Constants.DATABASE_NAME


@TypeConverters(Converters::class)
@Database(
    entities = [
        RoomOrder::class,
        RoomFavorite::class,
        RoomCart::class
    ],
    version = 4
)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun cartDao(): CartDao
    abstract fun favoriteDao(): FavoritesDao
    abstract fun orderDao(): OrdersDao

    companion object {
        private var instance: ProductDatabase? = null
        private val Lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(Lock) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ProductDatabase::class.java,
                DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()

    }
}