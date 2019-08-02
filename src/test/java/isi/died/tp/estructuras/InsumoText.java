package isi.died.tp.estructuras;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import isi.died.tp.dominio.Insumo;
import isi.died.tp.dominio.Liquido;

public class InsumoText {

	private Insumo insumoLiquido;
	
	@Before
	public void preTest() {
		insumoLiquido = new Liquido("Insumo1", "DescripcionLiquido", 50.00, 25, true, 997.00, true);
	}
	
	@Test
	public void calcularPeso() {
		Double resultado = 997.00;
		assertEquals(resultado, ((Liquido)insumoLiquido).calcularPeso(1000.00));
	}

}
