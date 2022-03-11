package com.stash.shopeklobek.model.room.daw

import androidx.lifecycle.LiveData
import com.stash.shopeklobek.model.entities.Products

interface BaseDao<T> {
    suspend fun  upsert(item: T):Long

    fun getAll(): LiveData<List<T>>

    suspend fun getWithId(id: Long): T?

    fun getWithCustomerId(customerEmail: String): LiveData<List<T>>

    suspend  fun  delete(id: Long)

}