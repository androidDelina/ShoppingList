package com.example.shoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private var count = 0
    private lateinit var ll_shop_list: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ll_shop_list = findViewById(R.id.ll_shop_list)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.shopList.observe(this) {
            showList(it)
        }
    }

    private fun showList(list: List<ShopItem>) {
        ll_shop_list.removeAllViews()
        for (shopItem in list) {
            val viewRes = if (shopItem.enabled) {
                R.layout.item_shop_enabled
            } else { R.layout.item_shop_disabled }
            val view = LayoutInflater.from(this).inflate(
                viewRes,
                ll_shop_list,
                false)
            val tv_name = view.findViewById<TextView>(R.id.tv_name)
            val tv_count = view.findViewById<TextView>(R.id.tv_count)
            tv_name.text = shopItem.name
            tv_count.text = shopItem.count.toString()
            view.setOnLongClickListener {
                viewModel.changeEnabledState(shopItem)
                true
            }
            ll_shop_list.addView(view)
        }
    }
}