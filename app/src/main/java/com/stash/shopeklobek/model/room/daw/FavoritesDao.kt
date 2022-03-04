package com.stash.shopeklobek.model.room.daw

import androidx.lifecycle.LiveData
import androidx.room.*
import com.stash.shopeklobek.model.entities.room.RoomFavorite
import com.stash.shopeklobek.utils.Constants
import com.stash.shopeklobek.utils.Constants.FAVORITES_TABLE


@Dao
interface FavoritesDao : BaseDao<RoomFavorite> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun upsert(item: RoomFavorite): Long

    @Query("Select * from $FAVORITES_TABLE")
    override fun getAll(): LiveData<List<RoomFavorite>>

    @Query("SELECT * FROM $FAVORITES_TABLE WHERE id=:id")
    override suspend fun getWithId(id: Long): RoomFavorite?

    @Query("DELETE FROM ${Constants.FAVORITES_TABLE} WHERE id = :id")
    override suspend fun delete(id: Long)
}