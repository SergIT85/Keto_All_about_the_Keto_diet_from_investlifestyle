package ru.investlifestyle.app.ui.subject.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.next_arrow_button.view.iv_next_arrow
import kotlinx.android.synthetic.main.next_arrow_button.view.tv_next_theme
import ru.investlifestyle.app.R
import ru.investlifestyle.app.databinding.NextArrowButtonBinding

data class CategoryId(
    val categoryId: Int
)
class FooterAdapter(private val context: Context) :
    ListAdapter<CategoryId, FooterAdapter.VH>(object :
            DiffUtil.ItemCallback<CategoryId>() {
            override fun areItemsTheSame(oldItem: CategoryId, newItem: CategoryId) = true
            override fun areContentsTheSame(oldItem: CategoryId, newItem: CategoryId) =
                oldItem == newItem
        }) {
    inner class VH(binding: NextArrowButtonBinding) : RecyclerView.ViewHolder(binding.root)
    var onPostClickListener: ((CategoryId) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = NextArrowButtonBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        if (item != null)
            with(holder.itemView) {

                tv_next_theme.text = context.getText(R.string.see_all)
                iv_next_arrow.setImageResource(R.drawable.baseline_arrow_forward_ios_24)
                rootView.setOnClickListener { onPostClickListener?.invoke(item) }
            }
    }
}