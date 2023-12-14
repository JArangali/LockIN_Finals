package com.example.lockin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.FirebaseDatabase

class AccountActivity : AppCompatActivity() {

    private lateinit var tvWebsite: TextView
    private lateinit var tvUsername: TextView
    private lateinit var tvPassword: TextView
    private lateinit var btnDelete: Button
    private lateinit var btnUpdate: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("empId").toString(),
                intent.getStringExtra("empName").toString()
            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("empId").toString()
            )
        }

    }

    private fun initView() {
        tvWebsite = findViewById(R.id.rvWebsite)
        tvUsername = findViewById(R.id.rvUsername)
        tvPassword = findViewById(R.id.tvPassword)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        tvWebsite.text = intent.getStringExtra("rvWebsite")
        tvUsername.text = intent.getStringExtra("rvUsername")
        tvPassword.text = intent.getStringExtra("rvPassword")
    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Passwords").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Account Details Deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, DashboardActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun openUpdateDialog(
        empId: String,
        empName: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.activity_updatepassword, null)

        mDialog.setView(mDialogView)

        val etWebsite = mDialogView.findViewById<EditText>(R.id.updateSite_edt)
        val etUsername = mDialogView.findViewById<EditText>(R.id.updateUser_edt)
        val etPassword = mDialogView.findViewById<EditText>(R.id.updatePass_edt)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.update_backbtn)

        etWebsite.setText(intent.getStringExtra("Website").toString())
        etUsername.setText(intent.getStringExtra("Username").toString())
        etPassword.setText(intent.getStringExtra("Password").toString())

        mDialog.setTitle("Updating $empName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateDataList(
                empId,
                etWebsite.text.toString(),
                etUsername.text.toString(),
                etPassword.text.toString()
            )

            Toast.makeText(applicationContext, "Employee Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvWebsite.text = etWebsite.text.toString()
            tvUsername.text = etUsername.text.toString()
            tvPassword.text = etPassword.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateDataList(
        id: String,
        websiteName: String,
        username: String,
        password: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Passwords").child(id)
        val empInfo = DataClass(id, websiteName, username, password)
        dbRef.setValue(empInfo)
    }

}

