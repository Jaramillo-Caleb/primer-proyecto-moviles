class ReportGenerator(
    private val gestorEstudiantes: GestorEstudiantes,
    private val gestorProfesores: GestorProfesores,
    private val courseManager: CourseManager,
    private val enrollmentService: EnrollmentService,
    private val gradeSystem: GradeSystem
) {

    fun reporteMejoresPromedios(top: Int = 5) {
        println("Estudiantes con mejor promedio")

        val estudiantes = gestorEstudiantes.obtenerTodosLosEstudiantes()

        val promedios = estudiantes.mapNotNull { estudiante ->
            val promedio = gradeSystem.calcularPromedioEstudiante(estudiante.id)
            if (promedio != null) estudiante to promedio else null
        }.sortedByDescending { it.second }

        if (promedios.isEmpty()) {
            println("No hay calificaciones registradas todavía.")
            return
        }

        promedios.take(top).forEachIndexed { index, (estudiante, promedio) ->
            println("${index + 1}. ${estudiante.nombre} (ID: ${estudiante.id}) - Promedio: ${"%.2f".format(promedio)}")
        }
    }

    fun reporteCursosVacios() {
        println("Cursos sin estudiantes inscritos")

        val cursos = courseManager.listarCursos()
        val vacios = cursos.filter { enrollmentService.contarInscritos(it.codigo) == 0 }

        if (vacios.isEmpty()) {
            println("Todos los cursos tienen al menos un estudiante inscrito.")
            return
        }

        vacios.forEach { curso ->
            println("${curso.codigo} - ${curso.nombre} (Cupos disponibles: ${curso.cupos})")
        }
    }

    fun reporteCursosLlenos() {
        println("Cursos sin cupos disponibles")

        val cursos = courseManager.listarCursos()
        val llenos = cursos.filter { it.cupos == 0 }

        if (llenos.isEmpty()) {
            println("Ningún curso está lleno actualmente.")
            return
        }

        llenos.forEach { curso ->
            val inscritos = enrollmentService.contarInscritos(curso.codigo)
            println("${curso.codigo} - ${curso.nombre} (Inscritos: $inscritos)")
        }
    }

    fun reporteDetalleCurso(codigoCurso: String) {
        val curso = courseManager.buscarCurso(codigoCurso)

        if (curso == null) {
            println("No se encontró un curso con el código '$codigoCurso'.")
            return
        }

        println("Detalle del curso ${curso.codigo}")

        println("Nombre: ${curso.nombre}")
        println("Horario: ${curso.horario}")
        println("Cupos disponibles: ${curso.cupos}")

        val profesor = curso.idProfesor?.let { gestorProfesores.buscarProfesorPorId(it) }
        println("Profesor: ${profesor?.nombre ?: "Sin asignar"}")

        val idsEstudiantes = enrollmentService.estudiantesEnCurso(curso.codigo)
        println("Estudiantes inscritos: ${idsEstudiantes.size}")

        val calificacionesCurso = gradeSystem.obtenerCalificacionesPorCurso(curso.codigo)

        idsEstudiantes.forEach { id ->
            val estudiante = gestorEstudiantes.buscarEstudiantePorId(id)
            val nota = calificacionesCurso.find { it.idEstudiante == id }?.nota
            val notaTexto = if (nota != null) "%.2f".format(nota) else "Sin calificar"
            println(" - ${estudiante?.nombre ?: "Desconocido"} (ID: $id) | Nota: $notaTexto")
        }

        gradeSystem.calcularPromedioCurso(curso.codigo)?.let {
            println("Promedio del curso: ${"%.2f".format(it)}")
        }
    }

    fun reporteProfesores() {
        println("Reporte de profesores y cursos asignados")

        val profesores = gestorProfesores.obtenerTodosLosProfesores()

        if (profesores.isEmpty()) {
            println("No hay profesores registrados.")
            return
        }

        val cursos = courseManager.listarCursos()

        profesores.forEach { profesor ->
            val cursosAsignados = cursos.filter { it.idProfesor == profesor.id }
            println("${profesor.nombre} (ID: ${profesor.id}) - Especialidad: ${profesor.especialidad}")

            if (cursosAsignados.isEmpty()) {
                println("   Sin cursos asignados.")
            } else {
                cursosAsignados.forEach { curso -> println("   - ${curso.codigo}: ${curso.nombre}") }
            }
        }
    }

    fun resumenGeneral() {
        println("Resumen general del sistema")

        val cursos = courseManager.listarCursos()
        val totalEstudiantes = gestorEstudiantes.obtenerTodosLosEstudiantes().size
        val totalProfesores = gestorProfesores.obtenerTodosLosProfesores().size
        val totalCursos = cursos.size
        val totalInscripciones = cursos.sumOf { enrollmentService.contarInscritos(it.codigo) }

        println("Total de estudiantes: $totalEstudiantes")
        println("Total de profesores: $totalProfesores")
        println("Total de cursos: $totalCursos")
        println("Total de inscripciones activas: $totalInscripciones")
    }
}