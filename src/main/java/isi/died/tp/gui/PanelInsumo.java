package isi.died.tp.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import isi.died.tp.aplicacion.Aplicacion;
import isi.died.tp.controladores.ControladorInsumos;
import isi.died.tp.dominio.Insumo;
import isi.died.tp.gui.util.GenericTableColumn;
import isi.died.tp.gui.util.GenericTableModel;

public class PanelInsumo extends JPanel {
	
	private JLabel lblPanelTitulo;
	private JLabel lblNombre;
	private JLabel lblCostoMinimo;
	private JLabel lblCostoMaximo;
	private JLabel lblStockMinimo;
	private JLabel lblStockMaximo;
	private JLabel lblOrdenarPor;
	private JTextField nombre;
	private JTextField costoMinimo;
	private JTextField costoMaximo;
	private JTextField stockMinimo;
	private JTextField stockMaximo;
	private JComboBox<String> cmboxOrdenarPor;
	private JRadioButton rbtnDescendente;
	private JRadioButton rbtnAscendente;
	GenericTableModel<Insumo> gtm ;
	private JTable tablaInsumos;
	private JButton btnBuscar;
	private JButton btnRegistrar;
	private JButton btnEditar;
	private JButton btnEliminar;
	private ControladorInsumos controlador;
	private int idSeleccionado;
	
	public PanelInsumo() {
		super();
		controlador = ControladorInsumos.getInstance();
		controlador.setpInsumo(this);
		armar();
		configurarEventos();
	}
	
	private void armar() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();  
		/*CONFIGURAR APARIENCIA DEL PANEL.
		gtm = crearModeloTabla();
    	tablaInsumos = new JTable(gtm);
    	tablaInsumos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	JScrollPane scrollPane = new JScrollPane(tablaInsumos);*/
	}
	
	private void configurarEventos() {
		btnBuscar.addActionListener( e -> {
			actualizarTablaInsumos(controlador.filtrar(nombre.getText(), costoMinimo.getText(), costoMaximo.getText(), stockMinimo.getText(), stockMaximo.getText()));
    		btnEliminar.setEnabled(false);
    		btnEditar.setEnabled(false);
		});
		btnRegistrar.addActionListener( e -> {
			Aplicacion.f.getContentPane().removeAll();
			Aplicacion.f.getContentPane().add(ControladorPaneles.getInstance().getPanelRegistrarInsumo());
			Aplicacion.f.pack();
    		btnEliminar.setEnabled(false);
    		btnEditar.setEnabled(false);
    	});
		btnEditar.addActionListener( e -> {
			Aplicacion.f.getContentPane().removeAll();
			Aplicacion.f.getContentPane().add(ControladorPaneles.getInstance().getPanelEditarInsumo(idSeleccionado));
			Aplicacion.f.pack();
    		btnEliminar.setEnabled(false);
    		btnEditar.setEnabled(false);
		});
		btnEliminar.addActionListener( e -> {
			int resultado = JOptionPane.showConfirmDialog(null, "Eliminar insumo", "¿Desea eliminar el insumo seleccionado?", JOptionPane.YES_NO_OPTION);
    		if(resultado == 0) controlador.eliminarInsumo(idSeleccionado);
	    	btnEliminar.setEnabled(false);
	    	btnEditar.setEnabled(false);
		});
		tablaInsumos.getSelectionModel().addListSelectionListener( e -> {
    		if(gtm.getDatos()!=null && !gtm.getDatos().isEmpty() && e.getFirstIndex()<gtm.getDatos().size()) {
	    		Insumo auxiliar = gtm.datos.get(e.getFirstIndex());
	    		idSeleccionado = auxiliar.getId();
	    		btnEliminar.setEnabled(true);
	    		btnEditar.setEnabled(true);
    		}
        });
		/*CONFIGURAR EVENTOS DE COMBOBOX Y RADIOBUTTONS PARA ORDENAR TABLA.
		    cmboxOrdenarPor.addActionListener( e -> {
			if(rbtnDescendente.isSelected())
			actualizarTablaInsumos(controlador.ordenarPor((String)cmboxOrdenarPor.getSelectedItem()));
		});
		
		rbtnDescendente.addActionListener( e -> {
			actualizarTablaInsumos(controlador.ordenarPor((String)cmboxOrdenarPor.getSelectedItem()));
		});*/
		
	}
	
	private GenericTableModel<Insumo> crearModeloTabla(){
    	gtm = new GenericTableModel<Insumo>();
    	List<GenericTableColumn> lista = new ArrayList<GenericTableColumn>();
    	lista.add(new GenericTableColumn("Id" , "getId"));
    	lista.add(new GenericTableColumn("Nombre" , "getNombre"));
    	lista.add(new GenericTableColumn("Descripción" , "getDescripcion"));
    	lista.add(new GenericTableColumn("UDM" , "getUnidadDeMedida"));
    	lista.add(new GenericTableColumn("Costo por UDM" , "getCosto"));
    	lista.add(new GenericTableColumn("Stock total" , "getStock"));
    	lista.add(new GenericTableColumn("Peso (KG)" , "getPeso"));
    	lista.add(new GenericTableColumn("Refrigeración" , "getEsRefrigerado"));
    	gtm.setColumnas(lista);
    	return gtm;
    }
	
	private void actualizarTablaInsumos(List<Insumo> lista) {
		this.gtm.setDatos(lista);
    	this.gtm.fireTableDataChanged();
	}
}
