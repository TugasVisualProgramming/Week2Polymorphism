package Model

open class Ayam(nama:String,Jenis:String,Usia:String) : Hewan(nama,Jenis,Usia) {
    override fun Suara():String {
        return "pok pok pok"
    }


}