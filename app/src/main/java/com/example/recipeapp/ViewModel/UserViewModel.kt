package com.example.recipeapplication.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapplication.LocalDB.UserDatabase
import com.example.recipeapplication.LocalDB.UserEntity
import com.example.recipeapplication.Reposatory.UserReposatory
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val reposatory: UserReposatory
    private val allUsers : LiveData<List<UserEntity>>
    init {
        val userDao = UserDatabase.getInstance(application).userDao()
        reposatory = UserReposatory(userDao)
        allUsers = reposatory.getAllUsers()
    }

    fun insertUser(user: UserEntity)=viewModelScope.launch {
        reposatory.insertUser(user)
    }

    suspend fun getUserByEmail(email: String): UserEntity?  {
       return reposatory.getUserByEmail(email)
    }

   suspend fun getUserByEmailAndPassword(email: String, password: String): UserEntity? {
        return reposatory.getUserByEmailAndPassword(email, password)
    }


    fun deleteAllUsers()= viewModelScope.launch {
        reposatory.deleteAllUsers()
    }

    fun deleteUserById(id: Int)= viewModelScope.launch {
        reposatory.deleteUserById(id)
    }
}