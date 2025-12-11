-- 4. INSERTAR DATOS
INSERT INTO Tiendas (Tienda_ID, Nombre_tienda, Direccion, Telefono, CorreoElectronico) VALUES (1, 'Pollos Sobrinos Centro', 'Calle Mayor 123, Madrid', '910123456', 'centro@pollossobrinos.com');
INSERT INTO Tiendas (Tienda_ID, Nombre_tienda, Direccion, Telefono, CorreoElectronico) VALUES (2, 'Pollos Sobrinos Norte', 'Avenida Norte 45, Barcelona', '932987654', 'norte@pollossobrinos.com');

INSERT INTO Proveedores (Proveedor_ID, Nombre, NIF_NIE_CIF, Telefono, CorreoElectronico, Direccion, Tienda_ID) VALUES (1, 'Proveedor Pollos S.L.', 'A12345678', '912345678', 'info@proveedorpollos.com', 'Polígono Industrial 1, Madrid', 1);
INSERT INTO Proveedores (Proveedor_ID, Nombre, NIF_NIE_CIF, Telefono, CorreoElectronico, Direccion, Tienda_ID) VALUES (2, 'Distribuidora Carnes Norte', 'B87654321', '934567890', 'ventas@carnesnorte.com', 'Calle Industria 45, Barcelona', 2);
INSERT INTO Proveedores (Proveedor_ID, Nombre, NIF_NIE_CIF, Telefono, CorreoElectronico, Direccion, Tienda_ID) VALUES (6, 'Panecillos María S.L', 'B2156899', '933654710', 'panes.maria@gmail.com', 'Carrer del Tosqué', 2);
INSERT INTO Proveedores (Proveedor_ID, Nombre, NIF_NIE_CIF, Telefono, CorreoElectronico, Direccion, Tienda_ID) VALUES (7, 'Productos Agricolas S.L', 'B156324789', '9123456712', 'info@productosagricolas.com', 'calle falsa 124', 1);
INSERT INTO Proveedores (Proveedor_ID, Nombre, NIF_NIE_CIF, Telefono, CorreoElectronico, Direccion, Tienda_ID) VALUES (8, 'Productos Agricolas S.L', 'B156324788', '9123456712', 'info@productosagricolas.com', 'calle falsa 124', 2);

INSERT INTO Productos (Producto_ID, Nombre, Precio, Stock, Proveedor_ID) VALUES (9, 'Pollo Entero', 12.50, 50, 1);
INSERT INTO Productos (Producto_ID, Nombre, Precio, Stock, Proveedor_ID) VALUES (10, 'Alitas de Pollo', 8.75, 100, 1);
INSERT INTO Productos (Producto_ID, Nombre, Precio, Stock, Proveedor_ID) VALUES (11, 'Hamburguesa de Pollo', 4.50, 200, 2);
INSERT INTO Productos (Producto_ID, Nombre, Precio, Stock, Proveedor_ID) VALUES (12, 'Pechuga de Pollo', 9.90, 80, 2);
INSERT INTO Productos (Producto_ID, Nombre, Precio, Stock, Proveedor_ID) VALUES (13, 'Muslos de Pollo', 6.25, 120, 1);
INSERT INTO Productos (Producto_ID, Nombre, Precio, Stock, Proveedor_ID) VALUES (14, 'Croquetas de Pollo', 5.80, 180, 1);
INSERT INTO Productos (Producto_ID, Nombre, Precio, Stock, Proveedor_ID) VALUES (15, 'Alas BBQ Preparadas', 9.40, 75, 2);
INSERT INTO Productos (Producto_ID, Nombre, Precio, Stock, Proveedor_ID) VALUES (16, 'Salchichas de Pollo', 4.20, 150, 2);
INSERT INTO Productos (Producto_ID, Nombre, Precio, Stock, Proveedor_ID) VALUES (17, 'Nuggets de Pollo', 6.80, 250, 1);
INSERT INTO Productos (Producto_ID, Nombre, Precio, Stock, Proveedor_ID) VALUES (18, 'Pollo Troceado', 8.30, 90, 2);

INSERT INTO Clientes (Cliente_ID, Nombre, Apellido, NIF_NIE, Telefono, CorreoElectronico, Tienda_ID) VALUES (1, 'María', 'Rodríguez', '22334455D', '644556677', 'maria.rodriguez@gmail.com', 1);
INSERT INTO Clientes (Cliente_ID, Nombre, Apellido, NIF_NIE, Telefono, CorreoElectronico, Tienda_ID) VALUES (2, 'Javier', 'Fernández', '33445566E', '655667788', 'javier.fernandez@hotmail.com', 2);
INSERT INTO Clientes (Cliente_ID, Nombre, Apellido, NIF_NIE, Telefono, CorreoElectronico, Tienda_ID) VALUES (3, 'Sofía', 'García', '44556677F', '666778899','sofia.garcia@hotmail.com' , 1);
INSERT INTO Clientes (Cliente_ID, Nombre, Apellido, NIF_NIE, Telefono, CorreoElectronico, Tienda_ID) VALUES (4, 'Juan', 'Ramón', '54863210Y', '646598725','juan.ramon@hotmail.com' , 2);
INSERT INTO Clientes (Cliente_ID, Nombre, Apellido, NIF_NIE, Telefono, CorreoElectronico, Tienda_ID) VALUES (5, 'África', 'Lorca', '87413210V', '647852634','africa.lorca@hotmail.com' , 2);

INSERT INTO Empleados (Empleado_ID, Nombre, Apellido, NIF_NIE, Telefono, CorreoElectronico, Puesto, Salario, Tienda_ID) VALUES (1, 'Carlos', 'Gómez', '12345678A', '611223344', 'carlos.gomez@pollossobrinos.com', 'Gerente', 2800.00, 1);
INSERT INTO Empleados (Empleado_ID, Nombre, Apellido, NIF_NIE, Telefono, CorreoElectronico, Puesto, Salario, Tienda_ID) VALUES (2, 'Ana', 'López', '87654321B', '622334455', 'ana.lopez@pollossobrinos.com', 'Cajera', 1800.00, 1);
INSERT INTO Empleados (Empleado_ID, Nombre, Apellido, NIF_NIE, Telefono, CorreoElectronico, Puesto, Salario, Tienda_ID) VALUES (3, 'Luis', 'Martínez', '11223344C', '633445566','luis.martines@hotmail.com' , 'Cocinero', 2000.00, 2);
INSERT INTO Empleados (Empleado_ID, Nombre, Apellido, NIF_NIE, Telefono, CorreoElectronico, Puesto, Salario, Tienda_ID) VALUES (4, 'Sandra', 'Sánchez', '94562145Q', '647856111','sandra.sanchez@hotmail.com' , 'Cocinero', 2000.00, 1);
INSERT INTO Empleados (Empleado_ID, Nombre, Apellido, NIF_NIE, Telefono, CorreoElectronico, Puesto, Salario, Tienda_ID) VALUES (5, 'Hugo', 'Diaz', '74532169P', '615645893','hugo.diaz@hotmail.com' , 'Cajero', 1800.00, 2);
INSERT INTO Empleados (Empleado_ID, Nombre, Apellido, NIF_NIE, Telefono, CorreoElectronico, Puesto, Salario, Tienda_ID) VALUES (6, 'Jesus', 'Gonzalez', '47852163N', '657148723','jesus.gonzalez@hotmail.com' , 'Gerente', 2800.00, 2);