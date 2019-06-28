package isi.died.tp.dominio;

public class Insumo implements Comparable<Insumo> {
	
	protected Integer id;
	protected String descripcion;
	protected UnidadDeMedida unidadDeMedida;
	protected Double costo;
	protected Double stock;
	protected Double peso;
	protected Boolean esRefrigerado;
	private static int CANT_INSUMOS=0;
	
	/*public Insumo(String descripcion, UnidadDeMedida unidadDeMedida, Double costoEnUDM, Double stock, Double pesoEnKg, Boolean esRefrigerado) {
		this.id = CANT_INSUMOS++;
		this.descripcion = descripcion;
		this.unidadDeMedida = unidadDeMedida;
		this.costoEnUDM = costoEnUDM;
		this.stock = stock;
		this.pesoEnKg = pesoEnKg;
		this.esRefrigerado = esRefrigerado;
	}*/
	
	@Override
	public int compareTo(Insumo insumo) {
		return stock.compareTo(insumo.getStock());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Double getStock() {
		return stock;
	}

	public void setStock(Double stock) {
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
	
}
