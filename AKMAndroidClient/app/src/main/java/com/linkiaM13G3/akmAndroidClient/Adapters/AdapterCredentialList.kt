package com.linkiaM13G3.akmAndroidClient.Adapters;

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.linkiaM13G3.akmAndroidClient.Entities.Password
import com.linkiaM13G3.akmAndroidClient.Helpers.Parsers
import com.linkiaM13G3.akmAndroidClient.R

class AdapterCredentialList(
        private val context: Context?,
        private val credentialList: List<Password>?,
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
                        val position = adapterPosition
                        notifyDataSetChanged()
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

                holder.textAlias.text = credential?.alias ?: "N/A"
                holder.textPassword.text = credential?.password ?: "N/A"
                holder.textDate.text = formattedDate
                holder.textUsername.text = credential?.username ?: "N/A"
                holder.textDescription.text = credential?.description ?: "N/A"

                credential?.tags?.let { tags ->
                        holder.chipGroup.removeAllViews()
                        for (tag in tags) {
                                val chip = Chip(holder.itemView.context)
                                chip.text = tag
                                chip.layoutParams = ViewGroup.LayoutParams(
                                        ViewGroup.LayoutParams.WRAP_CONTENT,
                                        ViewGroup.LayoutParams.MATCH_PARENT
                                )
                                chip.isClickable = false
                                chip.setChipBackgroundColorResource(R.color.grey_700)
                                chip.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
                                holder.chipGroup.addView(chip)
                        }
                }

                credential?.app?.icon?.let { iconUrl ->
                        Glide.with(holder.itemView.context)
                                .load(iconUrl)
                                .into(holder.imageApp)
                }
        }

        override fun getItemCount(): Int = credentialList?.size ?: 0
}
