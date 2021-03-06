package com.game.clubs.stumps.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.game.clubs.stumps.BaseActivity
import com.game.clubs.stumps.R
import com.game.clubs.stumps.landing.view.LandingActivity
import com.game.clubs.stumps.viewmodel.PlayerProfileViewModel
import kotlinx.android.synthetic.main.activity_player_profile.*

class PlayerProfileActivity : BaseActivity() {

    var viewModel: PlayerProfileViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_profile)
        viewModel = ViewModelProviders.of(this).get(PlayerProfileViewModel::class.java)
        initiateListeners()
    }

    private fun initiateListeners() {
        viewModel!!.getCheckUserLiveData().observe(this, Observer { t ->
            Toast.makeText(this, "Is it successfull:$t", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LandingActivity::class.java))
            finish()
        })

        btn_submit.setOnClickListener {
            //viewModel?.checkIfUserExits(firstNameValue.text.toString())
            viewModel!!.updatePlayerProfile(firstNameValue.text.toString(), lastNameValue.text.toString())
        }
    }
}