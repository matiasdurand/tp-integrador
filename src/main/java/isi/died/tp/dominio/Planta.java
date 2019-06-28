package isi.died.tp.dominio;

import java.util.List;
import java.util.stream.Collectors;

public class Planta {

		private Integer id;
		private String nombre;
		private List<Stock> listaStocks;
		
		public Double costoTotal() {
			
			 return listaStocks.stream().mapToDouble((s)->s.getCantidad()*s.getInsumo().getCosto()).sum();

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

		public List<Stock> getListaStocks() {
			return listaStocks;
		}

		public void setListaStocks(List<Stock> listaStocks) {
			this.listaStocks = listaStocks;
		}

		public List<Insumo> stockEntre(Integer s1, Integer s2){
			
			return listaStocks.stream().filter((s)->s.getCantidad()>=s1 && s.getCantidad()<=s2).map((s)->s.getInsumo()).collect(Collectors.toList());

		}
		
		public Boolean necesitaInsumo(Insumo i) {
			
			return listaStocks.stream().anyMatch((s) -> s.getInsumo()==i && s.getCantidad()<s.getPuntoPedido());
		}
}
