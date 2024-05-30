
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.linkiaM13G3.akmAndroidClient.Entities.App
import com.linkiaM13G3.akmAndroidClient.R

class AdapterAppList(
    private val context: Context?,
    private val appList: List<App?>?,
    private val onAppSelected: (App?)  -> Unit
) : RecyclerView.Adapter<AdapterAppList.ViewHolder>() {
    private var selectedItem = 0
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val textViewName: TextView = itemView.findViewById(R.id.tvAppName)
        val imageViewLogo: ImageView = itemView.findViewById(R.id.ivAppIcon)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val selectedApp = appList!![position]
                onAppSelected(selectedApp)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.item_app, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentPosition = holder.adapterPosition

        val app = appList?.get(currentPosition)
        holder.textViewName.text = app?.name

        app?.icon?.let { iconUrl ->
            Glide.with(holder.itemView.context)
                    .load(iconUrl)
                    .placeholder(R.drawable.logo_default)
                    .error(R.drawable.logo_default)
                    .into(holder.imageViewLogo)
        }

        holder.itemView.isSelected = currentPosition == selectedItem
    }

    override fun getItemCount(): Int = appList?.size ?: 0

    fun getSelectedItem(): App? {
        return appList?.get(selectedItem)
    }
}
