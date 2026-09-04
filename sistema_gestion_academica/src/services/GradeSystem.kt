data class Calificacion(
    val idEstudiante: Int,
    val codigoCourse: String,
    var nota: Double
)

class GradeSystem (
    private val studentManager: GestorEstudiantes,
    private val courseManager: CourseManager
) {

    companion object {
        private const val NOTA_MIN = 0.0
        private const val NOTA_MAX = 5.0

    }

    private val calificaciones = mutableListOf<Calificacion>()

    fun registrarCalificacion(idEstudiante: Int, codigoCourse: String, nota: Double): Boolean {
        val estudianteExiste = studentManager.buscarEstudiantePorId(idEstudiante) != null
        val cursoExiste = courseManager.buscarCurso(codigoCourse) != null

        if (!estudianteExiste || !cursoExiste) return false
        if (nota < NOTA_MIN || nota > NOTA_MAX) return false

        val existe = calificaciones.find { c ->
            c.idEstudiante == idEstudiante && c.codigoCourse == codigoCourse
        }
        if (existe != null) return false

        calificaciones.add(Calificacion(idEstudiante, codigoCourse, nota))
        return true

    }

    fun actualizarCalificacion(idEstudiante: Int, codigoCourse: String, nuevaNota: Double): Boolean {
        if (nuevaNota < NOTA_MIN || nuevaNota > NOTA_MAX) return false

        val  calificacion = calificaciones.find { c ->
            c.idEstudiante == idEstudiante && c.codigoCourse == codigoCourse
        }

        if (calificacion != null) {
            calificacion.nota = nuevaNota
            return true
        }
        return false
    }

    fun eliminarCalificacion (idEstudiante: Int, codigoCourse: String): Boolean {
        return calificaciones.removeIf { c ->
            c.idEstudiante == idEstudiante && c.codigoCourse == codigoCourse
        }
    }

    fun obtenerCalificacionesPorEstudiante(idEstudiante: Int): List<Calificacion> {
        return calificaciones.filter { c -> c.idEstudiante == idEstudiante }
    }

    fun obtenerCalificacionesPorCurso(codigoCourse: String): List<Calificacion> {
        return calificaciones.filter { c -> c.codigoCourse == codigoCourse }
    }

    fun calcularPromedioEstudiante(idEstudiante: Int): Double? {
        val notas = obtenerCalificacionesPorEstudiante(idEstudiante)
        if (notas.isEmpty()) return null

        val valores = notas.map {c -> c.nota}
        return valores.sum() / valores.size
    }

    fun  calcularPromedioCurso(codigoCourse: String): Double? {
        val notas = obtenerCalificacionesPorCurso(codigoCourse)
        if (notas.isEmpty()) return null

        val valores = notas.map {c -> c.nota}
        return valores.sum() / valores.size

    }
}