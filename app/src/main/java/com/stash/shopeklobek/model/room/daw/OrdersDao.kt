package com.stash.shopeklobek.model.room.daw

import androidx.lifecycle.LiveData
import androidx.room.*
import com.stash.shopeklobek.model.entities.room.RoomOrder
import com.stash.shopeklobek.utils.Constants
import com.stash.shopeklobek.utils.Constants.ORDER_TABLE


@Dao
interface OrdersDao : BaseDao<RoomOrder> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun upsert(item: RoomOrder): Long

    @Query("Select * from $ORDER_TABLE")
    override fun getAll(): LiveData<List<RoomOrder>>

    @Query("SELECT * FROM $ORDER_TABLE WHERE id=:id")
    override suspend fun getWithId(id: Long): RoomOrder?

    @Query("DELETE FROM $ORDER_TABLE WHERE id = :id")
    override suspend fun delete(id: Long)
}