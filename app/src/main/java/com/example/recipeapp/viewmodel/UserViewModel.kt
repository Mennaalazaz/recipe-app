package com.example.recipeapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.localdb.UserDatabase
import com.example.recipeapp.localdb.UserEntity
import com.example.recipeapp.reposatory.UserReposatory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserReposatory
    val allUsers: LiveData<List<UserEntity>>

    init {
        val userDao = UserDatabase.getInstance(application).userDao()
        repository = UserReposatory(userDao)
        allUsers = repository.getAllUsers()
    }

    fun insertUser(user: UserEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertUser(user)
    }

    suspend fun getUserByEmail(email: String): UserEntity? {
        return repository.getUserByEmail(email)
    }

    suspend fun getUserByEmailAndPassword(email: String, password: String): UserEntity? {
        return repository.getUserByEmailAndPassword(email, password)
    }

    fun deleteAllUsers() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAllUsers()
    }

    fun deleteUserById(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteUserById(id)
    }
}
