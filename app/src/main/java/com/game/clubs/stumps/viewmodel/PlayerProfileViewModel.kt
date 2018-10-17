package com.game.clubs.stumps.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore


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
}