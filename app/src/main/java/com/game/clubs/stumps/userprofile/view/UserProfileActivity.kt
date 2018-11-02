package com.game.clubs.stumps.userprofile.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import com.game.clubs.stumps.BaseActivity
import com.game.clubs.stumps.R
import com.game.clubs.stumps.landing.view.LandingActivity
import com.game.clubs.stumps.model.Player
import com.game.clubs.stumps.userprofile.viewmodel.UserProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_user_profile.*

class UserProfileActivity : BaseActivity() {

    var viewModel: UserProfileViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        viewModel = ViewModelProviders.of(this).get(UserProfileViewModel::class.java)
        initializeListeners()
        initializeObservers()
        viewModel!!.fetchUserProfile()
    }

    private fun initializeObservers() {
        viewModel!!.userListLiveData!!.observe(this, Observer { it -> handleUserProfile(it) })
    }

    private fun handleUserProfile(player: Player?) {
        if (player != null) {
            firstNameValue.text = player.fName
            lastNameValue.text = player.lName
        }
    }

    private fun initializeListeners() {
        logoutButton.setOnClickListener {
            clickedOnLogout()
        }
    }

    private fun clickedOnLogout() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, LandingActivity::class.java))
        finish()
    }


}