package com.example.exampleroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.exampleroom.Room.Constant
import com.example.exampleroom.Room.Note

import com.example.exampleroom.Room.NoteDB
import com.example.exampleroom.databinding.ActivityAddBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddActivity : AppCompatActivity() {
    val db by lazy { NoteDB(this) }
    private var getId  : Int = 0
    private lateinit var binding : ActivityAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        create()
        setupView()
    }
    private fun create(){
        binding.buttonSave.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.noteDao().addData(
                    Note(0,binding.editTitle.text.toString(),binding.editNote.text.toString())
                )
                startActivity(Intent(this@AddActivity,MainActivity::class.java).also {
                    finish()
                })
            }
        }

        binding.buttonUpdate.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.noteDao().UpdateData(
                        Note(getId,binding.editTitle.text.toString(),binding.editNote.text.toString())
                )
                startActivity(Intent(this@AddActivity,MainActivity::class.java).also {
                    finish()
                })
            }
        }
    }


    private fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val getIntentType = intent.getIntExtra("IntentType",0)
        when(getIntentType){
            Constant.TYPE_CREATE ->{
                binding.buttonUpdate.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                binding.buttonSave.visibility = View.GONE
                binding.buttonUpdate.visibility = View.GONE
                getdata()
            }
            Constant.TYPE_UPDATE -> {
                binding.buttonSave.visibility = View.GONE
                getdata()
            }
        }

    }

    fun getdata(){
       getId = intent.getIntExtra("noteId",0)
        CoroutineScope(Dispatchers.IO).launch {
            val getall = db.noteDao().getDatabyId(getId)[0]
            binding.editTitle.setText(getall.title)
            binding.editNote.setText(getall.desc)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}