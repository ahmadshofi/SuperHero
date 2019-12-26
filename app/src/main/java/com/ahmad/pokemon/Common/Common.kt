package com.ahmad.pokemon.Common

import com.ahmad.pokemon.HeroList
import com.ahmad.pokemon.Model.Data
import com.ahmad.pokemon.Model.DataX

object Common {
    fun findHeroByNum(num: String?): DataX? {
        for (hero in Common.heroList)
            if (hero.name.equals(num))
                return hero
        return null

    }

    var heroList:List<DataX> = ArrayList()
    val KEY_ENABLE_HOME = "position"
}