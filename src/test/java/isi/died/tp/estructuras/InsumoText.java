package isi.died.tp.estructuras;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import isi.died.tp.dominio.Insumo;
import isi.died.tp.dominio.Liquido;
import isi.died.tp.dominio.UnidadDeMedida;

public class InsumoText {

	private Insumo insumo1;
	private Insumo insumo2;
	private Insumo insumoLiquido;
	
	@Before
	public void preTest() {
		insumo1 = new Insumo("Descripcion1", UnidadDeMedida.Pieza, 500.00, 5.00, 10.00, false);
		insumo2 = new Insumo("Descripcion2", UnidadDeMedida.Kilogramo, 100.00, 50.00, 0.50, false);
		insumoLiquido = new Liquido("Descripcion3", 50.00, 25.00, true, 997.00);
	}
	
	@Test
	public void calcularPeso() {
		Double resultado = 997.00;
		assertEquals(resultado, ((Liquido)insumoLiquido).calcularPeso(1000.00));
	}

}
