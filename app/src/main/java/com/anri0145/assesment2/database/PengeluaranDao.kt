package com.anri0145.assesment2.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.anri0145.assesment2.model.Pengeluaran
import kotlinx.coroutines.flow.Flow

@Dao
interface PengeluaranDao {

    @Insert
    suspend fun insert(pengeluaran: Pengeluaran)

    @Update
    suspend fun update(pengeluaran: Pengeluaran)

    @Query("SELECT * FROM pengeluaran ORDER BY tanggal DESC")
    fun getPengeluara(): Flow<List<Pengeluaran>>

    @Query("SELECT * FROM pengeluaran WHERE id = :id")
    suspend fun getPengeluaranById(id: Long): Pengeluaran?

    @Query("DELETE FROM pengeluaran WHERE id = :id")
    suspend fun deleteById(id: Long)
}