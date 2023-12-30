package com.example.shoppinglist.presentation.main

import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.dto.ShopItem
import com.example.shoppinglist.domain.use_cases.AddShopItemUseCase
import com.example.shoppinglist.domain.use_cases.DeleteShopItemUseCase
import com.example.shoppinglist.domain.use_cases.GetShopItemListUseCase
import com.example.shoppinglist.domain.use_cases.GetShopItemUseCase
import com.example.shoppinglist.domain.use_cases.UpdateShopItemUseCase

class MainViewModel : ViewModel() {
    private val repository = ShopListRepositoryImpl

    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val getShopItemListUseCase = GetShopItemListUseCase(repository)
    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val updateShopItemUseCase = UpdateShopItemUseCase(repository)

    val data = getShopItemListUseCase.getShopItemList()
    fun getShopItemList() {
        getShopItemListUseCase.getShopItemList()
    }

    fun deleteItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)
    }

    fun changeEnableState(shopItem: ShopItem) {
        updateShopItemUseCase.updateShopItem(shopItem.copy(enabled = !shopItem.enabled))
    }

    fun getShopItem(id: Int) = getShopItemUseCase.getShopItemById(id)

    fun addShopItem(shopItem: ShopItem) {
        addShopItemUseCase.addShopItem(shopItem)
    }
}