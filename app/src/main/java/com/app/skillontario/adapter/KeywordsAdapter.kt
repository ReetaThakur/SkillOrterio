package com.app.skillontario.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.skillontario.callbacks.KeywordSelected
import com.app.skillontario.home.HomeFilterActivity
import com.app.skillorterio.R
import com.app.skillorterio.databinding.KeyWordsLayoutBinding
import java.util.*


class KeywordsAdapter(private var context: Context, callback: HomeFilterActivity, list: ArrayList<String>) :
        RecyclerView.Adapter<KeywordsAdapter.ViewHolder>() {

    private var list: ArrayList<String>? = list
    private var callback: KeywordSelected? = callback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: KeyWordsLayoutBinding = DataBindingUtil.inflate(inflater, R.layout.key_words_layout, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.tvKeywords.text = list!![position]
//        if (position != 0){
        holder.binding.tvKeywords.typeface = Typeface.DEFAULT
        holder.binding.tvKeywords.background = ContextCompat.getDrawable(context, R.drawable.rounded_corner_with_border)
       // holder.binding.tvKeywords.backgroundTintList = ContextCompat.getColorStateList(context, R.color.black)
        holder.binding.tvKeywords.setTextColor(ContextCompat.getColor(context, R.color.black))
        holder.binding.tvKeywords.setPadding(context.resources.getDimensionPixelSize(R.dimen._11sdp),
                context.resources.getDimensionPixelSize(R.dimen._5sdp),
                context.resources.getDimensionPixelSize(R.dimen._11sdp),
                context.resources.getDimensionPixelSize(R.dimen._5sdp))
//        }else{
//            holder.binding.tvKeywords.setPadding(context.resources.getDimensionPixelSize(R.dimen._5sdp), context.resources.getDimensionPixelSize(R.dimen._5sdp), 0, context.resources.getDimensionPixelSize(R.dimen._5sdp))
//            holder.binding.tvKeywords.typeface = Typeface.DEFAULT_BOLD
//        }

//        if (position !=0){
        holder.binding.tvKeywords.setOnClickListener {
            callback!!.onTextClick(list?.get(position))
        }
//        }
    }

    override fun getItemCount(): Int {
        return list!!.size
    }


    class ViewHolder(itemView: KeyWordsLayoutBinding) : RecyclerView.ViewHolder(itemView.root) {
        var binding: KeyWordsLayoutBinding = itemView
    }
}

