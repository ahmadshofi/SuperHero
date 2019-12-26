package com.ahmad.pokemon


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmad.pokemon.Adapter.HeroListAdapter
import com.ahmad.pokemon.Common.Common
import com.ahmad.pokemon.Common.ItemOffsetDecoration
import com.ahmad.pokemon.Model.DataX
import com.ahmad.pokemon.Retrofit.IHeroList
import com.ahmad.pokemon.Retrofit.RetrofitClient
import com.google.android.material.snackbar.Snackbar
import com.mancj.materialsearchbar.MaterialSearchBar
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_hero_list.*

/**
 * A simple [Fragment] subclass.
 */
class HeroList : Fragment() {

    internal var compositeDisposable = CompositeDisposable()
    internal var iHeroList:IHeroList
    internal lateinit var recycler_view :RecyclerView
    internal lateinit var adapter:HeroListAdapter
    internal lateinit var search_adapter:HeroListAdapter
    internal var last_suggets:MutableList<String> = ArrayList()

    internal lateinit var search_bar: MaterialSearchBar

    init {
        val retrofit = RetrofitClient.instance
        iHeroList = retrofit.create(IHeroList::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val itemView = inflater.inflate(R.layout.fragment_hero_list, container, false)

        recycler_view = itemView.findViewById(R.id.hero_recyclerview) as RecyclerView
        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = GridLayoutManager(activity,2) as RecyclerView.LayoutManager?
        val itemDecoration = ItemOffsetDecoration(activity!!,R.dimen.spacing)
        recycler_view.addItemDecoration(itemDecoration)

        //setup search bar
        search_bar = itemView.findViewById(R.id.search_bar) as MaterialSearchBar
        search_bar.setHint("Enter Hero Name...")
        search_bar.setCardViewElevation(10)
        search_bar.addTextChangeListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val suggest = ArrayList<String>()
                for (search in last_suggets)
                    if (search.toLowerCase().contains(search_bar.text.toLowerCase()))
                        suggest.add(search)
                search_bar.lastSuggestions = suggest
            }
        })
        search_bar.setOnSearchActionListener(object: MaterialSearchBar.OnSearchActionListener{
            override fun onButtonClicked(buttonCode: Int) {

            }

            override fun onSearchStateChanged(enabled: Boolean) {
                if (!enabled)
                    hero_recyclerview.adapter = adapter
            }

            override fun onSearchConfirmed(text: CharSequence?) {
                startSearch(text.toString())
            }
        })

        getData()

        return itemView
    }

    private fun startSearch(text: String) {
        if (Common.heroList.size > 0 ) {
            val result = ArrayList<DataX>()
            for (hero in Common.heroList)
                if (hero.name.toLowerCase().contains(text.toLowerCase())) {
                    result.add(hero)
                    search_adapter = HeroListAdapter(activity!!, result)
                    hero_recyclerview.adapter = search_adapter
                }
                else if(!hero.name.toLowerCase().contains(text.toLowerCase()))  {//snack bar
                    val snackbar = Snackbar.make(view!!, "Data Tidak Di temukan", Snackbar.LENGTH_LONG)
                    snackbar.show()
                }



        }


    }

    private fun getData() {
        compositeDisposable.add(iHeroList.listHero
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{herox ->
                Common.heroList = herox.data!!
                adapter = HeroListAdapter(activity!!,Common.heroList)

                recycler_view.adapter = adapter

                last_suggets.clear()
                for (hero in Common.heroList)
                    last_suggets.add(hero.name)
                search_bar.visibility = View.VISIBLE
                search_bar.lastSuggestions = last_suggets
            })
    }


}
