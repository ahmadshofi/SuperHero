package com.ahmad.pokemon.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmad.pokemon.Common.Common
import com.ahmad.pokemon.Interface.ItemClickListener
import com.ahmad.pokemon.Model.DataX
import com.ahmad.pokemon.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.hero_list_item.view.*

class HeroListAdapter(internal var context: Context,
                      internal var heroList:List<DataX>):RecyclerView.Adapter<HeroListAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.hero_list_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return heroList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context).load(heroList[position].imageurl).into(holder.img_hero)
        holder.txt_hero.text = heroList[position].name

        holder.setItemClickListener(object : ItemClickListener{
            override fun onClick(view: View, position: Int) {
                //Toast.makeText(context,"Clicked at Hero: "+heroList[position].name,Toast.LENGTH_SHORT).show()
                LocalBroadcastManager.getInstance(context)
                    .sendBroadcast(Intent(Common.KEY_ENABLE_HOME).putExtra("num",heroList[position].name))
            }
        })
    }

    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        internal var img_hero:ImageView
        internal var txt_hero:TextView

        internal var iItemClickListener:ItemClickListener?=null

        fun setItemClickListener(itemClickListener: ItemClickListener)
        {
            this.iItemClickListener = itemClickListener
        }

        init {
            img_hero = itemView.findViewById(R.id.hero_image) as ImageView
            txt_hero = itemView.findViewById(R.id.hero_name) as TextView

            itemView.setOnClickListener { view -> iItemClickListener!!.onClick(view,adapterPosition) }
        }

    }

}