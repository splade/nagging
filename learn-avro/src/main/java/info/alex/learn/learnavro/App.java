package info.alex.learn.learnavro;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

class App {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    void main() throws IOException {

        // 1. 指定　Schema
        Schema schema = new Schema.Parser().parse(
                new File("./learn-avro/src/main/avro/person.avsc"));

        // 2. Generic
        GenericRecord record = new GenericData.Record(schema);
        record.put("name", "allen");
        record.put("age", 20);
        record.put("favorite_color", "red, blue, yellow");


        // 3. save to file

        GenericDatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<GenericRecord>();
        DataFileWriter<GenericRecord> writer = new DataFileWriter<GenericRecord>(datumWriter);

        File file = new File("./learn-avro/person.avro");

        writer.create(schema, file);
        writer.append(record);
        writer.append(record);

        writer.close();


        // 4. read from file

        // Schema 存储在文件中，可以通过文件获取到
        GenericDatumReader<GenericRecord> datumReader = new GenericDatumReader<GenericRecord>();
        DataFileReader<GenericRecord> reader = new DataFileReader<GenericRecord>(file, datumReader);

        while (reader.hasNext()) {
            GenericRecord r = reader.next();
            logger.info("RECORD: {}", r);
        }

        reader.close();


    }
}
