package isi.died.tp.dao;

import java.util.List;

import isi.died.tp.dominio.Camion;

public interface CamionDao {

	public Camion crear(Camion c);
	public Camion actualizar(Camion c);
	public void borrar(Integer id);
	public Camion buscar(Integer id);
	public List<Camion> buscarTodos();
	
}
