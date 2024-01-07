package com.example.shoppinglist.presentation.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.dto.ShopItem
import com.example.shoppinglist.domain.use_cases.AddShopItemUseCase
import com.example.shoppinglist.domain.use_cases.DeleteShopItemUseCase
import com.example.shoppinglist.domain.use_cases.GetShopItemListUseCase
import com.example.shoppinglist.domain.use_cases.GetShopItemUseCase
import com.example.shoppinglist.domain.use_cases.UpdateShopItemUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ShopListRepositoryImpl(application)

    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val getShopItemListUseCase = GetShopItemListUseCase(repository)
    private val updateShopItemUseCase = UpdateShopItemUseCase(repository)

    val data = getShopItemListUseCase.getShopItemList()


    fun getShopItemList() {
        getShopItemListUseCase.getShopItemList()
    }

    fun deleteItem(shopItem: ShopItem) {
        viewModelScope.launch {
            deleteShopItemUseCase.deleteShopItem(shopItem)
        }
    }

    fun changeEnableState(shopItem: ShopItem) {
        viewModelScope.launch {
            updateShopItemUseCase.updateShopItem(shopItem.copy(enabled = !shopItem.enabled))
        }
    }
}