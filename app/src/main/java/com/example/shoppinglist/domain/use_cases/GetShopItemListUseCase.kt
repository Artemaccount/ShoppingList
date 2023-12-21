package com.example.shoppinglist.domain.use_cases

import androidx.lifecycle.LiveData
import com.example.shoppinglist.domain.ShopListRepository
import com.example.shoppinglist.domain.dto.ShopItem

class GetShopItemListUseCase(private val repository: ShopListRepository) {
    fun getShopItemList(): LiveData<List<ShopItem>> = repository.getShopItemList()
}