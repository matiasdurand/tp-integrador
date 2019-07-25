package isi.died.tp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import isi.died.tp.dominio.Liquido;


public class LiquidoDaoH2 implements LiquidoDao {


	private static final String SQL_CREATE = "CREATE TABLE IF NOT EXISTS Liquido (ID IDENTITY NOT NULL PRIMARY KEY, NOMBRE VARCHAR(30), DESCRIPCION VARCHAR(140), UNIDAD_DE_MEDIDA ENUM('Gramo', 'Kilogramo', 'Metro', 'MetroCuadrado', 'MetroCubico', 'Litro', 'Pieza'), COSTO DOUBLE, STOCK INTEGER, PESO DOUBLE, ES_REFRIGERADO BOOLEAN, DENSIDAD DOUBLE)";
	private static final String SQL_INSERT = "INSERT INTO Liquido (NOMBRE,DESCRIPCION,UNIDAD_DE_MEDIDA,COSTO,STOCK,PESO,ES_REFRIGERADO,DENSIDAD) VALUES (?,?,?,?,?,?,?,?)";
	private static final String SQL_UPDATE = "UPDATE Liquido SET NOMBRE =?, DESCRIPCION=?, UNIDAD_DE_MEDIDA=?, COSTO=?, STOCK=?, PESO=?, ES_REFRIGERADO=?, DENSIDAD=? WHERE ID = ?";
	private static final String SQL_DELETE = "DELETE FROM Liquido WHERE ID =?";
	private static final String SQL_SELECT = "SELECT ID, NOMBRE, DESCRIPCION, UNIDAD_DE_MEDIDA, COSTO, STOCK, PESO, ES_REFRIGERADO, DENSIDAD FROM Liquido";
		
	public LiquidoDaoH2() {
		try(Connection conn = DBConnection.get()){
			try(Statement st = conn.createStatement()){
				st.executeUpdate(SQL_CREATE);				
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	@Override
	public Liquido crear(Liquido l) {
		try(Connection conn = DBConnection.get()){
			try(PreparedStatement pst = conn.prepareStatement(SQL_INSERT,PreparedStatement.RETURN_GENERATED_KEYS)){
				pst.setString(1, l.getNombre());
				pst.setString(2, l.getDescripcion());
				pst.setString(3, l.getUnidadDeMedida().name());
				pst.setDouble(4, l.getCosto());
				pst.setInt(5, l.getStock());
				pst.setDouble(6, l.getPeso());
				pst.setBoolean(7, l.getEsRefrigerado());
				pst.setDouble(8, l.getDensidad());
				pst.executeUpdate();
				try(ResultSet rs = pst.getGeneratedKeys()){
					if(rs.next()) {
						l.setId(rs.getInt(1));
					}
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return l;
	}
	
	@Override
	public Liquido actualizar(Liquido l) {
		try(Connection conn = DBConnection.get()){
			try(PreparedStatement pst = conn.prepareStatement(SQL_UPDATE)){
				pst.setString(1, l.getNombre());
				pst.setString(2, l.getDescripcion());
				pst.setString(3, l.getUnidadDeMedida().name());
				pst.setDouble(4, l.getCosto());
				pst.setInt(5, l.getStock());
				pst.setDouble(6, l.getPeso());
				pst.setBoolean(7, l.getEsRefrigerado());
				pst.setDouble(8,l.getDensidad());
				pst.setInt(9, l.getId());
				pst.executeUpdate();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return l;
	}

	@Override
	public void borrar(Integer id) {
		try(Connection conn = DBConnection.get()){
			try(PreparedStatement pst = conn.prepareStatement(SQL_DELETE)){
				pst.setInt(1, id);
				pst.executeUpdate();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	@Override
	public Liquido buscar(Integer id) {
		Liquido resultado = null;
		String sqlById = SQL_SELECT + " WHERE ID = ?";
		try(Connection conn = DBConnection.get()){
			try(PreparedStatement pst = conn.prepareStatement(sqlById)){
				pst.setInt(1, id);
				try(ResultSet rs = pst.executeQuery()){
					if(rs.next()) {
						resultado = new Liquido();
						resultado.setId(rs.getInt("ID"));
						resultado.setNombre(rs.getString("NOMBRE"));
						resultado.setDescripcion(rs.getString("DESCRIPCION"));
						resultado.setUnidadDeMedida(rs.getString("UNIDAD_DE_MEDIDA"));
						resultado.setCosto(Double.valueOf(rs.getString("COSTO")));
						resultado.setStock(rs.getInt("STOCK"));
						resultado.setPeso(rs.getDouble("PESO"));
						resultado.setEsRefrigerado(rs.getBoolean("ES_REFRIGERADO"));
						resultado.setDensidad(rs.getDouble("DENSIDAD"));
					}
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return resultado;
	}
	
	@Override
	public List<Liquido> buscarTodos() {
		List<Liquido> resultado = new ArrayList<Liquido>();
		try(Connection conn = DBConnection.get()){
			try(PreparedStatement pst = conn.prepareStatement(SQL_SELECT)){
				try(ResultSet rs = pst.executeQuery()){
					while(rs.next()) {
						Liquido aux = new Liquido();
						aux = new Liquido();
						aux.setId(rs.getInt("ID"));
						aux.setNombre(rs.getString("NOMBRE"));
						aux.setDescripcion(rs.getString("DESCRIPCION"));
						aux.setUnidadDeMedida(rs.getString("UNIDAD_DE_MEDIDA"));
						aux.setCosto(Double.valueOf(rs.getString("COSTO")));
						aux.setStock(rs.getInt("STOCK"));
						aux.setPeso(rs.getDouble("PESO"));
						aux.setEsRefrigerado(rs.getBoolean("ES_REFRIGERADO"));
						aux.setDensidad(rs.getDouble("DENSIDAD"));
						resultado.add(aux);
					}
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return resultado;	
	}
}
