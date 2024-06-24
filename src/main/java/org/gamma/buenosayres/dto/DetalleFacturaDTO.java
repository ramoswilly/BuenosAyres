package org.gamma.buenosayres.dto;

import java.util.Date;

public class DetalleFacturaDTO {
     private ConceptoDTO concepto;
     private AlumnoDTO alumno;
     private int descuento;
     private boolean abonado;
     private Date fechaPago;
	public ConceptoDTO getConcepto() {
		return concepto;
	}
	public void setConcepto(ConceptoDTO concepto) {
		this.concepto = concepto;
	}
	public AlumnoDTO getAlumno() {
		return alumno;
	}
	public void setAlumno(AlumnoDTO alumno) {
		this.alumno = alumno;
	}
	public int getDescuento() {
		return descuento;
	}
	public void setDescuento(int descuento) {
		this.descuento = descuento;
	}
	public boolean isAbonado() {
		return abonado;
	}
	public void setAbonado(boolean abonado) {
		this.abonado = abonado;
	}
	public Date getFechaPago() {
		return fechaPago;
	}
	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}
     
}
