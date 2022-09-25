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

//    digunakan untuk manage isi card
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

//    digunakan untuk membuat view holder dalam rycyle view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.card_hewan, parent, false)
        return viewHolder(view, cardListener)
    }

//    digunakan untuk mengisi rycleview dan card view
    override fun onBindViewHolder(holder: viewHolder, position: Int) {




        if(GlobalVar.listFilterHewan.isEmpty()){
//            delete
            holder.setData(listHewan[position])
            holder.binding.deleteListButton.setOnClickListener {
                GlobalVar.listDataHewan.removeAt(position)
                Toast.makeText(it.context, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show()
                notifyDataSetChanged()
            }
//            edit
            holder.binding.editListButton.setOnClickListener{
                val myIntent = Intent(it.context, AddAnimalActivity::class.java)
                myIntent.putExtra("position",position)
                it.context.startActivity(myIntent)
                notifyDataSetChanged()
            }
//            suara
            holder.binding.suaraHewanButton.setOnClickListener{
                Toast.makeText(it.context, GlobalVar.listDataHewan.get(position).Suara(), Toast.LENGTH_SHORT).show()
            }
//            makan
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
        }else{
//            delete
            holder.setData(listHewan[position])
            holder.binding.deleteListButton.setOnClickListener {
                GlobalVar.listFilterHewan.removeAt(position)
                GlobalVar.listDataHewan.removeAt(position)
                Toast.makeText(it.context, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show()
                notifyDataSetChanged()
            }
//            edit
            holder.binding.editListButton.setOnClickListener{
                val myIntent = Intent(it.context, AddAnimalActivity::class.java)
                    myIntent.putExtra("position", position)
                it.context.startActivity(myIntent)
                notifyDataSetChanged()
            }


            holder.binding.suaraHewanButton.setOnClickListener{
                Toast.makeText(it.context, GlobalVar.listFilterHewan.get(position).Suara(), Toast.LENGTH_SHORT).show()
            }
            holder.binding.makanHewanButton.setOnClickListener{
                if(GlobalVar.listFilterHewan.get(position).jenisHewan == "Ayam") {
                    Toast.makeText(
                        it.context,
                        GlobalVar.listFilterHewan.get(position).Makan("Kamu memberi makan hewan dengan biji", "bijian!"),
                        Toast.LENGTH_SHORT
                    ).show()
                }else{
                    Toast.makeText(
                        it.context,
                        GlobalVar.listFilterHewan.get(position).Makan("Kamu memberi makan hewan dengan rerumputan!"),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }







    }

//digunakan untuk mengambil jumlah rycyleview
    override fun getItemCount(): Int {
        return listHewan.size
    }

}

