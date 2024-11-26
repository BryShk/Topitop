package Servlet;

import Clases.Producto;
import DAO.ProductoDAO;
import Conexion.Conectar;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

@MultipartConfig(maxFileSize = 16177215) // 16MB
public class ProductoServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener la acción
        String accion = request.getParameter("accion");

        ProductoDAO productoDAO = new ProductoDAO();
        Producto producto = new Producto();

        if (accion != null) {
            switch (accion) {
                case "crear":
                    // Obtener los parámetros del formulario
                    producto.setNombre(request.getParameter("nombre"));
                    producto.setDescripcion(request.getParameter("descripcion"));
                    producto.setPrecio(Double.parseDouble(request.getParameter("precio")));
                    producto.setCantidadEnStock(Integer.parseInt(request.getParameter("cantidad_en_stock")));

                    // Procesar la imagen
                    Part filePart = request.getPart("imagen");
                    if (filePart != null && filePart.getSize() > 0) {
                        InputStream inputStream = filePart.getInputStream();
                        byte[] imagenBytes = inputStream.readAllBytes();
                        producto.setImagen(imagenBytes);
                    }

                    // Crear el producto
                    productoDAO.crear(producto);
                    break;

                case "editar":
                    // Obtener los parámetros del formulario
                    producto.setIdProducto(Integer.parseInt(request.getParameter("id_producto")));
                    producto.setNombre(request.getParameter("nombre"));
                    producto.setDescripcion(request.getParameter("descripcion"));
                    producto.setPrecio(Double.parseDouble(request.getParameter("precio")));
                    producto.setCantidadEnStock(Integer.parseInt(request.getParameter("cantidad_en_stock")));

                    // Procesar la imagen si se subió una nueva
                    Part filePartEdit = request.getPart("imagen");
                    if (filePartEdit != null && filePartEdit.getSize() > 0) {
                        InputStream inputStream = filePartEdit.getInputStream();
                        byte[] imagenBytes = inputStream.readAllBytes();
                        producto.setImagen(imagenBytes);
                    } else {
                        // Mantener la imagen existente
                        Producto productoExistente = productoDAO.buscarPorId(producto.getIdProducto());
                        producto.setImagen(productoExistente.getImagen());
                    }

                    // Actualizar el producto
                    productoDAO.actualizar(producto);
                    break;

                case "eliminar":
                    int idProducto = Integer.parseInt(request.getParameter("id_producto"));
                    productoDAO.borrar(idProducto);
                    break;
            }
        }

        // Redireccionar de vuelta al JSP de administración
        response.sendRedirect("adminProductos.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
