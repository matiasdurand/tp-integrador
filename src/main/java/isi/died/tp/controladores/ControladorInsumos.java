package isi.died.tp.controladores;

import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import isi.died.tp.dao.InsumoDao;
import isi.died.tp.dao.InsumoDaoH2;
import isi.died.tp.dao.StockDao;
import isi.died.tp.dao.StockDaoH2;
import isi.died.tp.dominio.Insumo;
import isi.died.tp.dominio.Insumo.UnidadDeMedida;
import isi.died.tp.dominio.Liquido;
import isi.died.tp.estructuras.Arbol;
import isi.died.tp.estructuras.ArbolBinarioBusqueda;
import isi.died.tp.gui.PanelInsumo;

public class ControladorInsumos {

	private static ControladorInsumos _INSTANCIA = null;
	
	private PanelInsumo pInsumo;
	private InsumoDao daoInsumo;
	private StockDao daoStock;
	
	private ControladorInsumos() {
		daoInsumo = new InsumoDaoH2();
		daoStock = new StockDaoH2();
	}
	
	public static ControladorInsumos getInstance() {
		if(_INSTANCIA == null) _INSTANCIA = new ControladorInsumos();
		return _INSTANCIA;
	}
	
	public PanelInsumo getpInsumo() {
		return pInsumo;
	}
	
	public void setpInsumo(PanelInsumo pInsumo) {
		this.pInsumo = pInsumo;
	}
	
	public void crearInsumo(String nombre, String descripcion, UnidadDeMedida udm, Double costo, Integer stock, Double peso, Boolean esRefrigerado, Double densidad) {
		
		Runnable r = () -> {
			
			Insumo i;
			
			if(densidad>0) i = new Liquido(nombre, descripcion, costo, stock, esRefrigerado, densidad, true);
			else i = new Insumo(nombre, descripcion, udm, costo, stock, peso, esRefrigerado, false);
			
			almacenar(daoInsumo.crear(i));
			
			List<Insumo> insumos = daoInsumo.buscarTodos();
			
			try {
				SwingUtilities.invokeAndWait(() -> {
					pInsumo.actualizarTablaInsumos(insumos);
					JOptionPane.showMessageDialog(pInsumo, "El insumo ha sido creado correctamente");
					
				});
			} 
			catch (InvocationTargetException | InterruptedException e) {
				e.printStackTrace();
			}
		};
		
		Thread hilo = new Thread(r);
		hilo.start();

	}
	
	public void actualizarInsumo(Integer id, String nombre, String descripcion, UnidadDeMedida udm, Double costo, Integer stock, Double peso, Boolean esRefrigerado, Double densidad) {

		Runnable r = () -> {
			
			Insumo i;
			
			if(densidad>0) i = new Liquido(nombre, descripcion, costo, stock, esRefrigerado, densidad, true);
			else i = new Insumo(nombre, descripcion, udm, costo, stock, peso, esRefrigerado, false);
			
			i.setId(id);
			
			ControladorPlantas.getInstance().actualizarStocksEnGrafoPlantas(daoInsumo.actualizar(i));
			
			List<Insumo> insumos = daoInsumo.buscarTodos();
			
			try {
				SwingUtilities.invokeAndWait(() -> {
					pInsumo.actualizarTablaInsumos(insumos);
					JOptionPane.showMessageDialog(pInsumo, "El insumo ha sido actualizado correctamente");
				});
			} 
			catch (InvocationTargetException | InterruptedException e) {
				e.printStackTrace();
			}
			
		};
		
		Thread hilo = new Thread(r);
		hilo.start();
		
	}
	
	public void borrarInsumo(Integer id) {
		
		Runnable r = () -> {
			
			daoStock.borrar(-1, id);
			
			ControladorPlantas.getInstance().borrarStocksEnGrafoPlantas(daoInsumo.buscar(id));
			
			daoInsumo.borrar(id);
			
			List<Insumo> insumos = daoInsumo.buscarTodos();
			
			try {
				SwingUtilities.invokeAndWait(() -> {
					pInsumo.actualizarTablaInsumos(insumos);
					JOptionPane.showMessageDialog(pInsumo, "El insumo ha sido eliminado correctamente");
				});
			} 
			catch (InvocationTargetException | InterruptedException e) {
				e.printStackTrace();
			}
			
		};
		
		Thread hilo = new Thread(r);
		hilo.start();	
	}
	
	public Boolean validarDatos(Boolean esLiquido, String nombre, String descripcion, String costo, String stock, String peso, String densidad) {
		
		if(esLiquido && !nombre.isEmpty() && !descripcion.isEmpty() && !costo.isEmpty() && !stock.isEmpty() && !densidad.isEmpty()) return true;
		if(!esLiquido && !nombre.isEmpty() && !descripcion.isEmpty() && !costo.isEmpty() && !stock.isEmpty() && !peso.isEmpty()) return true;
		return false;
		
	}
	
	public List<Insumo> filtrar(String nombre, String costoMinimo, String costoMaximo, String stockMinimo, String stockMaximo) {
		
		List<Insumo> insumos = daoInsumo.buscarTodos();
		
		if(insumos.isEmpty()) return insumos;
		
		Double costoMin, costoMax;
		Integer stockMin, stockMax;
		
		if(costoMinimo.isEmpty()) costoMin = 0.0;
		else costoMin = Double.parseDouble(costoMinimo);
		
		if(costoMaximo.isEmpty()) costoMax = Double.MAX_VALUE;
		else costoMax = Double.parseDouble(costoMaximo);
		
		if(stockMinimo.isEmpty()) stockMin = 0;
		else stockMin = Integer.parseInt(stockMinimo);
		
		if(stockMaximo.isEmpty()) stockMax = Integer.MAX_VALUE;
		else stockMax = Integer.parseInt(stockMaximo);
		
		/*Arbol<Insumo> abb = new ArbolBinarioBusqueda<Insumo>(insumos.get(0));
		
		insumos.remove(0);
		
		if(!insumos.isEmpty()) for(Insumo i: insumos) abb.agregar(i);
		
		List<Insumo> filtrado = abb.rango(nombre, costoMin, costoMax, stockMin, stockMax);
		
		return filtrado;*/
		
		Arbol<Insumo> abb;
		
		if(!nombre.isEmpty()) {
			abb = new ArbolBinarioBusqueda<Insumo>(insumos.get(0), new Comparator<Insumo>(){ public int compare(Insumo i1, Insumo i2) { return i1.getNombre().compareTo(i2.getNombre());}});
			insumos.remove(0);
			if(!insumos.isEmpty()) for(Insumo i: insumos) abb.agregar(i);
			insumos = abb.rango(nombre);
		}
		
		if(!insumos.isEmpty() && (costoMin!=0 || costoMax!=Double.MAX_VALUE)) {
			abb = new ArbolBinarioBusqueda<Insumo>(insumos.get(0), new Comparator<Insumo>(){ public int compare(Insumo i1, Insumo i2) { return i1.getCosto().compareTo(i2.getCosto());}});
			insumos.remove(0);
			if(!insumos.isEmpty()) for(Insumo i: insumos) abb.agregar(i);
			insumos = abb.rango(costoMin, costoMax);
		}
		
		if(!insumos.isEmpty() && (stockMin!=0 || stockMax!=Integer.MAX_VALUE)) {
			abb = new ArbolBinarioBusqueda<Insumo>(insumos.get(0), new Comparator<Insumo>(){ public int compare(Insumo i1, Insumo i2) { return i1.getStock().compareTo(i2.getStock());}});
			insumos.remove(0);
			if(!insumos.isEmpty()) for(Insumo i: insumos) abb.agregar(i);
			insumos = abb.rango(stockMin, stockMax);
		}
	
		return insumos;
		
	}
	
	public void ordenarPor(List<Insumo> insumos, String criterio, Boolean descendente) {
		
		List<Insumo> insumosAOrdenar;
		
		if(insumos==null) insumosAOrdenar = daoInsumo.buscarTodos();
		else insumosAOrdenar = insumos.stream().collect(Collectors.toList());
		
		Arbol<Insumo> abb;
		
		if(!insumosAOrdenar.isEmpty()) {
			if(criterio.equals("Nombre")) {
				if(!descendente) {
					abb = new ArbolBinarioBusqueda<Insumo>(insumosAOrdenar.get(0), new Comparator<Insumo>(){ public int compare(Insumo i1, Insumo i2) { return i1.getNombre().compareTo(i2.getNombre());}});
					insumosAOrdenar.remove(0);
					if(!insumosAOrdenar.isEmpty()) for(Insumo i: insumosAOrdenar) abb.agregar(i);
					insumosAOrdenar = abb.inOrden();
				}
				else {
					abb = new ArbolBinarioBusqueda<Insumo>(insumosAOrdenar.get(0), new Comparator<Insumo>(){ public int compare(Insumo i1, Insumo i2) { return i2.getNombre().compareTo(i1.getNombre());}});
					insumosAOrdenar.remove(0);
					if(!insumosAOrdenar.isEmpty()) for(Insumo i: insumosAOrdenar) abb.agregar(i);
					insumosAOrdenar = abb.inOrden();
				}
			}
			else {
				if(criterio.equals("Stock total")) {
					if(!descendente) {
						abb = new ArbolBinarioBusqueda<Insumo>(insumosAOrdenar.get(0), new Comparator<Insumo>(){ public int compare(Insumo i1, Insumo i2) { return i1.getStock().compareTo(i2.getStock());}});
						insumosAOrdenar.remove(0);
						if(!insumosAOrdenar.isEmpty()) for(Insumo i: insumosAOrdenar) abb.agregar(i);
						insumosAOrdenar = abb.inOrden();
					}
					else {
						abb = new ArbolBinarioBusqueda<Insumo>(insumosAOrdenar.get(0), new Comparator<Insumo>(){ public int compare(Insumo i1, Insumo i2) { return i2.getStock().compareTo(i1.getStock());}});
						insumosAOrdenar.remove(0);
						if(!insumosAOrdenar.isEmpty()) for(Insumo i: insumosAOrdenar) abb.agregar(i);
						insumosAOrdenar = abb.inOrden();
					}
				}
				else {
					if(criterio.equals("Costo")) {
						if(!descendente) {
							abb = new ArbolBinarioBusqueda<Insumo>(insumosAOrdenar.get(0), new Comparator<Insumo>(){ public int compare(Insumo i1, Insumo i2) { return i1.getCosto().compareTo(i2.getCosto());}});
							insumosAOrdenar.remove(0);
							if(!insumosAOrdenar.isEmpty()) for(Insumo i: insumosAOrdenar) abb.agregar(i);
							insumosAOrdenar = abb.inOrden();
						}
						else {
							abb = new ArbolBinarioBusqueda<Insumo>(insumosAOrdenar.get(0), new Comparator<Insumo>(){ public int compare(Insumo i1, Insumo i2) { return i2.getCosto().compareTo(i1.getCosto());}});
							insumosAOrdenar.remove(0);
							if(!insumosAOrdenar.isEmpty()) for(Insumo i: insumosAOrdenar) abb.agregar(i);
							insumosAOrdenar = abb.inOrden();
						}
					}
				}
			}
		}
		
		pInsumo.actualizarTablaInsumos(insumosAOrdenar);
		
	}
	
	public void cargarComboOrdenarPor(JComboBox<String> combo){
		String[] criterios = {"Nombre", "Stock total", "Costo"};
		for(String criterio: criterios) combo.addItem(criterio);	
	}
	
	public Boolean esLiquido(Integer idInsumo) {
		if(daoInsumo.buscar(idInsumo).getClass().getSimpleName().equals("Liquido")) return true;
		else return false;
	}
	
	public Insumo buscarInsumo(Integer id){
		return daoInsumo.buscar(id);
	}

	public List<Insumo> buscarTodos() {
		return daoInsumo.buscarTodos();
	}

	public void almacenar(Insumo i) {
		ControladorPlantas.getInstance().almacenar(i);
	}

	public void aumentarStockInsumo(Insumo i, Integer cantidad) {
		
		Runnable r = () -> {
			
			i.setStock(i.getStock()+cantidad);
			
			daoInsumo.actualizar(i);
			
			List<Insumo> insumos = daoInsumo.buscarTodos();
			
			try {
				SwingUtilities.invokeAndWait(() -> {
					pInsumo.actualizarTablaInsumos(insumos);
				});
			} 
			catch (InvocationTargetException | InterruptedException e) {
				e.printStackTrace();
			}
			
		};
		
		Thread hilo = new Thread(r);
		hilo.start();
		
	}

	public void disminuirStockInsumo(Insumo i, Integer cantidad) {
		
		Runnable r = () -> {
			
			i.setStock(i.getStock()-cantidad);
			
			daoInsumo.actualizar(i);
			
			List<Insumo> insumos = daoInsumo.buscarTodos();
			
			try {
				SwingUtilities.invokeAndWait(() -> {
					pInsumo.actualizarTablaInsumos(insumos);
				});
			} 
			catch (InvocationTargetException | InterruptedException e) {
				e.printStackTrace();
			}
			
		};
		
		Thread hilo = new Thread(r);
		hilo.start();
		
	}

}
