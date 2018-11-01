package com.game.clubs.stumps.matchdetails.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.game.clubs.stumps.BaseActivity
import com.game.clubs.stumps.R
import com.game.clubs.stumps.matchdetails.viewmodel.MatchDetailsViewModel

class MatchDetailsActivity : BaseActivity() {

    val MATCHID = "MATCH_ID"
    var viewModel: MatchDetailsViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_details)
        viewModel = ViewModelProviders.of(this).get(MatchDetailsViewModel::class.java)
        if (intent.hasExtra(MATCHID)) {
            intent.getStringExtra(MATCHID)
        }
    }
}