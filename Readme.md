# Create a new Spring Boot project
curl https://start.spring.io/starter.tgz \
  -d dependencies=web,devtools \
  -d packageName=com.example \
  -d name=ParquetLoggingExample \
  -o parquet-logging-example.tgz

# Extract and enter the project
tar -xzf parquet-logging-example.tgz
cd ParquetLoggingExample


# Create Pom File 
<dependencies>
    <!-- Spring Boot dependencies -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- Apache Avro for schema definition -->
    <dependency>
        <groupId>org.apache.avro</groupId>
        <artifactId>avro</artifactId>
        <version>1.11.3</version>
    </dependency>

    <!-- Apache Parquet for Parquet writing -->
    <dependency>
        <groupId>org.apache.parquet</groupId>
        <artifactId>parquet-avro</artifactId>
        <version>1.12.3</version>
    </dependency>
</dependencies>


# Create Avro Schema log.json

{
  "type": "record",
  "name": "LogEntry",
  "fields": [
    {"name": "timestamp", "type": "long"},
    {"name": "level", "type": "string"},
    {"name": "message", "type": "string"},
    {"name": "source", "type": "string"}
  ]
}

# run spring boot
./mvnw spring-boot:run
