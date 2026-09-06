fun main() {

    val gestorEstudiantes = GestorEstudiantes()
    val gestorProfesores = GestorProfesores()
    val courseManager = CourseManager()
    val dataStorage = DataStorage()
    val enrollmentService = EnrollmentService(gestorEstudiantes, courseManager)
    val gradeSystem = GradeSystem(gestorEstudiantes, courseManager)
    val reportGenerator = ReportGenerator(
        gestorEstudiantes,
        gestorProfesores,
        courseManager,
        enrollmentService,
        gradeSystem
    )
    val menuRouter = MenuRouter(
        gestorEstudiantes,
        gestorProfesores,
        courseManager,
        enrollmentService,
        gradeSystem,
        reportGenerator,
        dataStorage
    )

    menuRouter.iniciar()
}