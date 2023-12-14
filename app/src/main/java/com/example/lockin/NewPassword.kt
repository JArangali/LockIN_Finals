package com.example.lockin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.text.TextUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class NewPassword : AppCompatActivity() {

    lateinit var databaseReference: DatabaseReference

    lateinit var webName : EditText
    lateinit var User : EditText
    lateinit var Pass : EditText
    lateinit var saveButton: Button
    lateinit var backButton: Button
    lateinit var LILogo : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newpassword)

        saveButton = findViewById(R.id.updatebtn)
        backButton = findViewById(R.id.update_backbtn)
        webName = findViewById(R.id.updateSite_edt)
        User = findViewById(R.id.updateUser_edt)
        Pass = findViewById(R.id.updatePass_edt)
        LILogo = findViewById(R.id.Logo)

        databaseReference = FirebaseDatabase.getInstance().getReference("Passwords")

        saveButton.setOnClickListener {
            saveAccount()

            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }

        backButton.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }

    }

    private fun saveAccount(){
        val websiteName = webName.text.toString()
        val username = User.text.toString()
        val password = Pass.text.toString()


        if (websiteName.isEmpty()) {
            webName.error = "Please enter name"
        }
        if (username.isEmpty()) {
            User.error = "Please enter age"
        }
        if (password.isEmpty()) {
            Pass.error = "Please enter salary"
        }

        val ID = databaseReference.push().key!!

        val employee = DataClass(ID, username, password, websiteName)

        databaseReference.child(ID).setValue(employee)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                webName.text.clear()
                User.text.clear()
                Pass.text.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }
}