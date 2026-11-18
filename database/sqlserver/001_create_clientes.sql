IF OBJECT_ID(N'dbo.Clientes', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.Clientes (
        cpf CHAR(11) NOT NULL,
        nome NVARCHAR(255) NOT NULL,
        email NVARCHAR(320) NOT NULL,
        telefone NVARCHAR(30) NOT NULL,
        CONSTRAINT PK_Clientes PRIMARY KEY (cpf)
    );
END;
