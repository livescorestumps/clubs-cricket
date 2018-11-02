package com.game.clubs.stumps.userprofile.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.game.clubs.stumps.model.Player
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UserProfileViewModel : ViewModel() {

    var userListLiveData: MutableLiveData<Player>? = null

    init {
        userListLiveData = MutableLiveData()
    }

    fun fetchUserProfile() {
        val firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseFirestore.collection("/Players")
                .whereEqualTo("uid", FirebaseAuth.getInstance().currentUser!!.uid)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result!!) {
                            val player = document.toObject(Player::class.java)
                            userListLiveData!!.postValue(player)
                        }
                    } else {
                        Log.d("UserProfileViewModel", "User Profile not found")
                        userListLiveData!!.postValue(null)
                    }
                }
    }
}