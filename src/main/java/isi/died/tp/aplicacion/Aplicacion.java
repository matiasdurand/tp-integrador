package isi.died.tp.aplicacion;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import isi.died.tp.controladores.ControladorPaneles;
import isi.died.tp.dominio.Insumo;
import isi.died.tp.dominio.Liquido;
import isi.died.tp.dominio.Planta;
import isi.died.tp.dominio.Stock;
import isi.died.tp.dominio.Insumo.UnidadDeMedida;
import isi.died.tp.estructuras.Grafo;
import isi.died.tp.estructuras.GrafoPlantas;
import isi.died.tp.estructuras.Vertice;

public class Aplicacion {

	public static Insumo insumo1;//para probar
	public static Insumo insumo2;//para probar
	public static Insumo insumo3;//para probar
	public static Insumo insumo4;//para probar
	
	public static List<Insumo> listaInsumos;//para probar
	
	public static List<Stock> listaStockPlantaAcopioI;//para probar
	public static List<Stock> listaStockPlanta02;//para probar
	public static List<Stock> listaStockPlanta03;//para probar
	public static List<Stock> listaStockPlanta04;//para probar
	public static List<Stock> listaStockPlanta05;//para probar
	public static List<Stock> listaStockPlantaAcopioF;//para probar

	public static Planta plantaAcopioI;//para probar
	public static Planta planta02;//para probar
	public static Planta planta03;//para probar
	public static Planta planta04;//para probar
	public static Planta planta05;//para probar
	public static Planta plantaAcopioF;//para probar
	
	public static List<Planta> listaPlantas;//para probar
	
	public static Grafo<Planta> grafoDePlantas;// para probar
	
	public static JFrame f;
	
	public static void main( String[] args )
    {
    	SwingUtilities.invokeLater(new Runnable(){ public void run(){ 
    		mostrarVentanaPrincipal();
    		cargarDatosParaProbar();
    		} 
    	} );
    }
	
	private static void mostrarVentanaPrincipal() {
		
		f = new JFrame("Sistema de Gestión Logística");
		//f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setMinimumSize(new Dimension(1000, 750));
        
        JMenuBar menuBar = new JMenuBar();
        JMenu menu0, menu1, menu2;
        JMenuItem menuItem1, menuItem2, menuItem3, menuItem4;
        
        f.setJMenuBar(menuBar);
        
        menu0 = new JMenu("Menú principal");
        menu1 = new JMenu("Administrar");
        menu2 = new JMenu("Ver");
        
        menuBar.add(menu0);
        menuBar.add(menu1);
        menuBar.add(menu2);
        
        menuItem1 = new JMenuItem("Plantas");
        menuItem1.addActionListener(e -> { 
      	  f.getContentPane().removeAll();
      	  f.getContentPane().add(ControladorPaneles.getInstance().getPanelPlanta());
      	  f.pack();
      	  f.revalidate();
      	  f.repaint();} );
        menu1.add(menuItem1);
        
        menuItem2 = new JMenuItem("Insumos");
        menuItem2.addActionListener(e -> { 
      	  f.getContentPane().removeAll();
      	  f.getContentPane().add(ControladorPaneles.getInstance().getPanelInsumo());
      	  f.pack();
      	  f.revalidate();
    	  f.repaint();} );
        menu1.add(menuItem2);
        
        menuItem3 = new JMenuItem("Camiones");
        menuItem3.addActionListener(e -> { 
      	  f.getContentPane().removeAll();
      	  f.getContentPane().add(ControladorPaneles.getInstance().getPanelCamion());
      	  f.pack();
      	  f.revalidate();
    	  f.repaint();} );
        menu1.add(menuItem3);  

        menuItem4 = new JMenuItem("Grafo");
        menuItem4.addActionListener(e -> { 
      	  f.getContentPane().removeAll();
      	  f.getContentPane().add(ControladorPaneles.getInstance().getPanelGrafoView());
      	  f.pack();
      	  f.revalidate();
    	  f.repaint();} );
        menu2.add(menuItem4);
       
        f.pack();
        f.setVisible(true);
		
	}
	
	private static void cargarDatosParaProbar() {
		
		insumo1 = new Insumo("Insumo1", "Descripcion1", UnidadDeMedida.Pieza, 500.00, 10, 5.00, false);
		insumo2 = new Insumo("Insumo2", "Descripcion2", UnidadDeMedida.Kilogramo, 100.00, 15, 1.00, false);
		insumo3 = new Insumo("Insumo3", "Descripcion3", UnidadDeMedida.Metro, 5.00, 1000, 0.10, false);
		insumo4 = new Liquido("Insumo4", "Descripcion4", 1000.00, 10, true, 13600.00);
		
		listaInsumos = new ArrayList<Insumo>();
		listaInsumos.add(insumo1);
		listaInsumos.add(insumo2);
		listaInsumos.add(insumo3);
		listaInsumos.add(insumo4);
		
		listaStockPlantaAcopioI = new ArrayList<Stock>();
		listaStockPlantaAcopioI.add(new Stock(10, 15, insumo1));
		listaStockPlantaAcopioI.add(new Stock(15, 8, insumo2));
		listaStockPlantaAcopioI.add(new Stock(1000, 10, insumo3));
		listaStockPlantaAcopioI.add(new Stock(10, 2, insumo4));
		
		listaStockPlanta02 = new ArrayList<Stock>();
		listaStockPlanta02.add(new Stock(1, 5, insumo2));
		
		listaStockPlanta03 = new ArrayList<Stock>();
		listaStockPlanta03.add(new Stock(5, 2, insumo4));
		listaStockPlanta03.add(new Stock(10, 20, insumo3));
		
		listaStockPlanta04 = new ArrayList<Stock>();
		listaStockPlanta04.add(new Stock(10, 5, insumo4));
		listaStockPlanta04.add(new Stock(20, 50, insumo1));
		listaStockPlanta04.add(new Stock(10, 15, insumo2));
		
		listaStockPlanta05 = new ArrayList<Stock>();
		listaStockPlanta05.add(new Stock(80, 100, insumo4));
		listaStockPlanta05.add(new Stock(10, 20, insumo3));
		listaStockPlanta05.add(new Stock(10, 20, insumo1));
		
		listaStockPlantaAcopioF = new ArrayList<Stock>();
		listaStockPlantaAcopioF.add(new Stock(11, 10, insumo1));
		listaStockPlantaAcopioF.add(new Stock(20, 5, insumo2));
		listaStockPlantaAcopioF.add(new Stock(5, 10, insumo3));
		
		plantaAcopioI = new Planta("PlantaAcopioI", listaStockPlantaAcopioI);  
		planta02 = new Planta("Planta02", listaStockPlanta02);
		planta03 = new Planta("Planta03", listaStockPlanta03);
		planta04 = new Planta("Planta04", listaStockPlanta04);
		planta05 = new Planta("Planta05", listaStockPlanta05);
		plantaAcopioF = new Planta("PlantaAcopioF", listaStockPlantaAcopioF);
		
		plantaAcopioI.setId(1);
		planta02.setId(2);
		planta03.setId(3);
		planta04.setId(4);
		planta05.setId(5);
		plantaAcopioF.setId(6);
		
		listaPlantas = new ArrayList<Planta>();
		listaPlantas.add(plantaAcopioI);
		listaPlantas.add(planta02);
		listaPlantas.add(planta03);
		listaPlantas.add(planta04);
		listaPlantas.add(planta05);
		listaPlantas.add(plantaAcopioF);
		
		grafoDePlantas = GrafoPlantas.getInstance();
		grafoDePlantas.addNodo(plantaAcopioI);
		grafoDePlantas.addNodo(planta02);
		grafoDePlantas.addNodo(planta03);
		grafoDePlantas.addNodo(planta04);
		grafoDePlantas.addNodo(planta05);
		grafoDePlantas.addNodo(plantaAcopioF);
		
		grafoDePlantas.conectar(plantaAcopioI, planta02, 10.00, 10.00, 500.00);
		grafoDePlantas.conectar(plantaAcopioI, planta03, 15.00, 12.00, 500.00);
		grafoDePlantas.conectar(planta02, plantaAcopioF, 30.00, 20.00, 500.00);
		grafoDePlantas.conectar(planta03, plantaAcopioF, 5.00, 2.00, 500.00);
		grafoDePlantas.conectar(plantaAcopioI, plantaAcopioF, 50.00, 30.00, 500.00);
		grafoDePlantas.conectar(planta02, planta04, 10.00, 10.00, 500.00);
		grafoDePlantas.conectar(planta04, planta05, 10.00, 10.00, 500.00);
		grafoDePlantas.conectar(planta05, plantaAcopioF, 10.00, 10.00, 500.00);
		
		/*HashMap<List<Vertice<Planta>>, Double> caminos = grafoDePlantas.caminosPosibles(new Vertice<Planta>(plantaAcopioI), new Vertice<Planta>(plantaAcopioF));
		for(Entry<List<Vertice<Planta>>, Double> camino: caminos.entrySet()) {
			System.out.println(camino.getKey()+" "+camino.getValue());
		}*/
	}
}
