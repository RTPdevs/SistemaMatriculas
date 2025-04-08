import javax.swing.JOptionPane;

public class SistemaMatricula {

    // Matrices para almacenar la informacion
    static String[][] estudiantes = new String[100][6];  // [ID, Nombre, Programa, Correo, Telefono, Estado]
    static int contadorEstudiantes = 0;

    static String[][] cursos = new String[10][3];  // [Periodo, Codigo, Nombre]
    static int contadorCursos = 0;

    static String[][] matriculas = new String[100][4];  // Cada estudiante puede tener hasta 4 cursos

    public static void main(String[] args) {
        int opcion = 0;
        do {
            opcion = Integer.parseInt(JOptionPane.showInputDialog(
                "===== SISTEMA DE MATRICULA UNIVERSITARIA =====\n\n" +
                "1. Registrar estudiante\n" +
                "2. Ver estudiantes\n" +
                "3. Registrar curso\n" +
                "4. Ver cursos\n" +
                "5. Matricular estudiante en cursos\n" +
                "6. Ver cursos de un estudiante\n" +
                "7. Ver estudiantes en un curso\n" +
                "8. Actualizar matricula\n" +
                "9. Buscar estudiante por ID\n" +
                "10. Salir\n\n" +
                "Seleccione una opcion:"
            ));

            if (opcion == 1) {
                registrarEstudiante();
            } else if (opcion == 2) {
                mostrarEstudiantes();
            } else if (opcion == 3) {
                registrarCurso();
            } else if (opcion == 4) {
                mostrarCursos();
            } else if (opcion == 5) {
                realizarMatricula();
            } else if (opcion == 6) {
                verCursosDeEstudiante();
            } else if (opcion == 7) {
                verEstudiantesEnCurso();
            } else if (opcion == 8) {
                actualizarMatricula();
            } else if (opcion == 9) {
                buscarEstudiantePorID();
            } else if (opcion == 10) {
                JOptionPane.showMessageDialog(null, "¡Hasta luego!");
            } else {
                JOptionPane.showMessageDialog(null, "Opcion invalida. Intente de nuevo.");
            }

        } while (opcion != 10);
    }

    // Opcion 1: Registrar estudiante
    public static void registrarEstudiante() {
        if (contadorEstudiantes >= 100) {
            JOptionPane.showMessageDialog(null, "No se pueden registrar mas estudiantes.");
            return;
        }

        String id = "";
        boolean idValido = false;
        while (!idValido) {
            id = JOptionPane.showInputDialog("Ingrese numero de identificacion:");
            if (id == null || id.equals("")) {
                JOptionPane.showMessageDialog(null, "El ID no puede estar vacio.");
                continue;
            }
            if (!esNumero(id)) {
                JOptionPane.showMessageDialog(null, "El ID debe tener solo numeros.");
                continue;
            }
            // Verificar que el ID sea unico
            boolean repetido = false;
            for (int i = 0; i < contadorEstudiantes; i++) {
                if (estudiantes[i][0].equals(id)) {
                    repetido = true;
                    break;
                }
            }
            if (repetido) {
                JOptionPane.showMessageDialog(null, "Este ID ya esta registrado.");
            } else {
                idValido = true;
            }
        }

        String nombre = "";
        while (true) {
            nombre = JOptionPane.showInputDialog("Ingrese nombre completo:");
            if (nombre != null && esSoloLetras(nombre)) {
                break;
            } else {
                JOptionPane.showMessageDialog(null, "El nombre debe tener solo letras.");
            }
        }

        String programa = "";
        while (true) {
            programa = JOptionPane.showInputDialog("Ingrese codigo de programa (01, 02 o 03):");
            if (programa != null && esProgramaValido(programa)) {
                break;
            } else {
                JOptionPane.showMessageDialog(null, "Codigo invalido. Use 01, 02 o 03.");
            }
        }

        String correo = "";
        while (true) {
            correo = JOptionPane.showInputDialog("Ingrese correo electronico:");
            if (correo != null && esCorreoValido(correo)) {
                break;
            } else {
                JOptionPane.showMessageDialog(null, "Correo electronico invalido.");
            }
        }

        String telefono = "";
        while (true) {
            telefono = JOptionPane.showInputDialog("Ingrese telefono de contacto:");
            if (telefono != null && esNumero(telefono)) {
                break;
            } else {
                JOptionPane.showMessageDialog(null, "El telefono debe contener solo numeros.");
            }
        }

        String estado = "";
        while (true) {
            estado = JOptionPane.showInputDialog("Ingrese estado (A=Activo, I=Inactivo):");
            if (estado != null && esEstadoValido(estado)) {
                break;
            } else {
                JOptionPane.showMessageDialog(null, "Estado invalido. Use A o I.");
            }
        }

        estudiantes[contadorEstudiantes][0] = id;
        estudiantes[contadorEstudiantes][1] = nombre;
        estudiantes[contadorEstudiantes][2] = programa;
        estudiantes[contadorEstudiantes][3] = correo;
        estudiantes[contadorEstudiantes][4] = telefono;
        estudiantes[contadorEstudiantes][5] = estado.toUpperCase();

        contadorEstudiantes++;
        JOptionPane.showMessageDialog(null, "Estudiante registrado con exito.");
    }

    // Opcion 2: Ver estudiantes
    public static void mostrarEstudiantes() {
        if (contadorEstudiantes == 0) {
            JOptionPane.showMessageDialog(null, "No hay estudiantes registrados.");
            return;
        }
        String lista = "Lista de Estudiantes:\n\n";
        for (int i = 0; i < contadorEstudiantes; i++) {
            lista += "ID: " + estudiantes[i][0] + ", Nombre: " + estudiantes[i][1] +
                     ", Programa: " + estudiantes[i][2] + ", Estado: " + estudiantes[i][5] + "\n";
        }
        JOptionPane.showMessageDialog(null, lista);
    }

    // Opcion 3: Registrar curso
    public static void registrarCurso() {
        if (contadorCursos >= 10) {
            JOptionPane.showMessageDialog(null, "No se pueden registrar mas cursos.");
            return;
        }

        String periodo = JOptionPane.showInputDialog("Ingrese el periodo (Ej: 20251):");
        String codigo = "";
        boolean codigoValido = false;
        while (!codigoValido) {
            codigo = JOptionPane.showInputDialog("Ingrese codigo del curso (4 digitos):");
            if (codigo.length() != 4 || !esNumero(codigo)) {
                JOptionPane.showMessageDialog(null, "Debe ingresar un numero de 4 digitos.");
                continue;
            }
            if (codigo.equals("9999")) {
                JOptionPane.showMessageDialog(null, "El codigo 9999 no esta permitido.");
                continue;
            }
            // Verificar que el codigo no se repita
            boolean existe = false;
            for (int i = 0; i < contadorCursos; i++) {
                if (cursos[i][1].equals(codigo)) {
                    existe = true;
                    break;
                }
            }
            if (existe) {
                JOptionPane.showMessageDialog(null, "Este codigo ya existe.");
            } else {
                codigoValido = true;
            }
        }

        String nombre = JOptionPane.showInputDialog("Ingrese nombre del curso:");

        cursos[contadorCursos][0] = periodo;
        cursos[contadorCursos][1] = codigo;
        cursos[contadorCursos][2] = nombre;

        contadorCursos++;
        JOptionPane.showMessageDialog(null, "Curso registrado con exito.");
    }

    // Opcion 4: Ver cursos
    public static void mostrarCursos() {
        if (contadorCursos == 0) {
            JOptionPane.showMessageDialog(null, "No hay cursos registrados.");
            return;
        }
        String mensaje = "Lista de Cursos:\n\n";
        for (int i = 0; i < contadorCursos; i++) {
            mensaje += "Periodo: " + cursos[i][0] +
                       ", Codigo: " + cursos[i][1] +
                       ", Nombre: " + cursos[i][2] + "\n";
        }
        JOptionPane.showMessageDialog(null, mensaje);
    }

    // Opcion 5: Matricular estudiante en cursos
    public static void realizarMatricula() {
        if (contadorEstudiantes == 0 || contadorCursos == 0) {
            JOptionPane.showMessageDialog(null, "Debe haber al menos un estudiante y un curso registrado.");
            return;
        }

        String id = JOptionPane.showInputDialog("Ingrese el ID del estudiante que desea matricular:");
        int pos = -1;
        for (int i = 0; i < contadorEstudiantes; i++) {
            if (estudiantes[i][0].equals(id)) {
                pos = i;
                break;
            }
        }
        if (pos == -1) {
            JOptionPane.showMessageDialog(null, "Estudiante no encontrado.");
            return;
        }
        if (!estudiantes[pos][5].equalsIgnoreCase("A")) {
            JOptionPane.showMessageDialog(null, "El estudiante no esta activo. No puede matricular cursos.");
            return;
        }

        int cursosTomados = 0;
        while (cursosTomados < 4) {
            String listaCursos = "Cursos disponibles:\n";
            for (int i = 0; i < contadorCursos; i++) {
                listaCursos += cursos[i][1] + ": " + cursos[i][2] + "\n";
            }
            String codigo = JOptionPane.showInputDialog(listaCursos + "\nIngrese codigo del curso a matricular o escriba 'fin' para terminar:");
            if (codigo.equalsIgnoreCase("fin")) {
                break;
            }
            boolean existe = false;
            for (int i = 0; i < contadorCursos; i++) {
                if (cursos[i][1].equals(codigo)) {
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                JOptionPane.showMessageDialog(null, "El codigo ingresado no corresponde a ningun curso.");
            } else {
                // Verificar si ya esta matriculado
                boolean yaMatriculado = false;
                for (int i = 0; i < cursosTomados; i++) {
                    if (matriculas[pos][i] != null && matriculas[pos][i].equals(codigo)) {
                        yaMatriculado = true;
                        break;
                    }
                }
                if (yaMatriculado) {
                    JOptionPane.showMessageDialog(null, "Ya matriculaste este curso.");
                } else {
                    matriculas[pos][cursosTomados] = codigo;
                    cursosTomados++;
                    JOptionPane.showMessageDialog(null, "Curso matriculado.");
                }
            }
        }
        // Rellenar los espacios vacios con "9999"
        for (int i = cursosTomados; i < 4; i++) {
            matriculas[pos][i] = "9999";
        }
        JOptionPane.showMessageDialog(null, "Matricula completada.");
    }

    // Opcion 6: Ver cursos de un estudiante
    public static void verCursosDeEstudiante() {
        if (contadorEstudiantes == 0 || contadorCursos == 0) {
            JOptionPane.showMessageDialog(null, "Debe haber estudiantes y cursos registrados.");
            return;
        }
        String id = JOptionPane.showInputDialog("Ingrese el ID del estudiante:");
        int pos = -1;
        for (int i = 0; i < contadorEstudiantes; i++) {
            if (estudiantes[i][0].equals(id)) {
                pos = i;
                break;
            }
        }
        if (pos == -1) {
            JOptionPane.showMessageDialog(null, "Estudiante no encontrado.");
            return;
        }
        String mensaje = "Cursos matriculados por " + estudiantes[pos][1] + ":\n\n";
        boolean tieneCursos = false;
        for (int j = 0; j < 4; j++) {
            String cod = matriculas[pos][j];
            if (cod != null && !cod.equals("9999")) {
                String nombreCurso = "(no encontrado)";
                for (int k = 0; k < contadorCursos; k++) {
                    if (cursos[k][1].equals(cod)) {
                        nombreCurso = cursos[k][2];
                        break;
                    }
                }
                mensaje += "- " + cod + ": " + nombreCurso + "\n";
                tieneCursos = true;
            }
        }
        if (!tieneCursos) {
            mensaje += "No tiene cursos matriculados.";
        }
        JOptionPane.showMessageDialog(null, mensaje);
    }

    // Opcion 7: Ver estudiantes en un curso
    public static void verEstudiantesEnCurso() {
        if (contadorEstudiantes == 0 || contadorCursos == 0) {
            JOptionPane.showMessageDialog(null, "No hay estudiantes o cursos registrados.");
            return;
        }
        String codigo = JOptionPane.showInputDialog("Ingrese el codigo del curso:");
        boolean cursoExiste = false;
        String nombreCurso = "";
        for (int i = 0; i < contadorCursos; i++) {
            if (cursos[i][1].equals(codigo)) {
                cursoExiste = true;
                nombreCurso = cursos[i][2];
                break;
            }
        }
        if (!cursoExiste) {
            JOptionPane.showMessageDialog(null, "El curso no existe.");
            return;
        }
        String lista = "Estudiantes matriculados en el curso " + codigo + " - " + nombreCurso + ":\n\n";
        boolean hayAlumnos = false;
        for (int i = 0; i < contadorEstudiantes; i++) {
            for (int j = 0; j < 4; j++) {
                if (matriculas[i][j] != null && matriculas[i][j].equals(codigo)) {
                    lista += "- " + estudiantes[i][1] + " (ID: " + estudiantes[i][0] + ")\n";
                    hayAlumnos = true;
                    break;
                }
            }
        }
        if (!hayAlumnos) {
            lista += "Ningun estudiante esta matriculado en este curso.";
        }
        JOptionPane.showMessageDialog(null, lista);
    }

    // Opcion 8: Actualizar matricula
    public static void actualizarMatricula() {
        String id = JOptionPane.showInputDialog("Ingrese el ID del estudiante:");
        int pos = -1;
        for (int i = 0; i < contadorEstudiantes; i++) {
            if (estudiantes[i][0].equals(id)) {
                pos = i;
                break;
            }
        }
        if (pos == -1) {
            JOptionPane.showMessageDialog(null, "Estudiante no encontrado.");
            return;
        }
        if (!estudiantes[pos][5].equalsIgnoreCase("A")) {
            JOptionPane.showMessageDialog(null, "Solo los estudiantes activos pueden actualizar la matricula.");
            return;
        }
        String mensaje = "Cursos actuales:\n";
        for (int i = 0; i < 4; i++) {
            String cod = matriculas[pos][i];
            if (cod != null && !cod.equals("9999")) {
                mensaje += (i + 1) + ". " + cod + "\n";
            }
        }
        mensaje += "\n¿Qué desea hacer?\n1. Eliminar un curso\n2. Agregar un curso nuevo";
        String opcion = JOptionPane.showInputDialog(mensaje);
        if (opcion.equals("1")) {
            String eliminar = JOptionPane.showInputDialog("Ingrese el codigo del curso a eliminar:");
            boolean eliminado = false;
            for (int i = 0; i < 4; i++) {
                if (matriculas[pos][i] != null && matriculas[pos][i].equals(eliminar)) {
                    matriculas[pos][i] = "9999";
                    eliminado = true;
                    break;
                }
            }
            if (eliminado) {
                JOptionPane.showMessageDialog(null, "Curso eliminado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "El estudiante no tenia ese curso.");
            }
        } else if (opcion.equals("2")) {
            int espacio = -1;
            for (int i = 0; i < 4; i++) {
                if (matriculas[pos][i] == null || matriculas[pos][i].equals("9999")) {
                    espacio = i;
                    break;
                }
            }
            if (espacio == -1) {
                JOptionPane.showMessageDialog(null, "Ya tiene 4 cursos. Elimine uno primero.");
                return;
            }
            String nuevo = JOptionPane.showInputDialog("Ingrese el codigo del nuevo curso:");
            boolean existe = false;
            for (int i = 0; i < contadorCursos; i++) {
                if (cursos[i][1].equals(nuevo)) {
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                JOptionPane.showMessageDialog(null, "El curso no existe.");
                return;
            }
            for (int i = 0; i < 4; i++) {
                if (matriculas[pos][i] != null && matriculas[pos][i].equals(nuevo)) {
                    JOptionPane.showMessageDialog(null, "El estudiante ya tiene este curso.");
                    return;
                }
            }
            matriculas[pos][espacio] = nuevo;
            JOptionPane.showMessageDialog(null, "Curso agregado correctamente.");
        } else {
            JOptionPane.showMessageDialog(null, "Opcion no valida.");
        }
    }

    // Opcion 9: Buscar estudiante por ID
    public static void buscarEstudiantePorID() {
        String id = JOptionPane.showInputDialog("Ingrese el numero de identificacion del estudiante:");
        int pos = -1;
        for (int i = 0; i < contadorEstudiantes; i++) {
            if (estudiantes[i][0].equals(id)) {
                pos = i;
                break;
            }
        }
        if (pos == -1) {
            JOptionPane.showMessageDialog(null, "Estudiante no encontrado.");
            return;
        }
        String datos = "Datos del estudiante:\n\n";
        datos += "ID: " + estudiantes[pos][0] + "\n";
        datos += "Nombre: " + estudiantes[pos][1] + "\n";
        datos += "Programa: " + estudiantes[pos][2] + "\n";
        datos += "Correo: " + estudiantes[pos][3] + "\n";
        datos += "Telefono: " + estudiantes[pos][4] + "\n";
        datos += "Estado: " + estudiantes[pos][5] + "\n\n";
        datos += "Cursos matriculados:\n";
        boolean tieneCursos = false;
        for (int j = 0; j < 4; j++) {
            String cod = matriculas[pos][j];
            if (cod != null && !cod.equals("9999")) {
                String nombreCurso = "(no encontrado)";
                for (int k = 0; k < contadorCursos; k++) {
                    if (cursos[k][1].equals(cod)) {
                        nombreCurso = cursos[k][2];
                        break;
                    }
                }
                datos += "- " + cod + ": " + nombreCurso + "\n";
                tieneCursos = true;
            }
        }
        if (!tieneCursos) {
            datos += "No tiene cursos matriculados.\n";
        }
        JOptionPane.showMessageDialog(null, datos);
    }

    // Funciones de validacion basicas

    public static boolean esNumero(String texto) {
        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    public static boolean esSoloLetras(String texto) {
        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == ' ')) {
                return false;
            }
        }
        return true;
    }

    public static boolean esCorreoValido(String correo) {
        if (correo.indexOf("@") != -1 && correo.indexOf(".") != -1) {
            return true;
        }
        return false;
    }

    public static boolean esProgramaValido(String programa) {
        if (programa.equals("01") || programa.equals("02") || programa.equals("03")) {
            return true;
        }
        return false;
    }

    public static boolean esEstadoValido(String estado) {
        if (estado.equals("A") || estado.equals("I") || estado.equals("a") || estado.equals("i")) {
            return true;
        }
        return false;
    }
}
