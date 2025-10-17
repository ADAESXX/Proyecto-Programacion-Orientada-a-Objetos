// models/dao/BaseDAO.js
//programador:Jose Ignacio Guardado
import mysql from "mysql2/promise";

// conexión a la base de datos (ajusta los datos según tu entorno)
const pool = mysql.createPool({
  host: "localhost",
  user: "root",
  password: "",
  database: "tu_basedatos",
  waitForConnections: true,
  connectionLimit: 10,
  queueLimit: 0
});

export default class BaseDAO {
  async query(sql, params = []) {
    try {
      const [rows] = await pool.query(sql, params);
      return rows;
    } catch (error) {
      console.error("Error ejecutando query:", error);
      throw error;
    }
  }

  async executeUpdate(sql, params = []) {
    try {
      const [result] = await pool.execute(sql, params);
      return result.affectedRows > 0;
    } catch (error) {
      console.error("Error ejecutando actualización:", error);
      return false;
    }
  }
}

