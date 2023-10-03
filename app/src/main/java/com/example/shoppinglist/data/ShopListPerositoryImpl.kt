package com.example.shoppinglist.data

import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository

class ShopListPerositoryImpl: ShopListRepository {

    var shopList = mutableListOf<ShopItem>()
    var autoIncrementId = 0
    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shopList.add(shopItem)
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldShopListItem = getShopItem(shopItem.id)
        shopList.remove(oldShopListItem)
        addShopItem(shopItem)
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find {
            it.id == shopItemId
        } ?: throw Exception("ShopListItem with id: $shopItemId not found")
    }

    override fun getShopList(): List<ShopItem> {
        TODO("Not yet implemented")
    }
}