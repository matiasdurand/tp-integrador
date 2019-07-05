package isi.died.tp.dominio;

public class Camion {

	private Integer id;
	private String marca;
	private String modelo;
	private String dominio;
	private Integer año;
	private Integer capacidad;
	private Double costoPorKm;
	private Boolean aptoParaLiquidos;
	
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getMarca() {
		return marca;
	}
	
	public void setMarca(String marca) {
		this.marca = marca;
	}
	
	public String getModelo() {
		return modelo;
	}
	
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	
	public String getDominio() {
		return dominio;
	}
	
	public void setDominio(String dominio) {
		this.dominio = dominio;
	}
	
	public Integer getAño() {
		return año;
	}
	
	public void setAño(Integer año) {
		this.año = año;
	}
	
	public Integer getCapacidad() {
		return capacidad;
	}
	
	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
	}
	
	public Double getCostoPorKm() {
		return costoPorKm;
	}
	
	public void setCostoPorKm(Double costoPorKm) {
		this.costoPorKm = costoPorKm;
	}
	
	public Boolean getAptoParaLiquidos() {
		return aptoParaLiquidos;
	}
	
	public void setAptoParaLiquidos(Boolean aptoParaLiquidos) {
		this.aptoParaLiquidos = aptoParaLiquidos;
	}
	
}
