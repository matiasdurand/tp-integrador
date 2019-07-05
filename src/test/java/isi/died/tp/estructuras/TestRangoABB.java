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
		
		abbInsumos = new ArbolBinarioBusqueda<Insumo>(new Insumo("Descripcion1", UnidadDeMedida.Kilogramo, 50.00, 10, 5.00, false));
		abbInsumos.agregar(new Insumo("Descripcion2", UnidadDeMedida.Pieza, 100.00, 5, 10.00, false));
		abbInsumos.agregar(new Liquido("Descripcion3", 250.00, 15, false, 997.00));
		
	}
	
	@Test
	public void testRango() {
		ArrayList<Insumo> lista1 = abbInsumos.rango(20.00, 30.00);
		assertEquals(0, lista1.size());
		
		ArrayList<Insumo> lista2 = abbInsumos.rango(10.00, 15.00);
		assertEquals(2, lista2.size());
		
		ArrayList<Insumo> lista3 = abbInsumos.rango(0.00, 18.00);
		assertEquals(3, lista3.size());
	}

}
