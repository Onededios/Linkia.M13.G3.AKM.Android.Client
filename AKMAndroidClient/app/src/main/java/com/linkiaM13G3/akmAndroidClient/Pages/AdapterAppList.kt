package com.linkiaM13G3.akmAndroidClient.Pages

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.linkiaM13G3.akmAndroidClient.Entities.App
import com.linkiaM13G3.akmAndroidClient.R

class AdapterAppList(
    private val fullAppsList: List<App>?,
    private val listener: PageAppsActivity,
    private var appsList: List<App>? = fullAppsList?.toList(),
) : RecyclerView.Adapter<AdapterAppList.AppsListHolder>() {

    data class AppsList(
        val appName: String,
        val appsIcon: Int,
    )

    class AppsListHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewAppName: TextView = view.findViewById(R.id.tvAppname)
        val imageViewLogo: ImageView = view.findViewById(R.id.ivAppIcon)
    }

    interface OnAppClickListener {
        fun onAppClick(appName: String?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppsListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_apps, parent, false)
        return AppsListHolder(view)
    }

    override fun onBindViewHolder(holder: AppsListHolder, position: Int) {
        val appItem = appsList?.get(position)
        holder.textViewAppName.text = appItem?.name
        appItem?.icon?.let { holder.imageViewLogo.setImageResource(it) }
        holder.itemView.setOnClickListener {
            listener.onAppClick(appItem?.name)
        }
    }

    override fun getItemCount(): Int {
        return appsList?.size ?: 0
    }

    fun filter(queryText: String) {
        appsList = if (queryText.isEmpty()) {
            fullAppsList
        } else {
            fullAppsList?.filter {
                it.name.contains(queryText, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }
}

private fun ImageView.setImageResource(icon: String?) {
    setImageResource(R.drawable.logo_google)
}

