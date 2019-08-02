package isi.died.tp.estructuras;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import isi.died.tp.dominio.Insumo;
import isi.died.tp.dominio.Insumo.UnidadDeMedida;
import isi.died.tp.dominio.Liquido;
import isi.died.tp.dominio.Planta;
import isi.died.tp.dominio.Stock;

public class PlantaTest {

	private Insumo insumo1;
	private Insumo insumo2;
	private Insumo insumo3;
	private Insumo insumo4;

	private Stock stock1;
	private Stock stock2;
	private Stock stock3;
	private Stock stock4;
	
	private List<Stock> listaStock;
	
	private Planta plantaAcopio;
	
	@Before
	public void cargarDatos() {
		
		insumo1 = new Insumo("Insumo1", "Descripcion1", UnidadDeMedida.Pieza, 500.00, 10, 5.00, false, false);
		insumo2 = new Insumo("Insumo2", "Descripcion3", UnidadDeMedida.Kilogramo, 100.00, 15, 1.00, false, false);
		insumo3 = new Insumo("Insumo3", "Descripcion6", UnidadDeMedida.Metro, 5.00, 1000, 0.10, false, false);
		insumo4 = new Liquido("Insumo4", "Descripcion10", 1000.00, 10, true, 1000.00, true);
		
		stock1 = new Stock(10, 15, insumo1);
		stock2 = new Stock(15, 8, insumo2);
		stock3 = new Stock(1000, 10, insumo3);
		stock4 = new Stock(10, 2, insumo4);
		
		listaStock = new ArrayList<Stock>();
		listaStock.add(stock1); listaStock.add(stock2); listaStock.add(stock3); listaStock.add(stock4);
		
		plantaAcopio = new Planta("PlantaAcopio");
		plantaAcopio.setListaStock(listaStock);
		
	}
	
	@Test
	public void costoTotal() {
		Double resultadoEsperado = 21500.00;
		Double costoTotal = plantaAcopio.costoTotal();
		assertEquals(resultadoEsperado, costoTotal);
	}
	
	@Test
	public void stockEntre() {
		List<Insumo> insumos1 = plantaAcopio.stockEntre(10, 1000);
		List<Insumo> insumos2 = plantaAcopio.stockEntre(0, 5);
		List<Insumo> insumos3 = plantaAcopio.stockEntre(15, 1000);
		List<Insumo> insumos4 = plantaAcopio.stockEntre(1000, 5000);
		assertEquals(4, insumos1.size());
		assertEquals(0, insumos2.size());
		assertEquals(2, insumos3.size());
		assertEquals(1, insumos4.size());
	}
	
	@Test
	public void necesitaInsumo() {
		assertTrue(plantaAcopio.necesitaInsumo(insumo1));
		assertFalse(plantaAcopio.necesitaInsumo(insumo3));
	}

}
