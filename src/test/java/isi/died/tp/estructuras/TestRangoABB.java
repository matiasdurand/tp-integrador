package isi.died.tp.estructuras;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import isi.died.tp.dominio.Insumo;
import isi.died.tp.dominio.Insumo.UnidadDeMedida;
import isi.died.tp.dominio.Liquido;

public class TestRangoABB {

	private ArbolBinarioBusqueda<Insumo> abbInsumos;
	
	@Before
	public void preTest() {
		
		abbInsumos = new ArbolBinarioBusqueda<Insumo>(new Insumo("Insumo1", "Descripcion1", UnidadDeMedida.Kilogramo, 50.00, 10, 5.00, false, false));
		abbInsumos.agregar(new Insumo("Insumo2", "Descripcion2", UnidadDeMedida.Pieza, 100.00, 5, 10.00, false, false));
		abbInsumos.agregar(new Liquido("Insumo3", "Descripcion3", 250.00, 15, false, 997.00, true));
		
	}
	
	@Test
	public void testRango() {
		ArrayList<Insumo> lista1 = abbInsumos.rango("", 1000.00, Double.MAX_VALUE, 0, Integer.MAX_VALUE);
		assertEquals(0, lista1.size());
		
		ArrayList<Insumo> lista2 = abbInsumos.rango("", 0.0, Double.MAX_VALUE, 0, Integer.MAX_VALUE);
		assertEquals(3, lista2.size());
	}

}
