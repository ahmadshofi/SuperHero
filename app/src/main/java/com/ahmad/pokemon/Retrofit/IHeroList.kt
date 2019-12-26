package com.ahmad.pokemon.Retrofit

import com.ahmad.pokemon.Model.Data
import io.reactivex.Observable
import retrofit2.http.GET

interface IHeroList {
    @get:GET("superheroes.json")
    val listHero:Observable<Data>
}