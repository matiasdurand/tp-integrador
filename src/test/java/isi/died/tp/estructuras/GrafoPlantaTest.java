package isi.died.tp.estructuras;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import isi.died.tp.dominio.Insumo;
import isi.died.tp.dominio.Liquido;
import isi.died.tp.dominio.Planta;
import isi.died.tp.dominio.Stock;
import isi.died.tp.dominio.UnidadDeMedida;

public class GrafoPlantaTest {

	private Insumo insumo1;
	private Insumo insumo2;
	private Insumo insumo3;
	private Insumo insumo4;
	
	private List<Stock> listaStockPlantaAcopio;
	private List<Stock> listaStockPlanta02;
	private List<Stock> listaStockPlanta03;
	private List<Stock> listaStockPlanta04;

	private Planta plantaAcopio;
	private Planta planta02;
	private Planta planta03;
	private Planta planta04;
	
	private Grafo<Planta> grafoDePlantas;
	
	@Before
	public void cargarDatos() {
		
		insumo1 = new Insumo("Descripcion1", UnidadDeMedida.Pieza, 500.00, 10, 5.00, false);
		insumo2 = new Insumo("Descripcion3", UnidadDeMedida.Kilogramo, 100.00, 15, 1.00, false);
		insumo3 = new Insumo("Descripcion6", UnidadDeMedida.Metro, 5.00, 1000, 0.10, false);
		insumo4 = new Liquido("Descripcion10", 1000.00, 10, true, 13600.00);
		
		listaStockPlantaAcopio = new ArrayList<Stock>();
		listaStockPlantaAcopio.add(new Stock(10, 15, insumo1));
		listaStockPlantaAcopio.add(new Stock(15, 8, insumo2));
		listaStockPlantaAcopio.add(new Stock(1000, 10, insumo3));
		listaStockPlantaAcopio.add(new Stock(10, 2, insumo4));
		
		listaStockPlanta02 = new ArrayList<Stock>();
		listaStockPlanta02.add(new Stock(1, 5, insumo2));
		
		listaStockPlanta03 = new ArrayList<Stock>();
		listaStockPlanta03.add(new Stock(5, 2, insumo4));
		listaStockPlanta03.add(new Stock(10, 20, insumo3));
		
		listaStockPlanta04 = new ArrayList<Stock>();
		listaStockPlanta04.add(new Stock(11, 10, insumo1));
		listaStockPlanta04.add(new Stock(20, 5, insumo2));
		listaStockPlanta04.add(new Stock(5, 10, insumo3));
		
		plantaAcopio = new Planta("PlantaAcopio", listaStockPlantaAcopio);  
		planta02 = new Planta("Planta02", listaStockPlanta02);
		planta03 = new Planta("Planta03", listaStockPlanta03);
		planta04 = new Planta("Planta04", listaStockPlanta04);
		
		grafoDePlantas = new GrafoPlanta();
		grafoDePlantas.addNodo(plantaAcopio);
		grafoDePlantas.addNodo(planta02);
		grafoDePlantas.addNodo(planta03);
		grafoDePlantas.addNodo(planta04);
		
		grafoDePlantas.conectar(plantaAcopio, planta02);
		grafoDePlantas.conectar(planta02, planta03);
		grafoDePlantas.conectar(planta03, planta04);
	
	}
	
	@Test
	public void buscarPlanta() {
		
		Planta plantaEncontrada1 = grafoDePlantas.buscarPlanta(plantaAcopio, insumo1, 2);
		assertEquals(plantaAcopio, plantaEncontrada1);
		
		Planta plantaEncontrada2 = grafoDePlantas.buscarPlanta(plantaAcopio, insumo2, 2);
		assertEquals(planta02, plantaEncontrada2);
		
		Planta plantaEncontrada3 = grafoDePlantas.buscarPlanta(plantaAcopio, insumo3, 2);
		assertEquals(planta03, plantaEncontrada3);
		Planta plantaEncontrada4 = grafoDePlantas.buscarPlanta(plantaAcopio, insumo3, 1);
		assertEquals(null, plantaEncontrada4);
		
		Planta plantaEncontrada5 = grafoDePlantas.buscarPlanta(plantaAcopio, insumo4, 2);
		assertEquals(null, plantaEncontrada5);
		
	}

}
