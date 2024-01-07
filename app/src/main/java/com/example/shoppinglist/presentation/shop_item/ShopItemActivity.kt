package com.example.shoppinglist.presentation.shop_item

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ActivityShopItemBinding
import com.example.shoppinglist.domain.dto.ShopItem

class ShopItemActivity : AppCompatActivity(), ShopItemFragment.OnEditFinishListener {
    private lateinit var binding: ActivityShopItemBinding

    private var screenMode: String = ShopItemFragment.UNKNOWN_MODE
    private var shopItemId: Int = ShopItem.UNDEFINED_ID
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        parseIntent()
        if (savedInstanceState == null) {
            launchScreenMode()
        }
    }

    private fun parseIntent() {
        if (!intent.hasExtra(ShopItemFragment.EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Param `screen mode` is absent")
        }
        val mode = intent.getStringExtra(ShopItemFragment.EXTRA_SCREEN_MODE)
        if (mode != ShopItemFragment.MODE_EDIT && mode != ShopItemFragment.MODE_ADD) {
            throw RuntimeException("Unknown screen mode: $mode")
        }
        screenMode = mode
        if (screenMode == ShopItemFragment.MODE_EDIT) {
            if (!intent.hasExtra(ShopItemFragment.EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("Param `shop item id` is absent")
            }
            shopItemId =
                intent.getIntExtra(ShopItemFragment.EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }


    private fun launchScreenMode() {
        val fragment = when (screenMode) {
            ShopItemFragment.MODE_ADD -> ShopItemFragment.getAddFragment()
            ShopItemFragment.MODE_EDIT -> ShopItemFragment.getEditFragment(shopItemId)
            else -> throw RuntimeException("Unknown screen mode: $screenMode")
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    companion object {
        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(ShopItemFragment.EXTRA_SCREEN_MODE, ShopItemFragment.MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, id: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(ShopItemFragment.EXTRA_SCREEN_MODE, ShopItemFragment.MODE_EDIT)
            intent.putExtra(ShopItemFragment.EXTRA_SHOP_ITEM_ID, id)
            return intent
        }
    }

    override fun onEditFinish() {
        Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
        finish()
    }
}