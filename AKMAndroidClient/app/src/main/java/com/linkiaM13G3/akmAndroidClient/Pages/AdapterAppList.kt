package com.linkiaM13G3.akmAndroidClient.Pages

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.linkiaM13G3.akmAndroidClient.R

public class AdapterAppList(val appslist:List<AppsList>, val listener: PageAppsActivity):RecyclerView.Adapter<AdapterAppList.AppsListHolder>(){


    //crear clase fuera del adaptador
    public data class AppsList (
        val appnName: String,
        val logoEmail :String,
        val arrowIcon :String,
        val userName :String)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppsListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_apps, parent, false)
        return AppsListHolder(view)
    }

    override fun getItemCount(): Int = appslist.size



    override fun onBindViewHolder(holder: AppsListHolder, position: Int) {
        val appItem = appslist[position]
        holder.textViewAppName.text = appItem.appnName

        holder.itemView.setOnClickListener {
            listener.onAppClick(appItem.appnName)
        }

    }


    class AppsListHolder(val view :View):RecyclerView.ViewHolder(view){

        val textViewAppName: TextView = view.findViewById(R.id.tvAppname)
        val imageViewLogo: ImageView = view.findViewById(R.id.ivAppIcon)


    }

    interface OnAppClickListener {
        fun onAppClick(appName: String)
    }

}
