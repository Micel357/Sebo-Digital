IF DB_ID(N'SeboDigital') IS NULL
BEGIN
    EXEC('CREATE DATABASE [SeboDigital]');
END;
