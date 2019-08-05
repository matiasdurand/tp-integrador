package isi.died.tp.controladores;

import java.awt.Color;
import java.awt.Font;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import isi.died.tp.dao.CamionDao;
import isi.died.tp.dao.CamionDaoH2;
import isi.died.tp.dominio.Camion;
import isi.died.tp.gui.PanelCamion;
import isi.died.tp.gui.PanelMejorEnvio;

public class ControladorCamiones {

	private static ControladorCamiones _INSTANCIA = null;
	
	private PanelCamion pCamion;
	private PanelMejorEnvio pMEnvio;
	private CamionDao daoCamion;
	
	public static final Font FUENTE_TITULO = new Font("Calibri",Font.BOLD,18);
	public static final Color COLOR_TITULO = new Color(5,85,244);
	
	
	private ControladorCamiones() {
		daoCamion = new CamionDaoH2();
	}
	
	public static ControladorCamiones getInstance() {
		if(_INSTANCIA == null) _INSTANCIA = new ControladorCamiones();
		return _INSTANCIA;
	}

	public PanelCamion getpCamion() {
		return pCamion;
	}

	public void setpCamion(PanelCamion pCamion) {
		this.pCamion = pCamion;
	}
	
	public void setpMEnvio(PanelMejorEnvio pMEnvio) {
		this.pMEnvio=pMEnvio;		
	}
	
	public void crearCamion(String marca, String modelo, Integer año, String dominio, Integer capacidad, Double costoPorKm, Boolean aptoParaLiquidos) {
		
		Runnable r = () -> {
			
			Camion c = new Camion(marca, modelo, año, dominio, capacidad, costoPorKm, aptoParaLiquidos);
			
			daoCamion.crear(c);
			
			List<Camion> camiones = daoCamion.buscarTodos();
			
			try {
				SwingUtilities.invokeAndWait(() -> {
					pCamion.actualizarDatosTabla(camiones);
					JOptionPane.showMessageDialog(pCamion, "El camión ha sido creado correctamente");
				});
			} 
			catch (InvocationTargetException | InterruptedException e) {
				e.printStackTrace();
			}
			
		};
		
		Thread hilo = new Thread(r);
		hilo.start();
		
	}
	
	public void borrarCamion(Integer id) {
		
		Runnable r = () -> {
			
			daoCamion.borrar(id);
			
			List<Camion> camiones = daoCamion.buscarTodos();
			
			try {
				SwingUtilities.invokeAndWait(() -> {
					pCamion.actualizarDatosTabla(camiones);
					JOptionPane.showMessageDialog(pCamion, "El camión ha sido eliminado correctamente");
				});
			} 
			catch (InvocationTargetException | InterruptedException e) {
				e.printStackTrace();
			}
			
		};
		
		Thread hilo = new Thread(r);
		hilo.start();
		
	}
	
	public void actualizarCamion(Integer id, String marca, String modelo, Integer año, String dominio, Integer capacidad, Double costoPorKm, Boolean aptoParaLiquidos) {
		
		Runnable r = () -> {
		
			Camion c = new Camion(id, marca, modelo, año, dominio, capacidad, costoPorKm, aptoParaLiquidos);
			
			daoCamion.actualizar(c);
			
			List<Camion> camiones = daoCamion.buscarTodos();
			
			try {
				SwingUtilities.invokeAndWait(() -> {
					pCamion.actualizarDatosTabla(camiones);
					JOptionPane.showMessageDialog(pCamion, "El camión ha sido actualizado correctamente");
				});
			} 
			catch (InvocationTargetException | InterruptedException e) {
				e.printStackTrace();
			}
			
		};
		
		Thread hilo = new Thread(r);
		hilo.start();
			
	}

	public void cargarCamiones() {
		pMEnvio.actualizarDatosTablaCamiones(daoCamion.buscarTodos());
	}

	public Camion buscarCamion(Integer id) {
		return daoCamion.buscar(id);
	}
	
	public Boolean aptoParaLiquidos(Integer id) {
		return daoCamion.buscar(id).getAptoParaLiquidos();
	}
	
}
