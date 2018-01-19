package com.github.saint1991.samples

import java.io.File

import org.apache.avro.Schema
import org.apache.avro.file.DataFileWriter
import org.apache.avro.specific.SpecificDatumWriter

import scala.util.control.Exception.allCatch

object FileGen extends App {

  final val Schema = Nobid.SCHEMA$
  val writer = new SpecificDatumWriter[Nobid](Schema)

  final val N = 10
  val dataset = DataSet.createDataset(N)

  final val outFile = new File("out/nobids.avro")
  outFile.createNewFile()

  val outFileWriter = new DataFileWriter[Nobid](writer)

  // write to file
  writeToFile(dataset, Schema, outFile, outFileWriter)

  private def writeToFile(dataset: Seq[Nobid], writerSchema: Schema, file: File, writer: DataFileWriter[Nobid]) = allCatch andFinally {
    writer.flush()
    writer.close()
  } apply {
    writer.create(writerSchema, file)
    dataset.foreach { nobid =>
      writer.append(nobid)
    }
  }
}
