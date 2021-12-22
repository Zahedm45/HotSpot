package com.example.hotspot.viewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hotspot.model.User

class UserViewModel : ViewModel() {

    private var user = User("First name", 24, "zahedm@gmail.com")

    private var use = MutableLiveData<User>(user)


    init {

    }

    private fun updateUser() {

        use.value?.name  = "My name is.."
        use.value?.age  = 25
        use.value?.emailAddress  = "zahedm45@gmai.com"



    }

}