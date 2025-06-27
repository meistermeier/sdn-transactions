package org.neo4j.sdntransactions;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

/**
 * @author Gerrit Meier
 */
@Node
public class Entry {

    @Id
    private final String id;

    private final String name;

    public Entry(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
