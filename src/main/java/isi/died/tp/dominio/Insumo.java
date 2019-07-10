package isi.died.tp.dominio;

public class Insumo implements Comparable<Insumo> {
	
	public enum UnidadDeMedida { Gramo, Kilogramo, Metro, MetroCuadrado, MetroCubico, Litro, Pieza };
	
	protected Integer id;
	protected String nombre;
	protected String descripcion;
	protected UnidadDeMedida unidadDeMedida;
	protected Double costo;
	protected Integer stock;
	protected Double peso;
	protected Boolean esRefrigerado;
	
	public Insumo() {
		
	}
	
	public Insumo(String nombre, String descripcion, UnidadDeMedida unidadDeMedida, Double costo, Integer stock, Double peso, Boolean esRefrigerado) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.unidadDeMedida = unidadDeMedida;
		this.costo = costo;
		this.stock = stock;
		this.peso = peso;
		this.esRefrigerado = esRefrigerado;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public UnidadDeMedida getUnidadDeMedida() {
		return unidadDeMedida;
	}

	public void setUnidadDeMedida(UnidadDeMedida unidadDeMedida) {
		this.unidadDeMedida = unidadDeMedida;
	}

	public Double getCosto() {
		return costo;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	public Boolean getEsRefrigerado() {
		return esRefrigerado;
	}

	public void setEsRefrigerado(Boolean esRefrigerado) {
		this.esRefrigerado = esRefrigerado;
	}
	
	@Override
	public int compareTo(Insumo insumo) {
		return stock.compareTo(insumo.getStock());
	}
	
}
