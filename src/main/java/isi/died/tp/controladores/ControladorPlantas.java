package isi.died.tp.controladores;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import isi.died.tp.dao.CamionDao;
import isi.died.tp.dao.CamionDaoH2;
import isi.died.tp.dao.PlantaDao;
import isi.died.tp.dao.PlantaDaoH2;
import isi.died.tp.dao.StockDao;
import isi.died.tp.dao.StockDaoH2;
import isi.died.tp.dominio.Camion;
import isi.died.tp.dominio.Insumo;
import isi.died.tp.dominio.Planta;
import isi.died.tp.dominio.Stock;
import isi.died.tp.estructuras.Arista;
import isi.died.tp.estructuras.GrafoPlantas;
import isi.died.tp.gui.PanelMejorEnvio;
import isi.died.tp.gui.PanelPlanta;
import isi.died.tp.gui.PanelPrincipal;

public class ControladorPlantas {

	private static ControladorPlantas _INSTANCIA = null;
	private PanelPlanta pPlanta;
	private GrafoPlantas grafoPlantas;
	private ControladorInsumos controladorInsumos;
	private PlantaDao daoPlanta;
	private StockDao daoStock;
	private CamionDao daoCamion;
	private PanelPrincipal pPrincipal;
	private PanelMejorEnvio pMEnvio;
	public static final Font FUENTE_TITULO_PRINCIPAL = new Font("Calibri",Font.BOLD,24);
	public static final Font FUENTE_TITULO = new Font("Calibri",Font.BOLD,18);
	public static final Color COLOR_TITULO = new Color(5,85,244);
	
	
	private ControladorPlantas() {
		controladorInsumos = ControladorInsumos.getInstance();
		daoPlanta = new PlantaDaoH2();
		daoStock = new StockDaoH2();
		daoCamion = new CamionDaoH2();
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
		return daoPlanta.buscarTodas();
	}
	
	public void setpMEnvio(PanelMejorEnvio pMEnvio) {
		this.pMEnvio = pMEnvio;
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
		Planta origen = daoPlanta.buscar(idOrigen);
		Planta destino = daoPlanta.buscar(idDestino);
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
			grafoPlantas.addNodo(daoPlanta.crear(p));
			List<Planta> lista = daoPlanta.buscarTodas();
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
			grafoPlantas.addNodo(daoPlanta.crear(acopioInicial));
			grafoPlantas.addNodo(daoPlanta.crear(acopioFinal));
			List<Planta> lista = daoPlanta.buscarTodas();
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
			daoPlanta.actualizar(p);
			List<Planta> lista = daoPlanta.buscarTodas();
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
			daoPlanta.borrar(id);
			List<Planta> lista = daoPlanta.buscarTodas();
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
			List<Planta> plantas = daoPlanta.buscarTodas();
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
		return daoPlanta.buscar(id);
	}
	
	public Planta obtenerPlanta(String nombre) {
		List<Planta> plantas = daoPlanta.buscarTodas();
		for(Planta p: plantas) {
			if(p.getNombre().equals(nombre)) {
				return p;
			}
		}
		return null;
	}
	
	public List<Stock> buscarStock(Integer id){
		Planta aux = daoPlanta.buscar(id);
		return aux.getListaStock();
	}

	public void conectarPlantas(Integer idPlanta1, Integer idPlanta2, Double distancia, Double duracion, Double pesoMax) {
		Planta p1 = daoPlanta.buscar(idPlanta1);
		Planta p2 = daoPlanta.buscar(idPlanta2);
		grafoPlantas.conectar(p1, p2, distancia, duracion, pesoMax);
	}
	
	public void cargarStock(Integer id, Insumo i, Integer cantidad, Integer puntoPedido) {


		Planta acopioInicial = daoPlanta.buscar(1);
		
		if(acopioInicial.validarCantidad(i, cantidad)) {
			
			Stock s = new Stock(cantidad, puntoPedido, i);
			
			Planta p = daoPlanta.buscar(id);
			
			Boolean existe = p.existeStock(s);
			
			if(existe) daoStock.actualizar(id,s);
			else daoStock.crear(id,s);
			
			daoStock.actualizar(1, acopioInicial.actualizarStock(i, cantidad));
			
			pPlanta.actualizarDatosTablaStock(daoPlanta.buscar(id).getListaStock());

			grafoPlantas.actualizar(daoPlanta.buscar(id));
			grafoPlantas.actualizar(daoPlanta.buscar(1));
			
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

	public List<Stock> buscarStockFaltante(Integer idPlantaSeleccionada) {
		List<Stock> stock = buscarStock(idPlantaSeleccionada);
		return stock.stream().filter(s -> s.getCantidad()<=s.getPuntoPedido()).collect(Collectors.toList());
	}

	public void cargarPlantas() {
		List<Planta> listaPlantas = daoPlanta.buscarTodas();
		pMEnvio.actualizarDatosTablaPlantas(listaPlantas);
	}

	/*public void mejorEnvio(Integer idCamionSeleccionado) {
		
		Camion camion = daoCamion.buscar(idCamionSeleccionado);
		
		List<Planta> plantas = daoPlanta.buscarTodas();
		
		Set<Insumo> insumosFaltantes = new HashSet<Insumo>();
		
		for(Planta p: plantas) {
			insumosFaltantes.addAll(buscarStockFaltante(p.getId()).stream().map(Stock::getInsumo).collect(Collectors.toList()));
		}
		
		System.out.println(insumosFaltantes);
		
		int[] pesos = new int[insumosFaltantes.size()];
		Double[] valores = new Double[insumosFaltantes.size()];
		
		int index=0;
		for(Insumo i: insumosFaltantes) {
			pesos[index] = i.getPeso();
			valores[index] = i.getCosto();
			index++;
		}
		
		Insumo[] insumosAEnviar = resolver()

	    
	}

	public Insumo[] resolver(Double[] pesos, Double[] valores, Double pesoMaximo) {
	    
	    int N = 4; // items
	    int W = 5; // max peso
	    
	    int[][] opt = new int[N][W]; //matriz que guarda el valor de cada esecenario
	    boolean[][] sol = new boolean[N][W]; // matriz que guarda si el element esta en el esceario
	    
	    for (int n = 1; n < N; n++) {
	        for (int w = 0; w < W; w++) {
	            int option1 = n < 1 ? 0 : opt[n - 1][w]; //completar
	            int option2 = Integer.MIN_VALUE;
	                if (peso[n]<=w) { //Hay espacio en la mochila?
	                option2 = valor[n] + opt[n-1][w-peso[n]]; //actualizar el valor de agregar a la mochila el elemento
	                }
	                
	            opt[n][w] = Math.max(option1, option2);
	            sol[n][w] = (option2 > option1);
	        }
	    }
	    
	// determinar la combinación óptima
	    Boolean[] esSolucion= new Boolean[N];
	    for (int n = N-1, w = W-1; n >= 0; n--) {
	        if (sol[n][w]) {
	            esSolucion [n] = true;
	            w = w - peso[n];
	        } else {
	            esSolucion [n] = false;
	        }
	    }
	    System.out.println("Pares peso valor en solucion optima");
	    boolean b=false;
	    for(int i=0;i<N;i++){
	        
	        if(esSolucion[i]){
	           if(b) System.out.print(" - ");
	            System.out.print("("+peso[i]+" "+valor[i]+")");
	               b=true;
	        }
	     
	    }
	    System.out.println("\n");
	    return esSolucion;
	    
	    }*/

	
}