package com.example.shoppinglist.domain.use_cases

import com.example.shoppinglist.domain.ShopListRepository
import com.example.shoppinglist.domain.dto.ShopItem

class GetShopItemUseCase(private val repository: ShopListRepository) {
    fun getShopItemById(id: Int): ShopItem = repository.getShopItemById(id)
}