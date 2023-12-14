package com.example.lockin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DashboardActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    private lateinit var rvLoadingData: TextView
    lateinit var dataList: ArrayList<DataClass>
    lateinit var databaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

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
        rvLoadingData = findViewById(R.id.rvLoadingData)

        dataList = arrayListOf<DataClass>()

        getData()
    }

    private fun getData(){
        recyclerView.visibility = View.GONE
        rvLoadingData.visibility = View.VISIBLE

        databaseReference = FirebaseDatabase.getInstance().getReference("Passwords")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                if (snapshot.exists()) {
                    for (empSnap in snapshot.children) {
                        val rvData = empSnap.getValue(DataClass::class.java)
                        dataList.add(rvData!!)
                    }
                }
                val rvAdapter = AdapterClass(dataList)
                recyclerView.adapter = rvAdapter

                rvAdapter.setOnItemClickListener(object : AdapterClass.onItemClickListener{
                    override fun onItemClick(position: Int) {

                        val intent = Intent(this@DashboardActivity, AccountActivity::class.java)

                        //put extras
                        intent.putExtra("Website", dataList[position].websiteName)
                        intent.putExtra("Username", dataList[position].username)
                        startActivity(intent)
                    }

                })

                recyclerView.visibility = View.VISIBLE
                rvLoadingData.visibility = View.GONE

    }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

            }
    }