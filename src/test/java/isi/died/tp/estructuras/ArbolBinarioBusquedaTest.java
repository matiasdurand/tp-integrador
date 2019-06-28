package isi.died.tp.estructuras;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ArbolBinarioBusquedaTest {
		
	private ArbolBinarioBusqueda<Integer> abb;
	private ArbolBinarioBusqueda<Integer> abb2;
	
	
	@Before
	public void preTest() {
		
		abb = new ArbolBinarioBusqueda<Integer>(10);
		
		abb.agregar(5);
		abb.agregar(15);
		
		abb2 = new ArbolBinarioBusqueda<Integer>(10);
		
		abb2.agregar(5);
		abb2.agregar(2);
		abb2.agregar(1);
		abb2.agregar(7);
		abb2.agregar(20);

	}

	@Test
	public void testContiene() {
		assertTrue(abb.contiene(10));
		assertFalse(abb.contiene(11));
	}

	@Test
	public void testEqualsArbolOfE() {
		fail("Not yet implemented");
	}

	@Test
	public void testAgregar() {
		fail("Not yet implemented");
	}

	@Test
	public void testProfundidad() {
		assertEquals(1, abb.profundidad());
	}

	@Test
	public void testCuentaNodosDeNivel() {
		assertEquals(1, abb.cuentaNodosDeNivel(0));
		assertEquals(2, abb.cuentaNodosDeNivel(1));
	}

	@Test
	public void testEsCompleto() {
		assertTrue(abb2.esCompleto());
		assertTrue(abb.esCompleto());
	}

	@Test
	public void testEsLleno() {
		assertTrue(abb.esLleno());
		
	}
}
