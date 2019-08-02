package isi.died.tp.dominio;

import isi.died.tp.dominio.Insumo.UnidadDeMedida;

public class Liquido extends Insumo {
	
	private Double densidad;
	
	public Liquido() {
		
	}
	
	public Liquido(String nombre, String descripcion, Double costo, Integer stock, Boolean esRefrigerado, Double densidad, Boolean esLiquido) {
		super(nombre, descripcion, UnidadDeMedida.Litro, costo, stock, 1*0.001*densidad , esRefrigerado, esLiquido);
		this.densidad = densidad;
	}
	
	@Override
	public Double getDensidad() {
		return densidad;
	}

	@Override
	public void setDensidad(Double densidad) {
		this.densidad = densidad;
	}

	public Double calcularPeso(Double litros) {
		return (litros*0.001*densidad);
	}
	
	@Override
	public Boolean getEsLiquido() {
		return esLiquido;
	}
	
	@Override
	public void setEsLiquido(Boolean esLiquido) {
		this.esLiquido=esLiquido;
	}
	
	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String getNombre() {
		return nombre;
	}

	@Override
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Override
	public String getDescripcion() {
		return descripcion;
	}
	
	@Override
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public UnidadDeMedida getUnidadDeMedida() {
		return unidadDeMedida;
	}

	@Override
	public void setUnidadDeMedida(UnidadDeMedida unidadDeMedida) {
		this.unidadDeMedida = unidadDeMedida;
	}
	
	@Override
	public void setUnidadDeMedida(String tipo) {
		this.unidadDeMedida = UnidadDeMedida.valueOf(tipo);
	}

	@Override
	public Double getCosto() {
		return costo;
	}

	@Override
	public void setCosto(Double costo) {
		this.costo = costo;
	}

	@Override
	public Integer getStock() {
		return stock;
	}

	@Override
	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Double getPeso() {
		return peso;
	}

	@Override
	public void setPeso(Double peso) {
		this.peso = peso;
	}

	@Override
	public Boolean getEsRefrigerado() {
		return esRefrigerado;
	}

	@Override
	public void setEsRefrigerado(Boolean esRefrigerado) {
		this.esRefrigerado = esRefrigerado;
	}

}
