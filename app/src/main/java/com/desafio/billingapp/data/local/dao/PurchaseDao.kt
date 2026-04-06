package com.desafio.billingapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.desafio.billingapp.data.local.entity.PurchaseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PurchaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPurchase(purchase: PurchaseEntity)

    @Query("SELECT * FROM purchases ORDER BY purchaseTime DESC")
    fun getAllPurchases(): Flow<List<PurchaseEntity>>

    @Query("SELECT * FROM purchases WHERE productId = :productId LIMIT 1")
    suspend fun getPurchaseByProductId(productId: String): PurchaseEntity?

    @Query("DELETE FROM purchases WHERE purchaseToken = :purchaseToken")
    suspend fun deletePurchase(purchaseToken: String)
}
