
data class Enrollment(
    val studentId: Int,
    val courseCode: String
)


sealed class EnrollmentResult {
    data class Success(val enrollment: Enrollment) : EnrollmentResult()
    object StudentNotFound : EnrollmentResult()
    object CourseNotFound : EnrollmentResult()
    object CourseFull : EnrollmentResult()
    object AlreadyEnrolled : EnrollmentResult()
}

class EnrollmentService(
    private val gestorEstudiantes: GestorEstudiantes,
    private val courseManager: CourseManager
) {

    private val enrollments = mutableListOf<Enrollment>()

    fun enroll(studentId: Int, courseCode: String): EnrollmentResult {
        val estudiante = gestorEstudiantes.buscarEstudiantePorId(studentId)
            ?: return EnrollmentResult.StudentNotFound

        val curso = courseManager.buscarCurso(courseCode)
            ?: return EnrollmentResult.CourseNotFound

        if (isAlreadyEnrolled(estudiante.id, curso.codigo)) {
            return EnrollmentResult.AlreadyEnrolled
        }

        if (!courseManager.hayCupo(curso.codigo)) {
            return EnrollmentResult.CourseFull
        }

        val cupoOcupado = courseManager.ocuparCupo(curso.codigo)
        if (!cupoOcupado) {
            return EnrollmentResult.CourseFull
        }

        val nuevaInscripcion = Enrollment(estudiante.id, curso.codigo)
        enrollments.add(nuevaInscripcion)
        return EnrollmentResult.Success(nuevaInscripcion)
    }

    fun cancelEnrollment(studentId: Int, courseCode: String): Boolean {
        val eliminada = enrollments.removeIf {
            it.studentId == studentId && it.courseCode == courseCode
        }

        if (eliminada) {
            val curso = courseManager.buscarCurso(courseCode)
            curso?.cupos = (curso?.cupos ?: 0) + 1
        }

        return eliminada
    }


    fun cursosDeEstudiante(studentId: Int): List<String> {
        return enrollments.filter { it.studentId == studentId }.map { it.courseCode }
    }
    
    fun estudiantesEnCurso(courseCode: String): List<Int> {
        return enrollments.filter { it.courseCode == courseCode }.map { it.studentId }
    }

    fun contarInscritos(courseCode: String): Int {
        return enrollments.count { it.courseCode == courseCode }
    }

    private fun isAlreadyEnrolled(studentId: Int, courseCode: String): Boolean {
        return enrollments.any { it.studentId == studentId && it.courseCode == courseCode }
    }
}