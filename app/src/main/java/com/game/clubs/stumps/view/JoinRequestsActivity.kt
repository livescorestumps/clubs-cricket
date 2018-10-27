package com.game.clubs.stumps.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.game.clubs.stumps.R
import com.game.clubs.stumps.adapters.JoinRequestsAdapter
import com.game.clubs.stumps.model.Player
import com.game.clubs.stumps.model.PlayerJoinTeam
import com.game.clubs.stumps.model.Team
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_join_requests.*

class JoinRequestsActivity : AppCompatActivity() {

    var adapter: JoinRequestsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_requests)
        var myId = ""
        if (FirebaseAuth.getInstance().currentUser != null) {
            myId = FirebaseAuth.getInstance().currentUser!!.uid
        }
        joinRequestsRecyclerView.layoutManager = LinearLayoutManager(this)
        FirebaseFirestore.getInstance().collection("Teams").whereEqualTo("admin", myId).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                var players = arrayListOf<PlayerJoinTeam>()
                var teams = arrayListOf<Team>()
                for (document in task.result!!.documents) {
                    teams.add(document.toObject(Team::class.java)!!)
                }
                for (team in teams) {
                    if (team.joinRequests == null)
                        continue
                    var isHeader = true
                    for (uid in team.joinRequests!!) {
                        FirebaseFirestore.getInstance().collection("Players").whereEqualTo("uid", uid).get().addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                for (docPlayer in task.result!!.documents) {
                                    var player = docPlayer.toObject(PlayerJoinTeam::class.java)!!
                                    if(isHeader) {
                                        player.isHeader = true
                                        isHeader = false
                                    }
                                    player.teamName = team.name!!
                                    players.add(player)
                                }
                                if (adapter == null) {
                                    adapter = JoinRequestsAdapter(players) { player, getPlayerDetails ->
                                        if (getPlayerDetails) {
                                            //TODO show player profile
                                        } else {
                                            FirebaseFirestore.getInstance().collection("Teams").document(player.teamName).update("players", FieldValue.arrayUnion(player.uid)).addOnCompleteListener {
                                                if (task.isSuccessful) {
                                                    FirebaseFirestore.getInstance().document("Teams/" + player.teamName).update("joinRequests", FieldValue.arrayRemove(player.uid)).addOnCompleteListener {
                                                        adapter!!.removePlayer(player)
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    joinRequestsRecyclerView.adapter = adapter
                                } else {
                                    adapter!!.notifyDataSetChanged()
                                }

                            }
                        }


                    }


                }


            } else {
                //TODO show error message
            }
        }
    }
}
