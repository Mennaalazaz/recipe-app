package com.example.recipeapp.reposatory

import androidx.lifecycle.LiveData
import com.example.recipeapp.localdb.UserDao
import com.example.recipeapp.localdb.UserEntity

class UserReposatory (private val userDao: UserDao){

    suspend fun insertUser(user: UserEntity){
        userDao.insertUser(user)
    }
    suspend fun getUserByEmail(email: String): UserEntity?{
        return userDao.getUserByEmail(email)
    }

    suspend fun getUserByEmailAndPassword(email: String, password: String): UserEntity?{
        return userDao.getUserByEmailAndPassword(email, password)
    }

     fun getAllUsers(): LiveData<List<UserEntity>>{
        return userDao.getAllUsers()
    }
    suspend fun deleteAllUsers(){
        userDao.deleteAllUsers()
    }

    suspend fun deleteUserById(id: Int){
        return userDao.deleteUserById(id)
    }


}