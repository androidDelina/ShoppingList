package com.example.shoppinglist.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository
import java.util.Random

object ShopListPerositoryImpl: ShopListRepository {

    private var shopList = sortedSetOf<ShopItem>({ o1, o2 -> o1.id.compareTo(o2.id)})
    val shopListLD = MutableLiveData<List<ShopItem>>()
    var autoIncrementId = 0

    init {
        for (i in 0 until 1000) {
            val item = ShopItem("Name $i", i, kotlin.random.Random.nextBoolean())
            addShopItem(item)
        }
    }

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shopList.add(shopItem)
        updateOpenShopList()
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateOpenShopList()
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

    override fun getShopList(): LiveData<List<ShopItem>> {
        Log.e("ShopListPerositoryImpl", "getShopList(): LiveData<List<ShopItem>")
        return shopListLD
    }

    private fun updateOpenShopList() {
        shopListLD.value = shopList.toList()
    }
}