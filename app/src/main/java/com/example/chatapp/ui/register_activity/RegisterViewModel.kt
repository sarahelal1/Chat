package com.example.chatapp.ui.register_activity

import android.database.Observable
import android.util.Log
import androidx.constraintlayout.motion.widget.TransitionBuilder.validate
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.ui.register_activity.firebase.addToFireStore
import com.example.chatapp.ui.register_activity.model.AppUser
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class RegisterViewModel:ViewModel() {
    val firstName = ObservableField<String>()
    val lastName = ObservableField<String>()
    val email = ObservableField<String>()
    val password = ObservableField<String>()
    val firstNameError = ObservableField<String?>()
    val lastNameError = ObservableField<String?>()
    val emailError = ObservableField<String?>()
    val passwordError = ObservableField<String?>()
    val messageLiveData=MutableLiveData<String>()
    val isLoadingLiveData=MutableLiveData<Boolean>()
    var navigator:Navigator?=null
    val auth = Firebase.auth
    fun createAccount() {
        if (validate()) {
            isLoadingLiveData.value=true
            auth.createUserWithEmailAndPassword(email.get()!!, password.get()!!)
                .addOnCompleteListener { task ->
                    isLoadingLiveData.value=false
                    if (!task.isSuccessful) {
                        Log.e("createAccount",  task.exception.toString())
                        messageLiveData.value=task.exception!!.message
                    } else {
                        val user:AppUser= AppUser(firstName=firstName.get(),
                        lastName = lastName.get(),
                            email = email.get(),
                            id=task.result.user?.uid
                            )
                        addToFireStore(user, successListener = {
                            isLoadingLiveData.value=false
                          navigator?.navigateToHome()
                        }) {
                            isLoadingLiveData.value=false
                            messageLiveData.value=it.message
                        }

                        Log.e("createAccount",  "createdAccount")
                    }
                }
        }
    }


    fun validate(): Boolean {
        var valid = true
        if (firstName.get().isNullOrBlank()) {
            valid = false
            firstNameError.set("please enter valid name")
        }else{
            firstNameError.set(null)
        }
        if (lastName.get().isNullOrBlank()) {
            valid = false
            lastNameError.set("please enter valid name")
        }else{
            lastNameError.set(null)
        }
        if (email.get().isNullOrBlank()) {
            valid = false
            emailError.set("please enter valid email")
        }else{
            emailError.set(null)
        }
        if (password.get().isNullOrBlank()) {
            valid = false
            passwordError.set("please enter valid password")
        }else{
            passwordError.set(null)
        }
        return valid
    }
}