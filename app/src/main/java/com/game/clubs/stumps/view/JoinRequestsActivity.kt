package com.game.clubs.stumps.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.game.clubs.stumps.R
import com.game.clubs.stumps.adapters.JoinRequestsAdapter
import com.game.clubs.stumps.model.Player
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_join_requests.*

class JoinRequestsActivity : AppCompatActivity() {

    var adapter: JoinRequestsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_requests)
        joinRequestsRecyclerView.layoutManager = LinearLayoutManager(this)
        FirebaseFirestore.getInstance().collection("Teams/Riders/joinRequests").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                var players = arrayListOf<Player>()
                var playerIds = arrayListOf<String>()
                for (document in task.result!!) {
                    playerIds.add(document["player"] as String)
                }
                for (uid in playerIds) {
                    FirebaseFirestore.getInstance().collection("Players").whereEqualTo("uid", uid).get().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            for (docPlayer in task.result!!.documents) {
                                players.add(docPlayer.toObject(Player::class.java)!!)
                            }
                            if (adapter == null) {
                                adapter = JoinRequestsAdapter(players) { player, getPlayerDetails ->
                                    if (getPlayerDetails) {
                                        //TODO show player profile
                                    } else {
                                        FirebaseFirestore.getInstance().collection("Teams").document("Riders").update("players", FieldValue.arrayUnion(player.uid)).addOnCompleteListener {
                                            if(task.isSuccessful){
                                                FirebaseFirestore.getInstance().document("Teams/Riders/joinRequests/"+player.uid).delete()
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


            } else {
                //TODO show error message
            }
        }
    }
}
