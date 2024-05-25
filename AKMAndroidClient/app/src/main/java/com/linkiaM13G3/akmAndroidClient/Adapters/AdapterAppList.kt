import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.linkiaM13G3.akmAndroidClient.Entities.App
import com.linkiaM13G3.akmAndroidClient.R

class AdapterAppList(
    private val context: Context?,
    private val appList: List<App>?,
) : RecyclerView.Adapter<AdapterAppList.ViewHolder>() {
    private var selectedItem = 0
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val textViewName: TextView = itemView.findViewById(R.id.tvAppName)
        val imageViewLogo: ImageView = itemView.findViewById(R.id.ivAppIcon)
        val radioButton: RadioButton = itemView.findViewById(R.id.radioButton)

        init {
            itemView.setOnClickListener(this)
            radioButton.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            // Get the clicked item position
            val position = adapterPosition

            // Update the selected item
            selectedItem = position

            // Notify adapter about item selection change
            notifyDataSetChanged()

            // Do something with the selected item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.item_app, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentPosition = holder.adapterPosition
        holder.radioButton.isChecked = currentPosition == selectedItem

        holder.radioButton.setOnClickListener {
            selectedItem = currentPosition
            notifyDataSetChanged() // Notify adapter of the change
        }

        val app = appList?.get(currentPosition)
        holder.textViewName.text = app?.name
        holder.imageViewLogo.setImageResource(R.drawable.logo_google)

        app?.icon?.let { iconUrl ->
            context?.let {
                Glide.with(it)
                    .load(iconUrl)
                    .into(holder.imageViewLogo)
            }
        }

        holder.itemView.isSelected = currentPosition == selectedItem
    }

    override fun getItemCount(): Int = appList?.size ?: 0
}
