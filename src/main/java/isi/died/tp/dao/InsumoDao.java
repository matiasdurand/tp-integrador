package isi.died.tp.dao;

import java.util.List;

import isi.died.tp.dominio.Insumo;

public interface InsumoDao {

	public void crear(Insumo i);
	public void actualizar(Insumo i);
	public void borrar(Integer id);
	public Insumo buscar(Integer id);
	public List<Insumo> buscarTodos();
}
