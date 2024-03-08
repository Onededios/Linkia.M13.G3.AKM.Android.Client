package com.linkiaM13G3.akmAndroidClient.Pages

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.linkiaM13G3.akmAndroidClient.R

class AdapterSavedList(private val credentialsList: List<SavedCredential>) :
    RecyclerView.Adapter<AdapterSavedList.SavedListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_apps, parent, false)
        return SavedListViewHolder(view)
    }

    override fun onBindViewHolder(holder: SavedListViewHolder, position: Int) {
        val credential = credentialsList[position]
        holder.bind(credential)
    }

    override fun getItemCount() = credentialsList.size

    class SavedListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userNameView: TextView = itemView.findViewById(R.id.tvUserId)
        private val appNameView: TextView = itemView.findViewById(R.id.tvAppname)
        private val iconView: ImageView = itemView.findViewById(R.id.ivAppIcon)

        fun bind(credential: SavedCredential) {
            userNameView.text = credential.userName
            appNameView.text = credential.appName
            // Set icon resource if you have it. If not, you might want to download it or get it in a different way.
            iconView.setImageResource(credential.icon)
        }
    }
}
