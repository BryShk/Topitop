

package DAO;

import Clases.Usuario;
import Clases.Orden;
import Conexion.Conectar;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO extends Conectar {

    public boolean crear(Usuario u) {
        conectar();
        String sql = "INSERT INTO Usuarios (nombre_usuario, contraseña, correo_electronico, nombre, apellido, direccion, telefono) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = getConexion().prepareStatement(sql);
            ps.setString(1, u.getNombreUsuario());
            ps.setString(2, u.getContraseña());
            ps.setString(3, u.getCorreoElectronico());
            ps.setString(4, u.getNombre());
            ps.setString(5, u.getApellido());
            ps.setString(6, u.getDireccion());
            ps.setString(7, u.getTelefono());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            desconectar();
        }
    }

    public boolean actualizar(Usuario u) {
        conectar();
        String sql = "UPDATE Usuarios SET nombre_usuario = ?, contraseña = ?, correo_electronico = ?, nombre = ?, apellido = ?, direccion = ?, telefono = ? WHERE id_usuario = ?";
        try {
            PreparedStatement ps = getConexion().prepareStatement(sql);
            ps.setString(1, u.getNombreUsuario());
            ps.setString(2, u.getContraseña());
            ps.setString(3, u.getCorreoElectronico());
            ps.setString(4, u.getNombre());
            ps.setString(5, u.getApellido());
            ps.setString(6, u.getDireccion());
            ps.setString(7, u.getTelefono());
            ps.setInt(8, u.getIdUsuario());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            desconectar();
        }
    }

    public boolean borrar(int id_usuario) {
        conectar();

        // Eliminación en cascada manual de órdenes y detalles de órdenes
        OrdenDAO ordenDAO = new OrdenDAO();
        List<Orden> ordenes = ordenDAO.obtenerPorUsuario(id_usuario);
        for (Orden orden : ordenes) {
            ordenDAO.borrar(orden.getIdOrden());
        }

        String sql = "DELETE FROM Usuarios WHERE id_usuario = ?";
        try {
            PreparedStatement ps = getConexion().prepareStatement(sql);
            ps.setInt(1, id_usuario);
            ps.execute();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            desconectar();
        }
    }

    public List<Usuario> obtener() {
        conectar();
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM Usuarios";
        try {
            Statement st = getConexion().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Usuario u = new Usuario(
                    rs.getInt("id_usuario"),
                    rs.getString("nombre_usuario"),
                    rs.getString("contraseña"),
                    rs.getString("correo_electronico"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("direccion"),
                    rs.getString("telefono")
                );
                usuarios.add(u);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            desconectar();
        }
        return usuarios;
    }

    public Usuario buscarPorId(int id) {
        conectar();
        Usuario usuario = null;
        String sql = "SELECT * FROM Usuarios WHERE id_usuario = ?";
        try {
            PreparedStatement ps = getConexion().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                usuario = new Usuario(
                    rs.getInt("id_usuario"),
                    rs.getString("nombre_usuario"),
                    rs.getString("contraseña"),
                    rs.getString("correo_electronico"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("direccion"),
                    rs.getString("telefono")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            desconectar();
        }
        return usuario;
    }
}
