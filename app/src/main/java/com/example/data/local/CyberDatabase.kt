package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ThreatLogEntity::class], version = 1, exportSchema = false)
abstract class CyberDatabase : RoomDatabase() {

  abstract fun threatLogDao(): ThreatLogDao

  companion object {
    @Volatile
    private var INSTANCE: CyberDatabase? = null

    fun getDatabase(context: Context): CyberDatabase {
      return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          CyberDatabase::class.java,
          "armas_cyber_security.db"
        )
          .fallbackToDestructiveMigration()
          .build()
        INSTANCE = instance
        instance
      }
    }
  }
}
