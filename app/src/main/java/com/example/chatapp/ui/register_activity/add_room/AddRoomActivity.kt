package com.example.chatapp.ui.register_activity.add_room

import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityAddRoomBinding
import com.example.chatapp.ui.register_activity.BaseActivity
import com.example.chatapp.ui.register_activity.model.Category

class AddRoomActivity : BaseActivity<AddRoomViewModel,ActivityAddRoomBinding>(), Navigator {
    var adapter:SpinnerAdapter= SpinnerAdapter(Category.categories)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.vm=viewModel
        subscribeToLiveData()
        viewDataBinding.spinner.adapter=adapter
         viewModel.navigator=this
        viewDataBinding.spinner.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, index: Int, id: Long) {
                viewModel.selectedCategoryId=Category.categories.get(index).id
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }
    private fun subscribeToLiveData() {
        viewModel.isLoadingLiveData.observe(this) {
            if (it) {
                showLoading()

            } else {
                hideLoading()
            }
        }
        viewModel.messageLiveData.observe(this) {
            val alertDialogBuilder = android.app.AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("Error")
                .setMessage(it)
                .setPositiveButton("OK", object : DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        p0!!.dismiss()
                    }

                }).show()
        }
    }

    override fun getLayoutFile(): Int {
       return R.layout.activity_add_room
    }

    override fun initViewModel(): AddRoomViewModel {
       return ViewModelProvider(this).get(AddRoomViewModel::class.java)
    }

    override fun finishAddRoom() {
        finish()
    }
}