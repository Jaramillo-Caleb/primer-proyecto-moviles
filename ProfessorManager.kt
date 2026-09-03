data class Profesor(
    val id: Int,
    val nombre: String,
    val especialidad: String
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
        println("Profesor registrado: $profesor")
        return profesor
    }

    fun buscarProfesorPorId(id: Int): Profesor? {
        return profesores.find { it.id == id }
    }

    fun buscarProfesoresPorNombre(nombre: String): List<Profesor> {
        return profesores.filter { it.nombre.contains(nombre, ignoreCase = true) }
    }

    fun obtenerTodosLosProfesores(): List<Profesor> {
        return profesores.toList()
    }

    fun actualizarProfesor(
        id: Int,
        nuevoNombre: String? = null,
        nuevaEspecialidad: String? = null
    ): Boolean {
        val profesor = buscarProfesorPorId(id)
        return if (profesor != null) {
            profesores.remove(profesor)
            val profesorActualizado = profesor.copy(
                nombre = nuevoNombre ?: profesor.nombre,
                especialidad = nuevaEspecialidad ?: profesor.especialidad
            )
            profesores.add(profesorActualizado)
            println("Profesor actualizado: $profesorActualizado")
            true
        } else {
            println("Error: No se encontró un profesor con ID $id")
            false
        }
    }

    fun eliminarProfesor(id: Int): Boolean {
        val profesor = buscarProfesorPorId(id)
        return if (profesor != null) {
            profesores.remove(profesor)
            println("Profesor eliminado: ${profesor.nombre} (ID: ${profesor.id})")
            true
        } else {
            println("Error: No se encontró un profesor con ID $id")
            false
        }
    }

    fun existeProfesor(id: Int): Boolean {
        return buscarProfesorPorId(id) != null
    }

    fun obtenerProfesoresPorEspecialidad(especialidad: String): List<Profesor> {
        return profesores.filter { it.especialidad.contains(especialidad, ignoreCase = true) }
    }

    fun imprimirTodosLosProfesores() {
        if (profesores.isEmpty()) {
            println("No hay profesores registrados.")
            return
        }
        println("\n LISTA DE PROFESORES ")
        profesores.forEach { profesor ->
            println("ID: ${profesor.id} | Nombre: ${profesor.nombre} | Especialidad: ${profesor.especialidad}")
        }
        println("Total: ${profesores.size} profesor(es)\n")
    }

    fun limpiarTodosLosProfesores() {
        profesores.clear()
        siguienteId = 1
        println("Todos los profesores han sido eliminados.")
    }
}