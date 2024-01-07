package com.example.shoppinglist.data

import android.app.Application
import android.service.autofill.Transformation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.shoppinglist.data.db.AppDatabase
import com.example.shoppinglist.data.db.ShopItemMapper
import com.example.shoppinglist.domain.ShopListRepository
import com.example.shoppinglist.domain.dto.ShopItem
import java.lang.RuntimeException
import kotlin.random.Random

class ShopListRepositoryImpl(
    application: Application
) : ShopListRepository {

    private val dao = AppDatabase.getInstance(application).shopListDao()
    private val mapper = ShopItemMapper()

    override suspend fun addShopItem(shopItem: ShopItem) {
        dao.addShopItem(mapper.mapToDbModel(shopItem))
    }

    override suspend fun deleteShopItem(shopItem: ShopItem) {
        dao.deleteShopItem(shopItem.id)
    }

    override fun getShopItemList(): LiveData<List<ShopItem>> = dao.getShopItemList().map {
        mapper.mapToShopItemList(it)
    }

    override suspend fun getShopItemById(id: Int): ShopItem = mapper.mapToShopItem(
        dao.getShopItem(id)
    )

    override suspend fun updateShopItem(shopItem: ShopItem) {
        dao.addShopItem(mapper.mapToDbModel(shopItem))
    }
}