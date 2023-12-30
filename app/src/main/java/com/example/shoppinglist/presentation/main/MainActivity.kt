package com.example.shoppinglist.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ActivityMainBinding
import com.example.shoppinglist.domain.dto.ShopItem
import com.example.shoppinglist.presentation.shop_item.ShopItemActivity
import com.example.shoppinglist.presentation.shop_item.ShopItemFragment

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditFinishListener {
    private val viewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ShopListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        viewModel.data.observe(this) {
            adapter.submitList(it)
        }

        viewModel.getShopItemList()

        addButtonClickListener()

    }

    private fun isOnePaneMode(): Boolean {
        return binding.fragmentContainer == null
    }

    private fun addButtonClickListener() {
        binding.addShopItem.setOnClickListener {
            if (isOnePaneMode()) {
                val intent = ShopItemActivity.newIntentAddItem(this)
                startActivity(intent)
            } else {
                val fragment = ShopItemFragment.getAddFragment()
                launchFragment(fragment)
            }

        }
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }


    private fun setupRecyclerView() {
        val recycler = binding.recyclerId
        adapter = ShopListAdapter(getOnClickInteractor())
        recycler.adapter = adapter
        getOnItemSwipedHelper().attachToRecyclerView(recycler)
    }

    private fun getOnClickInteractor() = object : OnShopItemClickInteractor {
        override fun onClick(shopItem: ShopItem) {
            viewModel.changeEnableState(shopItem)
            if (shopItem.enabled) {
                Toast.makeText(
                    this@MainActivity,
                    "We bought ${shopItem.name}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        override fun onLongClick(shopItem: ShopItem) {
            if (isOnePaneMode()) {
                val intent = ShopItemActivity.newIntentEditItem(
                    this@MainActivity,
                    shopItem.id
                )
                startActivity(intent)
            } else {
                val fragment = ShopItemFragment.getEditFragment(shopItem.id)
                launchFragment(fragment)
            }

        }
    }


    private fun getOnItemSwipedHelper(): ItemTouchHelper {
        return ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteItem(item)
            }

        })
    }

    override fun onEditFinish() {
        Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
        onBackPressedDispatcher.onBackPressed()
    }
}