package com.example.pixabyapp


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.util.*

class PixabyAdapter(
    private val mContext: Context,
    private val mExampleList: ArrayList<PixabyItem>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<PixabyAdapter.ExampleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val v = LayoutInflater.from(mContext).inflate(R.layout.example_item, parent, false)
        return ExampleViewHolder(v)
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = mExampleList[position]
        val imageUrl = currentItem.imageUrl
        val creatorName = currentItem.creator
        val comentCount = currentItem.comments
        holder.mTextViewCreator.text = creatorName
        holder.mTextViewLikes.text = "Coments: $comentCount"
        Picasso.get().load(imageUrl).fit().centerInside().into(holder.mImageView)
    }

    override fun getItemCount(): Int {
        return mExampleList.size
    }

    inner class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var mImageView: ImageView
        var mTextViewCreator: TextView
        var mTextViewLikes: TextView

        init {
            mImageView = itemView.findViewById(R.id.image_view)
            mTextViewCreator = itemView.findViewById(R.id.text_view_creator)
            mTextViewLikes = itemView.findViewById(R.id.text_view_coments)
            itemView.setOnClickListener { listener.onItemClick(position = position) }
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}