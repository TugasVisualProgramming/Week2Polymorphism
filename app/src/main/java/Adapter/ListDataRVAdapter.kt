package Adapter

import Database.GlobalVar
import Interface.CardListener
import Model.Hewan
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.rxcode.week2polymorphism.AddAnimalActivity
import com.rxcode.week2polymorphism.R
import com.rxcode.week2polymorphism.databinding.CardHewanBinding

class ListDataRVAdapter(val listHewan: ArrayList<Hewan>, val cardListener: CardListener):
    RecyclerView.Adapter<ListDataRVAdapter.viewHolder>() {
    private var position = -1

    class viewHolder (val itemView: View, val cardListener1: CardListener): RecyclerView.ViewHolder(itemView) {
        //Bind
        val binding = CardHewanBinding.bind(itemView)

        fun setData(data:Hewan){
            binding.namaList.text = data.namaHewan
            binding.jenisList.text = data.jenisHewan
            binding.usiaList.text = data.usiaHewan
            if(data.imageUri.isNotEmpty())binding.pictureList.setImageURI(Uri.parse(data.imageUri))
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.card_hewan, parent, false)
        return viewHolder(view, cardListener)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.setData(listHewan[position])
        holder.binding.deleteListButton.setOnClickListener {
            GlobalVar.listDataHewan.removeAt(position)
            Toast.makeText(it.context, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show()
            notifyDataSetChanged()
        }
        holder.binding.editListButton.setOnClickListener{
            val myIntent = Intent(it.context, AddAnimalActivity::class.java)
            myIntent.putExtra("position",position)
            it.context.startActivity(myIntent)
        }

        holder.binding.suaraHewanButton.setOnClickListener{
            Toast.makeText(it.context, GlobalVar.listDataHewan.get(position).Suara(), Toast.LENGTH_SHORT).show()
        }

        holder.binding.makanHewanButton.setOnClickListener{
            if(GlobalVar.listDataHewan.get(position).jenisHewan == "Ayam") {
                Toast.makeText(
                    it.context,
                    GlobalVar.listDataHewan.get(position).Makan("Kamu memberi makan hewan dengan biji", "bijian!"),
                    Toast.LENGTH_SHORT
                ).show()
            }else{
                Toast.makeText(
                    it.context,
                    GlobalVar.listDataHewan.get(position).Makan("Kamu memberi makan hewan dengan rerumputan!"),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }



    }


    override fun getItemCount(): Int {
        return listHewan.size
    }

}

