package com.example.firebase

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.firebase.databinding.ActivityAddRealtimeBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddRealtimeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddRealtimeBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRealtimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseReference = FirebaseDatabase.getInstance().getReference("students")

        binding.btnAddStudent.setOnClickListener(){
            val name = binding.edtName.text.toString()
            val class_name = binding.edtClass.text.toString()
            val roll_no = binding.edtRollno.text.toString().toInt()

            if(name.isEmpty() || class_name.isEmpty() || roll_no == 0){
                Toast.makeText(this, "Please enter all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val student = Student(name, class_name, roll_no)
            val studentId = databaseReference.push().key

            studentId?.let{
                databaseReference.child(studentId).setValue(student)
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            Toast.makeText(this, "Student added successfully", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, ShowRealtimeActivity::class.java)
                            startActivity(intent)
                            finish()

                        }
                        else{
                            Toast.makeText(this, "Failed to add student", Toast.LENGTH_SHORT).show()
                        }
                    }

            }
        }
    }
}