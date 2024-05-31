package com.example.firebase

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.firebase.databinding.ActivitySignInBinding
import com.example.firebase.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.tvAlreadyRegister.setOnClickListener(){
            val intent = Intent(this,SignInActivity::class.java)
            startActivity(intent)
            finish()

        }

        binding.signUp.setOnClickListener(){
            val email = binding.edtemail.text.toString()
            val pass = binding.pass.text.toString()
            val confirm_pass = binding.confirmPass.text.toString()

            if(email.isNotEmpty() && pass.isNotEmpty() && confirm_pass.isNotEmpty()){
                if(pass == confirm_pass){
                    firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this){
                        task-> if(task.isSuccessful){
                        Toast.makeText(this,"Authentication Successfully.",Toast.LENGTH_SHORT).show()
                        val intent = Intent(this,SignInActivity::class.java)
                        startActivity(intent)
                        finish()
                        }

                        else{
                        Toast.makeText(
                            this,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    }

                }
                else{
                    Toast.makeText(this,"password not match",Toast.LENGTH_LONG).show()
                }

            }
            else{
               Toast.makeText(this,"Empty Field not Allowed", Toast.LENGTH_LONG).show()
            }


        }


    }
}