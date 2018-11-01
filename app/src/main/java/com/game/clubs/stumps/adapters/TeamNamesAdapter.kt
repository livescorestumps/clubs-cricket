package com.game.clubs.stumps.adapters

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.game.clubs.stumps.R
import com.game.clubs.stumps.databinding.TeamNameViewItemBinding
import com.game.clubs.stumps.model.PlayerTeam

/**
 * Created by Venkareddy on 10/16/2018.
 */
class TeamNamesAdapter(var teamNames: ArrayList<PlayerTeam>, private val listener : (PlayerTeam) -> Unit) : RecyclerView.Adapter<TeamNamesAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return if (teamNames == null) 0 else teamNames.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(teamNames[position], listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.team_name_view_item, parent, false))
    }

    fun updateTeamAsRequestPending(team: PlayerTeam) {
        var indx = 0;
        for(t in teamNames){
            if(t.name.equals(team.name)){
                t.isRequested = true
                notifyItemChanged(indx)
                break
            }
            indx++
        }
    }

    class ViewHolder(private var binding: TeamNameViewItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(team : PlayerTeam, listener : (PlayerTeam) -> Unit) = with(binding){
            binding.team = team
            binding.buttonJoin.setOnClickListener {
                listener(team)
            }
        }
    }

}