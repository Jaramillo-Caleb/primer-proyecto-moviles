class MenuRouter(
    private val gestorEstudiantes: GestorEstudiantes,
    private val gestorProfesores: GestorProfesores,
    private val courseManager: CourseManager,
    private val enrollmentService: EnrollmentService,
    private val gradeSystem: GradeSystem,
    private val reportGenerator: ReportGenerator,
    private val dataStorage: DataStorage
) {

    fun iniciar() {
        var salir = false

        while (!salir) {
            mostrarMenuPrincipal()
            when (leerOpcion()) {
                "1" -> menuEstudiantes()
                "2" -> menuProfesores()
                "3" -> menuCursos()
                "4" -> menuInscripciones()
                "5" -> menuCalificaciones()
                "6" -> menuReportes()
                "7" -> guardarDatos()
                "8" -> verDatosGuardados()
                "0" -> {
                    println("Sistema cerrado")
                    salir = true
                }
                else -> println("Opción inválida, intenta de nuevo")
            }
        }
    }

    private fun leerOpcion(): String {
        print("Selecciona una opción: ")
        return readLine()?.trim() ?: ""
    }

    private fun mostrarMenuPrincipal() {
        println()
        println("Sistema de gestión academica")
        println("1. Gestión de estudiantes")
        println("2. Gestión de profesores")
        println("3. Gestión de cursos")
        println("4. Inscripciones")
        println("5. Calificaciones")
        println("6. Reportes")
        println("7. Guardar datos en archivo")
        println("8. Ver datos guardados en archivo")
        println("0. Salir")
    }

// estudiantes

    private fun menuEstudiantes() {
        var volver = false
        while (!volver) {
            println()
            println("Gestión de estudiantes")
            println("1. Registrar estudiante")
            println("2. Listar estudiantes")
            println("3. Buscar estudiante por ID")
            println("4. Buscar estudiante por nombre")
            println("5. Actualizar estudiante")
            println("6. Eliminar estudiante")
            println("0. Volver al menú principal")

            when (leerOpcion()) {
                "1" -> registrarEstudiante()
                "2" -> listarEstudiantes()
                "3" -> buscarEstudiantePorId()
                "4" -> buscarEstudiantePorNombre()
                "5" -> actualizarEstudiante()
                "6" -> eliminarEstudiante()
                "0" -> volver = true
                else -> println("Opción inválida.")
            }
        }
    }

    private fun registrarEstudiante() {
        print("Nombre del estudiante: ")
        val nombre = readLine()?.trim() ?: ""
        print("Edad del estudiante: ")
        val edad = readLine()?.trim()?.toIntOrNull()

        if (nombre.isBlank() || edad == null) {
            println("Datos inválidos")
            return
        }

        val estudiante = gestorEstudiantes.registrarEstudiante(nombre, edad)
        println("Estudiante registrado con ID: ${estudiante.id}")
    }

    private fun listarEstudiantes() {
        val estudiantes = gestorEstudiantes.obtenerTodosLosEstudiantes()
        if (estudiantes.isEmpty()) {
            println("No hay estudiantes registrados.")
            return
        }
        estudiantes.forEach { println("ID: ${it.id}, Nombre: ${it.nombre}, Edad: ${it.edad}") }
    }

    private fun buscarEstudiantePorId() {
        print("ID del estudiante: ")
        val id = readLine()?.trim()?.toIntOrNull()
        if (id == null) {
            println("ID inválido")
            return
        }

        val estudiante = gestorEstudiantes.buscarEstudiantePorId(id)
        if (estudiante != null) {
            println("ID: ${estudiante.id}, Nombre: ${estudiante.nombre}, Edad: ${estudiante.edad}")
        } else {
            println("No se encontró un estudiante con ese ID")
        }
    }

    private fun buscarEstudiantePorNombre() {
        print("Nombre a buscar: ")
        val nombre = readLine()?.trim() ?: ""
        val resultados = gestorEstudiantes.buscarEstudiantesPorNombre(nombre)

        if (resultados.isEmpty()) {
            println("No se encontraron coincidencias")
        } else {
            resultados.forEach { println("ID: ${it.id}, ${it.nombre}, Edad: ${it.edad}") }
        }
    }

    private fun actualizarEstudiante() {
        print("ID del estudiante a actualizar: ")
        val id = readLine()?.trim()?.toIntOrNull()
        if (id == null) {
            println("ID inválido")
            return
        }

        print("Nuevo nombre: ")
        val nombre = readLine()?.trim() ?: ""
        print("Nueva edad: ")
        val edad = readLine()?.trim()?.toIntOrNull()

        if (nombre.isBlank() || edad == null) {
            println("Datos inválidos")
            return
        }

        val actualizado = gestorEstudiantes.actualizarEstudiante(id, nombre, edad)
        println(if (actualizado) "Estudiante actualizado correctamente" else "No se encontró el estudiante")
    }

    private fun eliminarEstudiante() {
        print("ID del estudiante a eliminar: ")
        val id = readLine()?.trim()?.toIntOrNull()
        if (id == null) {
            println("ID inválido")
            return
        }

        val eliminado = gestorEstudiantes.eliminarEstudiante(id)
        println(if (eliminado) "Estudiante eliminado" else "No se encontró el estudiante")
    }

// Profesores

    private fun menuProfesores() {
        var volver = false
        while (!volver) {
            println()
            println("Gestión de profesores")
            println("1. Registrar profesor")
            println("2. Listar profesores")
            println("3. Buscar profesor por ID")
            println("4. Buscar profesor por nombre")
            println("5. Buscar por especialidad")
            println("6. Actualizar profesor")
            println("7. Eliminar profesor")
            println("0. Volver al menú principal")

            when (leerOpcion()) {
                "1" -> registrarProfesor()
                "2" -> listarProfesores()
                "3" -> buscarProfesorPorId()
                "4" -> buscarProfesorPorNombre()
                "5" -> buscarProfesoresPorEspecialidad()
                "6" -> actualizarProfesor()
                "7" -> eliminarProfesor()
                "0" -> volver = true
                else -> println("Opción inválida")
            }
        }
    }

    private fun registrarProfesor() {
        print("Nombre del profesor: ")
        val nombre = readLine()?.trim() ?: ""
        print("Especialidad: ")
        val especialidad = readLine()?.trim() ?: ""

        if (nombre.isBlank() || especialidad.isBlank()) {
            println("Datos inválidos")
            return
        }

        val profesor = gestorProfesores.registrarProfesor(nombre, especialidad)
        println("Profesor registrado con ID: ${profesor.id}")
    }

    private fun listarProfesores() {
        val profesores = gestorProfesores.obtenerTodosLosProfesores()
        if (profesores.isEmpty()) {
            println("No hay profesores registrados")
            return
        }
        profesores.forEach { println("ID: ${it.id}, ${it.nombre}, ${it.especialidad}") }
    }

    private fun buscarProfesorPorId() {
        print("ID del profesor: ")
        val id = readLine()?.trim()?.toIntOrNull()
        if (id == null) {
            println("ID inválido")
            return
        }

        val profesor = gestorProfesores.buscarProfesorPorId(id)
        if (profesor != null) {
            println("ID: ${profesor.id}, ${profesor.nombre}, ${profesor.especialidad}")
        } else {
            println("No se encontró un profesor con ese ID")
        }
    }

    private fun buscarProfesorPorNombre() {
        print("Nombre a buscar: ")
        val nombre = readLine()?.trim() ?: ""
        val resultados = gestorProfesores.buscarProfesoresPorNombre(nombre)

        if (resultados.isEmpty()) {
            println("No se encontraron coincidencias")
        } else {
            resultados.forEach { println("ID: ${it.id}, ${it.nombre}, ${it.especialidad}") }
        }
    }

    private fun buscarProfesoresPorEspecialidad() {
        print("Especialidad a buscar: ")
        val especialidad = readLine()?.trim() ?: ""
        val resultados = gestorProfesores.obtenerProfesoresPorEspecialidad(especialidad)

        if (resultados.isEmpty()) {
            println("No se encontraron coincidencias")
        } else {
            resultados.forEach { println("ID: ${it.id}, ${it.nombre}, ${it.especialidad}") }
        }
    }

    private fun actualizarProfesor() {
        print("ID del profesor a actualizar: ")
        val id = readLine()?.trim()?.toIntOrNull()
        if (id == null) {
            println("ID inválido")
            return
        }

        print("Nuevo nombre: ")
        val nombre = readLine()?.trim() ?: ""
        print("Nueva especialidad: ")
        val especialidad = readLine()?.trim() ?: ""

        val actualizado = gestorProfesores.actualizarProfesor(id, nombre, especialidad)
        println(if (actualizado) "Profesor actualizado correctamente" else "No se encontró el profesor")
    }

    private fun eliminarProfesor() {
        print("ID del profesor a eliminar: ")
        val id = readLine()?.trim()?.toIntOrNull()
        if (id == null) {
            println("ID inválido")
            return
        }

        val eliminado = gestorProfesores.eliminarProfesor(id)
        println(if (eliminado) "Profesor eliminado" else "No se encontró el profesor")
    }

// cursos

    private fun menuCursos() {
        var volver = false
        while (!volver) {
            println()
            println("Gestión de cursos")
            println("1. Crear curso")
            println("2. Listar cursos")
            println("3. Buscar curso por código")
            println("4. Buscar cursos por nombre")
            println("5. Actualizar curso")
            println("6. Eliminar curso")
            println("0. Volver al menú principal")

            when (leerOpcion()) {
                "1" -> crearCurso()
                "2" -> listarCursos()
                "3" -> buscarCursoPorCodigo()
                "4" -> buscarCursosPorNombre()
                "5" -> actualizarCurso()
                "6" -> eliminarCurso()
                "0" -> volver = true
                else -> println("Opción inválida")
            }
        }
    }

    private fun crearCurso() {
        print("Código del curso: ")
        val codigo = readLine()?.trim() ?: ""
        print("Nombre del curso: ")
        val nombre = readLine()?.trim() ?: ""
        print("Cupos disponibles: ")
        val cupos = readLine()?.trim()?.toIntOrNull()
        print("Horario: ")
        val horario = readLine()?.trim() ?: ""
        print("ID del profesor asignado (vacío si no aplica): ")
        val idProfesor = readLine()?.trim()?.toIntOrNull()

        if (codigo.isBlank() || nombre.isBlank() || cupos == null || horario.isBlank()) {
            println("Datos inválidos")
            return
        }

        if (idProfesor != null && gestorProfesores.buscarProfesorPorId(idProfesor) == null) {
            println("ups, no existe un profesor con ese ID, el curso se creará sin profesor asignado.")
            courseManager.crearCurso(codigo, nombre, cupos, horario, null)
        } else {
            courseManager.crearCurso(codigo, nombre, cupos, horario, idProfesor)
        }

        println("Curso creado correctamente")
    }

    private fun listarCursos() {
        val cursos = courseManager.listarCursos()
        if (cursos.isEmpty()) {
            println("No hay cursos registrados")
            return
        }
        cursos.forEach {
            println("${it.codigo}, ${it.nombre}, Cupos: ${it.cupos}, Horario: ${it.horario}, ProfesorID: ${it.idProfesor ?: "Sin asignar"}")
        }
    }

    private fun buscarCursoPorCodigo() {
        print("Código del curso: ")
        val codigo = readLine()?.trim() ?: ""
        val curso = courseManager.buscarCurso(codigo)

        if (curso != null) {
            println("${curso.codigo}, ${curso.nombre}, Cupos: ${curso.cupos}, Horario: ${curso.horario}")
        } else {
            println("No se encontró un curso con ese código")
        }
    }

    private fun buscarCursosPorNombre() {
        print("Nombre a buscar: ")
        val nombre = readLine()?.trim() ?: ""
        val resultados = courseManager.buscarCursosPorNombre(nombre)

        if (resultados.isEmpty()) {
            println("No se encontraron coincidencias")
        } else {
            resultados.forEach { println("${it.codigo}, ${it.nombre}, Cupos: ${it.cupos}") }
        }
    }

    private fun actualizarCurso() {
        print("Código del curso a actualizar: ")
        val codigo = readLine()?.trim() ?: ""
        print("Nuevo nombre: ")
        val nombre = readLine()?.trim() ?: ""
        print("Nuevos cupos: ")
        val cupos = readLine()?.trim()?.toIntOrNull()
        print("Nuevo horario: ")
        val horario = readLine()?.trim() ?: ""
        print("Nuevo ID de profesor (vacío si no aplica): ")
        val idProfesor = readLine()?.trim()?.toIntOrNull()

        if (nombre.isBlank() || cupos == null || horario.isBlank()) {
            println("Datos inválidos")
            return
        }

        val actualizado = courseManager.actualizarCurso(codigo, nombre, cupos, horario, idProfesor)
        println(if (actualizado) "Curso actualizado correctamente" else "No se encontró el curso")
    }

    private fun eliminarCurso() {
        print("Código del curso a eliminar: ")
        val codigo = readLine()?.trim() ?: ""
        val eliminado = courseManager.eliminarCurso(codigo)
        println(if (eliminado) "Curso eliminado" else "No se encontró el curso")
    }

// Inscripcciones

    private fun menuInscripciones() {
        var volver = false
        while (!volver) {
            println()
            println("Inscripciones")
            println("1. Inscribir estudiante en curso")
            println("2. Cancelar inscripción")
            println("3. Ver cursos de un estudiante")
            println("4. Ver estudiantes de un curso")
            println("0. Volver al menú principal")

            when (leerOpcion()) {
                "1" -> inscribirEstudiante()
                "2" -> cancelarInscripcion()
                "3" -> verCursosDeEstudiante()
                "4" -> verEstudiantesDeCurso()
                "0" -> volver = true
                else -> println("Opción inválida")
            }
        }
    }

    private fun inscribirEstudiante() {
        print("ID del estudiante: ")
        val idEstudiante = readLine()?.trim()?.toIntOrNull()
        print("Código del curso: ")
        val codigoCurso = readLine()?.trim() ?: ""

        if (idEstudiante == null) {
            println("ID inválido")
            return
        }

        when (enrollmentService.enroll(idEstudiante, codigoCurso)) {
            is EnrollmentResult.Success -> println("Inscripción exitosa")
            is EnrollmentResult.StudentNotFound -> println("No existe un estudiante con ese ID")
            is EnrollmentResult.CourseNotFound -> println("No existe un curso con ese código")
            is EnrollmentResult.CourseFull -> println("El curso no tiene cupos disponibles")
            is EnrollmentResult.AlreadyEnrolled -> println("El estudiante ya está inscrito en este curso")
        }
    }

    private fun cancelarInscripcion() {
        print("ID del estudiante: ")
        val idEstudiante = readLine()?.trim()?.toIntOrNull()
        print("Código del curso: ")
        val codigoCurso = readLine()?.trim() ?: ""

        if (idEstudiante == null) {
            println("ID inválido")
            return
        }

        val cancelado = enrollmentService.cancelEnrollment(idEstudiante, codigoCurso)
        println(if (cancelado) "Inscripción cancelada" else "No se encontró esa inscripción")
    }

    private fun verCursosDeEstudiante() {
        print("ID del estudiante: ")
        val idEstudiante = readLine()?.trim()?.toIntOrNull()

        if (idEstudiante == null) {
            println("ID inválido")
            return
        }

        val cursos = enrollmentService.cursosDeEstudiante(idEstudiante)
        if (cursos.isEmpty()) {
            println("El estudiante no tiene cursos inscritos")
        } else {
            cursos.forEach { println(it) }
        }
    }

    private fun verEstudiantesDeCurso() {
        print("Código del curso: ")
        val codigoCurso = readLine()?.trim() ?: ""

        val estudiantes = enrollmentService.estudiantesEnCurso(codigoCurso)
        if (estudiantes.isEmpty()) {
            println("El curso no tiene estudiantes inscritos")
        } else {
            estudiantes.forEach { id ->
                val estudiante = gestorEstudiantes.buscarEstudiantePorId(id)
                println("ID: $id, ${estudiante?.nombre ?: "Desconocido"}")
            }
        }
    }

// Calificaciones

    private fun menuCalificaciones() {
        var volver = false
        while (!volver) {
            println()
            println("Calificaciones")
            println("1. Registrar calificación")
            println("2. Actualizar calificación")
            println("3. Eliminar calificación")
            println("4. Ver calificaciones de un estudiante")
            println("5. Ver calificaciones de un curso")
            println("0. Volver al menú principal")

            when (leerOpcion()) {
                "1" -> registrarCalificacion()
                "2" -> actualizarCalificacion()
                "3" -> eliminarCalificacion()
                "4" -> verCalificacionesEstudiante()
                "5" -> verCalificacionesCurso()
                "0" -> volver = true
                else -> println("Opción inválida")
            }
        }
    }

    private fun registrarCalificacion() {
        print("ID del estudiante: ")
        val idEstudiante = readLine()?.trim()?.toIntOrNull()
        print("Código del curso: ")
        val codigoCurso = readLine()?.trim() ?: ""
        print("Nota (0.0 a 5.0): ")
        val nota = readLine()?.trim()?.toDoubleOrNull()

        if (idEstudiante == null || nota == null) {
            println("Datos inválidos")
            return
        }

        val registrado = gradeSystem.registrarCalificacion(idEstudiante, codigoCurso, nota)
        println(
            if (registrado) "Calificación registrada"
            else "No se pudo registrar (verifica el ID, el curso o si ya existe una nota)"
        )
    }

    private fun actualizarCalificacion() {
        print("ID del estudiante: ")
        val idEstudiante = readLine()?.trim()?.toIntOrNull()
        print("Código del curso: ")
        val codigoCurso = readLine()?.trim() ?: ""
        print("Nueva nota (0.0 a 5.0): ")
        val nota = readLine()?.trim()?.toDoubleOrNull()

        if (idEstudiante == null || nota == null) {
            println("Datos inválidos")
            return
        }

        val actualizado = gradeSystem.actualizarCalificacion(idEstudiante, codigoCurso, nota)
        println(if (actualizado) "Calificación actualizada" else "No se encontró la calificación")
    }

    private fun eliminarCalificacion() {
        print("ID del estudiante: ")
        val idEstudiante = readLine()?.trim()?.toIntOrNull()
        print("Código del curso: ")
        val codigoCurso = readLine()?.trim() ?: ""

        if (idEstudiante == null) {
            println("ID inválido")
            return
        }

        val eliminado = gradeSystem.eliminarCalificacion(idEstudiante, codigoCurso)
        println(if (eliminado) "Calificación eliminada." else "No se encontró la calificación.")
    }

    private fun verCalificacionesEstudiante() {
        print("ID del estudiante: ")
        val idEstudiante = readLine()?.trim()?.toIntOrNull()

        if (idEstudiante == null) {
            println("ID inválido.")
            return
        }

        val calificaciones = gradeSystem.obtenerCalificacionesPorEstudiante(idEstudiante)
        if (calificaciones.isEmpty()) {
            println("El estudiante no tiene calificaciones registradas.")
        } else {
            calificaciones.forEach { println("${it.codigoCourse}: ${it.nota}") }
            gradeSystem.calcularPromedioEstudiante(idEstudiante)?.let {
                println("Promedio: ${"%.2f".format(it)}")
            }
        }
    }

    private fun verCalificacionesCurso() {
        print("Código del curso: ")
        val codigoCurso = readLine()?.trim() ?: ""

        val calificaciones = gradeSystem.obtenerCalificacionesPorCurso(codigoCurso)
        if (calificaciones.isEmpty()) {
            println("El curso no tiene calificaciones registradas")
        } else {
            calificaciones.forEach { println("Estudiante ${it.idEstudiante}: ${it.nota}") }
            gradeSystem.calcularPromedioCurso(codigoCurso)?.let {
                println("Promedio del curso: ${"%.2f".format(it)}")
            }
        }
    }

// Reportes

    private fun menuReportes() {
        var volver = false
        while (!volver) {
            println()
            println("Reportes")
            println("1. Mejores promedios")
            println("2. Cursos vacíos")
            println("3. Cursos llenos")
            println("4. Detalle de un curso")
            println("5. Profesores y cursos asignados")
            println("6. Resumen general")
            println("0. Volver al menú principal")

            when (leerOpcion()) {
                "1" -> reportGenerator.reporteMejoresPromedios()
                "2" -> reportGenerator.reporteCursosVacios()
                "3" -> reportGenerator.reporteCursosLlenos()
                "4" -> {
                    print("Código del curso: ")
                    val codigo = readLine()?.trim() ?: ""
                    reportGenerator.reporteDetalleCurso(codigo)
                }
                "5" -> reportGenerator.reporteProfesores()
                "6" -> reportGenerator.resumenGeneral()
                "0" -> volver = true
                else -> println("Opción inválida")
            }
        }
    }

// Persistencia (DataStorage)

    private fun guardarDatos() {
        dataStorage.guardar("estudiantes.json", gestorEstudiantes.obtenerTodosLosEstudiantes()) { e ->
            """  {"id": ${e.id}, "nombre": "${e.nombre}", "edad": ${e.edad}}"""
        }

        dataStorage.guardar("profesores.json", gestorProfesores.obtenerTodosLosProfesores()) { p ->
            """  {"id": ${p.id}, "nombre": "${p.nombre}", "especialidad": "${p.especialidad}"}"""
        }

        dataStorage.guardar("cursos.json", courseManager.listarCursos()) { c ->
            """  {"codigo": "${c.codigo}", "nombre": "${c.nombre}", "cupos": ${c.cupos}, "horario": "${c.horario}", "idProfesor": ${c.idProfesor}}"""
        }

        println("Los datos fueron guardados correctamente en la carpeta data")
    }

    private fun verDatosGuardados() {
        println()
        println("Contenido de estudiantes.json")
        println(dataStorage.leer("estudiantes.json"))

        println()
        println("Contenido de profesores.json")
        println(dataStorage.leer("profesores.json"))

        println()
        println("Contenido de cursos.json")
        println(dataStorage.leer("cursos.json"))
    }
}