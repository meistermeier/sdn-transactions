package org.neo4j.sdntransactions;

import org.neo4j.driver.AccessMode;
import org.neo4j.driver.Driver;
import org.neo4j.driver.SessionConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Gerrit Meier
 */
@Service
public class EntryServiceReadonly {

    private final EntryRepositoryReadOnly repository;
    private final Driver driver;
    private final SessionConfig sessionConfig;

    public EntryServiceReadonly(EntryRepositoryReadOnly repository, Driver driver,  @Value("${spring.data.neo4j.database}") String databaseName) {
        this.repository = repository;
        this.driver = driver;
        this.sessionConfig = SessionConfig.builder().withDatabase(databaseName).withDefaultAccessMode(AccessMode.READ).build();
    }

    @Transactional(readOnly = true)
    public Collection<String> returnNamesViaRepository() {
        return repository.getNames();
    }

    public String returnNamesViaDriver() {
        long start = System.currentTimeMillis();
        try (var session = driver.session(sessionConfig)) {

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
