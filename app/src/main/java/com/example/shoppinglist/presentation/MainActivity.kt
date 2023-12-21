package com.example.shoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var deleted = false

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        Log.d("myTag", "HELLO!")

        viewModel.data.observe(this) { items ->
            items.forEach { Log.d("myTag", it.toString()) }
            if (!deleted) {
                val item = items[0]
                val item2 = items[1]
                viewModel.deleteItem(item)
                viewModel.changeEnableState(item2)
                deleted = true
            }
            items.forEach { Log.d("myTag", it.toString()) }
        }
        viewModel.getShopItemList()
    }
}