package isi.died.tp.controladores;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import isi.died.tp.dao.PlantaDao;
import isi.died.tp.dao.PlantaDaoH2;
import isi.died.tp.dominio.Insumo;
import isi.died.tp.dominio.Planta;
import isi.died.tp.dominio.Stock;
import isi.died.tp.estructuras.Arista;
import isi.died.tp.estructuras.GrafoPlantas;
import isi.died.tp.gui.PanelPlanta;

public class ControladorPlantas {

	private static ControladorPlantas _INSTANCIA = null;
	private PanelPlanta pPlanta;
	private GrafoPlantas grafoPlantas;
	private ControladorInsumos controladorInsumos;
	private PlantaDao dao;
	public static final Font FUENTE_TITULO = new Font("Calibri",Font.BOLD,18);
	public static final Color COLOR_TITULO = new Color(5,85,244);
	
	
	private ControladorPlantas() {
		dao = new PlantaDaoH2();
		grafoPlantas = GrafoPlantas.getInstance();
	}
	
	public static ControladorPlantas getInstance() {
		if(_INSTANCIA == null) _INSTANCIA = new ControladorPlantas();
		return _INSTANCIA;
	}

	public PanelPlanta getpPlanta() {
		return pPlanta;
	}

	public void setpPlanta(PanelPlanta pPlanta) {
		this.pPlanta = pPlanta;
	}
	
	public List<Planta> buscarPlantas(){
		return dao.buscarTodas();
	}
	
	public List<Planta> necesitanInsumo(Insumo i){
		List<Planta> plantas = buscarPlantas();
		return plantas.stream().filter(p->p.necesitaInsumo(i)).collect(Collectors.toList());
	}

	public List<Arista<Planta>> getAristasGrafoPlantas() {
		return grafoPlantas.getAristas();
	}

	public List<List<Planta>> buscarMejorCamino(Insumo i, Boolean priorizarDistancia) {
		List<Planta> plantas = necesitanInsumo(i);
		return grafoPlantas.buscarMejoresCaminos(plantas, priorizarDistancia);
		
	}

	public HashMap<List<Planta>, Double[]> buscarCaminos(Integer idOrigen, Integer idDestino) {
		Planta origen = dao.buscar(idOrigen);
		Planta destino = dao.buscar(idDestino);
		return grafoPlantas.buscarCaminos(origen, destino);
	}
	
	public void cargarComboInsumos(JComboBox<Insumo> combo){
		Runnable r = () -> {
				List<Insumo> insumos = controladorInsumos.buscarTodos();
				//List<Insumo> insumos = Aplicacion.listaInsumos;//para probar
				try {
					SwingUtilities.invokeAndWait(() -> {
						for(Insumo i: insumos){
							combo.addItem(i);
						}
					});
				} catch (InvocationTargetException | InterruptedException e) {
					e.printStackTrace();
				}
			};
			Thread hilo = new Thread(r);
			hilo.start();	
	}
	
	public void crearPlanta(String nombre) {
		Runnable r = () -> {
			Planta p = new Planta(nombre);
			dao.crear(p);
			List<Planta> lista = dao.buscarTodas();
			try {
				SwingUtilities.invokeAndWait(() -> {
					pPlanta.actualizarDatosTabla(lista);
					JOptionPane.showMessageDialog((Component) pPlanta, "La planta ha sido creada correctamente");
				});
			} catch (InvocationTargetException | InterruptedException e) {
				e.printStackTrace();
			}
		};
		
		Thread hilo = new Thread(r);
		
		hilo.start();
	}
	
	public void actualizarPlanta(Integer id, String nombre) {
		Runnable r = () -> {
			Planta p = new Planta(id, nombre);
			dao.actualizar(p);
			List<Planta> lista = dao.buscarTodas();
			try {
				SwingUtilities.invokeAndWait(() -> {
					pPlanta.actualizarDatosTabla(lista);
					JOptionPane.showMessageDialog((Component) pPlanta, "La planta ha sido actualizada correctamente");
				});
			} catch (InvocationTargetException | InterruptedException e) {
				e.printStackTrace();
			}
		};
		
		Thread hilo = new Thread(r);
		
		hilo.start();	
	}
	
	public void borrarPlanta(Integer id) {
		Runnable r = () -> {
			dao.borrar(id);
			List<Planta> lista = dao.buscarTodas();
			try {
				SwingUtilities.invokeAndWait(() -> {
					pPlanta.actualizarDatosTabla(lista);
					JOptionPane.showMessageDialog((Component) pPlanta, "La planta ha sido eliminada correctamente");
				});
			} catch (InvocationTargetException | InterruptedException e) {
				e.printStackTrace();
			}
		};
		
		Thread hilo = new Thread(r);
		
		hilo.start();
	}

	public void cargarComboPlantasExceptoSeleccionada(JComboBox<String> combo, Integer id) {
		Runnable r = () -> {
			List<Planta> plantas = dao.buscarTodas();
			//List<Planta> plantas = Aplicacion.listaPlantas;//para probar
			try {
				SwingUtilities.invokeAndWait(() -> {
					for(Planta p: plantas){
						if(p.getId()!=id) {
							combo.addItem(p.getNombre());
						}
					}
				});
			} catch (InvocationTargetException | InterruptedException e) {
				e.printStackTrace();
			}
		};
		Thread hilo = new Thread(r);
		hilo.start();
		
	}
	
	public Planta obtenerPlanta(Integer id) {
		return dao.buscar(id);
	}
	
	public Planta obtenerPlanta(String nombre) {
		List<Planta> plantas = dao.buscarTodas();
		for(Planta p: plantas) {
			if(p.getNombre().equals(nombre)) {
				return p;
			}
		}
		return null;
	}
	
	public List<Stock> obtenerInsumos(Integer id){
		Planta aux = dao.buscar(id);
		return aux.getListaStock();
	}

	public void conectarPlantas(Integer idPlanta1, Integer idPlanta2, Double distancia, Double duracion, Double pesoMax) {
		Planta p1 = dao.buscar(idPlanta1);
		Planta p2 = dao.buscar(idPlanta2);
		grafoPlantas.conectar(p1, p2, distancia, duracion, pesoMax);
	}
	
	public void cargarStock(Integer id, Insumo i, Integer cantidad, Integer puntoPedido) {
		//Comportamiento
		Stock aux = dao.buscar(id).buscarStock(i.getNombre());
		aux.aumentarStock(cantidad);
		aux.setPuntoPedido(puntoPedido);
	}
	
}