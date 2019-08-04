package isi.died.tp.aplicacion;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import isi.died.tp.controladores.ControladorPaneles;

public class Aplicacion {
	
	public static void main( String[] args )
    {
    	SwingUtilities.invokeLater(
    		new Runnable() {
    			public void run() 
    			{ 
    				createAndShowGUI(); 
    			} 
    		} 
    	);
    }
	
	private static void createAndShowGUI() {
		
		ControladorPaneles.getInstance().getPanelPlanta();
		ControladorPaneles.getInstance().getPanelInsumo();
		ControladorPaneles.getInstance().getPanelCamion();
		ControladorPaneles.getInstance().getPanelGrafoView();
		
		JFrame f = new JFrame("Sistema de Gestión Logística");
		
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setMinimumSize(new Dimension(1000, 750));
        
        JMenuBar menuBar = new JMenuBar();
        JMenu menu1, menu2;
        JMenuItem menuItem1, menuItem2, menuItem3, menuItem4, menuItem5;
        
        f.setJMenuBar(menuBar);
        
        menu1 = new JMenu("Mostrar");
        menu2 = new JMenu("Administrar");
        
        menuBar.add(menu1);
        menuBar.add(menu2);
        
        menuItem1 = new JMenuItem("Plantas");
        menuItem1.addActionListener(e -> { 
      	  f.getContentPane().removeAll();
      	  f.getContentPane().add(ControladorPaneles.getInstance().getPanelPlanta());
      	  f.pack();
      	  f.revalidate();
      	  f.repaint();} );
        menu2.add(menuItem1);
        
        menuItem2 = new JMenuItem("Insumos");
        menuItem2.addActionListener(e -> { 
      	  f.getContentPane().removeAll();
      	  f.getContentPane().add(ControladorPaneles.getInstance().getPanelInsumo());
      	  f.pack();
      	  f.revalidate();
    	  f.repaint();} );
        menu2.add(menuItem2);
        
        menuItem3 = new JMenuItem("Camiones");
        menuItem3.addActionListener(e -> { 
      	  f.getContentPane().removeAll();
      	  f.getContentPane().add(ControladorPaneles.getInstance().getPanelCamion());
      	  f.pack();
      	  f.revalidate();
    	  f.repaint();} );
        menu2.add(menuItem3);  
        
        menuItem4 = new JMenuItem("Vista Principal");
        menuItem4.addActionListener(e -> { 
      	  f.getContentPane().removeAll();
      	  f.getContentPane().add(ControladorPaneles.getInstance().getPanelPrincipal());
      	  f.pack();
      	  f.revalidate();
    	  f.repaint();} );
        menu1.add(menuItem4);

        menuItem5 = new JMenuItem("Grafo");
        menuItem5.addActionListener(e -> { 
      	  f.getContentPane().removeAll();
      	  f.getContentPane().add(ControladorPaneles.getInstance().getPanelGrafoView());
      	  f.pack();
      	  f.revalidate();
    	  f.repaint();} );
        menu1.add(menuItem5);
       
        f.getContentPane().add(ControladorPaneles.getInstance().getPanelPrincipal());
        
        f.pack();
        f.setVisible(true);
		
	}
		
}
