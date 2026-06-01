package com.krakedev.jdbc.videojuegos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.krakedev.apijdbc.excepciones.VideojuegoDuplicadoExcepcion;
import com.krakedev.apijdbc.excepciones.VideojuegoNoEncontradoExcepcion;
import com.krakedev.jdbc.Conexion;
import com.krakedev.videojuegos.entidades.Videojuego;


public class VideojuegoJdbc {
	
	private static final Logger log = LogManager.getLogger(VideojuegoJdbc.class);

	private static final String SQL_INSERT = """
										INSERT INTO videojuegos(codigo, nombre, plataforma, precio, disponible, genero)
										VALUES (?,?,?,?,?,?)
										""";
	private static final String SQL_BUSCAR_POR_CODIGO = """
													SELECT * FROM videojuegos
													WHERE codigo = ?
													""";
	private static final String SQL_LISTAR = "SELECT * FROM videojuegos";
	
	private static final String SQL_ACTUALIZAR = """
												UPDATE videojuegos SET nombre=?, plataforma=?, precio=?, disponible=?, genero=?
												WHERE codigo=?
												""";
	private static final String SQL_ELIMINAR = "DELETE FROM videojuegos WHERE codigo =?";
	
	
//	private void cerrar(PreparedStatement ps, Connection con) {
//		try {
//			if(ps!=null) {
//				ps.close();
//			}
//		}catch (SQLException e) {
//			log.error(e.getMessage());
//		}
//		try {
//			if(con!=null) {
//				con.close();
//			}
//		}catch (SQLException e) {
//			log.error(e.getMessage());
//		}
//	}
	
	public static boolean insertar(Videojuego v) {
		
		Connection con = null;
		PreparedStatement ps1=null;
		PreparedStatement ps2=null;
		ResultSet rs=null;
		
		try {
			con = Conexion.getConnection();
			ps1 = con.prepareStatement(SQL_BUSCAR_POR_CODIGO);
			ps1.setString(1,v.getCodigo());
			rs = ps1.executeQuery();
			
			if(!rs.next()) {
				con = Conexion.getConnection();
				ps2 = con.prepareStatement(SQL_INSERT);
				ps2.setString(1,v.getCodigo());
				ps2.setString(2,v.getNombre());
				ps2.setString(3,v.getPlataforma());
				ps2.setDouble(4,v.getPrecio());
				ps2.setBoolean(5,v.isDisponible());
				ps2.setString(6,v.getGenero());
				int filas = ps2.executeUpdate();
				log.info("Videojuegos insertados: "+filas);
				return filas >0? true : false;
			} else {
				throw new VideojuegoDuplicadoExcepcion("Esta intentado insertar un codigo ya existente");
			}
			
		} catch (SQLException e) {
			log.error("Error de conexión: "+ e.getMessage());
			throw new RuntimeException("No se puede insertar videojuego");
		} finally {
			
			if(ps1 != null) {
				try {
					ps1.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(ps2 != null) {
				try {
					ps2.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con !=null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			log.info("Conexion Cerrada");
			
		}
		
	}	
	
	public static Videojuego buscar(String codigo) {
		Connection con = null;
		PreparedStatement ps=null;
		ResultSet rs= null;
		Videojuego v = null;
		try {
			
			con = Conexion.getConnection();
			ps = con.prepareStatement(SQL_BUSCAR_POR_CODIGO);
			ps.setString(1,codigo);
			rs = ps.executeQuery();
			
			
			if(rs.next()) {
				v = new Videojuego();
				v.setCodigo(codigo);
				v.setNombre(rs.getString("nombre"));
				v.setPlataforma(rs.getString("plataforma"));
				v.setPrecio(rs.getInt("precio"));
				v.setDisponible(rs.getBoolean("disponible"));
				v.setGenero(rs.getString("genero"));
			}
			log.info("Videojuego enontrado");
			if (v == null) {
		        throw new VideojuegoNoEncontradoExcepcion("No se encontró el videojuego con el código: " + codigo);
		    }
			return v;
			
		} catch (SQLException e) {
			log.error("Error al buscar videojuego: "+ e.getMessage());
			throw new VideojuegoNoEncontradoExcepcion("Error general al buscar el videojuego con codigo "+codigo);
		} finally {
			if(ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(con !=null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			log.info("Conexion Cerrada");

		}
		
	}
	
	public static ArrayList<Videojuego> listar() {
		Connection con = null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		ArrayList<Videojuego> videojuegos = new ArrayList<Videojuego>();

		try {
			
			con = Conexion.getConnection();
			ps = con.prepareStatement(SQL_LISTAR);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Videojuego v = new Videojuego();
				v.setCodigo(rs.getString("codigo"));
				v.setNombre(rs.getString("nombre"));
				v.setPlataforma(rs.getString("plataforma"));
				v.setPrecio(rs.getInt("precio"));
				v.setDisponible(rs.getBoolean("disponible"));
				v.setGenero(rs.getString("genero"));
				v.toString();
				videojuegos.add(v);
			}
			if(videojuegos.isEmpty()) {
				throw new RuntimeException("No existen videojuegos");
			}
			log.info("Videojuegos listados exitosamente");
			return videojuegos;
			
			
		} catch (SQLException e) {
			log.error("Error al listar videojuegos: "+ e.getMessage());
			throw new RuntimeException("Error general al listar videojuegos");
		} finally {
			if(ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(con !=null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			log.info("Conexion Cerrada");

		}
	}
	
	public static boolean actualizar(String codigo, Videojuego v) {

		Connection con = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;

		try {
			con = Conexion.getConnection();
			ps1 = con.prepareStatement(SQL_BUSCAR_POR_CODIGO);
			ps1.setString(1, codigo);
			rs = ps1.executeQuery();

			if (rs.next()) {
				con = Conexion.getConnection();
				ps2 = con.prepareStatement(SQL_ACTUALIZAR);
				ps2.setString(1, v.getNombre());
				ps2.setString(2, v.getPlataforma());
				ps2.setDouble(3, v.getPrecio());
				ps2.setBoolean(4, v.isDisponible());
				ps2.setString(5, v.getGenero());
				ps2.setString(6, codigo);
				int filas = ps2.executeUpdate();
				log.info("Videojuego actualizado: " + filas);
				return filas > 0 ? true : false;
			} else {
				throw new VideojuegoNoEncontradoExcepcion("No se encontró el videojuego con el código: " + codigo);
			}

		} catch (SQLException e) {
			log.error("Error de conexión: " + e.getMessage());
			throw new RuntimeException("No se puede actualizar videojuego");
		} finally {

			if (ps1 != null) {
				try {
					ps1.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (ps2 != null) {
				try {
					ps2.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			log.info("Conexion Cerrada");

		}

	}

	public static boolean eliminar(String codigo) {

		Connection con = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;

		try {
			con = Conexion.getConnection();
			ps1 = con.prepareStatement(SQL_BUSCAR_POR_CODIGO);
			ps1.setString(1, codigo);
			rs = ps1.executeQuery();

			if (rs.next()) {
				con = Conexion.getConnection();
				ps2 = con.prepareStatement(SQL_ELIMINAR);
				ps2.setString(1, codigo);
				int filas = ps2.executeUpdate();
				log.info("Videojuego eliminado: " + filas);
				return filas > 0 ? true : false;
			} else {
				throw new VideojuegoNoEncontradoExcepcion("No se encontró el videojuego con el código: " + codigo);
			}

		} catch (SQLException e) {
			log.error("Error de conexión: " + e.getMessage());
			throw new RuntimeException("No se puede eliminar videojuego");
		} finally {

			if (ps1 != null) {
				try {
					ps1.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (ps2 != null) {
				try {
					ps2.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			log.info("Conexion Cerrada");

		}

	}
	
	
	

}
