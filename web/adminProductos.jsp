<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, Clases.Producto, DAO.ProductoDAO" %>
<!DOCTYPE html>
<html>
<head>
    <title>Administración de Productos</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
</head>

<body>
<div class="container mt-5">
    <h2 class="mb-4">Administración de Productos</h2>

    <!-- Formulario para agregar/editar productos -->
    <form action="ProductoServlet" method="post" enctype="multipart/form-data">
        <input type="hidden" name="accion" value="<%= request.getParameter("accion") != null ? request.getParameter("accion") : "crear" %>">
        <input type="hidden" name="id_producto" value="<%= request.getParameter("id_producto") != null ? request.getParameter("id_producto") : "" %>">

        <div class="form-group">
            <label for="nombre">Nombre del Producto</label>
            <input type="text" class="form-control" id="nombre" name="nombre" value="<%= request.getParameter("nombre") != null ? request.getParameter("nombre") : "" %>" required>
        </div>

        <div class="form-group">
            <label for="descripcion">Descripción</label>
            <textarea class="form-control" id="descripcion" name="descripcion" required><%= request.getParameter("descripcion") != null ? request.getParameter("descripcion") : "" %></textarea>
        </div>

        <div class="form-group">
            <label for="precio">Precio</label>
            <input type="number" step="0.01" class="form-control" id="precio" name="precio" value="<%= request.getParameter("precio") != null ? request.getParameter("precio") : "" %>" required>
        </div>

        <div class="form-group">
            <label for="cantidad_en_stock">Cantidad en Stock</label>
            <input type="number" class="form-control" id="cantidad_en_stock" name="cantidad_en_stock" value="<%= request.getParameter("cantidad_en_stock") != null ? request.getParameter("cantidad_en_stock") : "" %>" required>
        </div>

        <div class="form-group">
            <label for="imagen">Imagen del Producto</label>
            <input type="file" class="form-control-file" id="imagen" name="imagen" <%= request.getParameter("accion") != null && request.getParameter("accion").equals("editar") ? "" : "required" %>>
        </div>

        <button type="submit" class="btn btn-primary"><%= request.getParameter("accion") != null && request.getParameter("accion").equals("editar") ? "Actualizar" : "Agregar" %> Producto</button>
    </form>

    <hr>

    <!-- Listado de productos -->
    <h3>Listado de Productos</h3>
    <table class="table table-bordered mt-3">
        <thead>
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Descripción</th>
            <th>Precio</th>
            <th>Cantidad en Stock</th>
            <th>Imagen</th>
            <th>Acciones</th>
        </tr>
        </thead>
        <tbody>
        <%
            ProductoDAO productoDAO = new ProductoDAO();
            List<Producto> productos = productoDAO.obtener();

            for (Producto producto : productos) {
        %>
        <tr>
            <td><%= producto.getIdProducto() %></td>
            <td><%= producto.getNombre() %></td>
            <td><%= producto.getDescripcion() %></td>
            <td><%= producto.getPrecio() %></td>
            <td><%= producto.getCantidadEnStock() %></td>
            <td>
                <% if (producto.getImagen() != null) { %>
                    <img src="data:image/jpeg;base64,<%= Base64.getEncoder().encodeToString(producto.getImagen()) %>" width="100">
                <% } else { %>
                    Sin imagen
                <% } %>
            </td>
            <td>
                <form action="adminProductos.jsp" method="get" style="display:inline;">
                    <input type="hidden" name="accion" value="editar">
                    <input type="hidden" name="id_producto" value="<%= producto.getIdProducto() %>">
                    <input type="hidden" name="nombre" value="<%= producto.getNombre() %>">
                    <input type="hidden" name="descripcion" value="<%= producto.getDescripcion() %>">
                    <input type="hidden" name="precio" value="<%= producto.getPrecio() %>">
                    <input type="hidden" name="cantidad_en_stock" value="<%= producto.getCantidadEnStock() %>">
                    <button type="submit" class="btn btn-warning">Editar</button>
                </form>
                <form action="ProductoServlet" method="post" style="display:inline;">
                    <input type="hidden" name="accion" value="eliminar">
                    <input type="hidden" name="id_producto" value="<%= producto.getIdProducto() %>">
                    <button type="submit" class="btn btn-danger" onclick="return confirm('¿Estás seguro de eliminar este producto?');">Eliminar</button>
                </form>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>
</body>
</html>
