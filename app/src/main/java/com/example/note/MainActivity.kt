package com.example.note

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.note.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val notes: MutableList<Note> = mutableListOf()
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = NoteAdapter(notes)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        binding.add.setOnClickListener {
            Note().apply {
                height = 100
                content = "2023-5-23 11:15"
            }.also {
                add(it)
            }
        }
        update()

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()

        val userDao = db.userDao()

        GlobalScope.launch(Dispatchers.IO) {
            val users = userDao.getAll()

        }

    }

    private fun add(note: Note) {
        notes += note
        adapter.notifyItemInserted(notes.lastIndex)
    }

    private fun update() {
        adapter.notifyDataSetChanged()
    }
}

@Entity(tableName = "user")
data class User(
    @PrimaryKey val id: Int,
    val name: String,
    val age: Int
)

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Insert
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)
}

@Database(entities = arrayOf(User::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}

