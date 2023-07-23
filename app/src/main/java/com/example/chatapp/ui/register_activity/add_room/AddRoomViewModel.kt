package com.example.chatapp.ui.register_activity.add_room


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.ui.register_activity.firebase.addRoomToFireStore
import com.example.chatapp.ui.register_activity.model.Room


class AddRoomViewModel:ViewModel() {
var title=MutableLiveData<String>()
    var titleError=MutableLiveData<String?>()
    var description=MutableLiveData<String>()
    var descriptionError=MutableLiveData<String?>()
    var selectedCategoryId="sports"
    val messageLiveData=MutableLiveData<String>()
    val isLoadingLiveData=MutableLiveData<Boolean>()
    var navigator:Navigator?=null
    fun createRoom(){
if (! validate()) return
        isLoadingLiveData.value=true

        var room= Room(
            title = title.value!!,
            description = title.value!!,
            categoryId = selectedCategoryId
        )
        addRoomToFireStore(room,{
            isLoadingLiveData.value=false
            navigator?.finishAddRoom()
        },{
            isLoadingLiveData.value=false
        messageLiveData.value=it.message
        })
    }
    fun validate():Boolean{
        var valid = true

        if (title.value.isNullOrBlank()) {
            valid = false
            titleError.value="please enter valid title"
        }else{
            titleError.value=null
        }
        if (description.value.isNullOrBlank()) {
            valid = false
            descriptionError.value="please enter valid description"
        }else{
            descriptionError.value=null
        }
        return valid
    }
    }
