package com.example.lockin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    lateinit var editTextFName: EditText
    lateinit var editTextLName: EditText
    lateinit var editTextEmail: EditText
    lateinit var editTextUName: EditText
    lateinit var editTextPass: EditText
    lateinit var buttonReg: Button
    lateinit var buttonback: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Initialize Firebase Auth
        auth = Firebase.auth

        editTextFName = findViewById(R.id.et_Firstname)
        editTextLName = findViewById(R.id.et_Lastname)
        editTextEmail = findViewById(R.id.et_Email)
        editTextUName = findViewById(R.id.et_username)
        editTextPass = findViewById(R.id.et_password)
        buttonReg = findViewById(R.id.btn_submit)
        buttonback = findViewById(R.id.btn_back)

        buttonReg.setOnClickListener(){
            if(validateForm()) {
                performSignUp()
            }
        }
        buttonback.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })
    }

    private fun validateForm(): Boolean {
        var valid = true

        val fname = editTextFName.text.toString()
        if (TextUtils.isEmpty(fname)) {
            editTextFName.error = "Required."
            valid = false
        } else {
            editTextFName.error = null
        }

        val lname = editTextLName.text.toString()
        if (TextUtils.isEmpty(lname)) {
            editTextLName.error = "Required."
            valid = false
        } else {
            editTextLName.error = null
        }

        val email = editTextEmail.text.toString()
        if (TextUtils.isEmpty(email)) {
            editTextEmail.error = "Required."
            valid = false
        } else {
            editTextEmail.error = null
        }

        val uname = editTextUName.text.toString()
        if (TextUtils.isEmpty(uname)) {
            editTextUName.error = "Required."
            valid = false
        } else {
            editTextUName.error = null
        }

        val pass = editTextPass.text.toString()
        if (TextUtils.isEmpty(pass)) {
            editTextPass.error = "Required."
            valid = false
        } else {
            editTextPass.error = null
        }

        if(editTextFName.text.toString().isNotEmpty() &&
            editTextLName.text.toString().isNotEmpty() &&
            editTextEmail.text.toString().isNotEmpty() &&
            editTextUName.text.toString().isNotEmpty() &&
            editTextPass.text.toString().isNotEmpty())
        {
            if(isEmailValid()){
                if(editTextPass.text.toString().length>=8){
                    return valid
                }
            }else{
                editTextPass.error = "Password must be at least 8 characters"
            }
        }else{
            editTextEmail.error = "Please Enter Valid Email ID"
        }

        return valid
    }

    private fun isEmailValid(): Boolean {
        val email = editTextEmail.text.toString()
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun performSignUp() {

        val inemail = findViewById<EditText>(R.id.et_Email)
        val inpassword = findViewById<EditText>(R.id.et_password)

        if (inemail.text.isEmpty() || inpassword.text.isEmpty()){
            Toast.makeText(this, "Pls fill all fields", Toast.LENGTH_SHORT)
                .show()
            return
        }

        val email = inemail.text.toString().trim()
        val password = inpassword.text.toString().trim()

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

                Toast.makeText(
                    baseContext, "Register Success",
                    Toast.LENGTH_SHORT,)
                    .show()

            } else {
                // If sign in fails, display a message to the user.

                Toast.makeText(
                    baseContext, "Register Fail",
                    Toast.LENGTH_SHORT,)
                    .show()
            }
        }
            .addOnFailureListener{
                Toast.makeText(this, "Error occurred ${it.localizedMessage}", Toast.LENGTH_SHORT)
                    .show()
            }
    }

}