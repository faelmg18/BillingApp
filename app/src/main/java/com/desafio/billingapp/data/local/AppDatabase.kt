package com.desafio.billingapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.desafio.billingapp.data.local.dao.PurchaseDao
import com.desafio.billingapp.data.local.entity.PurchaseEntity

@Database(
    entities = [PurchaseEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun purchaseDao(): PurchaseDao
}
