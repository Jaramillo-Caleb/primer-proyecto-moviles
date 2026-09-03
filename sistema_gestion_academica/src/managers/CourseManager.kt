data class Course(
    val codigo: String,
    val nombre: String,
    var cupos: Int,
    var horario: String,
    var idProfesor: Int? = null
)

class CourseManager {
    private val cursos = mutableListOf<Course>()

    fun crearCurso(
        codigo: String,
        nombre: String,
        cupos: Int,
        horario: String,
        idProfesor: Int? = null
    ) {
        val curso = Course(
            codigo,
            nombre,
            cupos,
            horario,
            idProfesor
        )

        cursos.add(curso)
    }

    fun buscarCurso(codigo: String): Course? {
        return cursos.find { it.codigo == codigo }
    }

    fun buscarCursosPorNombre(nombre: String): List<Course> {
        return cursos.filter {
            it.nombre.contains(nombre, ignoreCase = true)
        }
    }

    fun hayCupo(codigo: String): Boolean {
        val curso = buscarCurso(codigo)
        return curso != null && curso.cupos > 0
    }

    fun ocuparCupo(codigo: String): Boolean {
        val curso = buscarCurso(codigo)

        if (curso != null && curso.cupos > 0) {
            curso.cupos--
            return true
        }

        return false
    }

    fun actualizarCurso(
        codigo: String,
        nombre: String,
        cupos: Int,
        horario: String,
        idProfesor: Int?
    ): Boolean {
        val curso = buscarCurso(codigo)

        if (curso != null) {
            curso.nombre = nombre
            curso.cupos = cupos
            curso.horario = horario
            curso.idProfesor = idProfesor
            return true
        }

        return false
    }

    fun eliminarCurso(codigo: String): Boolean {
        return cursos.removeIf { it.codigo == codigo }
    }

    fun listarCursos(): List<Course> {
        return cursos
    }
}