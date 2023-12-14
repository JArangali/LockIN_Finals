package com.example.lockin

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var userEmail: EditText
    private lateinit var userPassword: EditText
    lateinit var loginButton: Button
    lateinit var signupButton: Button
    lateinit var forgotPasswordButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth

        userEmail = findViewById(R.id.login_email)
        userPassword = findViewById(R.id.login_password)
        loginButton = findViewById(R.id.btn_sign_in)
        signupButton = findViewById(R.id.btn_signup)
        forgotPasswordButton = findViewById(R.id.btn_forget_password)

        loginButton.setOnClickListener(View.OnClickListener {
            if (validateForm()) {
                performLogin()
            }
        })

        signupButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        })

        forgotPasswordButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, ForgetPasswordActivity::class.java)
            startActivity(intent)
        })
    }

    private fun performLogin() {
        val email: EditText = findViewById(R.id.login_email)
        val password: EditText = findViewById(R.id.login_password)

        val emailInput = email.text.toString()
        val passwordInput = password.text.toString()

        auth.signInWithEmailAndPassword(emailInput, passwordInput)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)

                    Toast.makeText(
                        baseContext, "Success",
                        Toast.LENGTH_SHORT,)
                        .show()
                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()

                }
            }
            .addOnFailureListener{
                Toast.makeText(this, "Error occurred ${it.localizedMessage}", Toast.LENGTH_SHORT)
                    .show()
            }

    }

    private fun validateForm(): Boolean {
        var valid = true

        val email = userEmail.text.toString()
        if (TextUtils.isEmpty(email)) {
            userEmail.error = "Required."
            valid = false
        } else {
            userEmail.error = null
        }

        val password = userPassword.text.toString()
        if (TextUtils.isEmpty(password)) {
            userPassword.error = "Required."
            valid = false
        } else {
            userPassword.error = null
        }

        if(userEmail.text.toString().isNotEmpty() &&
            userPassword.text.toString().isNotEmpty())
        {

        }

        return valid
    }

    private fun loginUser() {
        // implement your own logic to validate user credentials
        // typically done via network API
    }
}