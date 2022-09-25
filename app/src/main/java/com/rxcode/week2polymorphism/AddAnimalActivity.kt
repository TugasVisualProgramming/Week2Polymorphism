package com.rxcode.week2polymorphism

import Database.GlobalVar
import Model.Ayam
import Model.Hewan
import Model.Kambing
import Model.Sapi
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.rxcode.week2polymorphism.databinding.ActivityAddAnimalBinding

class AddAnimalActivity : AppCompatActivity() {
    private lateinit var viewBind: ActivityAddAnimalBinding
    private lateinit var hewan: Hewan
    private var position=-1
    var tempUri:String =""

    var ayam = false;
    var sapi = false;
    var kambing = false;

    private val GetResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == Activity.RESULT_OK){
            val uri = it.data?.data
            viewBind.fotoHewan.setImageURI(uri)
            if(uri!=null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    baseContext.getContentResolver().takePersistableUriPermission(
                        uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    )
                }
                tempUri = uri.toString()
            }

            //GlobalVar.listDataMovie[position].imageUri = uri.toString()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBind = ActivityAddAnimalBinding.inflate(layoutInflater)
        setContentView(viewBind.root)

        GetIntent()
        Listener()
    }

//    posisi saat di tekan
    private fun GetIntent() {
        position=intent.getIntExtra("position", -1)

        if(position!=-1){
            viewBind.tambahHewan.setText("Ubah Data Movie")
            viewBind.simpanHewan.setText("Simpan")
            val hewan= GlobalVar.listDataHewan[position]
            Display(hewan)
        }
    }

//    menampilkan data pada halaman edit
    private fun Display(hewan: Hewan?) {
        viewBind.namaHewanInput.editText?.setText(hewan?.namaHewan)
        viewBind.usiaHewanInput.editText?.setText(hewan?.usiaHewan)
        viewBind.fotoHewan.setImageURI(Uri.parse(hewan?.imageUri))
        when(hewan?.jenisHewan) {
            "Ayam" -> viewBind.ayamRadio.setChecked(true)
            "Sapi" -> viewBind.sapiRadio.setChecked(true)
            "Kambing" -> viewBind.kambingRadio.setChecked(true)
        }


    }
    private fun Listener() {
        viewBind.simpanHewan.setOnClickListener {

            var nama = viewBind.namaHewanInput.editText?.text.toString().trim()
            var usia = viewBind.usiaHewanInput.editText?.text.toString().trim()
            val radioId = viewBind.radioGroupHewan.checkedRadioButtonId
            val radioButton = findViewById<RadioButton>(radioId)
            var jenis = radioButton.text.toString()
            hewan = Hewan(nama,jenis,usia)
            if (jenis == "Ayam") {
                hewan= Ayam(nama, jenis, usia)
            } else if (jenis == "Sapi") {
                hewan= Sapi(nama, jenis, usia)
            } else if (jenis == "Kambing") {
                hewan= Kambing(nama, jenis, usia)
            }
            //fungsi check apakah input kosong atau tidak
            checker()

        }

        viewBind.BackButTambahHewan.setOnClickListener{
            val myIntent = Intent(this,MainActivity::class.java)
            startActivity(myIntent)

        }

        viewBind.fotoHewan.setOnClickListener{
            val myIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            myIntent.type="image/*"
            GetResult.launch(myIntent)
        }

    }

    private fun checker() {
        var isCompleted: Boolean=true

        //nama
        if (hewan.namaHewan!!.isEmpty()) {
            viewBind.namaHewanInput.error="tolong isi kolom Nama Hewan"
            isCompleted=false
        } else {
            viewBind.namaHewanInput.error=""
        }




        //usia
        if(hewan.usiaHewan!!.isEmpty()){
            viewBind.usiaHewanInput.error = "Usia hewan harus diisi 1-100"
            isCompleted=false
        }else if(hewan.usiaHewan!!.contains(".*[A-Z].*".toRegex())){
            viewBind.usiaHewanInput.error = "Usia hewan tidak boleh huruf"
            isCompleted=false
        }else if(hewan.usiaHewan!!.contains(".*[a-z].*".toRegex())){
            viewBind.usiaHewanInput.error = "Usia hewan tidak boleh huruf"
            isCompleted=false
        }else if(hewan.usiaHewan!!.contains(".*[0-9].*".toRegex())){
            if(hewan.usiaHewan!!.toInt()>100) {
                viewBind.usiaHewanInput.error = "Usia hewan harus 1-100"
                isCompleted = false
            }
            else {
                viewBind.usiaHewanInput.error = ""
            }
        }

        if (isCompleted) {
            if(position==-1){
                hewan.imageUri= tempUri
                GlobalVar.listDataHewan.add(hewan)
                Toast.makeText(this, "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show()
                val myIntent = Intent(this,MainActivity::class.java)
                startActivity(myIntent)
                finish()

            } else {
                if(tempUri== GlobalVar.listDataHewan[position].imageUri) {
                    hewan.imageUri = GlobalVar.listDataHewan[position].imageUri
                }
                else if(tempUri==""){
                    hewan.imageUri = GlobalVar.listDataHewan[position].imageUri
                }
                else{
                    hewan.imageUri = tempUri
                }
                GlobalVar.listDataHewan[position] = hewan
                Toast.makeText(this, "Data Berhasil Diubah", Toast.LENGTH_SHORT).show()
                val myIntent = Intent(this, MainActivity::class.java)
                startActivity(myIntent)
                finish()

            }

        }
    }
}