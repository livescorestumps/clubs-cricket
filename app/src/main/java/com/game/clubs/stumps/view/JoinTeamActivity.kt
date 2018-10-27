package com.game.clubs.stumps.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.game.clubs.stumps.R
import com.game.clubs.stumps.adapters.TeamNamesAdapter
import com.game.clubs.stumps.model.PlayerTeam
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_join_team.*

/**
 * Created by Venkareddy on 10/16/2018.
 */
class JoinTeamActivity : AppCompatActivity() {
    companion object {
        const val TEAMS = "Teams"
    }

    var teams = arrayListOf<PlayerTeam>()
    lateinit var teamNamesAdapter: TeamNamesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_team)
        var myId = ""
        if (FirebaseAuth.getInstance().currentUser != null) {
            myId = FirebaseAuth.getInstance().currentUser!!.uid
        }
        teamNamesRecyclerView.layoutManager = LinearLayoutManager(this)
        FirebaseFirestore.getInstance().collection(TEAMS).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (document in task.result!!) {
                    var team = document.toObject(PlayerTeam::class.java)
                    if (team.players == null || !team.players!!.contains(myId)) {

                        if (team.joinRequests != null && team.joinRequests!!.contains(myId))
                            team.isRequested = true
                        teams.add(team)

                    }
                }
                teamNamesAdapter = TeamNamesAdapter(teams) { team ->
                    FirebaseFirestore.getInstance().collection(TEAMS).document(team.name!!).update("joinRequests", FieldValue.arrayUnion(myId)).addOnCompleteListener {
                        if (task.isSuccessful) {
                            teamNamesAdapter.updateTeamAsRequestPending(team)
                        }
                    }
                }
                teamNamesRecyclerView.adapter = teamNamesAdapter
            }
        }

    }
}
