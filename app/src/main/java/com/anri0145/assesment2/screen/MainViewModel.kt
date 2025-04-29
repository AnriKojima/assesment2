package com.anri0145.assesment2.screen

import androidx.lifecycle.ViewModel
import com.anri0145.assesment2.model.Pengeluaran

class MainViewModel : ViewModel() {

    val data = listOf(
        Pengeluaran(
            1,
            "sdfghjkl",
            "xcfghjkiuytf",
            "dfghjkl"
        )
    )
    fun getPengeluaran(id: Long) : Pengeluaran?{
        return data.find { it.id == id }
    }
}