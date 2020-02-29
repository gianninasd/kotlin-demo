package dg.dao

import org.apache.commons.dbcp2.BasicDataSource
import java.sql.*
import java.sql.Date
import java.util.*

/**
 * Base class for all DAO with re-usable methods
 */
abstract class AbstractDAO(private val config:Properties) {

  /**
   * Executes INSERT database operation using the SQL statement and data provided
   */
  fun insert( sqlStmt:String, data:Array<Any> ):Int {
    val ds:BasicDataSource?
    var cn:Connection? = null

    try {
      ds = getDataSource(config)
      cn = ds.getConnection()
      val stmt:PreparedStatement = cn.prepareStatement(sqlStmt, Statement.RETURN_GENERATED_KEYS)

      // loop thru each data parameter and assign it to the statement
      for( idx in data.indices ) {
        when( data[idx] ) {
          is String -> stmt.setString((idx + 1), data[idx] as String)
          is Int -> stmt.setInt((idx + 1), data[idx] as Int)
          is Double -> stmt.setDouble((idx + 1), data[idx] as Double)
          is Date -> stmt.setDate((idx + 1), data[idx] as Date)
        }
      }

      stmt.executeUpdate()

      var generatedId = 0
      val rs:ResultSet = stmt.generatedKeys

      if( rs.next() ){
        generatedId = rs.getInt(1)
      }

      return generatedId
    }
    finally {
      cn?.close()
      //ds?.close()
    }
  }

  /**
   * Returns a single value from executing a SELECT database operation using the SQL statement and data provided
   */
  fun queryForInt( sqlStmt:String, data:Array<Any> ):Int {
    val ds:BasicDataSource?
    var cn:Connection? = null

    try {
      ds = getDataSource(config)
      cn = ds.getConnection()
      val stmt:PreparedStatement = cn.prepareStatement(sqlStmt)

      // loop thru each data parameter and assign it to the statement
      for( idx in data.indices ) {
        when( data[idx] ) {
          is String -> stmt.setString((idx + 1), data[idx] as String)
          is Int -> stmt.setInt((idx + 1), data[idx] as Int)
          is Double -> stmt.setDouble((idx + 1), data[idx] as Double)
          is Date -> stmt.setDate((idx + 1), data[idx] as Date)
        }
      }

      val rs:ResultSet = stmt.executeQuery()
      var value = 0

      if( rs.next() ){
        value = rs.getInt(1)
      }

      return value
    }
    finally {
      cn?.close()
      //ds?.close()
    }
  }

  companion object {
    private var ds:BasicDataSource? = null

    // initializes and returns pooled data source
    fun getDataSource( config:Properties ):BasicDataSource {
      if( ds == null ) {
        ds = BasicDataSource()
        ds?.setUrl(config.getProperty("db.url"))
        ds?.setUsername(config.getProperty("db.username"))
        ds?.setPassword(config.getProperty("db.password"))
        ds?.setDriverClassName(config.getProperty("db.driverClassName"))
        ds?.setMinIdle(config.getProperty("db.minIdle").toInt())
        ds?.setMaxIdle(config.getProperty("db.maxIdle").toInt())
        ds?.setMaxOpenPreparedStatements(config.getProperty("db.maxOpenPreparedStatements").toInt())
      }

      return ds!!
    }
  }
}