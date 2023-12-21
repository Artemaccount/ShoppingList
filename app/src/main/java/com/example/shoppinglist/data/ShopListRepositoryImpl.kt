package com.example.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglist.domain.ShopListRepository
import com.example.shoppinglist.domain.dto.ShopItem
import java.lang.RuntimeException

object ShopListRepositoryImpl : ShopListRepository {
    private val _data = MutableLiveData<List<ShopItem>>()
    private val list = mutableListOf<ShopItem>()
    private var autoIncrementId = 0

    init {
        for (i in 1..10) {
            val shopItem = ShopItem("Item name", i, true)
            addShopItem(shopItem)
        }
    }

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        list.add(shopItem)
        updateList()
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        list.remove(shopItem)
        updateList()
    }

    override fun getShopItemList(): LiveData<List<ShopItem>> {
        return _data
    }

    override fun getShopItemById(id: Int): ShopItem {
        return list.find {
            it.id == id
        } ?: throw RuntimeException("Element with id $id not found")
    }

    override fun updateShopItem(shopItem: ShopItem) {
        val oldElement = getShopItemById(shopItem.id)
        val elementPosition = list.indexOf(oldElement)
        list.add(elementPosition, shopItem)
        updateList()
    }

    private fun updateList() {
        _data.value = list.toList()
    }
}