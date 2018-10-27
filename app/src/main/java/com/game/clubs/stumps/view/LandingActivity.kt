package com.game.clubs.stumps.view

import android.content.Intent
import android.os.Bundle
import com.game.clubs.stumps.BaseActivity
import com.game.clubs.stumps.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_landing.*

class LandingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
        if (FirebaseAuth.getInstance().currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        buttonJoinTeam.setOnClickListener {
            startActivity(Intent(this, JoinTeamActivity::class.java))
        }

        buttonTeamRequests.setOnClickListener {
            startActivity(Intent(this, JoinRequestsActivity::class.java))
        }

        buttonNewTeam.setOnClickListener {
            startActivity(Intent(this, NewTeamActivity::class.java))
        }

        buttonLogOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

}