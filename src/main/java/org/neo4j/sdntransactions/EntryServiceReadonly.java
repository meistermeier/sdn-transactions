package org.neo4j.sdntransactions;

import org.neo4j.driver.AccessMode;
import org.neo4j.driver.Driver;
import org.neo4j.driver.SessionConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;

/**
 * @author Gerrit Meier
 */
@Service
@Transactional(readOnly = true)
public class EntryServiceReadonly {

    private final EntryRepositoryReadOnly repository;
    private final Driver driver;
    private final SessionConfig sessionConfig;

    public EntryServiceReadonly(EntryRepositoryReadOnly repository, Driver driver,  @Value("${spring.data.neo4j.database}") String databaseName) {
        this.repository = repository;
        this.driver = driver;
        this.sessionConfig = SessionConfig.builder().withDatabase(databaseName).withDefaultAccessMode(AccessMode.READ).build();
    }

    public String returnNamesViaRepository() {
        long start = System.currentTimeMillis();
        var names = repository.getNames();
        long end = System.currentTimeMillis();
        System.out.println("SDN readonly: " + Duration.ofMillis(end - start));

        return String.join(",", names);
    }

    public String returnNamesViaDriver() {
        try (var session = driver.session(sessionConfig)) {
            long start = System.currentTimeMillis();

            var names = new ArrayList<String>();
            session.run("MATCH (e:Entry) RETURN e.name").forEachRemaining(record ->
                names.add(record.get("e.name").asString())
            );
            long end = System.currentTimeMillis();
            System.out.println("Driver readonly: " + Duration.ofMillis(end - start));
            return String.join(",", names);
        }
    }
}
