package com.example.littlelemon

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update
import androidx.room.Upsert

@Entity
data class Menu(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val image: String,
    val category: String
)

@Dao
interface DataAccessObject{
    @Query("Select * from Menu Order By title")
    fun getAllMenu(): LiveData<List<Menu>>

    @Insert
    fun insertAllItems(item: Menu)

    @Upsert
    fun updateItems(item: Menu)
}

@Database(entities = [Menu::class], version = 1)
abstract class MenuData: RoomDatabase(){
    abstract fun inventoryDao(): DataAccessObject
}

class InventoryDatabase(context: Context){
    val database = Room.databaseBuilder(
        context.applicationContext,
        MenuData::class.java,
        "database.db"
    ).build()

    fun getFoodMenu() = database.inventoryDao().getAllMenu()

    fun insertAllItems(item: Menu) = database.inventoryDao().insertAllItems(item)

    fun updateMenuItem(item: Menu) = database.inventoryDao().updateItems(item)
}
