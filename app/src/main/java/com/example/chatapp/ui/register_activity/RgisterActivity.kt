package com.example.chatapp.ui.register_activity

import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityRegisterBinding
import com.example.chatapp.ui.register_activity.BaseActivity
import com.example.chatapp.ui.register_activity.RegisterViewModel
import com.example.chatapp.ui.register_activity.home_activity.HomeActivity

class RegisterActivity : BaseActivity<RegisterViewModel, ActivityRegisterBinding>(),Navigator {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.vm=viewModel
        viewModel.navigator=this
        subscribeToLiveData()


    }

    private fun subscribeToLiveData() {
        viewModel.isLoadingLiveData.observe(this){
            if(it){
                showLoading()

            }else{
                hideLoading()
            }
        }
        viewModel.messageLiveData.observe(this){
            val alertDialogBuilder=android.app.AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("Error")
                .setMessage(it)
                .setPositiveButton("OK",object :DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        p0!!.dismiss()
                    }

                }).show()
        }
    }

    override fun getLayoutFile(): Int {
        return R.layout.activity_register
    }

    override fun initViewModel(): RegisterViewModel {
        return ViewModelProvider(this).get(RegisterViewModel::class.java)
    }

    override fun navigateToHome() {
        val intent= Intent(this,HomeActivity::class.java)
        startActivity(intent)
    }


}