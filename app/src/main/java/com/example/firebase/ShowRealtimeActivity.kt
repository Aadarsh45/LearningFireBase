package com.example.firebase

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebase.databinding.ActivityShowRealtimeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ShowRealtimeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShowRealtimeBinding
    private lateinit var database: DatabaseReference
    private lateinit var studentList: ArrayList<Student>
    private lateinit var adapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowRealtimeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        studentList = ArrayList()
        database = FirebaseDatabase.getInstance().getReference("students")
        adapter= RecyclerViewAdapter(studentList)
        binding.fbAdd.setOnClickListener {
            val intent = Intent(this, AddRealtimeActivity::class.java)
            startActivity(intent)
            finish()

        }

        binding.rvMain.adapter = adapter
        binding.rvMain.layoutManager = LinearLayoutManager(this)
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                studentList.clear()
                for (snap in snapshot.children) {
                    val student = snap.getValue(Student::class.java)
//                    studentList.add(student!!)
                    student?.let { studentList.add(it) }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}