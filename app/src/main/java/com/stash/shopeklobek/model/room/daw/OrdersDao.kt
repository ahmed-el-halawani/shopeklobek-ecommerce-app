package com.stash.shopeklobek.model.room.daw

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stash.shopeklobek.model.entities.room.RoomOrder
import com.stash.shopeklobek.utils.Constants.ORDER_TABLE


@Dao
interface OrdersDao : BaseDao<RoomOrder> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun upsert(item: RoomOrder): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(item: List<RoomOrder>)

    @Query("Select * from $ORDER_TABLE")
    override fun getAll(): LiveData<List<RoomOrder>>

    @Query("SELECT * FROM $ORDER_TABLE WHERE id=:id")
    override suspend fun getWithId(id: Long): RoomOrder?

    @Query("SELECT * FROM $ORDER_TABLE WHERE customerEmail=:customerEmail")
    override fun getWithCustomerId(customerEmail: String): LiveData<List<RoomOrder>>

    @Query("DELETE FROM $ORDER_TABLE WHERE id = :id")
    override suspend fun delete(id: Long)

    @Query("DELETE FROM $ORDER_TABLE WHERE customerEmail = :customerEmail")
    suspend fun deleteAll(customerEmail: String)

    @Query("DELETE FROM $ORDER_TABLE")
    suspend fun dropAll()

}