package com.anri0145.assesment2.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anri0145.assesment2.database.PengeluaranDao
import com.anri0145.assesment2.model.Pengeluaran
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailViewModel(private val dao: PengeluaranDao) : ViewModel() {

    private val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)

    fun insert(judul: String, isi: String, jumlah: String,hari: String){
        val pengeluaran = Pengeluaran(
            tanggal = formatter.format(Date()),
            judul = judul,
            belanja = isi,
            jumlah = jumlah,
            hari = hari
        )
        viewModelScope.launch (Dispatchers.IO){
            dao.insert(pengeluaran)
        }
    }
    suspend fun getPengeluaran(id: Long) : Pengeluaran?{
        return dao.getPengeluaranById(id)
    }
    fun update(id: Long, judul: String, isi: String, jumlah: String, hari: String){
        val pengeluaran = Pengeluaran(
            id = id,
            tanggal = formatter.format(Date()),
            judul = judul,
            belanja = isi,
            jumlah = jumlah,
            hari = hari
        )
        viewModelScope.launch (Dispatchers.IO){
            dao.update(pengeluaran)
        }
    }
    fun cycleDelete(id: Long){
        viewModelScope.launch (Dispatchers.IO){
            dao.cycleDeleteById(id)
        }
    }
}