package com.example.shoppinglist.presentation.shop_item

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.databinding.FragmentShopItemBinding
import com.example.shoppinglist.domain.dto.ShopItem
import com.example.shoppinglist.presentation.main.MainActivity

class ShopItemFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this)[ShopItemViewModel::class.java]
    }
    private val binding by lazy {
        FragmentShopItemBinding.inflate(layoutInflater)
    }

    private lateinit var onEditFinishListener: OnEditFinishListener

    private var screenMode: String = UNKNOWN_MODE
    private var shopItemId: Int = ShopItem.UNDEFINED_ID


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("ShopItemFragment", "!!!!!!!!!!!!!!!!!!!!onCreate")
        parseParams()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("ShopItemFragment", "!!!!!!!!!!!!!!!!!!!!onAttach")
        if (context is OnEditFinishListener) {
            onEditFinishListener = context
        } else {
            throw RuntimeException("Activity using ShopItemFragment must implement OnEditFinishListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("ShopItemFragment", "!!!!!!!!!!!!!!!!!!!!onCreateView")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("ShopItemFragment", "!!!!!!!!!!!!!!!!!!!!onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        launchByMode()
        dropErrorOnItemNameInputChange()
        dropErrorOnItemCountInputChange()
        observeViewModel()
    }

    override fun onStart() {
        Log.d("ShopItemFragment", "!!!!!!!!!!!!!!!!!!!!onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d("ShopItemFragment", "!!!!!!!!!!!!!!!!!!!!onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d("ShopItemFragment", "!!!!!!!!!!!!!!!!!!!!onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d("ShopItemFragment", "!!!!!!!!!!!!!!!!!!!!onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.d("ShopItemFragment", "!!!!!!!!!!!!!!!!!!!!onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d("ShopItemFragment", "!!!!!!!!!!!!!!!!!!!!onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.d("ShopItemFragment", "!!!!!!!!!!!!!!!!!!!!onDetach")
        super.onDetach()
    }

    private fun launchByMode() {
        when (screenMode) {
            MODE_ADD -> launchAddMode()
            MODE_EDIT -> launchEditMode()
        }
    }

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Param `screen mode` is absent")
        }
        val mode = args.getString(EXTRA_SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode: $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("Param `shop item id` is absent")
            }
            shopItemId =
                args.getInt(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }

    private fun observeViewModel() {
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            binding.itemNameId.error = if (it) "Error!" else null
        }
        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            binding.itemCountId.error = if (it) "Error!" else null
        }

        viewModel.closeScreen.observe(viewLifecycleOwner) {
            onEditFinishListener.onEditFinish()
        }
    }


    private fun launchAddMode() {
        with(binding) {
            saveButtonId.setOnClickListener {
                viewModel.addShopItem(
                    itemNameInputEditText.text.toString(),
                    itemCountInputEditText.text.toString()
                )
            }
        }
    }

    private fun launchEditMode() {
        viewModel.getShopItem(shopItemId)
        viewModel.item.observe(viewLifecycleOwner) { shopItem ->
            with(binding) {
                itemNameInputEditText.setText(shopItem.name)
                itemCountInputEditText.setText(shopItem.count.toString())
            }
        }
        binding.saveButtonId.setOnClickListener {
            viewModel.updateShopItem(
                name = binding.itemNameInputEditText.text.toString(),
                count = binding.itemCountInputEditText.text.toString()
            )
        }
    }


    private fun dropErrorOnItemNameInputChange() {
        binding.itemNameInputEditText.doOnTextChanged { _, _, _, _ ->
            viewModel.resetErrorInputName()
        }
    }


    private fun dropErrorOnItemCountInputChange() {
        binding.itemCountInputEditText.doOnTextChanged { _, _, _, _ ->
            viewModel.resetErrorInputCount()
        }
    }


    interface OnEditFinishListener {
        fun onEditFinish()
    }

    companion object {
        const val EXTRA_SCREEN_MODE = "extra_mode"
        const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        const val MODE_EDIT = "mode_edit"
        const val MODE_ADD = "mode_add"
        const val UNKNOWN_MODE = ""

        fun getAddFragment(): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = bundleOf(EXTRA_SCREEN_MODE to MODE_ADD)
            }
        }

        fun getEditFragment(id: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = bundleOf(
                    EXTRA_SCREEN_MODE to MODE_EDIT,
                    EXTRA_SHOP_ITEM_ID to id
                )
            }
        }


    }
}
