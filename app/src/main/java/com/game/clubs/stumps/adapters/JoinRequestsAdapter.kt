package com.game.clubs.stumps.adapters

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.game.clubs.stumps.R
import com.game.clubs.stumps.databinding.ItemViewJoinRequestBinding
import com.game.clubs.stumps.model.Player

class JoinRequestsAdapter(var players: ArrayList<Player>, private val listener: (Player, Boolean) -> Unit) : RecyclerView.Adapter<JoinRequestsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_view_join_request, parent, false))
    }

    override fun getItemCount(): Int {
        return if (players == null) 0 else players.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(players[position], listener)
    }

    class ViewHolder(private var binding: ItemViewJoinRequestBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(player: Player, listener: (Player, Boolean) -> Unit) = with(binding) {
            binding.player = player
            binding.root.setOnClickListener {
                listener(player, true)
            }

            binding.buttonAccept.setOnClickListener {
                listener(player, false)
            }

        }
    }
}