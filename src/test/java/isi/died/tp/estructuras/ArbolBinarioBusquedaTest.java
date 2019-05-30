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
		
		abb.agregar(0);
		abb.agregar(5);
		abb.agregar(15);
		
		abb2 = new ArbolBinarioBusqueda<Integer>(10);
		
		abb2.agregar(5);
		abb2.agregar(0);
		abb2.agregar(15);
		abb2.agregar(13);
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
		assertEquals(2, abb.profundidad());
	}

	@Test
	public void testCuentaNodosDeNivel() {
		assertEquals(1, abb.cuentaNodosDeNivel(0));
		assertEquals(2, abb.cuentaNodosDeNivel(1));
		assertEquals(1, abb.cuentaNodosDeNivel(2));
	}

	@Test
	public void testEsCompleto() {
		assertTrue(abb2.esCompleto());
		assertFalse(abb.esCompleto());
	}

	@Test
	public void testEsLleno() {
		fail("Not yet implemented");
	}

}
