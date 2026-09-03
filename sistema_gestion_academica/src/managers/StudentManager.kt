data class Estudiante(
    val id: Int,
    var nombre: String,
    var edad: Int
)

class GestorEstudiantes {

    private val estudiantes = mutableListOf<Estudiante>()
    private var siguienteId = 1

    fun registrarEstudiante(nombre: String, edad: Int): Estudiante {
        val estudiante = Estudiante(
            id = siguienteId,
            nombre = nombre,
            edad = edad
        )

        estudiantes.add(estudiante)
        siguienteId++

        return estudiante
    }

    fun obtenerTodosLosEstudiantes(): List<Estudiante> {
        return estudiantes
    }

    fun buscarEstudiantePorId(id: Int): Estudiante? {
        return estudiantes.find { it.id == id }
    }

    fun buscarEstudiantesPorNombre(nombre: String): List<Estudiante> {
        return estudiantes.filter {
            it.nombre.contains(nombre, ignoreCase = true)
        }
    }

    fun actualizarEstudiante(
        id: Int,
        nombre: String,
        edad: Int
    ): Boolean {

        val estudiante = buscarEstudiantePorId(id)

        if (estudiante != null) {
            estudiante.nombre = nombre
            estudiante.edad = edad

            return true
        }

        return false
    }

    fun eliminarEstudiante(id: Int): Boolean {
        return estudiantes.removeIf { it.id == id }
    }
}

