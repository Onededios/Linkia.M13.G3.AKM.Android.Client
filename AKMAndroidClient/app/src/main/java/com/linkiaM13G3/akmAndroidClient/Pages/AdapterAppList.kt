package com.linkiaM13G3.akmAndroidClient.Pages

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.linkiaM13G3.akmAndroidClient.R

class AdapterAppList(



    val fullAppsList: List<AppsList>,
    val listener: PageAppsActivity,
    var appsList: List<AppsList> = fullAppsList.toList(),
) : RecyclerView.Adapter<AdapterAppList.AppsListHolder>() {

    public data class AppsList(
        val appName: String,
        val appsIcon :Int,

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
        val appItem = appsList[position]
        holder.textViewAppName.text = appItem.appName
        holder.imageViewLogo.setImageResource(appItem.appsIcon)
        holder.itemView.setOnClickListener {
            listener.onAppClick(appItem.appName)
        }
    }

    override fun getItemCount(): Int = appsList.size

    fun filter(queryText: String) {
        appsList = if (queryText.isEmpty()) {
            fullAppsList
        } else {
            fullAppsList.filter {
                it.appName.contains(queryText, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }

    interface OnAppClickListener {
        fun onAppClick(appName: String)
    }
}



