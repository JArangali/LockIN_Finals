package com.example.lockin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class ForgetPasswordActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    lateinit var editfpEmail: EditText
    lateinit var btnReset: Button
    lateinit var btnBack: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        editfpEmail = findViewById(R.id.et_fpemail)
        btnReset = findViewById(R.id.btn_reset)
        btnBack = findViewById(R.id.btn_fpback)

        auth = Firebase.auth

        btnReset.setOnClickListener{
            compareEmail(editfpEmail)
        }

        btnBack.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })

    }
    private fun compareEmail(editfpEmail: EditText){
        if (editfpEmail.text.toString().isEmpty()){
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(editfpEmail.text.toString()).matches()){
            return
        }
        auth.sendPasswordResetEmail(editfpEmail.text.toString()).addOnCompleteListener { task ->
            if (task.isSuccessful){
                Toast.makeText(this, "Please check your email!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}