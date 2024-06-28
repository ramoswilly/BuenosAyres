package org.gamma.buenosayres.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainViewController {

    //index
    @GetMapping("/index")
    public String index() {
        return "alumnos";
    }

    // Alumnos
    @GetMapping("/alumnos")
    public String alumnos() {
        return "alumnos";
    }

    @GetMapping("/agregar-alumno")
    public String agregarAlumno() {
        return "agregar-alumno";
    }

    @GetMapping("/editar-alumno")
    public String editarAlumno() {
        return "editar-alumno";
    }

    // Padres
    @GetMapping("/padres")
    public String padres() {
        return "padres";
    }

    @GetMapping("/agregar-padre")
    public String agregarPadre() {
        return "agregar-padre";
    }

    @GetMapping("/editar-padre")
    public String editarPadre() {
        return "editar-padre";
    }

    // Familias
    @GetMapping("/familias")
    public String familias() {
        return "familias";
    }

    @GetMapping("/agregar-familia")
    public String agregarFamilia() {
        return "agregar-familia";
    }

    @GetMapping("/editar-familia")
    public String editarFamilia() {
        return "editar-familia";
    }

    @GetMapping("/ver-familias")
    public String verFamilias() {
        return "ver-familias";
    }

    // Talleres
    @GetMapping("/talleres")
    public String talleres() {
        return "talleres";
    }

    @GetMapping("/editar-talleres")
    public String editarTalleres() {
        return "editar-talleres";
    }

    // Conceptos
    @GetMapping("/conceptos")
    public String conceptos() {
        return "conceptos";
    }

    // Facturación
    @GetMapping("/facturacion")
    public String facturacion() {
        return "facturacion";
    }

    @GetMapping("/detalles-facturacion")
    public String detallesFacturacion() {
        return "detalles-facturacion";
    }

    @GetMapping("/deudas")
    public String deudas() {
        return "deudas";
    }
}
