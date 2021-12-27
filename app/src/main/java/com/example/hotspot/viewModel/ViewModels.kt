package com.example.hotspot.viewModel

import com.example.hotspot.Repository.Repository
import com.example.hotspot.model.User


class ViewModels {

    private lateinit var user: User


    companion object {
        private val repository = Repository()
        private lateinit var user: User

        val createProfileViewModel = CreateProfileViewModel(repository)

        val userViewModel = UserViewModel()




        private fun assignUser(user: User): User  {
            return user
        }

    }
}