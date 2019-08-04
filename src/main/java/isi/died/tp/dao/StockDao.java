package isi.died.tp.dao;

import java.util.List;

import isi.died.tp.dominio.Stock;

public interface StockDao {
	
	public Stock crear(Integer idPlanta, Stock s);
	public Stock actualizar(Integer idPlanta, Stock s);
	public void borrar(Integer idPlanta, Integer idInsumo);
	public List<Stock> buscarTodos(Integer idPlanta);
	public Stock buscar(Integer idPlanta, Integer idInsumo);
}
