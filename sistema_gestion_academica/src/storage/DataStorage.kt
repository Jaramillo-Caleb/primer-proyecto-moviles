import java.io.File

object DataStorage {

    private const val CARPETA = "data"
    private const val ARCHIVO_ESTUDIANTES = "$CARPETA/estudiantes.json"
    private const val ARCHIVO_PROFESORES = "$CARPETA/profesores.json"
    private const val ARCHIVO_CURSOS = "$CARPETA/cursos.json"

    init {
        File(CARPETA).mkdirs()
    }

    fun guardarEstudiantes(lista: List<Estudiante>) {
        val objetos = lista.map { """{"id":${it.id},"nombre":"${it.nombre}","edad":${it.edad}}""" }
        File(ARCHIVO_ESTUDIANTES).writeText("[\n${objetos.joinToString(",\n")}\n]")
    }

    fun cargarEstudiantes(): List<Estudiante> {
        val archivo = File(ARCHIVO_ESTUDIANTES)
        if (!archivo.exists()) return emptyList()
        return archivo.readLines().filter { it.contains("\"id\"") }.map {
            val p = it.replace("{", "").replace("}", "").replace(",", "")
                .split("\"").filter { s -> s.isNotBlank() && s != ":" }
            Estudiante(id = p[1].toInt(), nombre = p[3], edad = p[5].toInt())
        }
    }

    fun guardarProfesores(lista: List<Profesor>) {
        val objetos = lista.map { """{"id":${it.id},"nombre":"${it.nombre}","especialidad":
            |"${it.especialidad}"}""".trimMargin() }
        File(ARCHIVO_PROFESORES).writeText("[\n${objetos.joinToString(",\n")}\n]")
    }

    fun cargarProfesores(): List<Profesor> {
        val archivo = File(ARCHIVO_PROFESORES)
        if (!archivo.exists()) return emptyList()
        return archivo.readLines().filter { it.contains("\"id\"") }.map {
            val p = it.replace("{", "").replace("}", "").replace(",", "")
                .split("\"").filter { s -> s.isNotBlank() && s != ":" }
            Profesor(id = p[1].toInt(), nombre = p[3], especialidad = p[5])
        }
    }

    fun guardarCursos(lista: List<Course>) {
        val objetos = lista.map {
            val idProf = it.idProfesor?.toString() ?: "null"
            """{"codigo":"${it.codigo}","nombre":"${it.nombre}","cupos":${it.cupos},
                |"horario":"${it.horario}","idProfesor":$idProf}""".trimMargin()
        }
        File(ARCHIVO_CURSOS).writeText("[\n${objetos.joinToString(",\n")}\n]")
    }

    fun cargarCursos(): List<Course> {
        val archivo = File(ARCHIVO_CURSOS)
        if (!archivo.exists()) return emptyList()
        return archivo.readLines().filter { it.contains("\"codigo\"") }.map { linea ->
            val idProf = linea.substringAfter("\"idProfesor\":").replace("}", "").trim().toIntOrNull()
            val p = linea.substringBefore("\"idProfesor\"").replace("{", "").replace(",", "")
                .split("\"").filter { s -> s.isNotBlank() && s != ":" }
            Course(codigo = p[1], nombre = p[3], cupos = p[5].toInt(), horario = p[7], idProfesor = idProf)
        }
    }

    fun guardarTodo(estudiantes: List<Estudiante>, profesores: List<Profesor>, cursos: List<Course>) {
        guardarEstudiantes(estudiantes)
        guardarProfesores(profesores)
        guardarCursos(cursos)
    }
}