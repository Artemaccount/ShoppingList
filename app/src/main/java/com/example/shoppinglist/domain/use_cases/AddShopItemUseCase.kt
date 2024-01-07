package com.example.shoppinglist.domain.use_cases

import com.example.shoppinglist.domain.ShopListRepository
import com.example.shoppinglist.domain.dto.ShopItem

class AddShopItemUseCase(private val repository: ShopListRepository) {
    suspend fun addShopItem(shopItem: ShopItem) {
        repository.addShopItem(shopItem)
    }
}