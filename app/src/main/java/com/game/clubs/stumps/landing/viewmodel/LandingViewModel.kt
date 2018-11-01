package com.game.clubs.stumps.landing.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.game.clubs.stumps.model.Matches
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class LandingViewModel : ViewModel() {
    var dp: FirebaseFirestore? = FirebaseFirestore.getInstance()

    var matchesListLiveData: MutableLiveData<MutableList<Matches>>? = null

    init {
        matchesListLiveData = MutableLiveData()
    }

    fun getListOfMatches() {
        val firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseFirestore.collection("/Matches")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("LandingViewModel", "Match Result:${task.result!!.size()}")
                        var matchList = mutableListOf<Matches>()
                        for (document in task.result!!) {
                            val match = document.toObject(Matches::class.java)
                            match.id = document.id
                            Log.d("LandingViewModel","Match:${match.id}")
                            matchList.add(match)
                        }
                        matchesListLiveData!!.postValue(matchList)
                    } else {
                        Log.d("LandingViewModel", "Result failed")
                    }
                }

    }

    /**
     * Return Matches live data
     */
    fun getMatchesLiveData(): MutableLiveData<MutableList<Matches>>? {
        return this!!.matchesListLiveData
    }

}