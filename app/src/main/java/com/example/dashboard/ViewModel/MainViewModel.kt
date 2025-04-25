package com.example.dashboard.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dashboard.Domain.BoostersModel
import com.example.dashboard.Domain.CategoryModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.sql.Ref


class MainViewModel() : ViewModel() {

    private val firebaseDatabase = FirebaseDatabase.getInstance("https://boostify-66654-default-rtdb.asia-southeast1.firebasedatabase.app")

    private val _category = MutableLiveData<MutableList<CategoryModel>>()
    private val _boosters = MutableLiveData<MutableList<BoostersModel>>()

    val category: LiveData<MutableList<CategoryModel>> = _category
    val boosters: LiveData<MutableList<BoostersModel>> = _boosters

    fun loadCategory(){
        val ref=firebaseDatabase.getReference("Category")
        ref.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists =  mutableListOf<CategoryModel>()
                    for (childSnapshot in snapshot.children){
                        val list=childSnapshot.getValue(CategoryModel::class.java)
                        if(list!=null){
                            lists.add(list)
                        }
                    }
                _category.value=lists
                Log.d("FirebaseData", "Items loaded: ${lists.size}")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseError", error.message)
            }
        })
    }

    fun loadBoosters(){
        val ref=firebaseDatabase.getReference("Boosters")
        ref.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<BoostersModel>()

                for (childSnapshot in snapshot.children){
                    val list = childSnapshot.getValue(BoostersModel::class.java)

                    if (list !=null){
                        lists.add(list)
                    }
                }
                _boosters.value = lists
                Log.d("BoostersData", "Loaded ${lists.size} boosters")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}