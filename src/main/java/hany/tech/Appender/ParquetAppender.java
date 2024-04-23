package hany.tech.Appender;

import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.hadoop.fs.Path;

import java.io.IOException;

public class ParquetAppender extends AppenderBase<ILoggingEvent> {
    private ParquetWriter<GenericRecord> parquetWriter;
    private Schema avroSchema;

    public ParquetAppender() {
        try {
            // Define Avro schema for Parquet
            avroSchema = new Schema.Parser().parse("{"
                + "\"type\":\"record\","
                + "\"name\":\"LogEntry\","
                + "\"fields\":["
                + "{\"name\":\"timestamp\", \"type\":\"long\"},"
                + "{\"name\":\"level\", \"type\":\"string\"},"
                + "{\"name\":\"logger\", \"type\":\"string\"},"
                + "{\"name\":\"message\", \"type\":\"string\"}"
                + "]"
                + "}");

            // Initialize ParquetWriter with Avro schema
            parquetWriter = AvroParquetWriter.<GenericRecord>builder(new Path("logs/logs.parquet"))
                .withSchema(avroSchema)
                .build();
        } catch (IOException e) {
            addError("Error initializing ParquetWriter", e);
        }
    }

    @Override
    protected void append(ILoggingEvent event) {
        GenericRecord record = new GenericData.Record(avroSchema);
        record.put("timestamp", event.getTimeStamp());
        record.put("level", event.getLevel().toString());
        record.put("logger", event.getLoggerName());
        record.put("message", event.getFormattedMessage());

        try {
            parquetWriter.write(record);
        } catch (IOException e) {
            addError("Error writing to Parquet file", e);
        }
    }

    @Override
    public void stop() {
        super.stop();
        if (parquetWriter != null) {
            try {
                parquetWriter.close();
            } catch (IOException e) {
                addError("Error closing ParquetWriter", e);
            }
        }
    }
}
