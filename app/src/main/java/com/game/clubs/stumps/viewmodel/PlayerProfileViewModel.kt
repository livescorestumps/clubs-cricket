package com.game.clubs.stumps.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import android.support.annotation.NonNull
import android.util.Log
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.firestore.DocumentReference


class PlayerProfileViewModel : ViewModel() {
    //    var dp: FirebaseFirestore? = null
    var dp: FirebaseFirestore? = FirebaseFirestore.getInstance()

    //Mutable data
    val checkUserLiveData = MutableLiveData<Boolean>()


    fun checkIfUserExits(email: String) {
        var result: Boolean = false
        dp?.collection("Players")!!.whereEqualTo("emailId", email).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                checkUserLiveData.postValue(task.result!!.size() > 0)
            } else
                checkUserLiveData.postValue(false)
        }
    }

    fun getCheckUserLiveData(): LiveData<Boolean> {
        return checkUserLiveData
    }

    public fun updatePlayerProfile(fName: String, lName: String) {
        var fireUser: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
        var hashMap: HashMap<String, Any> = hashMapOf()
        hashMap.put("fName", fName)
        hashMap.put("lName", lName)
        hashMap.put("emailId", fireUser.email!!)
        hashMap.put("uid",fireUser.uid)

        dp!!.collection("Players")!!.add(hashMap)
                .addOnSuccessListener { documentReference ->
                    Log.d("PlayerProfileViewModel", documentReference.id)
                    checkUserLiveData.postValue(true)
                }.addOnFailureListener { exception ->
                    Log.d("PlayerProfileViewModel", "$exception.toString()")
                    checkUserLiveData.postValue(false)
                }

    }
}