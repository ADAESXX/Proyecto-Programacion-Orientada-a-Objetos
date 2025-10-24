import mysql from 'mysql2/promise';

class ConexionBD {
    // Datos de conexión (ajusta según tu configuración)
    static #URL = 'localhost';
    static #PORT = 3306;
    static #DATABASE = 'plataforma_apoyo';
    static #USER = 'root';
    static #PASSWORD = 'MyNewPass1';

    // Objeto conexión
    static #conn = null;

    // Obtener conexión
    static async getConnection() {
        if (this.#conn === null) {
            try {
                this.#conn = await mysql.createConnection({
                    host: this.#URL,
                    port: this.#PORT,
                    database: this.#DATABASE,
                    user: this.#USER,
                    password: this.#PASSWORD,
                    charset: 'utf8mb4'
                });
                console.log('Conectado a la base de datos.');
            } catch (error) {
                console.error('Error de conexión:', error.message);
                return null;
            }
        }
        return this.#conn;
    }

    // Probar conexión
    static async probarConexion() {
        try {
            const connection = await this.getConnection();
            if (connection) {
                await connection.ping();
                return true;
            }
            return false;
        } catch (error) {
            console.error('Error al verificar el estado de la conexión:', error.message);
            return false;
        }
    }

    // Cerrar conexión
    static async cerrarConexion() {
        try {
            if (this.#conn !== null) {
                await this.#conn.end();
                this.#conn = null;
                console.log('Conexión cerrada correctamente.');
            }
        } catch (error) {
            console.error('Error al cerrar la conexión:', error.message);
        }
    }
}

export default ConexionBD;
