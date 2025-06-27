Simple throughput comparison between read-only and write transaction.

1. update _application.properties_ accordingly to set the needed configuration values
2. alter the Cypher queries in `EntryService(Readonly)` and `EntryRepository(ReadOnly)` to match your sub-graph/data you want to test. Keep in mind that having a `String`-typed result is a must for the application to work.
3. start this application via `./mvnw spring-boot:run`
4. run jmeter tests via `jmeter -n -t sdn_driver.jmx -l results -e`