package com.example.shoppinglist.data.db

import com.example.shoppinglist.domain.dto.ShopItem

class ShopItemMapper {
    fun mapToDbModel(shopItem: ShopItem): ShopItemDbModel {
        return ShopItemDbModel(
            id = shopItem.id,
            name = shopItem.name,
            count = shopItem.count,
            enabled = shopItem.enabled
        )
    }

    fun mapToShopItem(dbModel: ShopItemDbModel): ShopItem {
        return ShopItem(
            id = dbModel.id,
            name = dbModel.name,
            count = dbModel.count,
            enabled = dbModel.enabled
        )
    }

    fun mapToShopItemList(dbList: List<ShopItemDbModel>): List<ShopItem> = dbList.map {
        mapToShopItem(it)
    }
}