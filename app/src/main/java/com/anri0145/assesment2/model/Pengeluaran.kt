package com.anri0145.assesment2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pengeluaran")
data class Pengeluaran(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val judul: String,
    val belanja: String,
    val jumlah: String,
    val tanggal: String,
    val hari: String,
    val isDeleted: Boolean = false
)
