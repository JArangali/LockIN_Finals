package com.example.lockin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DashboardActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var dataList: ArrayList<DataClass>
    lateinit var databaseReference: DatabaseReference
    lateinit var titleList: Array<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        databaseReference = Firebase.database.getReference("Passwords")

        val signout = findViewById(R.id.signout) as ImageView
        signout.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val add = findViewById(R.id.add) as ImageView
        add.setOnClickListener(){
            val intent = Intent(this, NewPassword::class.java)
            startActivity(intent)
        }



        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        dataList = arrayListOf<DataClass>()
        getData()
    }

    private fun getData(){
                recyclerView.adapter = AdapterClass(dataList)

        databaseReference.child("SavedPass").child("-NlNruISDhOVn5LBRY_l").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val snapshot = task.result
                recyclerView = findViewById(R.id.recyclerView)

                titleList = arrayOf(
                    snapshot.child("username").getValue(String::class.java).toString()
                )

                titleList = arrayOf(
                    snapshot.child("username").getValue(String::class.java).toString()
                )

                titleList = arrayOf(
                    snapshot.child("username").getValue(String::class.java).toString()
                )



                Log.i("sampleshot", snapshot.child("username").getValue(String::class.java).toString())
            }
        }
            }

    }
