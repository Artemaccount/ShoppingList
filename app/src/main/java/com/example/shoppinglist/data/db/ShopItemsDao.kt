package com.example.shoppinglist.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ShopItemsDao {
    @Query("SELECT * FROM shop_items")
    fun getShopItemList(): LiveData<List<ShopItemDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShopItem(shopItemDbModel: ShopItemDbModel)

    @Query("DELETE FROM shop_items WHERE id=:id")
    suspend fun deleteShopItem(id: Int)

    @Query("SELECT * FROM shop_items WHERE id=:id LIMIT 1")
    suspend fun getShopItem(id: Int): ShopItemDbModel
}