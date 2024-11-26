package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import Clases.Producto;
import DAO.ProductoDAO;

public final class adminProductos_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write('\n');
      out.write('\n');

    // Manejo de acciones (crear, actualizar, eliminar)
    String accion = request.getParameter("accion");
    ProductoDAO productoDAO = new ProductoDAO();

    if ("crear".equals(accion)) {
        // Obtener parámetros del formulario
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        double precio = Double.parseDouble(request.getParameter("precio"));
        int cantidadEnStock = Integer.parseInt(request.getParameter("cantidadEnStock"));
        String imagenUrl = request.getParameter("imagenUrl");

        Producto nuevoProducto = new Producto(0, nombre, descripcion, precio, cantidadEnStock, imagenUrl);
        productoDAO.crear(nuevoProducto);
    } else if ("actualizar".equals(accion)) {
        int idProducto = Integer.parseInt(request.getParameter("idProducto"));
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        double precio = Double.parseDouble(request.getParameter("precio"));
        int cantidadEnStock = Integer.parseInt(request.getParameter("cantidadEnStock"));
        String imagenUrl = request.getParameter("imagenUrl");

        Producto productoActualizado = new Producto(idProducto, nombre, descripcion, precio, cantidadEnStock, imagenUrl);
        productoDAO.actualizar(productoActualizado);
    } else if ("eliminar".equals(accion)) {
        int idProducto = Integer.parseInt(request.getParameter("idProducto"));
        productoDAO.borrar(idProducto);
    }

    // Obtener la lista actualizada de productos
    List<Producto> listaProductos = productoDAO.obtener();

      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("    <meta charset=\"UTF-8\">\n");
      out.write("    <title>Administración de Productos</title>\n");
      out.write("    <!-- Agrega los enlaces a Bootstrap CSS -->\n");
      out.write("    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css\">\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("<div class=\"container\">\n");
      out.write("    <h1 class=\"mt-4\">Panel de Administración de Productos</h1>\n");
      out.write("\n");
      out.write("    <!-- Formulario para crear nuevo producto -->\n");
      out.write("    <h3 class=\"mt-5\">Crear Nuevo Producto</h3>\n");
      out.write("    <form method=\"post\" action=\"adminProductos.jsp\">\n");
      out.write("        <input type=\"hidden\" name=\"accion\" value=\"crear\">\n");
      out.write("        <div class=\"form-group\">\n");
      out.write("            <label>Nombre:</label>\n");
      out.write("            <input type=\"text\" name=\"nombre\" class=\"form-control\" required>\n");
      out.write("        </div>\n");
      out.write("        <div class=\"form-group\">\n");
      out.write("            <label>Descripción:</label>\n");
      out.write("            <textarea name=\"descripcion\" class=\"form-control\"></textarea>\n");
      out.write("        </div>\n");
      out.write("        <div class=\"form-group\">\n");
      out.write("            <label>Precio:</label>\n");
      out.write("            <input type=\"number\" step=\"0.01\" name=\"precio\" class=\"form-control\" required>\n");
      out.write("        </div>\n");
      out.write("        <div class=\"form-group\">\n");
      out.write("            <label>Cantidad en Stock:</label>\n");
      out.write("            <input type=\"number\" name=\"cantidadEnStock\" class=\"form-control\" required>\n");
      out.write("        </div>\n");
      out.write("        <div class=\"form-group\">\n");
      out.write("            <label>URL de la Imagen:</label>\n");
      out.write("            <input type=\"text\" name=\"imagenUrl\" class=\"form-control\">\n");
      out.write("        </div>\n");
      out.write("        <button type=\"submit\" class=\"btn btn-primary\">Crear Producto</button>\n");
      out.write("    </form>\n");
      out.write("\n");
      out.write("    <!-- Lista de productos -->\n");
      out.write("    <h3 class=\"mt-5\">Lista de Productos</h3>\n");
      out.write("    <table class=\"table table-bordered\">\n");
      out.write("        <thead>\n");
      out.write("        <tr>\n");
      out.write("            <th>ID Producto</th>\n");
      out.write("            <th>Nombre</th>\n");
      out.write("            <th>Descripción</th>\n");
      out.write("            <th>Precio</th>\n");
      out.write("            <th>Cantidad en Stock</th>\n");
      out.write("            <th>Imagen</th>\n");
      out.write("            <th>Acciones</th>\n");
      out.write("        </tr>\n");
      out.write("        </thead>\n");
      out.write("        <tbody>\n");
      out.write("        ");

            for (Producto producto : listaProductos) {
        
      out.write("\n");
      out.write("        <tr>\n");
      out.write("            <td>");
      out.print( producto.getIdProducto() );
      out.write("</td>\n");
      out.write("            <td>");
      out.print( producto.getNombre() );
      out.write("</td>\n");
      out.write("            <td>");
      out.print( producto.getDescripcion() );
      out.write("</td>\n");
      out.write("            <td>");
      out.print( producto.getPrecio() );
      out.write("</td>\n");
      out.write("            <td>");
      out.print( producto.getCantidadEnStock() );
      out.write("</td>\n");
      out.write("            <td><img src=\"");
      out.print( producto.getImagenUrl() );
      out.write("\" alt=\"Imagen\" width=\"50\"></td>\n");
      out.write("            <td>\n");
      out.write("                <!-- Botón para editar -->\n");
      out.write("                <button class=\"btn btn-warning btn-sm\" data-toggle=\"modal\" data-target=\"#modalEditar");
      out.print( producto.getIdProducto() );
      out.write("\">Editar</button>\n");
      out.write("\n");
      out.write("                <!-- Modal para editar producto -->\n");
      out.write("                <div class=\"modal fade\" id=\"modalEditar");
      out.print( producto.getIdProducto() );
      out.write("\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"modalEditarLabel");
      out.print( producto.getIdProducto() );
      out.write("\" aria-hidden=\"true\">\n");
      out.write("                  <div class=\"modal-dialog\" role=\"document\">\n");
      out.write("                    <form method=\"post\" action=\"adminProductos.jsp\">\n");
      out.write("                        <input type=\"hidden\" name=\"accion\" value=\"actualizar\">\n");
      out.write("                        <input type=\"hidden\" name=\"idProducto\" value=\"");
      out.print( producto.getIdProducto() );
      out.write("\">\n");
      out.write("                        <div class=\"modal-content\">\n");
      out.write("                          <div class=\"modal-header\">\n");
      out.write("                            <h5 class=\"modal-title\" id=\"modalEditarLabel");
      out.print( producto.getIdProducto() );
      out.write("\">Editar Producto</h5>\n");
      out.write("                            <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Cerrar\">\n");
      out.write("                              <span aria-hidden=\"true\">&times;</span>\n");
      out.write("                            </button>\n");
      out.write("                          </div>\n");
      out.write("                          <div class=\"modal-body\">\n");
      out.write("                                <div class=\"form-group\">\n");
      out.write("                                    <label>Nombre:</label>\n");
      out.write("                                    <input type=\"text\" name=\"nombre\" class=\"form-control\" value=\"");
      out.print( producto.getNombre() );
      out.write("\" required>\n");
      out.write("                                </div>\n");
      out.write("                                <div class=\"form-group\">\n");
      out.write("                                    <label>Descripción:</label>\n");
      out.write("                                    <textarea name=\"descripcion\" class=\"form-control\">");
      out.print( producto.getDescripcion() );
      out.write("</textarea>\n");
      out.write("                                </div>\n");
      out.write("                                <div class=\"form-group\">\n");
      out.write("                                    <label>Precio:</label>\n");
      out.write("                                    <input type=\"number\" step=\"0.01\" name=\"precio\" class=\"form-control\" value=\"");
      out.print( producto.getPrecio() );
      out.write("\" required>\n");
      out.write("                                </div>\n");
      out.write("                                <div class=\"form-group\">\n");
      out.write("                                    <label>Cantidad en Stock:</label>\n");
      out.write("                                    <input type=\"number\" name=\"cantidadEnStock\" class=\"form-control\" value=\"");
      out.print( producto.getCantidadEnStock() );
      out.write("\" required>\n");
      out.write("                                </div>\n");
      out.write("                                <div class=\"form-group\">\n");
      out.write("                                    <label>URL de la Imagen:</label>\n");
      out.write("                                    <input type=\"text\" name=\"imagenUrl\" class=\"form-control\" value=\"");
      out.print( producto.getImagenUrl() );
      out.write("\">\n");
      out.write("                                </div>\n");
      out.write("                          </div>\n");
      out.write("                          <div class=\"modal-footer\">\n");
      out.write("                            <button type=\"button\" class=\"btn btn-secondary\" data-dismiss=\"modal\">Cerrar</button>\n");
      out.write("                            <button type=\"submit\" class=\"btn btn-primary\">Guardar Cambios</button>\n");
      out.write("                          </div>\n");
      out.write("                        </div>\n");
      out.write("                    </form>\n");
      out.write("                  </div>\n");
      out.write("                </div>\n");
      out.write("\n");
      out.write("                <!-- Botón para eliminar -->\n");
      out.write("                <form method=\"post\" action=\"adminProductos.jsp\" style=\"display:inline-block;\">\n");
      out.write("                    <input type=\"hidden\" name=\"accion\" value=\"eliminar\">\n");
      out.write("                    <input type=\"hidden\" name=\"idProducto\" value=\"");
      out.print( producto.getIdProducto() );
      out.write("\">\n");
      out.write("                    <button type=\"submit\" class=\"btn btn-danger btn-sm\" onclick=\"return confirm('¿Está seguro de eliminar este producto?')\">Eliminar</button>\n");
      out.write("                </form>\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("        ");

            }
        
      out.write("\n");
      out.write("        </tbody>\n");
      out.write("    </table>\n");
      out.write("</div>\n");
      out.write("\n");
      out.write("<!-- Agrega los scripts de Bootstrap JS y jQuery -->\n");
      out.write("<script src=\"https://code.jquery.com/jquery-3.5.1.min.js\"></script>\n");
      out.write("<script src=\"https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js\"></script>\n");
      out.write("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js\"></script>\n");
      out.write("\n");
      out.write("</body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
