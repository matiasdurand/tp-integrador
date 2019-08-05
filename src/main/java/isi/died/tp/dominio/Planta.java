package isi.died.tp.dominio;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Planta {

		private Integer id;
		private String nombre;
		private List<Stock> listaStock;
		
		public Planta() {};
		
		public Planta(String nombre) {
			this.nombre = nombre;
			listaStock = new ArrayList<Stock>();
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

		public List<Stock> getListaStock() {
			return listaStock;
		}

		public void setListaStock(List<Stock> listaStock) {
			this.listaStock = listaStock;
		}
		
		public Double costoTotal() {
			 return listaStock.stream().mapToDouble((s) -> s.getCantidad()*s.getInsumo().getCosto()).sum();
		}

		public List<Insumo> stockEntre(Integer s1, Integer s2){
			return listaStock.stream().filter((s) -> s.getCantidad()>=s1 && s.getCantidad()<=s2).map((s) -> s.getInsumo()).collect(Collectors.toList());
		}
		
		public Boolean necesitaInsumo(Insumo i) {
			return listaStock.stream().anyMatch((s) -> s.getInsumo().equals(i) && s.getCantidad()<=s.getPuntoPedido());
		}
		
		@Override
		public boolean equals(Object obj) {
			Planta p = (Planta)obj;
			return id.equals(p.getId()) && nombre.equals(p.getNombre());
		}
		
		@Override
		public String toString() {
			return nombre;
		}

		public Boolean existeStock(Stock s) {
			
			if(!listaStock.contains(s)) return false;
			else {
				Stock aux = listaStock.stream().filter( st -> st.equals(s)).collect(Collectors.toList()).get(0);
				
				s.aumentarStock(aux.getCantidad());
				
				s.setId(aux.getId());
				
				return true;
			}
		}

		public boolean validarCantidad(Insumo i, Integer cantidad) {
			
			Stock stock = listaStock.stream().filter( s -> s.getInsumo().equals(i)).collect(Collectors.toList()).get(0);
			
			if(stock.getCantidad()>=cantidad) return true;
			else return false;
			
		}
		
		public Integer disponible(Insumo i) {
			return listaStock.stream().filter(s->s.getInsumo().equals(i)).map(Stock::getCantidad).collect(Collectors.toList()).get(0);
		}
		
}
