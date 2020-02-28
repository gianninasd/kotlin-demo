package dg.dao

import org.apache.commons.dbcp2.BasicDataSource
import java.sql.Connection
import java.sql.PreparedStatement

/**
 * Base class for all DAO with re-usable methods
 */
abstract class AbstractDAO {

  /**
   * Executes INSERT database operation using the SQL statement and data provided
   */
  fun insert( sqlStmt:String, data:Array<Any> ):Int {
    val ds:BasicDataSource = getDataSource()
    val cn:Connection = ds.getConnection()
    val stmt:PreparedStatement = cn.prepareStatement(sqlStmt)
    stmt.setString(1,data[0] as String)
    stmt.setString(2,data[1] as String)
    stmt.executeUpdate()
    //cn.commit()

    return 0
    /*Sql sql = null
    def id = 0

    try {
      def ds = getDataSource(config)
      sql = new Sql(ds)
      def result = sql.executeInsert(sqlStmt, data)
      id = result[0][0]
    }
    finally {
      sql.close() // returns connection to the pool
    }*/
  }

  companion object {
    private var ds:BasicDataSource? = null

    // initializes and returns pooled data source
    fun getDataSource(/* def config */):BasicDataSource {
      if( ds == null ) {
        ds = BasicDataSource()
        ds?.setUrl("jdbc:mysql://localhost:3306/fileproc"/*config.url*/)
        ds?.setUsername("root"/*config.username*/)
        ds?.setPassword("root"/*config.password*/)
        ds?.setDriverClassName("com.mysql.cj.jdbc.Driver"/*config.driverClassName*/)
        ds?.setMinIdle(5/*config.minIdle*/)
        ds?.setMaxIdle(10/*config.maxIdle*/)
        ds?.setMaxOpenPreparedStatements(100/*config.maxOpenPreparedStatements*/)
      }

      return ds!!
    }
  }
}