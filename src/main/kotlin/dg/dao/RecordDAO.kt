package dg.dao

import java.util.*

const val CREATE:String = """
    insert into file_records (file_id,status_cde,raw_record,creation_date,modification_date)
    values (?,?,?,now(),now())"""

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
}