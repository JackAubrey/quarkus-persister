DROP DATABASE IF EXISTS cluster_resources;
DROP USER IF EXISTS `cluster_resources_user`@`%`;
CREATE DATABASE IF NOT EXISTS cluster_resources CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER IF NOT EXISTS `cluster_resources_user`@`%` IDENTIFIED WITH mysql_native_password BY 'cluster_resources_pwd';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, REFERENCES, INDEX, ALTER, EXECUTE, CREATE VIEW, SHOW VIEW,
CREATE ROUTINE, ALTER ROUTINE, EVENT, TRIGGER ON `cluster_resources`.* TO `cluster_resources_user`@`%`;
FLUSH PRIVILEGES