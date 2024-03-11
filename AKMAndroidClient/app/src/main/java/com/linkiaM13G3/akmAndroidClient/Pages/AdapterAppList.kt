package com.linkiaM13G3.akmAndroidClient.Pages

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.linkiaM13G3.akmAndroidClient.R
import kotlinx.coroutines.CoroutineScope

class AdapterAppList(
    private val fullAppsList: List<AppsList>,
    private val listener: OnAppClickListener
) : RecyclerView.Adapter<AdapterAppList.AppsListHolder>() {
    private var currentList: List<AppsList> = fullAppsList
    data class AppsList(
        val name: String,
        val icon: Int,
        val id: String
    )


    class AppsListHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewAppName: TextView = view.findViewById(R.id.tvAppname)
        val imageViewLogo: ImageView = view.findViewById(R.id.ivAppIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppsListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_apps, parent, false)
        return AppsListHolder(view)
    }

    override fun onBindViewHolder(holder: AppsListHolder, position: Int) {
        val appItem = fullAppsList[position]
        holder.textViewAppName.text = appItem.name
        holder.imageViewLogo.setImageResource(appItem.icon) //

        holder.itemView.setOnClickListener {
            listener.onAppClick(appItem.name, appItem.id)
        }
    }
    fun filter(queryText: String) {
        currentList = if (queryText.isEmpty()) {
            fullAppsList
        } else {
            fullAppsList.filter {
                it.name.contains(queryText, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int = currentList.size

    interface OnAppClickListener {
        fun onAppClick(appName: String, appId: String)
    }
}
