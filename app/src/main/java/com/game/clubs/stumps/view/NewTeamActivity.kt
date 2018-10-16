package com.game.clubs.stumps.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.game.clubs.stumps.R
import com.game.clubs.stumps.model.Team
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_new_team.*

/**
 * Created by Venkareddy on 10/12/2018.
 */

class NewTeamActivity : AppCompatActivity() {
    companion object {
        const val TEAMS: String = "Teams"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_team)
        val db = FirebaseFirestore.getInstance()
        val teamNamesArray = arrayListOf<String>()
        buttonSubmit.setOnClickListener {
            val team = Team()
            if (teamNameEditText.text.toString().isNotEmpty() && teamShortNameEditText.text.toString().isNotEmpty()) {
                val name = teamNameEditText.text.toString()
                teamNamesArray.clear()
                db.collection(TEAMS).get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result!!) {
                            teamNamesArray.add(document.id.toLowerCase())
                        }
                    }
                    if (teamNamesArray.contains(name.toLowerCase())) {
                        teamNameEditText.error = "Already name exists, please choose another name"
                        return@addOnCompleteListener
                    }
                    team.name = teamNameEditText.text.toString()
                    team.shortName = teamShortNameEditText.text.toString()
                    db.collection(TEAMS).document(team.name!!).set(team).addOnSuccessListener {
                        teamNameEditText.text.clear()
                        teamShortNameEditText.text.clear()
                        teamNameEditText.requestFocus()
                    }
                }


            }
        }

    }
}
