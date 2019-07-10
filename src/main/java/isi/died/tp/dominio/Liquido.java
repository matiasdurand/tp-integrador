package isi.died.tp.dominio;

public class Liquido extends Insumo {
	
	private Double densidad;
	
	
	public Liquido(String nombre, String descripcion, Double costo, Integer stock, Boolean esRefrigerado, Double densidad) {
		super(nombre, descripcion, UnidadDeMedida.Litro, costo, stock, 1*0.001*densidad , esRefrigerado);
		this.densidad = densidad;
	}
	
	public Double getDensidad() {
		return densidad;
	}

	public void setDensidad(Double densidad) {
		this.densidad = densidad;
	}

	public Double calcularPeso(Double litros) {
		return (litros*0.001*densidad);
	}

}
