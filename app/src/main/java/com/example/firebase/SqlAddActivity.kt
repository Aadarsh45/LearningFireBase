package com.example.firebase

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.firebase.databinding.ActivitySqlAddBinding

class SqlAddActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySqlAddBinding
    private lateinit var dataBaseHelper: DataBaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySqlAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataBaseHelper = DataBaseHelper(this)

        binding.btnAddStudent.setOnClickListener{
            val name = binding.edtName.text.toString()
            val className = binding.edtClass.text.toString()
            val roll_no = binding.edtRollno.text.toString().toInt()
            val student = Student(name, className, roll_no)

            dataBaseHelper.insertStudent(student)
            startActivity(Intent(this, SqlViewActivity::class.java))
        }

    }
}