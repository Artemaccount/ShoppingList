package com.example.shoppinglist.domain.use_cases

import com.example.shoppinglist.domain.ShopListRepository
import com.example.shoppinglist.domain.dto.ShopItem

class DeleteShopItemUseCase(private val repository: ShopListRepository) {
    suspend fun deleteShopItem(shopItem: ShopItem) {
        repository.deleteShopItem(shopItem)
    }
}