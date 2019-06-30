package isi.died.tp.dominio;

public class Stock {
	
	private Integer id;
	private Integer cantidad;
	private Integer puntoPedido;
	private Insumo insumo;
	private static Integer CANT_STOCKS=0;
	
	public Stock(Integer cantidad, Integer puntoPedido, Insumo insumo) {
		id = CANT_STOCKS++;
		this.cantidad = cantidad;
		this.puntoPedido = puntoPedido;
		this.insumo = insumo;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getCantidad() {
		return cantidad;
	}
	
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	
	public Integer getPuntoPedido() {
		return puntoPedido;
	}
	
	public void setPuntoPedido(Integer puntoPedido) {
		this.puntoPedido = puntoPedido;
	}
	
	public Insumo getInsumo() {
		return insumo;
	}
	
	public void setInsumo(Insumo insumo) {
		this.insumo = insumo;
	}
	
}
