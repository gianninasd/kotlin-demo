package dg.dao

import java.sql.ResultSet
import java.util.*
import kotlin.collections.ArrayList

const val CREATE:String = """
    insert into file_records (file_id,status_cde,raw_record,creation_date,modification_date)
    values (?,?,?,now(),now())"""

const val LOAD_ALL_INITIAL:String = """
  select record_id, creation_date, raw_record from fileproc.file_records
  where status_cde = "INITIAL" order by creation_date asc limit 10 """

data class Record(val recordId:Int = 0, val rawData:String = "")

/**
 * DAO to interact with file records
 */
class RecordDAO(config:Properties):AbstractDAO(config) {

  /**
   * Create a new record entry
   */
  fun createInitial( fileId:Int, record:String ) {
    val data:Array<Any> = arrayOf(fileId,"INITIAL",record)

    insert(CREATE, data)
  }

  /**
   * Returns all the records in an initial status
   */
  fun getAllWithStatusInitial():ArrayList<Record> {
    val data:Array<Any> = emptyArray()
    var rs:ResultSet? = null

    try {
      rs = query(LOAD_ALL_INITIAL, data)
      val recs:ArrayList<Record> = arrayListOf()

      while( rs.next() ) {
        val rec = Record(rs.getInt("record_id"), rs.getString("raw_record"))
        recs.add(rec)
      }

      return recs
    }
    finally {
      rs?.statement?.connection?.close()
    }
  }
}