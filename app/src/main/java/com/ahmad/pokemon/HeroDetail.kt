package com.ahmad.pokemon


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ahmad.pokemon.Common.Common
import com.ahmad.pokemon.Model.DataX
import com.bumptech.glide.Glide

/**
 * A simple [Fragment] subclass.
 */
class HeroDetail : Fragment() {

    internal lateinit var hero_img:ImageView
    internal lateinit var hero_name:TextView
    internal lateinit var hero_realname:TextView
    internal lateinit var hero_created:TextView
    internal lateinit var hero_bio:TextView
    internal lateinit var hero_peran:TextView
    internal lateinit var hero_pembuat:TextView
    internal lateinit var hero_team:TextView

    companion object{
        internal var instance:HeroDetail?=null

        fun getInstance():HeroDetail{
            if (instance == null)
                instance = HeroDetail()
            return instance!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val itemView = inflater.inflate(R.layout.fragment_hero_detail, container, false)

        val hero = Common.findHeroByNum(arguments!!.getString("num"))

        hero_img = itemView.findViewById(R.id.image_hero) as ImageView
        hero_name = itemView.findViewById(R.id.name) as TextView
        hero_realname = itemView.findViewById(R.id.realname) as TextView
        hero_team = itemView.findViewById(R.id.team) as TextView
        hero_created = itemView.findViewById(R.id.created) as TextView
        hero_pembuat = itemView.findViewById(R.id.penerbit) as TextView
        hero_peran = itemView.findViewById(R.id.firstappearance) as TextView
        hero_bio = itemView.findViewById(R.id.bio) as TextView


        setDetailHero(hero)
        return itemView
    }

    private fun setDetailHero(hero: DataX?) {

        Glide.with(activity!!).load(hero!!.imageurl).into(hero_img)
        hero_name.text = hero.name
        hero_realname.text = hero.realname
        hero_team.text = hero.team
        hero_created.text = hero.createdby
        hero_pembuat.text = hero.publisher
        hero_peran.text = hero.firstappearance
        hero_bio.text = hero.bio

    }


}
