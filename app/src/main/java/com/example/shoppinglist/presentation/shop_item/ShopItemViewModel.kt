package com.example.shoppinglist.presentation.shop_item

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.dto.ShopItem
import com.example.shoppinglist.domain.use_cases.AddShopItemUseCase
import com.example.shoppinglist.domain.use_cases.GetShopItemUseCase
import com.example.shoppinglist.domain.use_cases.UpdateShopItemUseCase
import java.lang.ClassCastException

class ShopItemViewModel : ViewModel() {
    private val repository = ShopListRepositoryImpl

    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val updateShopItemUseCase = UpdateShopItemUseCase(repository)
    private val getShopItemUseCase = GetShopItemUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _item = MutableLiveData<ShopItem>()

    val item: LiveData<ShopItem>
        get() = _item

    private val _closeScreen = MutableLiveData<Unit>()
    val closeScreen: LiveData<Unit>
        get() = _closeScreen


    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        if (isFieldsCorrect(name, count)) {
            addShopItemUseCase.addShopItem(
                ShopItem(
                    name = name,
                    count = count,
                    enabled = true
                )
            )
            finishWork()
        }
    }

    private fun isFieldsCorrect(name: String, count: Int): Boolean {
        var correct = true
        if (name.isBlank()) {
            _errorInputName.value = true
            correct = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            correct = false
        }
        return correct
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun parseCount(inputName: String?): Int {
        return try {
            inputName?.trim()?.toInt() ?: 0
        } catch (e: NumberFormatException) {
            0
        }
    }

    private fun parseName(inputName: String?): String = inputName?.trim() ?: ""


    fun updateShopItem(name: String?, count: String?) {
        val parsedName = parseName(name)
        val parsedCount = parseCount(count)
        _item.value?.let {
            if (isFieldsCorrect(parsedName, parsedCount)) {
                updateShopItemUseCase.updateShopItem(
                    it.copy(
                        name = parsedName,
                        count = parsedCount
                    )
                )
                finishWork()
            }
        }
    }

    fun getShopItem(id: Int) {
        val item = getShopItemUseCase.getShopItemById(id)
        _item.value = item
    }

    private fun finishWork() {
        _closeScreen.value = Unit
    }

}