package isi.died.tp.controladores;

import java.awt.Component;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import isi.died.tp.dao.InsumoDao;
import isi.died.tp.dao.InsumoDaoH2;
import isi.died.tp.dominio.Insumo;
import isi.died.tp.dominio.Insumo.UnidadDeMedida;
import isi.died.tp.dominio.Liquido;
import isi.died.tp.gui.PanelEditarInsumo;
import isi.died.tp.gui.PanelInsumo;
import isi.died.tp.gui.PanelRegistrarInsumo;

public class ControladorInsumos {

	private static ControladorInsumos _INSTANCIA = null;
	private PanelInsumo pInsumo;
	private PanelRegistrarInsumo pRInsumo;
	private PanelEditarInsumo pEInsumo;
	private InsumoDao dao;
	
	private ControladorInsumos() {
		//EL CONTROLADOR NO PUEDE SER INSTANCIADO FUERA DE ESTE AMBITO
		dao = new InsumoDaoH2();
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
	
	public PanelRegistrarInsumo getpRInsumo() {
		return pRInsumo;
	}
	
	public void setpRInsumo(PanelRegistrarInsumo pRInsumo) {
		this.pRInsumo = pRInsumo;
	}
	
	public PanelEditarInsumo getpEInsumo() {
		return pEInsumo;
	}
	
	public void setpEInsumo(PanelEditarInsumo pEInsumo) {
		this.pEInsumo = pEInsumo;
	}
	
	public Insumo crearInsumo(String nombre, String descripcion, UnidadDeMedida udm, Double costo, Integer stock, Double peso, Boolean esRefrigerado, Double densidad) {
		Insumo i;
		if(densidad>0) i = new Liquido(nombre, descripcion, costo, stock, esRefrigerado, densidad);
		else {
			i = new Insumo(nombre, descripcion, udm, costo, stock, peso, esRefrigerado);
			i.setEsLiquido(false);
		}
		i=dao.crear(i);
		Runnable r = () -> {
			List<Insumo> listaInsumos = dao.buscarTodos();
			try {
				SwingUtilities.invokeAndWait(() -> {
					pInsumo.actualizarTablaInsumos(listaInsumos);
					JOptionPane.showMessageDialog((Component)pInsumo, "El insumo ha sido creado correctamente");
					
				});
			} catch (InvocationTargetException | InterruptedException e) {
				e.printStackTrace();
			}
		};
		Thread hilo = new Thread(r);
		hilo.start();
		
		return i;
	}
	
	public void actualizarInsumo(Integer id, String nombre, String descripcion, UnidadDeMedida udm, Double costo, Integer stock, Double peso, Boolean esRefrigerado, Double densidad) {
		Runnable r = () -> {
			Insumo i;
			if(densidad>0) i = new Liquido(nombre, descripcion, costo, stock, esRefrigerado, densidad);
			else i = new Insumo(nombre, descripcion, udm, costo, stock, peso, esRefrigerado);
			i.setId(id);
			dao.actualizar(i);
			List<Insumo> listaInsumos = dao.buscarTodos();
			try {
				SwingUtilities.invokeAndWait(() -> {
					pInsumo.actualizarTablaInsumos(listaInsumos);
					JOptionPane.showMessageDialog((Component)pInsumo, "El insumo ha sido actualizado correctamente");
				});
			} catch (InvocationTargetException | InterruptedException e) {
				e.printStackTrace();
			}
		};
		Thread hilo = new Thread(r);
		hilo.start();
	}
	
	public void borrarInsumo(Integer id) {
		Runnable r = () -> {
			dao.borrar(id);
			List<Insumo> listaInsumos = dao.buscarTodos();
			try {
				SwingUtilities.invokeAndWait(() -> {
					pInsumo.actualizarTablaInsumos(listaInsumos);
					JOptionPane.showMessageDialog((Component)pInsumo, "El insumo ha sido eliminado correctamente");
				});
			} catch (InvocationTargetException | InterruptedException e) {
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
		Double costoMin, costoMax;
		Integer stockMin, stockMax;
		List<Insumo> filtrado = dao.buscarTodos();
		if(!nombre.isEmpty() && !filtrado.isEmpty()) {
			filtrado = filtrado.stream().filter( i -> i.getNombre().equals(nombre)).collect(Collectors.toList());
		}
		if(!costoMinimo.isEmpty() && !filtrado.isEmpty()) {
			costoMin = Double.parseDouble(costoMinimo);
			filtrado = filtrado.stream().filter( i -> i.getCosto()>=costoMin).collect(Collectors.toList());
		}
		if(!costoMaximo.isEmpty() && !filtrado.isEmpty()) {
			costoMax = Double.parseDouble(costoMaximo);
			filtrado = filtrado.stream().filter( i -> i.getCosto()<=costoMax).collect(Collectors.toList());
		}
		if(!stockMinimo.isEmpty() && !filtrado.isEmpty()) {
			stockMin = Integer.parseInt(stockMinimo);
			filtrado = filtrado.stream().filter( i -> i.getStock()>=stockMin).collect(Collectors.toList());
		}
		if(!stockMaximo.isEmpty() && !filtrado.isEmpty()) {
			stockMax = Integer.parseInt(stockMaximo);
			filtrado = filtrado.stream().filter( i -> i.getStock()<=stockMax).collect(Collectors.toList());
		}
		return filtrado;
	}
	
	public List<Insumo> ordenarPor(List<Insumo> insumos, String criterio, Boolean descendente) {
		
		if(criterio.equals("Nombre")) {
			if(!descendente) {
				Collection<Insumo> arbol = new TreeSet<Insumo>((i1,i2) -> i1.getNombre().compareTo(i2.getNombre()));
				arbol.addAll(insumos);
				insumos.clear();
				insumos.addAll(arbol);
			}
			else {
				Collection<Insumo> arbol = new TreeSet<Insumo>((i1,i2) -> i2.getNombre().compareTo(i1.getNombre()));
				arbol.addAll(insumos);
				insumos.clear();
				insumos.addAll(arbol);
			}
		}
		else {
			if(criterio.equals("Stock total")) {
				if(!descendente) {
					Collection<Insumo> arbol = new TreeSet<Insumo>((i1,i2) -> i1.getStock().compareTo(i2.getStock()));
					arbol.addAll(insumos);
					insumos.clear();
					insumos.addAll(arbol);
				}
				else {
					Collection<Insumo> arbol = new TreeSet<Insumo>((i1,i2) -> i2.getStock().compareTo(i1.getStock()));
					arbol.addAll(insumos);
					insumos.clear();
					insumos.addAll(arbol);
				}
			}
			else {
				if(criterio.equals("Costo")) {
					if(!descendente) {
						Collection<Insumo> arbol = new TreeSet<Insumo>((i1,i2) -> i1.getCosto().compareTo(i2.getCosto()));
						arbol.addAll(insumos);
						insumos.clear();
						insumos.addAll(arbol);
					}
					else {
						Collection<Insumo> arbol = new TreeSet<Insumo>((i1,i2) -> i2.getCosto().compareTo(i1.getCosto()));
						arbol.addAll(insumos);
						insumos.clear();
						insumos.addAll(arbol);
					}
				}
			}
		}
		return insumos;
	}
	
	public void cargarComboOrdenarPor(JComboBox<String> combo){
		Runnable r = () -> {
				String[] criterios = {"Nombre", "Stock total", "Costo"};
				try {
					SwingUtilities.invokeAndWait(() -> {
						for(String criterio: criterios){
							combo.addItem(criterio);
						}
					});
				} catch (InvocationTargetException | InterruptedException e) {
					e.printStackTrace();
				}
			};
			Thread hilo = new Thread(r);
			hilo.start();	
		}
	
	public void cargarComboUDM(JComboBox<UnidadDeMedida> combo){
		Runnable r = () -> {
				UnidadDeMedida[] unidadesDeMedida = UnidadDeMedida.values();
				try {
					SwingUtilities.invokeAndWait(() -> {
						for(UnidadDeMedida udm: unidadesDeMedida){
							combo.addItem(udm);
						}
					});
				} catch (InvocationTargetException | InterruptedException e) {
					e.printStackTrace();
				}
			};
			Thread hilo = new Thread(r);
			hilo.start();	
		}
	
	public Boolean esLiquido(Integer idInsumo) {
		Insumo i = dao.buscar(idInsumo);
		if(i.getClass().getSimpleName().equals("Liquido")) return true;
		else return false;
	}
	
	public Insumo buscarInsumo(Integer id){
		return dao.buscar(id);
	}

	public List<Insumo> buscarTodos() {
		return dao.buscarTodos();
	}

	public void almacenar(Insumo i) {
		ControladorPlantas.getInstance().almacenar(i);
	}


}
