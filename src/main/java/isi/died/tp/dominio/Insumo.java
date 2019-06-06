package isi.died.tp.dominio;

public class Insumo implements Comparable<Insumo> {
	
	protected Integer id;
	protected String descripcion;
	protected UnidadDeMedida unidadDeMedida;
	protected Double costoEnUDM;
	protected Double stock;
	protected Double pesoEnKg;
	protected Boolean esRefrigerado;
	private static int CANT_INSUMOS=0;
	
	public Insumo(String descripcion, UnidadDeMedida unidadDeMedida, Double costoEnUDM, Double stock, Double pesoEnKg, Boolean esRefrigerado) {
		this.id = CANT_INSUMOS++;
		this.descripcion = descripcion;
		this.unidadDeMedida = unidadDeMedida;
		this.costoEnUDM = costoEnUDM;
		this.stock = stock;
		this.pesoEnKg = pesoEnKg;
		this.esRefrigerado = esRefrigerado;
	}

	public Double getStock() {
		return stock;
	}
	
	@Override
	public int compareTo(Insumo insumo) {
		return stock.compareTo(insumo.getStock());
	}
	
}
