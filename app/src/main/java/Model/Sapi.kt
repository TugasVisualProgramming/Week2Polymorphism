package Model

class Sapi(nama:String,Jenis:String,Usia:String):Hewan(nama,Jenis,Usia) {
    override fun Suara():String {
        return "moo moo moo"
    }
}