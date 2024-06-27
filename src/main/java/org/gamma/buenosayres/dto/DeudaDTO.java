package org.gamma.buenosayres.dto;

import java.util.List;
import java.util.UUID;

public class DeudaDTO {
    private UUID idFamilia;
    private String apellidoFamilia;
    private float deudaTotal;
    private List<FacturaDTO> facturas;
    public UUID getIdFamilia() {
        return idFamilia;
    }
    public void setIdFamilia(UUID idFamilia) {
        this.idFamilia = idFamilia;
    }
    public String getApellidoFamilia() {
        return apellidoFamilia;
    }
    public void setApellidoFamilia(String apellidoFamilia) {
        this.apellidoFamilia = apellidoFamilia;
    }
    public float getDeudaTotal() {
        return deudaTotal;
    }
    public void setDeudaTotal(float deudaTotal) {
        this.deudaTotal = deudaTotal;
    }
    public List<FacturaDTO> getFacturas() {
        return facturas;
    }
    public void setFacturas(List<FacturaDTO> facturas) {
        this.facturas = facturas;
    }

}
