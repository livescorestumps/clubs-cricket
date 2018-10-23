package com.game.clubs.stumps.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.game.clubs.stumps.R
import com.game.clubs.stumps.adapters.TeamNamesAdapter
import com.game.clubs.stumps.model.Team
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_join_team.*
import java.util.*

/**
 * Created by Venkareddy on 10/16/2018.
 */
class JoinTeamActivity : AppCompatActivity() {
    companion object {
        const val TEAMS = "Teams"
    }

    var teams = arrayListOf<Team>()
    lateinit var teamNamesAdapter: TeamNamesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_team)
        var myId = ""
        if (FirebaseAuth.getInstance().currentUser != null) {
            myId = FirebaseAuth.getInstance().currentUser!!.providerId
        }
        teamNamesRecyclerView.layoutManager = LinearLayoutManager(this)
        FirebaseFirestore.getInstance().collection(TEAMS).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (document in task.result!!) {
                    teams.add(document.toObject(Team::class.java))
                }
                teamNamesAdapter = TeamNamesAdapter(teams) { team ->
                    val player = HashMap<String, Any>()
                    player.put("uid", if (myId.isEmpty()) "playerUid" else myId)

                    player.put("date", FieldValue.serverTimestamp())
                    FirebaseFirestore.getInstance().collection(TEAMS).document(team.name!!).collection("joinRequests").document(player.get("uid") as String).update(player).addOnCompleteListener {

                    }
                }
                teamNamesRecyclerView.adapter = teamNamesAdapter
            }
        }

    }
}
