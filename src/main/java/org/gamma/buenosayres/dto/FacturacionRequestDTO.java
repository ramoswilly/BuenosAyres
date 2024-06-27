package org.gamma.buenosayres.dto;

import java.util.List;
import java.util.UUID;

public class FacturacionRequestDTO {
    List<UUID> adicionales;
    boolean matricula;
    public boolean facturarMatricula() {
        return matricula;
    }
    public void setMatricula(boolean matricula) {
        this.matricula = matricula;
    }
    public List<UUID> getAdicionales() {
        return adicionales;
    }
    public void setAdicionales(List<UUID> adicionales) {
        this.adicionales = adicionales;
    }

}
