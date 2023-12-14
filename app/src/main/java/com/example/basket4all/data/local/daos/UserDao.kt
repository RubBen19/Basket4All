package com.example.basket4all.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.basket4all.data.local.entities.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserEntity)

    @Delete
    fun delete(user: UserEntity)

    @Update
    fun update(user: UserEntity)

    @Query("SELECT * FROM users_table")
    fun getAll(): List<UserEntity>

    @Query("SELECT * FROM users_table WHERE userId = :id")
    fun getById(id: Int): UserEntity

    @Query("SELECT * FROM users_table WHERE Name LIKE :name")
    fun getByName(name: String): List<UserEntity>

    @Query("SELECT * FROM users_table WHERE Name LIKE :name AND " +
            "`First Surname` LIKE :surname1 AND `Second Surname` LIKE :surname2")
    fun getByName(name: String, surname1: String, surname2: String): List<UserEntity>

    @Query("SELECT * FROM users_table WHERE `Date of birth` LIKE :date")
    fun getByDate(date: String): List<UserEntity>
}