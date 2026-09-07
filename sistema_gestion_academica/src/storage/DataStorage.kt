import java.io.File

class DataStorage {

    private val carpeta = "data"

    init {
        File(carpeta).mkdirs()
    }

    fun <T> guardar(nombreArchivo: String, datos: List<T>, convertir: (T) -> String) {
        val objetos = datos.map { convertir(it) }
        val contenido = "[\n${objetos.joinToString(",\n")}\n]"
        File("$carpeta/$nombreArchivo").writeText(contenido)
    }

    fun leer(nombreArchivo: String): String {
        val archivo = File("$carpeta/$nombreArchivo")
        if (!archivo.exists()) return "[]"
        return archivo.readText()
    }
}