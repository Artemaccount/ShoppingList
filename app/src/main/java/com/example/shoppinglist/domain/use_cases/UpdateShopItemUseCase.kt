package com.example.shoppinglist.domain.use_cases

import com.example.shoppinglist.domain.ShopListRepository
import com.example.shoppinglist.domain.dto.ShopItem

class UpdateShopItemUseCase(private val repository: ShopListRepository) {
    fun updateShopItem(shopItem: ShopItem) {
        repository.updateShopItem(shopItem)
    }
}