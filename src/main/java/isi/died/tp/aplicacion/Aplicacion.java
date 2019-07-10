package isi.died.tp.aplicacion;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import isi.died.tp.gui.ControladorPaneles;

public class Aplicacion {

	public static JFrame f;
	
	public static void main( String[] args )
    {
    	SwingUtilities.invokeLater(new Runnable(){ public void run(){ mostrarVentanaPrincipal(); } } );
    }
	
	private static void mostrarVentanaPrincipal() {
		
		f = new JFrame("Aplicacion");
		
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(1000, 750);
        f.setMinimumSize(new Dimension(500, 500));
        
        JMenuBar menuBar = new JMenuBar();
        JMenu menu1, menu2;
        JMenuItem menuItem1, menuItem2, menuItem3, menuItem4;
        
        f.setJMenuBar(menuBar);
 
        menu1 = new JMenu("Administrar");
        menu2 = new JMenu("Ver");
        
        menuBar.add(menu1);
        menuBar.add(menu2);
        
        menuItem1 = new JMenuItem("Plantas");
        menuItem1.addActionListener(e -> { 
      	  f.getContentPane().removeAll();
      	  f.getContentPane().add(ControladorPaneles.getInstance().getPanelPlanta());
      	  f.pack(); } );
        menu1.add(menuItem1);
        
        menuItem2 = new JMenuItem("Insumos");
        menuItem2.addActionListener(e -> { 
      	  f.getContentPane().removeAll();
      	  f.getContentPane().add(ControladorPaneles.getInstance().getPanelInsumo());
      	  f.pack(); } );
        menu1.add(menuItem2);
        
        menuItem3 = new JMenuItem("Camiones");
        menuItem3.addActionListener(e -> { 
      	  f.getContentPane().removeAll();
      	  f.getContentPane().add(ControladorPaneles.getInstance().getPanelCamion());
      	  f.pack(); } );
        menu1.add(menuItem3);  

        menuItem4 = new JMenuItem("Grafo");
        menuItem4.addActionListener(e -> { 
      	  f.getContentPane().removeAll();
      	  f.getContentPane().add(ControladorPaneles.getInstance().getPanelGrafo());
      	  f.pack(); } );
        menu2.add(menuItem4);
        
        f.pack();
        f.setVisible(true);
		
	}
}
