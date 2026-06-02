CREATE DATABASE InventoryDB;
GO

USE InventoryDB;
GO

CREATE TABLE Barang (
    id_barang INT IDENTITY(1,1) PRIMARY KEY,
    nama_barang VARCHAR(100) NOT NULL,
    kategori VARCHAR(50) NOT NULL,
    stok INT NOT NULL,
    harga INT NOT NULL
);
GO

USE InventoryDB;
GO

CREATE USER Db_Con FOR LOGIN Db_Con;
GO

ALTER ROLE db_owner ADD MEMBER Db_Con;
GO

