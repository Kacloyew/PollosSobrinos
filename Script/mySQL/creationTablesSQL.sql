-- Eliminar tablas en orden inverso (primero las que tienen claves foráneas)
DROP TABLE IF EXISTS Pedidos;
DROP TABLE IF EXISTS Empleados;
DROP TABLE IF EXISTS Clientes;
DROP TABLE IF EXISTS Productos;
DROP TABLE IF EXISTS Proveedores;
DROP TABLE IF EXISTS Tiendas;

-- Crear la tabla Tiendas
CREATE TABLE Tiendas (
    Tienda_ID INT PRIMARY KEY AUTO_INCREMENT,
    Nombre_tienda VARCHAR(100) NOT NULL,
    Direccion VARCHAR(255),
    Telefono VARCHAR(20),
    CorreoElectronico VARCHAR(100)
);

-- Crear la tabla Proveedores
CREATE TABLE Proveedores (
    Proveedor_ID INT PRIMARY KEY AUTO_INCREMENT,
    Nombre VARCHAR(100) NOT NULL,
    NIF_NIE_CIF VARCHAR(20) NOT NULL UNIQUE,
    Telefono VARCHAR(20),
    CorreoElectronico VARCHAR(100),
    Direccion VARCHAR(255),
    Tienda_ID INT,
    FOREIGN KEY (Tienda_ID) REFERENCES Tiendas(Tienda_ID) ON DELETE SET NULL
);

-- Crear la tabla Productos
CREATE TABLE Productos (
    Producto_ID INT PRIMARY KEY AUTO_INCREMENT,
    Nombre VARCHAR(100) NOT NULL,
    Precio DECIMAL(10,2) NOT NULL,
    Stock INT DEFAULT 0,
    Proveedor_ID INT,
    FOREIGN KEY (Proveedor_ID) REFERENCES Proveedores(Proveedor_ID) ON DELETE SET NULL
);

-- Crear la tabla Clientes
CREATE TABLE Clientes (
    Cliente_ID INT PRIMARY KEY AUTO_INCREMENT,
    Nombre VARCHAR(50) NOT NULL,
    Apellido VARCHAR(50) NOT NULL,
    NIF_NIE VARCHAR(20) NOT NULL UNIQUE,
    Telefono VARCHAR(20),
    CorreoElectronico VARCHAR(100),
    Tienda_ID INT,
    FOREIGN KEY (Tienda_ID) REFERENCES Tiendas(Tienda_ID) ON DELETE SET NULL
);

-- Crear la tabla Empleados
CREATE TABLE Empleados (
    Empleado_ID INT PRIMARY KEY AUTO_INCREMENT,
    Nombre VARCHAR(50) NOT NULL,
    Apellido VARCHAR(50) NOT NULL,
    NIF_NIE VARCHAR(20) NOT NULL UNIQUE,
    Telefono VARCHAR(20),
    CorreoElectronico VARCHAR(100),
    Puesto VARCHAR(50),
    Salario DECIMAL(10,2),
    Tienda_ID INT,
    FOREIGN KEY (Tienda_ID) REFERENCES Tiendas(Tienda_ID) ON DELETE SET NULL
);

-- Crear la tabla Pedidos
CREATE TABLE Pedidos (
    Pedido_ID INT PRIMARY KEY AUTO_INCREMENT,
    Empleado_ID INT,
    Tienda_ID INT,
    Cliente_ID INT,
    Producto_ID INT,
    Fecha_Pedido DATETIME DEFAULT CURRENT_TIMESTAMP,
    Cantidad INT DEFAULT 1,
    FOREIGN KEY (Empleado_ID) REFERENCES Empleados(Empleado_ID) ON DELETE SET NULL,
    FOREIGN KEY (Tienda_ID) REFERENCES Tiendas(Tienda_ID) ON DELETE SET NULL,
    FOREIGN KEY (Cliente_ID) REFERENCES Clientes(Cliente_ID) ON DELETE SET NULL,
    FOREIGN KEY (Producto_ID) REFERENCES Productos(Producto_ID) ON DELETE SET NULL
);