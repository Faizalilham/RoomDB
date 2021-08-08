package com.example.exampleroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exampleroom.Adapter.MainAdapter
import com.example.exampleroom.Room.Constant
import com.example.exampleroom.Room.Note
import com.example.exampleroom.Room.NoteDB
import com.example.exampleroom.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    val db by lazy { NoteDB(this) }
    private lateinit var binding : ActivityMainBinding
    lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        moveActivity()
        showrecycler()
    }

    override fun onResume() {
        super.onResume()
        reloadrecycler()
    }

    fun reloadrecycler(){
        CoroutineScope(Dispatchers.IO).launch {
            val notes = db.noteDao().getData()
            withContext(Dispatchers.Main){
                mainAdapter.setData(notes)
            }
        }
    }


    private fun moveActivity(){
       binding.buttonCreate.setOnClickListener {
           intentEdit(0,Constant.TYPE_CREATE)
       }
    }

    private fun showrecycler(){
        mainAdapter = MainAdapter(arrayListOf(), object  : MainAdapter.OnAdapterListerner{
            override fun Onclick(notes: Note) {
               intentEdit(notes.id,Constant.TYPE_READ)
            }

            override fun OnUpdate(notes: Note) {
                intentEdit(notes.id,Constant.TYPE_UPDATE)
            }

            override fun OnDelete(notes: Note) {
                CoroutineScope(Dispatchers.IO).launch {
                    db.noteDao().DeleteData(notes)
                    reloadrecycler()

                }
            }

        })
        binding.listNote.apply {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    fun intentEdit(noteId : Int, IntentType : Int){
        startActivity(Intent(applicationContext,AddActivity::class.java)
                .putExtra("noteId",noteId)
                .putExtra("IntentType",IntentType)
        )
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}