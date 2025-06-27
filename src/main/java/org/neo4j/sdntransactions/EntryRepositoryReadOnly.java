package org.neo4j.sdntransactions;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * @author Gerrit Meier
 */
public interface EntryRepositoryReadOnly extends Neo4jRepository<Entry, String> {

    @Query("MATCH (e:Entry) RETURN e.name")
    @Transactional(readOnly = true)
    Collection<String> getNames();

}
