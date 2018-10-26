package com.game.clubs.stumps.landing.viewmodel

import android.arch.lifecycle.ViewModel
import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class LandingViewModel : ViewModel() {
    var dp: FirebaseFirestore? = FirebaseFirestore.getInstance()


    fun getListOfMatches() {
        val firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseFirestore.collection("Teams")
                .whereEqualTo("name","Match2")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("LandingViewModel", "Match Result:${task.result!!.size()}")
                        for (document in task.result!!){
                            Log.d("LandingViewModel","Match:${document.data}")
                        }
                    } else {
                        Log.d("LandingViewModel","Result failed")
                    }
                }

    }

}