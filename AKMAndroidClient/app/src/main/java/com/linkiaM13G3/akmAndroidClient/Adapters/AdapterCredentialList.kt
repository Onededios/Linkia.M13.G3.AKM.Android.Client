package com.linkiaM13G3.akmAndroidClient.Adapters;

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.chip.ChipGroup
import com.linkiaM13G3.akmAndroidClient.Clients.PasswordClient
import com.linkiaM13G3.akmAndroidClient.Dialogs.DialogModifyCredential
import com.linkiaM13G3.akmAndroidClient.Entities.App
import com.linkiaM13G3.akmAndroidClient.Entities.Password
import com.linkiaM13G3.akmAndroidClient.Entities.Tag
import com.linkiaM13G3.akmAndroidClient.Helpers.Parsers
import com.linkiaM13G3.akmAndroidClient.Helpers.Updaters
import com.linkiaM13G3.akmAndroidClient.R

class AdapterCredentialList(
        private val context: Context?,
        private val credentialList: List<Password>?,
        private val appList: List<App>,
        private val tagList: List<Tag>?,
        private val api: PasswordClient
) : RecyclerView.Adapter<AdapterCredentialList.ViewHolder>() {
        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
                val textAlias: TextView = itemView.findViewById(R.id.card_password_alias)
                val textDate: TextView = itemView.findViewById(R.id.card_password_date)
                val textUsername: TextView = itemView.findViewById(R.id.card_password_username)
                val textPassword: TextView = itemView.findViewById(R.id.card_password_password)
                val textDescription: TextView = itemView.findViewById(R.id.card_password_description)
                val imageApp: ImageView = itemView.findViewById(R.id.card_password_image)
                val chipGroup: ChipGroup = itemView.findViewById(R.id.card_chip_group)

                init {
                        itemView.setOnClickListener(this)
                }

                override fun onClick(v: View) {
                        context?.let { context ->
                                val password = credentialList?.get(adapterPosition)
                                password?.let {
                                        val dialog = DialogModifyCredential(context, it, appList, tagList, api)
                                        dialog.show((context as AppCompatActivity).supportFragmentManager, "DialogModifyCredential")
                                }
                        }
                }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                val inflater: LayoutInflater = LayoutInflater.from(context)
                val view = inflater.inflate(R.layout.item_card_password, parent, false)
                return ViewHolder(view)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                val credential = credentialList?.get(position)
                val date = credential?.date_expiration
                val formattedDate = if (date != null) Parsers.parseISODateToYMD(date) else "N/A"
                val pass = credential?.password
                val formattedPass = if (pass != null) Parsers.parsePasswordSecure(pass) else "N/A"

                holder.textAlias.text = credential?.alias ?: "N/A"
                holder.textPassword.text =  formattedPass
                holder.textDate.text = formattedDate
                holder.textUsername.text = credential?.username ?: "N/A"
                holder.textDescription.text = credential?.description ?: "N/A"

                credential?.tags?.let { tags -> Updaters.updateChipGroup(holder.chipGroup, tags, context!!)}

                credential?.app?.icon?.let { iconUrl ->
                        Glide.with(holder.itemView.context)
                                .load(iconUrl)
                                .into(holder.imageApp)
                }
        }

        override fun getItemCount(): Int = credentialList?.size ?: 0
}
