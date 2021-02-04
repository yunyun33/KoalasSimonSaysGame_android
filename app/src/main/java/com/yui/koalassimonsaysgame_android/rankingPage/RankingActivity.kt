package com.yui.koalassimonsaysgame_android.rankingPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yui.koalassimonsaysgame_android.R
import com.yui.koalassimonsaysgame_android.resultPage.ResultActivity

class RankingActivity : AppCompatActivity(), RankingContract.View {

    private lateinit var presenter: RankingContract.Presenter

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: RecyclerListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)

        presenter = RankingPresenter(this)

        //DBからデータ取得する。
        presenter.didGetRankingData()



        recyclerView = findViewById<RecyclerView>(R.id.main_recycler_view)
        //RecyclerViewにlayoutManagerをセットする。
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        //val rankingDataList = mutableListOf<RankingData>()

        val rankingDataList = presenter.didGetRankingData()

        //RecyclerViewのレイアウトサイズを変更しない設定をONにする(パフォーマンス向上のため)
        recyclerView.setHasFixedSize(true)

        //Adapter生成してRecyclerViewにセットする。
        adapter = RecyclerListAdapter(rankingDataList)
        recyclerView.adapter = adapter

    }
}