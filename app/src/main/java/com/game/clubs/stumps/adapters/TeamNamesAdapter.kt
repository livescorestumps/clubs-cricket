package com.game.clubs.stumps.adapters

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.game.clubs.stumps.R
import com.game.clubs.stumps.databinding.TeamNameViewItemBinding
import com.game.clubs.stumps.model.Team

/**
 * Created by Venkareddy on 10/16/2018.
 */
class TeamNamesAdapter(var teamNames: ArrayList<Team>, private val listener : (Team) -> Unit) : RecyclerView.Adapter<TeamNamesAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return if (teamNames == null) 0 else teamNames.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(teamNames[position], listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.team_name_view_item, parent, false))
    }

    class ViewHolder(private var binding: TeamNameViewItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(team : Team, listener : (Team) -> Unit) = with(binding){
            binding.team = team
            binding.buttonJoin.setOnClickListener {
                listener(team)
            }
        }
    }

}