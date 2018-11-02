package com.game.clubs.stumps.landing.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.game.clubs.stumps.BaseActivity
import com.game.clubs.stumps.R
import com.game.clubs.stumps.creatematch.view.CreateMatchActivity
import com.game.clubs.stumps.landing.viewmodel.LandingViewModel
import com.game.clubs.stumps.matchdetails.view.MatchDetailsActivity
import com.game.clubs.stumps.model.Matches
import com.game.clubs.stumps.userprofile.view.UserProfileActivity
import com.game.clubs.stumps.view.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_landing.*

class LandingActivity : BaseActivity() {

    var viewModel: LandingViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
        viewModel = ViewModelProviders.of(this).get(LandingViewModel::class.java)
        //Returns Matches List to Observers
        viewModel!!.getListOfMatches()
        initilizeObservers()
        initializeClickListeners()
//        if (FirebaseAuth.getInstance().currentUser == null) {
//            startActivity(Intent(this, LoginActivity::class.java))
//            finish()
//        }

        buttonJoinTeam.setOnClickListener {
            startActivity(Intent(this, JoinTeamActivity::class.java))
        }

        buttonTeamRequests.setOnClickListener {
            startActivity(Intent(this, JoinRequestsActivity::class.java))
        }

        buttonNewTeam.setOnClickListener {
            startActivity(Intent(this, NewTeamActivity::class.java))
        }

    }

    /**
     * Clicked On Profile
     */
    private fun clickedOnProfile() {
        if (FirebaseAuth.getInstance().currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            startActivity(Intent(this, UserProfileActivity::class.java))
        }
    }

    private fun initializeClickListeners() {
        createMatch.setOnClickListener {
            clickedOnCreateMatch()
        }
        profileIcon.setOnClickListener {
            clickedOnProfile()
        }
    }

    private fun clickedOnCreateMatch() {
        val intent = Intent(this, CreateMatchActivity::class.java)
        startActivityForResult(intent, 2000)
    }

    private fun initilizeObservers() {
        viewModel!!.getMatchesLiveData()!!.observe(this, Observer { it -> loadRecyclerView(it) })
    }

    private fun loadRecyclerView(matches: MutableList<Matches>?) {
        if (matches != null) {
//            errorScenario.visibility = View.GONE
            rvMatchList.layoutManager = LinearLayoutManager(this)
            rvMatchList.adapter = MatchesAdapter(matches)
        } else {
//            errorScenario.visibility = View.VISIBLE
//            errorScenario.text = "No Matches found!!"
        }
    }

    fun navToMatchDetails() {
        val intent = Intent(this, MatchDetailsActivity::class.java)
        startActivity(intent)
    }

}