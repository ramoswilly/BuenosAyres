package org.gamma.buenosayres.dto;

public class BoletinItemDTO {
	private String materia;
	private float primerTrimestre;
	private float segundoTrimestre;
	private float tercerTrimestre;
	private float final_;

	public String getMateria()
	{
		return materia;
	}

	public void setMateria(String materia)
	{
		this.materia = materia;
	}

	public float getPrimerTrimestre()
	{
		return primerTrimestre;
	}

	public void setPrimerTrimestre(float primerTrimestre)
	{
		this.primerTrimestre = primerTrimestre;
	}

	public float getSegundoTrimestre()
	{
		return segundoTrimestre;
	}

	public void setSegundoTrimestre(float segundoTrimestre)
	{
		this.segundoTrimestre = segundoTrimestre;
	}

	public float getTercerTrimestre()
	{
		return tercerTrimestre;
	}

	public void setTercerTrimestre(float tercerTrimestre)
	{
		this.tercerTrimestre = tercerTrimestre;
	}

	public float getFinal()
	{
		return final_;
	}

	public void setFinal(float final_)
	{
		this.final_ = final_;
	}
}