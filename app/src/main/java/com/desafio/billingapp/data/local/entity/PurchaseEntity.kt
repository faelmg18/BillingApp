package com.desafio.billingapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "purchases")
data class PurchaseEntity(
    @PrimaryKey
    val purchaseToken: String,
    val productId: String,
    val orderId: String?,
    val purchaseTime: Long,
    val purchaseState: Int,
    val isAcknowledged: Boolean,
    val packageName: String
)
