package org.gamma.buenosayres.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class FacturacionRequestDTO {
    List<UUID> adicionales;
    boolean matricula;
    LocalDate periodo;
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
    public LocalDate getPeriodo()
    {
        return periodo;
    }

    public void setPeriodo(LocalDate periodo)
    {
        this.periodo = periodo;
    }
}
