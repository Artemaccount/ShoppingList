package com.example.shoppinglist.presentation.binding_adapter

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView


@BindingAdapter("int_to_string")
fun adapterBinding(ed: TextInputEditText, count: Int) {
    ed.setText(count.toString())
}

@BindingAdapter("int_to_string")
fun adapterBinding(ed: MaterialTextView, count: Int) {
    ed.text = count.toString()
}

@BindingAdapter("error_adapter")
fun errorBinding(ed: TextInputLayout, error: Boolean) {
    ed.error = if (error) "Error!" else null
}
