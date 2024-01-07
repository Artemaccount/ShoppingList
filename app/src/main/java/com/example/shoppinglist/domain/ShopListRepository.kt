package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData
import com.example.shoppinglist.domain.dto.ShopItem

interface ShopListRepository {
    suspend fun addShopItem(shopItem: ShopItem)
    suspend fun deleteShopItem(shopItem: ShopItem)
    fun getShopItemList(): LiveData<List<ShopItem>>
    suspend fun getShopItemById(id: Int): ShopItem
    suspend fun updateShopItem(shopItem: ShopItem)
}