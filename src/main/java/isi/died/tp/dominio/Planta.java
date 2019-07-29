package isi.died.tp.dominio;

import java.util.List;
import java.util.stream.Collectors;

public class Planta {

		private Integer id;
		private String nombre;
		private List<Stock> listaStock;
		
		public Planta() {
			
		}
		
		public Planta(String nombre) {
			this.nombre = nombre;
		}
		
		public Planta(Integer id, String nombre) {
			this.id=id;
			this.nombre=nombre;
		}
		
		public Planta(String nombre, List<Stock> listaStock) {
			this.nombre = nombre;
			this.listaStock = listaStock;
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
			return listaStock.stream().anyMatch((s) -> s.getInsumo()==i && s.getCantidad()<=s.getPuntoPedido());
		}
		
		@Override
		public boolean equals(Object obj) {
			Planta p = (Planta)obj;
			return id.equals(p.getId()) && nombre.equals(p.getNombre());
		}

		public Stock buscarStock(String nombre) {
			for(Stock s: listaStock) {
				if(s.getInsumo().getNombre().equals(nombre))
					return s;
			}
			return null;
		}
}
