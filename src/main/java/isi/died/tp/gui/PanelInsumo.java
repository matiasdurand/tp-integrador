package isi.died.tp.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import isi.died.tp.controladores.ControladorCamiones;
import isi.died.tp.controladores.ControladorInsumos;
import isi.died.tp.controladores.ControladorPaneles;
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
	private Integer idSeleccionado;
	
	public PanelInsumo() {
		super();
		controlador = ControladorInsumos.getInstance();
		controlador.setpInsumo(this);
		armar();
		configurarEventos();
	}
	
	public Dimension getPreferredSize() {
        return new Dimension(450,250);
    }
	
	private void armar() {
		setLayout(new GridBagLayout());
		//
		
		int fila = 0;
    	int col = 0;
    	
    	GridBagConstraints c = new GridBagConstraints();    	

    	this.lblPanelTitulo = new JLabel("GESTIÓN DE INSUMOS");
    	this.lblPanelTitulo.setFont(ControladorCamiones.FUENTE_TITULO);
    	this.lblPanelTitulo.setForeground(ControladorCamiones.COLOR_TITULO);
    	c.gridx=col;
    	c.gridy=fila;
    	c.anchor = GridBagConstraints.NORTH;
    	c.gridwidth=GridBagConstraints.NONE;
    	c.insets = new Insets(10, 10, 10, 10);
    	this.add(lblPanelTitulo,c);
    	
    	//Primer fila
    	
    	col =0;
    	fila++;

    	this.lblNombre = new JLabel("Nombre:");
    	c.gridx=col++;
    	c.gridy=fila;
    	c.gridwidth=1;
    	c.gridheight=1;
    	c.weightx=0.0;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.NONE;
    	c.insets = new Insets(10, 10, 10, 10);
    	this.add(lblNombre,c);
    	
    	this.nombre = new JTextField(30);
    	c.gridx=col++;
    	c.gridy=fila;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.BOTH;
    	c.weightx=0.5;
    	this.add(nombre,c);
    	
    	this.lblOrdenarPor = new JLabel("Ordenar por:");
    	c.gridx=col++;
    	c.gridy=fila;
    	c.gridwidth=1;
    	c.gridheight=1;
    	c.weightx=0.0;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.NONE;
    	c.insets = new Insets(10, 10, 10, 10);
    	this.add(lblOrdenarPor,c);
    	
    	this.cmboxOrdenarPor = new JComboBox<String>();
    	cmboxOrdenarPor.addItem("Costo");
    	cmboxOrdenarPor.addItem("Nombre");
    	cmboxOrdenarPor.addItem("Stock total");
    	c.gridx=col++;
    	c.gridy=fila;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.BOTH;
    	c.weightx=0.5;
    	this.add(cmboxOrdenarPor,c);
    	
    	this.rbtnAscendente = new JRadioButton("Ascendente");
    	c.gridx=col++;
    	c.gridy=fila;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.BOTH;
    	c.weightx=0.5;
    	rbtnAscendente.setSelected(true);
    	this.add(rbtnAscendente,c);
    	
    	this.rbtnDescendente = new JRadioButton("Descendente");
    	c.gridx=col++;
    	c.gridy=fila;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.BOTH;
    	c.weightx=0.5;
    	this.add(rbtnDescendente,c);
    	
    	//Segunda fila
    	
    	col =0;
    	fila++;
    	
    	this.lblCostoMinimo = new JLabel("Costo mínimo:");
    	c.gridx=col++;
    	c.gridy=fila;
    	c.gridwidth=1;
    	c.gridheight=1;
    	c.weightx=0.0;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.NONE;
    	c.insets = new Insets(10, 10, 10, 10);
    	this.add(lblCostoMinimo,c);
    	
    	this.costoMinimo = new JTextField(30);
    	c.gridx=col++;
    	c.gridy=fila;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.BOTH;
    	c.weightx=0.5;
    	this.add(costoMinimo,c);
    	
    	this.lblCostoMaximo = new JLabel("Costo máximo:");
    	c.gridx=col++;
    	c.gridy=fila;
    	c.gridwidth=1;
    	c.gridheight=1;
    	c.weightx=0.0;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.NONE;
    	c.insets = new Insets(10, 10, 10, 10);
    	this.add(lblCostoMaximo,c);
    	
    	this.costoMaximo = new JTextField(30);
    	c.gridx=col++;
    	c.gridy=fila;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.BOTH;
    	c.weightx=0.5;
    	this.add(costoMaximo,c);
    	
    	this.lblStockMinimo = new JLabel("Stock mínimo:");
    	c.gridx=col++;
    	c.gridy=fila;
    	c.gridwidth=1;
    	c.gridheight=1;
    	c.weightx=0.0;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.NONE;
    	c.insets = new Insets(10, 10, 10, 10);
    	this.add(lblStockMinimo,c);
    	
    	this.stockMinimo = new JTextField(30);
    	c.gridx=col++;
    	c.gridy=fila;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.BOTH;
    	c.weightx=0.5;
    	this.add(stockMinimo,c);
    	
    	this.lblStockMaximo = new JLabel("Stock máximo:");
    	c.gridx=col++;
    	c.gridy=fila;
    	c.gridwidth=1;
    	c.gridheight=1;
    	c.weightx=0.0;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.NONE;
    	c.insets = new Insets(10, 10, 10, 10);
    	this.add(lblStockMaximo,c);
    	
    	this.stockMaximo = new JTextField(30);
    	c.gridx=col++;
    	c.gridy=fila;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.BOTH;
    	c.weightx=0.5;
    	this.add(stockMaximo,c);
    	
    	//Tercera fila
    	
    	col =0;
    	fila++;
    	
    	c.gridx=col++;
    	c.gridy=fila;
    	c.gridwidth=8;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.BOTH;
    	c.weighty=1.0;
    	c.weightx=1.0;
		
		gtm = crearModeloTabla();
    	tablaInsumos = new JTable(gtm);
    	tablaInsumos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	JScrollPane scrollPane = new JScrollPane(tablaInsumos);
    	this.add(scrollPane,c);
    	
    	//Cuarta fila
    	
    	col =0;
    	fila++;
    	
    	JPanel panelBotones = new JPanel();

    	this.btnBuscar = new JButton("Buscar");
    	this.btnRegistrar = new JButton("Registrar");
    	this.btnEditar = new JButton("Editar");
    	this.btnEliminar = new JButton("Eliminar");
    	this.btnEditar.setEnabled(false);
    	this.btnEliminar.setEnabled(false);
    	panelBotones.add(btnBuscar);
    	panelBotones.add(btnRegistrar);
    	panelBotones.add(btnEditar);
    	panelBotones.add(btnEliminar);
    	c.fill=GridBagConstraints.BOTH;
    	c.anchor=GridBagConstraints.CENTER;
    	c.gridy=fila;
    	c.gridwidth=8;
    	c.weighty=0.0;
    	c.weightx=0.0;
    	this.add(panelBotones,c);
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
			Aplicacion.f.revalidate();
			Aplicacion.f.repaint();
    		btnEliminar.setEnabled(false);
    		btnEditar.setEnabled(false);
    	});
		
		btnEditar.addActionListener( e -> {
			Aplicacion.f.getContentPane().removeAll();
			Aplicacion.f.setContentPane(ControladorPaneles.getInstance().getPanelEditarInsumo(idSeleccionado));
			Aplicacion.f.pack();
			Aplicacion.f.revalidate();
			Aplicacion.f.repaint();
    		btnEliminar.setEnabled(false);
    		btnEditar.setEnabled(false);
		});
		
		btnEliminar.addActionListener( e -> {
			int confirmar = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar el insumo seleccionado?", "Eliminar insumo", JOptionPane.YES_NO_OPTION);
			if(confirmar==0) {
				controlador.borrarInsumo(idSeleccionado);
				btnEliminar.setEnabled(false);
			}
		});
		
		tablaInsumos.getSelectionModel().addListSelectionListener( e -> {
    		if(gtm.getDatos()!=null && !gtm.getDatos().isEmpty() && e.getFirstIndex()<gtm.getDatos().size()) {
	    		Insumo auxiliar = gtm.datos.get(e.getFirstIndex());
	    		idSeleccionado = auxiliar.getId();
	    		btnEliminar.setEnabled(true);
	    		btnEditar.setEnabled(true);
    		}
        });
		cmboxOrdenarPor.addActionListener( e -> {
			if(tablaInsumos.getRowCount()>0) {
				actualizarTablaInsumos(controlador.ordenarPor((String)cmboxOrdenarPor.getSelectedItem(), rbtnDescendente.isSelected()));
			}
		});
		rbtnDescendente.addActionListener( e -> {
			rbtnAscendente.setSelected(false);
			if(tablaInsumos.getRowCount()>0) {
				actualizarTablaInsumos(controlador.ordenarPor((String)cmboxOrdenarPor.getSelectedItem(), true));
			}
		});
		rbtnAscendente.addActionListener( e -> {
			rbtnDescendente.setSelected(false);
			if(tablaInsumos.getRowCount()>0) {
				actualizarTablaInsumos(controlador.ordenarPor((String)cmboxOrdenarPor.getSelectedItem(), false));
			}
		});
		
	}
	
	private GenericTableModel<Insumo> crearModeloTabla(){
    	gtm = new GenericTableModel<Insumo>();
    	List<GenericTableColumn> lista = new ArrayList<GenericTableColumn>();
    	lista.add(new GenericTableColumn("Id" , "getId"));
    	lista.add(new GenericTableColumn("Nombre" , "getNombre"));
    	lista.add(new GenericTableColumn("Descripción" , "getDescripcion"));
    	lista.add(new GenericTableColumn("Unidad de medida" , "getUnidadDeMedida"));
    	lista.add(new GenericTableColumn("Costo por unidad" , "getCosto"));
    	lista.add(new GenericTableColumn("Stock total" , "getStock"));
    	lista.add(new GenericTableColumn("Peso (KG)" , "getPeso"));
    	lista.add(new GenericTableColumn("Líquido", "getEsLiquido"));
    	lista.add(new GenericTableColumn("Refrigerado" , "getEsRefrigerado"));
    	gtm.setColumnas(lista);
    	return gtm;
    }
	
	public void actualizarTablaInsumos(List<Insumo> lista) {
		this.gtm.setDatos(lista);
    	this.gtm.fireTableDataChanged();
	}
}
