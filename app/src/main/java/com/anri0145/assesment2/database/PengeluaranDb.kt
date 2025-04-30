package com.anri0145.assesment2.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anri0145.assesment2.model.Pengeluaran

@Database(entities = [Pengeluaran::class], version = 4, exportSchema = false)
abstract class PengeluaranDb : RoomDatabase(){

    abstract val dao: PengeluaranDao

    companion object{

        @Volatile
        private var INSTANCE: PengeluaranDb? = null

        fun getInstance(context: Context): PengeluaranDb{
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PengeluaranDb::class.java,
                        "pengeluaran.db"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}