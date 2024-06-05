

package com.example.firebase

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebase.databinding.ActivitySqlViewBinding
import com.google.firebase.database.DatabaseReference

class SqlViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySqlViewBinding
    private lateinit var dataBaseHelper: DataBaseHelper
    private lateinit var studentList: ArrayList<Student>
    private lateinit var adapter: RecyclerViewAdapter
     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         binding = ActivitySqlViewBinding.inflate(layoutInflater)
         setContentView(binding.root)
         studentList = ArrayList()
         dataBaseHelper = DataBaseHelper(this)

         adapter= RecyclerViewAdapter(studentList)
         binding.fbAdd.setOnClickListener {
             val intent = Intent(this, SqlAddActivity::class.java)
             startActivity(intent)
             finish()

         }

         binding.rvMain.adapter = adapter
         binding.rvMain.layoutManager = LinearLayoutManager(this)

         studentList.addAll(dataBaseHelper.getAllStudents())
         adapter.notifyDataSetChanged()






    }
}