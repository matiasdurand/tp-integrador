package isi.died.tp.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import isi.died.tp.aplicacion.Aplicacion;
import isi.died.tp.controladores.ControladorInsumos;
import isi.died.tp.controladores.ControladorPaneles;
import isi.died.tp.dominio.Insumo.UnidadDeMedida;

public class PanelRegistrarInsumo extends JPanel {

	private JLabel lblPanelTitulo;
	private JLabel lblTipo;
	private JLabel lblNombre;
	private JLabel lblDescripcion;
	private JLabel lblCosto;
	private JLabel lblStock;
	private JLabel lblPeso;
	private JLabel lblDensidad;
	private JTextField nombre;
	private JTextField costo;
	private JTextField stock;
	private JTextField peso;
	private JTextField densidad;
	private JTextArea descripcion;
	private JComboBox<UnidadDeMedida> cmboxUDM;
	private JRadioButton rbtnLiquido;
	private JRadioButton rbtnNoLiquido;
	private JRadioButton rbtnRefrigerado;
	private JRadioButton rbtnNoRefrigerado;
	private JButton btnGuardar;
	private JButton btnCancelar;
	private ControladorInsumos controlador;
	
	public PanelRegistrarInsumo() {
		super();
		controlador = ControladorInsumos.getInstance();
		controlador.setpRInsumo(this);
		armar();
		configurarEventos();
	}
	
	private void armar() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();  
		//CONFIGURAR APARIENCIA DEL PANEL.
	}
	
	private void configurarEventos() {
		rbtnLiquido.addActionListener( e -> {
			cmboxUDM.setEnabled(false);
			cmboxUDM.setSelectedItem(UnidadDeMedida.Litro);
			peso.setEnabled(false);
			peso.setText("");
			densidad.setText("");
			densidad.setEnabled(true);
		});
		rbtnNoLiquido.addActionListener( e -> {
			cmboxUDM.setSelectedItem(UnidadDeMedida.Gramo);
			cmboxUDM.setEnabled(true);
			peso.setEnabled(true);
			densidad.setEnabled(false);
		});
		btnGuardar.addActionListener( e -> {
			//VALIDAR DATOS INGRESADOS.
			if(rbtnLiquido.isSelected()) controlador.crearInsumo(nombre.getText(), descripcion.getText(), (UnidadDeMedida)cmboxUDM.getSelectedItem(), Double.parseDouble(costo.getText()), Integer.parseInt(stock.getText()), -1.00, rbtnRefrigerado.isSelected(), Double.parseDouble(densidad.getText()));
			else controlador.crearInsumo(nombre.getText(), descripcion.getText(), (UnidadDeMedida)cmboxUDM.getSelectedItem(), Double.parseDouble(costo.getText()), Integer.parseInt(stock.getText()), Double.parseDouble(peso.getText()), rbtnRefrigerado.isSelected(), -1.00);
			Aplicacion.f.getContentPane().removeAll();
			Aplicacion.f.getContentPane().add(ControladorPaneles.getInstance().getPanelInsumo());
			Aplicacion.f.pack();
		});
		btnCancelar.addActionListener( e -> {
			Aplicacion.f.getContentPane().removeAll();
			Aplicacion.f.getContentPane().add(ControladorPaneles.getInstance().getPanelInsumo());
			Aplicacion.f.pack();
		});
	}
}
