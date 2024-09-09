package org.gamma.buenosayres.dto;

import java.time.LocalDate;

public class IngresosResponse {
	private LocalDate periodo;
	private Double total;
	public IngresosResponse(Double total, LocalDate periodo)
	{
		this.total = total;
		this.periodo = periodo;
	}

	public LocalDate getPeriodo()
	{
		return periodo;
	}

	public void setPeriodo(LocalDate periodo)
	{
		this.periodo = periodo;
	}

	public Double getTotal()
	{
		return total;
	}

	public void setTotal(Double total)
	{
		this.total = total;
	}
}
