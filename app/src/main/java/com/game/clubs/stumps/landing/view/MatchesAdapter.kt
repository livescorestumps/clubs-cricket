package com.game.clubs.stumps.landing.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.game.clubs.stumps.R
import com.game.clubs.stumps.model.Matches
import kotlinx.android.synthetic.main.item_match_card.view.*

class MatchesAdapter(matches: MutableList<Matches>?) : RecyclerView.Adapter<MatchesAdapter.MatchViewHolder>() {

    var matchesList: MutableList<Matches>? = null

    init {
        this.matchesList = matches
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        return MatchViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_match_card, parent, false), parent.context)

    }

    override fun getItemCount(): Int {
        matchesList?.let { return matchesList!!.size }
        return 0
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bindData(matchesList!![position])
    }


    class MatchViewHolder(val view: View, val context: Context) : RecyclerView.ViewHolder(view) {

        fun bindData(matches: Matches) {
            setMatchName(matches)
            view.setOnClickListener { view -> clickedOnMatch(view, matches) }
        }

        private fun clickedOnMatch(view: View?, matches: Matches) {
            if (context is LandingActivity){
                context.navToMatchDetails()
            }
        }

        /**
         * Set Match name to text view
         */
        private fun setMatchName(matches: Matches) {
            view.matchNameTV.text = matches.name
        }

    }
}