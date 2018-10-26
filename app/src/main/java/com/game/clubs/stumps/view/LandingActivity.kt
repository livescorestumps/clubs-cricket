package com.game.clubs.stumps.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.game.clubs.stumps.BaseActivity
import com.game.clubs.stumps.R
import com.game.clubs.stumps.landing.viewmodel.LandingViewModel

class LandingActivity : BaseActivity() {

    var viewModel: LandingViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
        viewModel = ViewModelProviders.of(this).get(LandingViewModel::class.java)
        viewModel!!.getListOfMatches()
    }

}