data class Course(
    val codigo: String,
    val nombre: String,
    var cupos: Int,
    var horario: String
)

class CourseManager {
    private val cursos = mutableListOf<Course>()

    fun crearCurso(codigo: String,
                   nombre: String,
                   cupos: Int,
                   horario: String){


        val curso = Course(codigo, nombre, cupos, horario)
        cursos.add(curso)
    }

    fun buscarCurso(codigo: String): Course? {
        return cursos.find {it.codigo == codigo}
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

    fun listarCursos(): List<Course> {
        return cursos
    }
}

