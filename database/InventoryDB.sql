USE InventoryDB;
GO

DROP TABLE Barang;
GO

CREATE TABLE Barang (
    idBarang INT IDENTITY(1,1) PRIMARY KEY,
    kodeBarang VARCHAR(10) NOT NULL UNIQUE,
    namaBarang VARCHAR(100) NOT NULL,
    kategori VARCHAR(50) NOT NULL,
    stok INT NOT NULL CHECK (stok >= 0),
    harga DECIMAL(12,2) NOT NULL CHECK (harga >= 0),
    tanggalMasuk DATE NOT NULL
);
GO

INSERT INTO Barang
(kodeBarang, namaBarang, kategori, stok, harga, tanggalMasuk)
VALUES
('MN001', 'Es Teh Manis', 'Teh', 30, 4000, '2024-05-10'),
('MN002', 'Jus Jeruk', 'Jus', 25, 8000, '2024-05-12'),
('MN003', 'Le Minerale 600ml', 'Air Mineral', 50, 5000, '2024-05-08'),
('MN004', 'Cappuccino Cincau', 'Kopi', 12, 12000, '2024-05-14'),
('MN005', 'Es Lemon Tea', 'Teh', 8, 6000, '2024-05-16'),
('MN006', 'Susu Cokelat', 'Susu', 15, 7000, '2024-05-11');
GO

SELECT * FROM Barang;