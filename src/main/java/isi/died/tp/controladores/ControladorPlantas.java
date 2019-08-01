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
import isi.died.tp.dao.StockDao;
import isi.died.tp.dao.StockDaoH2;
import isi.died.tp.dominio.Insumo;
import isi.died.tp.dominio.Planta;
import isi.died.tp.dominio.Stock;
import isi.died.tp.estructuras.Arista;
import isi.died.tp.estructuras.GrafoPlantas;
import isi.died.tp.gui.PanelPlanta;
import isi.died.tp.gui.PanelPrincipal;

public class ControladorPlantas {

	private static ControladorPlantas _INSTANCIA = null;
	private PanelPlanta pPlanta;
	private GrafoPlantas grafoPlantas;
	private ControladorInsumos controladorInsumos;
	private PlantaDao dao;
	private StockDao daoStock;
	private PanelPrincipal pPrincipal;
	public static final Font FUENTE_TITULO_PRINCIPAL = new Font("Calibri",Font.BOLD,24);
	public static final Font FUENTE_TITULO = new Font("Calibri",Font.BOLD,18);
	public static final Color COLOR_TITULO = new Color(5,85,244);
	
	
	private ControladorPlantas() {
		controladorInsumos = ControladorInsumos.getInstance();
		dao = new PlantaDaoH2();
		daoStock = new StockDaoH2();
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
	
	public PanelPrincipal getpPrincipal() {
		return pPrincipal;
	}
	
	public void setpPrincipal(PanelPrincipal pPrincipal) {
		this.pPrincipal = pPrincipal;
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
			grafoPlantas.addNodo(dao.crear(p));
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
	
	public void crearAcopios(String nombre1, String nombre2) {
		Runnable r = () -> {
			Planta acopioInicial = new Planta(nombre1);
			Planta acopioFinal = new Planta(nombre2);
			grafoPlantas.addNodo(dao.crear(acopioInicial));
			grafoPlantas.addNodo(dao.crear(acopioFinal));
			List<Planta> lista = dao.buscarTodas();
			try {
				SwingUtilities.invokeAndWait(() -> {
					pPlanta.actualizarDatosTabla(lista);
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
			Planta p = new Planta(nombre);
			p.setId(id);
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

	public void cargarComboPlantasExceptoSeleccionada(JComboBox<Planta> combo, Integer id) {
		Runnable r = () -> {
			List<Planta> plantas = dao.buscarTodas();
			try {
				SwingUtilities.invokeAndWait(() -> {
					for(Planta p: plantas){
						if(p.getId()!=id) {
							combo.addItem(p);
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
	
	public List<Stock> buscarStock(Integer id){
		Planta aux = dao.buscar(id);
		return aux.getListaStock();
	}

	public void conectarPlantas(Integer idPlanta1, Integer idPlanta2, Double distancia, Double duracion, Double pesoMax) {
		Planta p1 = dao.buscar(idPlanta1);
		Planta p2 = dao.buscar(idPlanta2);
		grafoPlantas.conectar(p1, p2, distancia, duracion, pesoMax);
	}
	
	public void cargarStock(Integer id, Insumo i, Integer cantidad, Integer puntoPedido) {


		Planta acopioInicial = dao.buscar(1);
		
		if(acopioInicial.validarCantidad(i, cantidad)) {
			
			Stock s = new Stock(cantidad, puntoPedido, i);
			
			Planta p = dao.buscar(id);
			
			Boolean existe = p.existeStock(s);
			
			if(existe) daoStock.actualizar(id,s);
			else daoStock.crear(id,s);
			
			daoStock.actualizar(1, acopioInicial.actualizarStock(i, cantidad));
			
			pPlanta.actualizarDatosTablaStock(dao.buscar(id).getListaStock());

			grafoPlantas.actualizar(dao.buscar(id));
			grafoPlantas.actualizar(dao.buscar(1));
			
			JOptionPane.showMessageDialog((Component) pPlanta, "El stock ha sido cargado correctamente");
			

		}
		
	}
	
	public List<Planta> pageRank(){
		return grafoPlantas.pageRank();
	}

	public void calcularFlujoMaximo() {
		pPrincipal.mostrarFlujoMaximo(grafoPlantas.flujoMaximo());
	}

	public void almacenar(Insumo i) {
		daoStock.crear(1, new Stock(i.getStock(), 0, i));
	}
	
}