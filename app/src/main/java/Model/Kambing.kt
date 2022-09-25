package Model

class Kambing(nama:String,Jenis:String,Usia:String):Hewan(nama,Jenis,Usia) {
    override fun Suara():String {
        return "mbek mbek mbek"
    }
}