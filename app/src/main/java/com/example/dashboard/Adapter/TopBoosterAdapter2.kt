package com.example.dashboard.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.example.dashboard.Activity.DetailActivity
import com.example.dashboard.Activity.GlideApp
import com.example.dashboard.Domain.BoostersModel
import com.example.dashboard.databinding.ViewholderTopBooster2Binding
import com.example.dashboard.databinding.ViewholderTopBoosterBinding

class TopBoosterAdapter2(val items: MutableList<BoostersModel>): RecyclerView.Adapter<TopBoosterAdapter2.Viewholder>() {
    private var context: Context?=null


    class Viewholder(val binding: ViewholderTopBooster2Binding):
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopBoosterAdapter2.Viewholder {
        context = parent.context
        val binding =
            ViewholderTopBooster2Binding.inflate(LayoutInflater.from(context), parent, false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: TopBoosterAdapter2.Viewholder, position: Int) {
            holder.binding.nameTxt.text = items[position].Name
            holder.binding.specialTxt.text = items[position].Special
            holder.binding.scoreTxt.text = items[position].Rating.toString()
            holder.binding.ratingBar.rating=items[position].Rating.toFloat()
            holder.binding.scoreTxt.text=items[position].Rating.toString()
            holder.binding.certTxt.text="E-Sports Athelete"

        Glide.with(holder.itemView.context)
                .load(items[position].Picture)
                .apply { RequestOptions().transform(CenterCrop()) }
                .into(holder.binding.img)

        holder.binding.requestBtn.setOnClickListener {
            val intent=Intent(context, DetailActivity::class.java)
            intent.putExtra("object",items[position])
            context?.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size
}