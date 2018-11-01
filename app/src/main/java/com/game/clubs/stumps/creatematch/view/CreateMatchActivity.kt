package com.game.clubs.stumps.creatematch.view

import android.content.Intent
import android.os.Bundle
import com.game.clubs.stumps.BaseActivity
import com.game.clubs.stumps.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_landing.*

class CreateMatchActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_match)
    }
}