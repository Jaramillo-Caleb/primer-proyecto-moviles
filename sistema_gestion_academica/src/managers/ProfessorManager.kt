data class Profesor(
    val id: Int,
    var nombre: String,
    var especialidad: String,
)

class GestorProfesores {

    private val profesores = mutableListOf<Profesor>()
    private var siguienteId = 1

    fun registrarProfesor(nombre: String, especialidad: String): Profesor {
        val profesor = Profesor(
            id = siguienteId++,
            nombre = nombre,
            especialidad = especialidad
        )

        profesores.add(profesor)
        return profesor
    }

    fun buscarProfesorPorId(id: Int): Profesor? {
        return profesores.find { it.id == id }
    }

    fun buscarProfesoresPorNombre(nombre: String): List<Profesor> {
        return profesores.filter {
            it.nombre.contains(nombre, ignoreCase = true)
        }
    }

    fun obtenerTodosLosProfesores(): List<Profesor> {
        return profesores
    }

    fun actualizarProfesor(
        id: Int,
        nombre: String,
        especialidad: String
    ): Boolean {

        val profesor = buscarProfesorPorId(id)

        if (profesor != null) {
            profesor.nombre = nombre
            profesor.especialidad = especialidad
            return true
        }

        return false
    }

    fun eliminarProfesor(id: Int): Boolean {
        return profesores.removeIf { it.id == id }
    }

    fun obtenerProfesoresPorEspecialidad(especialidad: String): List<Profesor> {
        return profesores.filter {
            it.especialidad.contains(especialidad, ignoreCase = true)
        }
    }
}
