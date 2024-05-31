
package com.example.firebase

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.firebase.databinding.ActivityUploadStorageBinding
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.UUID

class UploadStorageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadStorageBinding
     var fileUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadStorageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.idBtnChooseImage.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 100)


        }
        binding.idBtnUploadImage.setOnClickListener(){
            uploadImage()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK && data != null && data.data != null) {
            fileUri = data.data
            try {
                val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, fileUri)
                binding.idIVImage.setImageBitmap(bitmap)
            } catch (e: Exception) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }

        }
    }
    fun uploadImage(){
        if(fileUri!= null){
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()
            val ref:StorageReference = FirebaseStorage.getInstance().getReference().child(UUID.randomUUID())
            ref.putFile(fileUri!!).addOnSuccessListener{
                progressDialog.dismiss()
                Toast.makeText(this,"unsucessfull",Toast.LENGTH_LONG).show()
            }.addOnFailureListener{
                progressDialog.show()
                Toast.makeText(this,"unsucessfull",Toast.LENGTH_LONG).show()
            }
        }
    }
}