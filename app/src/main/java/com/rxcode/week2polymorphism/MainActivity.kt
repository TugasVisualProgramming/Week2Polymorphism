package com.rxcode.week2polymorphism

import Adapter.ListDataRVAdapter
import Database.GlobalVar
import Interface.CardListener
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.rxcode.week2polymorphism.databinding.ActivityMainBinding
import com.rxcode.week2polymorphism.databinding.CardHewanBinding

class MainActivity : AppCompatActivity() , CardListener {

    private lateinit var viewBind: ActivityMainBinding

    private var position=-1

    //    private val listDataHewan = ArrayList<Hewan>()
    private val adapter = ListDataRVAdapter(GlobalVar.listDataHewan,this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBind.root)
        Listener()
        setupRecyclerView()
//        addDummyData()
    }

    private fun setupRecyclerView(){
        val layoutManager = LinearLayoutManager(baseContext) //untuk mengatur recyleview mau baris atau kolom
        viewBind.listDataRV.layoutManager = layoutManager //set layout
        viewBind.listDataRV.adapter = adapter //set adapter
        if(GlobalVar.listDataHewan.isNotEmpty()){
            viewBind.pesanHewan.isInvisible = true
        }

    }

    private fun Listener(){
        viewBind.addDataFAB.setOnClickListener{
            val myIntent = Intent(this, AddAnimalActivity::class.java)
            startActivity(myIntent)
            finish()
        }
    }
    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
        viewBind.AyamiFilterButton.setOnClickListener{
            GlobalVar.listFilterHewan.clear();
          for (item in  GlobalVar.listDataHewan.filter { it.jenisHewan == "Ayam"  }){
             GlobalVar.listFilterHewan.add(item)
          }
            viewBind.listDataRV.adapter  = ListDataRVAdapter(GlobalVar.listFilterHewan,this)
            onRestart();
        }

        viewBind.SapiFilterButton.setOnClickListener{
            GlobalVar.listFilterHewan.clear();
            for (item in  GlobalVar.listDataHewan.filter { it.jenisHewan == "Sapi"  }){
                GlobalVar.listFilterHewan.add(item)
            }
            viewBind.listDataRV.adapter  = ListDataRVAdapter(GlobalVar.listFilterHewan,this)
            onRestart();
        }

        viewBind.KambingFilterButton.setOnClickListener{
            GlobalVar.listFilterHewan.clear();
            for (item in  GlobalVar.listDataHewan.filter { it.jenisHewan == "Kambing"  }){
                GlobalVar.listFilterHewan.add(item)
            }
            viewBind.listDataRV.adapter  = ListDataRVAdapter(GlobalVar.listFilterHewan,this)
            onRestart();
        }

        viewBind.SemuaFilterButton.setOnClickListener{
            onRestart();
            viewBind.listDataRV.adapter  = ListDataRVAdapter(GlobalVar.listDataHewan,this)
            onRestart();
        }
    }







//    private fun addDummyData(){
//        listDataHewan.add(Hewan("joko","anjing","19"))
//        listDataHewan.add(Hewan("joko","anjing","19"))
//        listDataHewan.add(Hewan("joko","anjing","19"))
//        listDataHewan.add(Hewan("joko","anjing","19"))
//        listDataHewan.add(Hewan("joko","anjing","19"))
//        listDataHewan.add(Hewan("joko","anjing","19"))
//        listDataHewan.add(Hewan("joko","anjing","19"))
//        listDataHewan.add(Hewan("joko","anjing","19"))
//    }

}