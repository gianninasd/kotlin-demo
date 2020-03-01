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
      cn = ds.connection
      val stmt:PreparedStatement = cn.prepareStatement(sqlStmt, Statement.RETURN_GENERATED_KEYS)

      applyData( stmt, data )
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
      cn = ds.connection
      val stmt:PreparedStatement = cn.prepareStatement(sqlStmt)

      applyData( stmt, data )
      val rs:ResultSet = stmt.executeQuery()
      var value = 0

      if( rs.next() ){
        value = rs.getInt(1)
      }

      return value
    }
    finally {
      cn?.close()
    }
  }

  /**
   * Loop thru each data parameter and assign it to the statement
   */
  private fun applyData( stmt:PreparedStatement, data:Array<Any> ) {
    for( idx in data.indices ) {
      when( data[idx] ) {
        is String -> stmt.setString((idx + 1), data[idx] as String)
        is Int -> stmt.setInt((idx + 1), data[idx] as Int)
        is Double -> stmt.setDouble((idx + 1), data[idx] as Double)
        is Date -> stmt.setDate((idx + 1), data[idx] as Date)
      }
    }
  }

  companion object {
    private var ds:BasicDataSource? = null

    // initializes and returns pooled data source
    fun getDataSource( config:Properties ):BasicDataSource {
      if( ds == null ) {
        ds = BasicDataSource()
        ds?.url = config.getProperty("db.url")
        ds?.username = config.getProperty("db.username")
        ds?.password = config.getProperty("db.password")
        ds?.driverClassName = config.getProperty("db.driverClassName")
        ds?.minIdle = config.getProperty("db.minIdle").toInt()
        ds?.maxIdle = config.getProperty("db.maxIdle").toInt()
        ds?.maxOpenPreparedStatements = config.getProperty("db.maxOpenPreparedStatements").toInt()
      }

      return ds!!
    }
  }
}