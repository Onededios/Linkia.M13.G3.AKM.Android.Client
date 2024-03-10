package com.linkiaM13G3.akmAndroidClient.Pages

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.linkiaM13G3.akmAndroidClient.R


class AdapterSavedList(
    private val credentialsList: MutableList<Credential>,
    private val onItemClicked: (Credential) -> Unit
) : RecyclerView.Adapter<AdapterSavedList.SavedListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_saved_pwd, parent, false)
        return SavedListViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(holder: SavedListViewHolder, position: Int) {
        val credential = credentialsList[position]
        holder.bind(credential)
    }

    override fun getItemCount() = credentialsList.size

    class SavedListViewHolder(itemView: View, val onItemClicked: (Credential) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val userNameView: TextView = itemView.findViewById(R.id.tvUserId)
        private val appNameView: TextView = itemView.findViewById(R.id.tvAppName)
        private val iconView: ImageView = itemView.findViewById(R.id.ivAppIcon)

        fun bind(credential: Credential) {
            userNameView.text = credential.userName


            itemView.setOnClickListener {
                onItemClicked(credential)
            }
        }
    }
}
