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

        saveButton.setOnClickListener {

            if (validateForm()) {
                val websiteName = webName.text.toString()
                val username = User.text.toString()
                val password = Pass.text.toString()

                databaseReference = FirebaseDatabase.getInstance().getReference("Passwords/SavedPass")
                var dataKey = databaseReference.push().getKey()
                var SavedPass = DataClass(websiteName, username, password)
                databaseReference.child("SavedPass").child(dataKey.toString())
                    .setValue(SavedPass).addOnSuccessListener {
                        Toast.makeText(this, "Password Added!", Toast.LENGTH_SHORT).show()
                    }
                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
            }
        }

        backButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        })

        LILogo.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        })

    }

    private fun validateForm(): Boolean {
        var valid = true

        val Name = webName.text.toString()
        if (TextUtils.isEmpty(Name)) {
            webName.error = "Required."
            valid = false
        } else {
            webName.error = null
        }

        val username = User.text.toString()
        if (TextUtils.isEmpty(username)) {
            User.error = "Required."
            valid = false
        } else {
            User.error = null
        }

        val password = Pass.text.toString()
        if (TextUtils.isEmpty(password)) {
            Pass.error = "Required."
            valid = false
        } else {
            Pass.error = null
        }

        return valid
    }

    private fun NewPass() {
        // implement your own logic to validate user credentials
        // typically done via network API
    }
}