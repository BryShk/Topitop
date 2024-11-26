package DAO;

import Clases.Orden;
import Clases.DetalleOrden;
import Conexion.Conectar;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdenDAO extends Conectar {

    public boolean crear(Orden o) {
        conectar();
        String sql = "INSERT INTO Ordenes (id_usuario, fecha_orden, total) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = getConexion().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, o.getIdUsuario());
            ps.setTimestamp(2, new Timestamp(o.getFechaOrden().getTime()));
            ps.setDouble(3, o.getTotal());
            ps.executeUpdate();

            // Obtener el id_orden generado
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                o.setIdOrden(generatedKeys.getInt(1));
            }

            // Insertar detalles de la orden
            DetalleOrdenDAO detalleOrdenDAO = new DetalleOrdenDAO();
            for (DetalleOrden detalle : o.getDetalles()) {
                detalle.setIdOrden(o.getIdOrden());
                detalleOrdenDAO.crear(detalle);
            }

            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            desconectar();
        }
    }

    public boolean actualizar(Orden o) {
        conectar();
        String sql = "UPDATE Ordenes SET id_usuario = ?, fecha_orden = ?, total = ? WHERE id_orden = ?";
        try {
            PreparedStatement ps = getConexion().prepareStatement(sql);
            ps.setInt(1, o.getIdUsuario());
            ps.setTimestamp(2, new Timestamp(o.getFechaOrden().getTime()));
            ps.setDouble(3, o.getTotal());
            ps.setInt(4, o.getIdOrden());
            ps.execute();

            // Actualizar detalles de la orden
            DetalleOrdenDAO detalleOrdenDAO = new DetalleOrdenDAO();

            // Eliminar detalles existentes
            detalleOrdenDAO.borrarPorOrden(o.getIdOrden());

            // Insertar nuevos detalles
            for (DetalleOrden detalle : o.getDetalles()) {
                detalle.setIdOrden(o.getIdOrden());
                detalleOrdenDAO.crear(detalle);
            }

            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            desconectar();
        }
    }

    public boolean borrar(int id_orden) {
        conectar();

        // Eliminación en cascada manual de detalles de la orden
        DetalleOrdenDAO detalleOrdenDAO = new DetalleOrdenDAO();
        detalleOrdenDAO.borrarPorOrden(id_orden);

        String sql = "DELETE FROM Ordenes WHERE id_orden = ?";
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

    public List<Orden> obtener() {
        conectar();
        List<Orden> ordenes = new ArrayList<>();
        String sql = "SELECT * FROM Ordenes";
        try {
            Statement st = getConexion().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Orden o = new Orden(
                    rs.getInt("id_orden"),
                    rs.getInt("id_usuario"),
                    rs.getTimestamp("fecha_orden"),
                    rs.getDouble("total")
                );

                // Obtener detalles de la orden
                DetalleOrdenDAO detalleOrdenDAO = new DetalleOrdenDAO();
                o.setDetalles(detalleOrdenDAO.obtenerPorOrden(o.getIdOrden()));

                ordenes.add(o);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            desconectar();
        }
        return ordenes;
    }

    public Orden buscarPorId(int id) {
        conectar();
        Orden orden = null;
        String sql = "SELECT * FROM Ordenes WHERE id_orden = ?";
        try {
            PreparedStatement ps = getConexion().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                orden = new Orden(
                    rs.getInt("id_orden"),
                    rs.getInt("id_usuario"),
                    rs.getTimestamp("fecha_orden"),
                    rs.getDouble("total")
                );

                // Obtener detalles de la orden
                DetalleOrdenDAO detalleOrdenDAO = new DetalleOrdenDAO();
                orden.setDetalles(detalleOrdenDAO.obtenerPorOrden(orden.getIdOrden()));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            desconectar();
        }
        return orden;
    }

    public List<Orden> obtenerPorUsuario(int id_usuario) {
        conectar();
        List<Orden> ordenes = new ArrayList<>();
        String sql = "SELECT * FROM Ordenes WHERE id_usuario = ?";
        try {
            PreparedStatement ps = getConexion().prepareStatement(sql);
            ps.setInt(1, id_usuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Orden o = new Orden(
                    rs.getInt("id_orden"),
                    rs.getInt("id_usuario"),
                    rs.getTimestamp("fecha_orden"),
                    rs.getDouble("total")
                );

                // Obtener detalles de la orden
                DetalleOrdenDAO detalleOrdenDAO = new DetalleOrdenDAO();
                o.setDetalles(detalleOrdenDAO.obtenerPorOrden(o.getIdOrden()));

                ordenes.add(o);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            desconectar();
        }
        return ordenes;
    }
}
