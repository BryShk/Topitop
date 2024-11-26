package DAO;

import Clases.Producto;
import Conexion.Conectar;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO extends Conectar {

    public boolean crear(Producto p) {
        conectar();
        String sql = "INSERT INTO Productos (nombre, descripcion, precio, cantidad_en_stock, imagen) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = getConexion().prepareStatement(sql);
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDescripcion());
            ps.setDouble(3, p.getPrecio());
            ps.setInt(4, p.getCantidadEnStock());
            ps.setBytes(5, p.getImagen());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            desconectar();
        }
    }

    public boolean actualizar(Producto p) {
        conectar();
        String sql = "UPDATE Productos SET nombre = ?, descripcion = ?, precio = ?, cantidad_en_stock = ?, imagen = ? WHERE id_producto = ?";
        try {
            PreparedStatement ps = getConexion().prepareStatement(sql);
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDescripcion());
            ps.setDouble(3, p.getPrecio());
            ps.setInt(4, p.getCantidadEnStock());
            ps.setBytes(5, p.getImagen());
            ps.setInt(6, p.getIdProducto());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            desconectar();
        }
    }

    public boolean borrar(int id_producto) {
        conectar();

        // Eliminación en cascada manual de detalles de órdenes (si aplica)
        // Aquí podrías agregar código para eliminar relaciones si es necesario

        String sql = "DELETE FROM Productos WHERE id_producto = ?";
        try {
            PreparedStatement ps = getConexion().prepareStatement(sql);
            ps.setInt(1, id_producto);
            ps.execute();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            desconectar();
        }
    }

    public List<Producto> obtener() {
        conectar();
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM Productos";
        try {
            Statement st = getConexion().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Producto p = new Producto(
                    rs.getInt("id_producto"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getDouble("precio"),
                    rs.getInt("cantidad_en_stock"),
                    rs.getBytes("imagen")
                );
                productos.add(p);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            desconectar();
        }
        return productos;
    }

    public Producto buscarPorId(int id) {
        conectar();
        Producto producto = null;
        String sql = "SELECT * FROM Productos WHERE id_producto = ?";
        try {
            PreparedStatement ps = getConexion().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                producto = new Producto(
                    rs.getInt("id_producto"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getDouble("precio"),
                    rs.getInt("cantidad_en_stock"),
                    rs.getBytes("imagen")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            desconectar();
        }
        return producto;
    }
}
