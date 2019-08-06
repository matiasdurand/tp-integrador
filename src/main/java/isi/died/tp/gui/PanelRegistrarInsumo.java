package isi.died.tp.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import isi.died.tp.controladores.ControladorCamiones;
import isi.died.tp.controladores.ControladorInsumos;
import isi.died.tp.controladores.ControladorPaneles;
import isi.died.tp.dominio.Insumo.UnidadDeMedida;

public class PanelRegistrarInsumo extends JPanel {

	private JLabel lblPanelTitulo;
	private JLabel lblTipo;
	private JLabel lblNombre;
	private JLabel lblDescripcion;
	private JLabel lblCosto;
	private JLabel lblStockInicial;
	private JLabel lblPeso;
	private JLabel lblDensidad;
	private JLabel lblUnidadDeMedida;
	private JTextField nombre;
	private JTextField costo;
	private JTextField stock;
	private JTextField peso;
	private JTextField densidad;
	private JTextArea descripcion;
	private JComboBox<UnidadDeMedida> cmboxUDM;
	private JCheckBox checkBoxLiquido;
	private JCheckBox checkBoxRefrigerado;
	private JButton btnGuardar;
	private JButton btnCancelar;
	private ControladorInsumos controlador;
	
	
	public PanelRegistrarInsumo() {
		super();
		controlador = ControladorInsumos.getInstance();
		armar();
		configurarEventos();
	}
	
	public Dimension getPreferredSize() {
        return new Dimension(1000,750);
    }
	
	private void armar() {
		
		setLayout(new GridBagLayout());
		//
		
		int fila = 0;
    	int col = 0;
    	
    	GridBagConstraints c = new GridBagConstraints();    	

    	this.lblPanelTitulo = new JLabel("REGISTRAR INSUMO");
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
    	
    	this.lblUnidadDeMedida = new JLabel("Unidad de medida:");
    	c.gridx=col++;
    	c.gridy=fila;
    	c.gridwidth=1;
    	c.gridheight=1;
    	c.weightx=0.0;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.NONE;
    	c.insets = new Insets(10, 10, 10, 10);
    	this.add(lblUnidadDeMedida,c);
    	
    	this.cmboxUDM = new JComboBox<UnidadDeMedida>();
    	cmboxUDM.setModel(new DefaultComboBoxModel<>(UnidadDeMedida.values()));
    	c.gridx=col++;
    	c.gridy=fila;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.BOTH;
    	c.weightx=0.5;
    	this.add(cmboxUDM,c);
    	
    	this.lblTipo = new JLabel("Tipo:");
    	c.gridx=col++;
    	c.gridy=fila;
    	c.gridwidth=1;
    	c.gridheight=1;
    	c.weightx=0.0;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.NONE;
    	c.insets = new Insets(10, 10, 10, 10);
    	this.add(lblTipo,c);
    	
    	this.checkBoxLiquido = new JCheckBox("Líquido");
    	c.gridx=col++;
    	c.gridy=fila;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.BOTH;
    	c.weightx=0.5;
    	this.add(checkBoxLiquido,c);
    	
    	this.checkBoxRefrigerado = new JCheckBox("Refrigerado");
    	c.gridx=col++;
    	c.fill=GridBagConstraints.HORIZONTAL;
    	c.weightx=0.5;
    	this.add(checkBoxRefrigerado,c);
    	
    	//Segunda fila
    	
    	col=0;
    	fila++;
    	    	
    	this.lblCosto = new JLabel("Costo:");
    	c.gridx=col++;
    	c.gridy=fila;
    	c.gridwidth=1;
    	c.gridheight=1;
    	c.weightx=0.0;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.NONE;
    	c.insets = new Insets(10, 10, 10, 10);
    	this.add(lblCosto,c);
    	
    	this.costo = new JTextField(30);
    	c.gridx=col++;
    	c.gridy=fila;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.BOTH;
    	c.weightx=0.5;
    	this.add(costo,c);
    	
    	this.lblStockInicial = new JLabel("Stock inicial:");
    	c.gridx=col++;
    	c.gridy=fila;
    	c.gridwidth=1;
    	c.gridheight=1;
    	c.weightx=0.0;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.NONE;
    	c.insets = new Insets(10, 10, 10, 10);
    	this.add(lblStockInicial,c);
    	
    	this.stock = new JTextField(30);
    	c.gridx=col++;
    	c.gridy=fila;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.BOTH;
    	c.weightx=0.5;
    	this.add(stock,c);
    	
    	this.lblPeso = new JLabel("Peso:");
    	c.gridx=col++;
    	c.gridy=fila;
    	c.gridwidth=1;
    	c.gridheight=1;
    	c.weightx=0.0;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.NONE;
    	c.insets = new Insets(10, 10, 10, 10);
    	this.add(lblPeso,c);
    	
    	this.peso = new JTextField(30);
    	c.gridx=col++;
    	c.gridy=fila;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.BOTH;
    	c.weightx=0.5;
    	this.add(peso,c);
    	
    	this.lblDensidad = new JLabel("Densidad:");
    	c.gridx=col++;
    	c.gridy=fila;
    	c.gridwidth=1;
    	c.gridheight=1;
    	c.weightx=0.0;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.NONE;
    	c.insets = new Insets(10, 10, 10, 10);
    	this.add(lblDensidad,c);
    	
    	this.densidad = new JTextField(30);
    	c.gridx=col++;
    	c.gridy=fila;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.BOTH;
    	c.weightx=0.5;
    	densidad.setEnabled(false);
    	this.add(densidad,c);
    	
    	//Tercer fila
    	
    	col=0;
    	fila++;
    	
    	this.lblDescripcion = new JLabel("Descripción:");
    	c.gridx=col++;
    	c.gridy=fila;
    	c.gridwidth=1;
    	c.gridheight=1;
    	c.weightx=0.0;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.NONE;
    	c.insets = new Insets(10, 10, 10, 10);
    	this.add(lblDescripcion,c);
    	
    	//Cuarta fila
    	
    	col=0;
    	fila++;
    	
    	this.descripcion = new JTextArea();
    	c.gridx=col++;
    	c.gridy=fila;
    	c.gridwidth=8;
    	c.gridheight=2;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.BOTH;
    	c.insets = new Insets(10, 10, 10, 10);
    	c.weighty=0.0;
    	c.weightx=1.0;
    	this.add(descripcion,c);
    	
    	//Quinta fila
    	
    	col =0;
    	fila=fila+2;
    	JPanel panelBotones = new JPanel();

    	this.btnGuardar = new JButton("Guardar");
    	this.btnCancelar = new JButton("Cancelar");
    	panelBotones.add(btnGuardar);
    	panelBotones.add(btnCancelar);
    	c.fill=GridBagConstraints.NONE;
    	c.anchor=GridBagConstraints.CENTER;
    	c.gridy=fila;
    	c.gridwidth=8;
    	c.weighty=0.0;
    	c.weightx=0.0;
    	this.add(panelBotones,c);
    	
    	//Sexta fila
    	
    	JPanel panelFila6 = new JPanel();
    	
    	col =0;
    	fila++;
    	
    	c.gridx=col++;
    	c.gridy=fila;
    	c.gridwidth=8;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.BOTH;
    	c.weighty=1.0;
    	c.weightx=1.0;
    	this.add(panelFila6,c);
	}
	
	private void configurarEventos() {
		
		checkBoxLiquido.addActionListener( e -> {
			
			if(checkBoxLiquido.isSelected()) {
				cmboxUDM.setEnabled(false);
				cmboxUDM.setSelectedItem(UnidadDeMedida.Litro);
				peso.setEnabled(false);
				peso.setText("");
				densidad.setText("");
				densidad.setEnabled(true);
			}
			else {
				cmboxUDM.setSelectedItem(UnidadDeMedida.Kilogramo);
				cmboxUDM.setEnabled(true);
				peso.setEnabled(true);
				peso.setText("");
				densidad.setText("");
				densidad.setEnabled(false);
			}
			
		});
		
		btnGuardar.addActionListener( e -> {
			
			if(controlador.validarDatos(checkBoxLiquido.isSelected(), nombre.getText(), descripcion.getText(), costo.getText(), stock.getText(), peso.getText(), densidad.getText())) {
				if(checkBoxLiquido.isSelected()) controlador.crearInsumo(nombre.getText(), descripcion.getText(), (UnidadDeMedida)cmboxUDM.getSelectedItem(), Double.parseDouble(costo.getText()), Integer.parseInt(stock.getText()), -1.00, checkBoxRefrigerado.isSelected(), Double.parseDouble(densidad.getText()));
				else controlador.crearInsumo(nombre.getText(), descripcion.getText(), (UnidadDeMedida)cmboxUDM.getSelectedItem(), Double.parseDouble(costo.getText()), Integer.parseInt(stock.getText()), Double.parseDouble(peso.getText()), checkBoxRefrigerado.isSelected(), -1.00);
			}
			
			JFrame frame = ((JFrame)SwingUtilities.getWindowAncestor(this));
			frame.getContentPane().removeAll();
			frame.getContentPane().add(ControladorPaneles.getInstance().getPanelInsumo());
			frame.pack();
			frame.revalidate();
			frame.repaint();
			
		});
		
		btnCancelar.addActionListener( e -> {
			JFrame frame = ((JFrame)SwingUtilities.getWindowAncestor(this));
			frame.getContentPane().removeAll();
			frame.getContentPane().add(ControladorPaneles.getInstance().getPanelInsumo());
			frame.pack();
			frame.revalidate();
			frame.repaint();
		});
		
	}
}
