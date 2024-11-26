package DAO;

import Clases.DetalleOrden;
import Conexion.Conectar;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetalleOrdenDAO extends Conectar {

    public boolean crear(DetalleOrden d) {
        conectar();
        String sql = "INSERT INTO DetallesOrden (id_orden, id_producto, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = getConexion().prepareStatement(sql);
            ps.setInt(1, d.getIdOrden());
            ps.setInt(2, d.getIdProducto());
            ps.setInt(3, d.getCantidad());
            ps.setDouble(4, d.getPrecioUnitario());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            desconectar();
        }
    }

    public boolean actualizar(DetalleOrden d) {
        conectar();
        String sql = "UPDATE DetallesOrden SET id_orden = ?, id_producto = ?, cantidad = ?, precio_unitario = ? WHERE id_detalle = ?";
        try {
            PreparedStatement ps = getConexion().prepareStatement(sql);
            ps.setInt(1, d.getIdOrden());
            ps.setInt(2, d.getIdProducto());
            ps.setInt(3, d.getCantidad());
            ps.setDouble(4, d.getPrecioUnitario());
            ps.setInt(5, d.getIdDetalle());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            desconectar();
        }
    }

    public boolean borrar(int id_detalle) {
        conectar();
        String sql = "DELETE FROM DetallesOrden WHERE id_detalle = ?";
        try {
            PreparedStatement ps = getConexion().prepareStatement(sql);
            ps.setInt(1, id_detalle);
            ps.execute();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            desconectar();
        }
    }

    public boolean borrarPorOrden(int id_orden) {
        conectar();
        String sql = "DELETE FROM DetallesOrden WHERE id_orden = ?";
        try {
            PreparedStatement ps = getConexion().prepareStatement(sql);
            ps.setInt(1, id_orden);
            ps.execute();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            desconectar();
        }
    }

    public List<DetalleOrden> obtener() {
        conectar();
        List<DetalleOrden> detalleOrdenes = new ArrayList<>();
        String sql = "SELECT * FROM DetallesOrden";
        try {
            Statement st = getConexion().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                DetalleOrden d = new DetalleOrden(
                    rs.getInt("id_detalle"),
                    rs.getInt("id_orden"),
                    rs.getInt("id_producto"),
                    rs.getInt("cantidad"),
                    rs.getDouble("precio_unitario")
                );
                detalleOrdenes.add(d);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            desconectar();
        }
        return detalleOrdenes;
    }

    public List<DetalleOrden> obtenerPorOrden(int id_orden) {
        conectar();
        List<DetalleOrden> detalleOrdenes = new ArrayList<>();
        String sql = "SELECT * FROM DetallesOrden WHERE id_orden = ?";
        try {
            PreparedStatement ps = getConexion().prepareStatement(sql);
            ps.setInt(1, id_orden);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DetalleOrden d = new DetalleOrden(
                    rs.getInt("id_detalle"),
                    rs.getInt("id_orden"),
                    rs.getInt("id_producto"),
                    rs.getInt("cantidad"),
                    rs.getDouble("precio_unitario")
                );
                detalleOrdenes.add(d);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            desconectar();
        }
        return detalleOrdenes;
    }

    public List<DetalleOrden> obtenerPorProducto(int id_producto) {
        conectar();
        List<DetalleOrden> detalleOrdenes = new ArrayList<>();
        String sql = "SELECT * FROM DetallesOrden WHERE id_producto = ?";
        try {
            PreparedStatement ps = getConexion().prepareStatement(sql);
            ps.setInt(1, id_producto);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DetalleOrden d = new DetalleOrden(
                    rs.getInt("id_detalle"),
                    rs.getInt("id_orden"),
                    rs.getInt("id_producto"),
                    rs.getInt("cantidad"),
                    rs.getDouble("precio_unitario")
                );
                detalleOrdenes.add(d);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            desconectar();
        }
        return detalleOrdenes;
    }
}
