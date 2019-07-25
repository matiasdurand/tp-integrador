package isi.died.tp.dao;

import java.util.List;

import isi.died.tp.dominio.Liquido;

public interface LiquidoDao {

	public Liquido crear(Liquido i);
	public Liquido actualizar(Liquido i);
	public void borrar(Integer id);
	public Liquido buscar(Integer id);
	public List<Liquido> buscarTodos();
}
