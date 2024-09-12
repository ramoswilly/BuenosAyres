package org.gamma.buenosayres.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminViewController {
        //index
        @GetMapping("/index")
        public String index() {
        return "admin/alumnos";
    }

        // Alumnos
        @GetMapping("/alumnos")
        public String alumnos() {
        return "admin/alumnos";
    }

        @GetMapping("/agregar-alumno")
        public String agregarAlumno() {
        return "admin/agregar-alumno";
    }

        @GetMapping("/editar-alumno")
        public String editarAlumno() {
        return "admin/editar-alumno";
    }

        // Padres
        @GetMapping("/padres")
        public String padres() {
        return "admin/padres";
    }

        @GetMapping("/agregar-padre")
        public String agregarPadre() {
        return "admin/agregar-padre";
    }

        @GetMapping("/editar-padre")
        public String editarPadre() {
        return "admin/editar-padre";
    }

        // Familias
        @GetMapping("/familias")
        public String familias() {
        return "admin/familias";
    }

        @GetMapping("/agregar-familia")
        public String agregarFamilia() {
        return "admin/agregar-familia";
    }

        @GetMapping("/editar-familia")
        public String editarFamilia() {
        return "admin/editar-familia";
    }

        @GetMapping("/ver-familias")
        public String verFamilias() {
        return "admin/ver-familias";
    }

        // Talleres
        @GetMapping("/talleres")
        public String talleres() {
        return "admin/talleres";
    }

        // Conceptos
        @GetMapping("/conceptos")
        public String conceptos() {
        return "admin/conceptos";
    }

        // Facturación
        @GetMapping("/facturacion")
        public String facturacion() {
        return "admin/facturacion";
    }

        @GetMapping("/deudas")
        public String deudas() {
        return "admin/deudas";
    }

        // Profesores
        @GetMapping("/profesores")
        public String profesores() {
        return "admin/profesores";
    }

        @GetMapping("/agregar-profesor")
        public String agregarProfesor() {
        return "admin/agregar-profesor";
    }

        @GetMapping("/editar-profesor")
        public String editarProfesor() {
        return "admin/editar-profesor";
    }

        // Cursos
        @GetMapping("/cursos")
        public String cursos() {
        return "admin/cursos";
    }

        // Materias
        @GetMapping("/materias")
        public String materias() {
        return "admin/materias";
    }

        @GetMapping("/agregar-materia")
        public String agregarMateria() {
        return "admin/agregar-materia";
    }

        @GetMapping("/editar-materia")
        public String editarMateria() {
        return "admin/editar-materia";
    }

        // Evaluaciones
        @GetMapping("/evaluaciones")
        public String evaluaciones() {
        return "admin/evaluaciones";
    }

        @GetMapping("/agregar-evaluacion")
        public String agregarEvaluacion() {
        return "admin/agregar-evaluacion";
    }

        @GetMapping("/evaluaciones-creadas")
        public String evaluacionesCreadas() {
        return "admin/evaluaciones-creadas";
    }

        // Disciplina
        @GetMapping("/disciplina")
        public String disciplina() {
        return "admin/disciplina";
    }

        @GetMapping("/agregar-sancion")
        public String agregarSancion() {
        return "admin/agregar-sancion";
    }

        // Inasistencias
        @GetMapping("/inasistencias")
        public String inasistencias() {
        return "admin/inasistencias";
    }

        @GetMapping("/agregar-inasistencia")
        public String agregarInasistencia() {
        return "admin/agregar-inasistencia";
    }

        // Reportes
        @GetMapping("/ver-reportes")
        public String verReportes() {
        return "admin/ver-reportes";
    }
}
