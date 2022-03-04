package com.stash.shopeklobek.model.room.daw

import androidx.lifecycle.LiveData

interface BaseDao<T> {
    suspend fun  upsert(item: T):Long

    fun getAll(): LiveData<List<T>>

    suspend fun getWithId(id: Long): T?

    suspend  fun  delete(id: Long)
}