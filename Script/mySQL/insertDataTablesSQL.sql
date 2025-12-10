-- Insertar Tiendas
INSERT INTO Tiendas (Tienda_ID, Nombre_tienda, Direccion, Telefono, CorreoElectronico) VALUES
(1, 'Pollos Sobrinos Centro', 'Calle Mayor 123, Madrid', '910123456', 'centro@pollossobrinos.com'),
(2, 'Pollos Sobrinos Norte', 'Avenida Norte 45, Barcelona', '932987654', 'norte@pollossobrinos.com');

-- Insertar Proveedores
INSERT INTO Proveedores (Proveedor_ID, Nombre, NIF_NIE_CIF, Telefono, CorreoElectronico, Direccion, Tienda_ID) VALUES
(1, 'Proveedor Pollos S.L.', 'A12345678', '912345678', 'info@proveedorpollos.com', 'Polígono Industrial 1, Madrid', 1),
(2, 'Distribuidora Carnes Norte', 'B87654321', '934567890', 'ventas@carnesnorte.com', 'Calle Industria 45, Barcelona', 2),
(6, 'Panecillos María S.L', 'B2156899', '933654710', 'panes.maria@gmail.com', 'Carrer del Tosqué', 2),
(7, 'Productos Agricolas S.L', 'B156324789', '9123456712', 'info@productosagricolas.com', 'calle falsa 124', 1),
(8, 'Productos Agricolas S.L', 'B156324788', '9123456712', 'info@productosagricolas.com', 'calle falsa 124', 2);

-- Insertar Productos
INSERT INTO Productos (Producto_ID, Nombre, Precio, Stock, Proveedor_ID) VALUES
(9, 'Pollo Entero', 12.50, 50, 1),
(10, 'Alitas de Pollo', 8.75, 100, 1),
(11, 'Hamburguesa de Pollo', 4.50, 200, 2),
(12, 'Pechuga de Pollo', 9.90, 80, 2),
(13, 'Muslos de Pollo', 6.25, 120, 1),
(14, 'Croquetas de Pollo', 5.80, 180, 1),
(15, 'Alas BBQ Preparadas', 9.40, 75, 2),
(16, 'Salchichas de Pollo', 4.20, 150, 2),
(17, 'Nuggets de Pollo', 6.80, 250, 1),
(18, 'Pollo Troceado', 8.30, 90, 2);

-- Insertar Clientes
INSERT INTO Clientes (Cliente_ID, Nombre, Apellido, NIF_NIE, Telefono, CorreoElectronico, Tienda_ID) VALUES
(1, 'María', 'Rodríguez', '22334455D', '644556677', 'maria.rodriguez@gmail.com', 1),
(2, 'Javier', 'Fernández', '33445566E', '655667788', 'javier.fernandez@hotmail.com', 2),
(3, 'Sofía', 'García', '44556677F', '666778899', 'sofia.garcia@yahoo.com', 1),
(4, 'Juan', 'Ramón', '54863210Y', '646598725', 'juan.ramon@gmail.com', 2),
(5, 'África', 'Lorca', '87413210V', '647852634', 'africa.lorca@gmail.com', 2);

-- Insertar Empleados
INSERT INTO Empleados (Empleado_ID, Nombre, Apellido, NIF_NIE, Telefono, CorreoElectronico, Puesto, Salario, Tienda_ID) VALUES
(1, 'Carlos', 'Gómez', '12345678A', '611223344', 'carlos.gomez@pollossobrinos.com', 'Gerente', 2800.00, 1),
(2, 'Ana', 'López', '87654321B', '622334455', 'ana.lopez@pollossobrinos.com', 'Cajera', 1800.00, 1),
(3, 'Luis', 'Martínez', '11223344C', '633445566', 'luis.martinez@pollossobrinos.com', 'Cocinero', 2000.00, 2),
(4, 'Sandra', 'Sánchez', '94562145Q', '647856111', 'sandra.sanchez@gmail.com', 'cocinero', 2000.00, 1),
(5, 'Hugo', 'Diaz', '74532169P', '615645893', 'hugo.diaz@gmail.com', 'cajero', 1800.00, 2),
(6, 'Jesus', 'Gonzalez', '47852163N', '657148723', 'jesus.gonzalez@gmail.com', 'Gerente', 2800.00, 2);