package com.example.chatapp.ui.register_activity

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
@BindingAdapter("app:Error")
fun setError(textInput:TextInputLayout,errorMessage:String?){
    textInput.error=errorMessage
}