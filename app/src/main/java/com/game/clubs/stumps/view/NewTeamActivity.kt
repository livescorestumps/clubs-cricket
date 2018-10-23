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
        buttonSubmit.setOnClickListener {
            var team = Team()
            if(teamNameEditText.text.toString().isNotEmpty() && teamShortNameEditText.text.toString().isNotEmpty()) {
                team.name = teamNameEditText.text.toString()
                team.shortName = teamShortNameEditText.text.toString()
                FirebaseFirestore.getInstance().collection(TEAMS).document(team.name!!).set(team).addOnSuccessListener {
                    teamNameEditText.text.clear()
                    teamShortNameEditText.text.clear()
                    teamNameEditText.requestFocus()
                }
            }
        }
    }
}
