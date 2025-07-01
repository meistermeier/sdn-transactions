package org.neo4j.sdntransactions;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

/**
 * @author Gerrit Meier
 */
@RestController
public class EntryPointController {

    private final EntryService service;
    private final EntryServiceReadonly serviceReadonly;

    public EntryPointController(EntryService service, EntryServiceReadonly serviceReadonly) {
        this.service = service;
        this.serviceReadonly = serviceReadonly;
    }

    @GetMapping("/sdn")
    public String benchWithSDN() {
        long start = System.currentTimeMillis();
        var names = service.returnNamesViaRepository();
        long end = System.currentTimeMillis();
        System.out.println("SDN readonly: " + Duration.ofMillis(end - start));
        return String.join(",", names);
    }

    @GetMapping("/sdn/readonly")
    public String benchWithSDNReadOnly() {
        long start = System.currentTimeMillis();
        var names = serviceReadonly.returnNamesViaRepository();
        long end = System.currentTimeMillis();
        System.out.println("SDN readonly: " + Duration.ofMillis(end - start));
        return String.join(",", names);
    }

    @GetMapping("/driver")
    public String benchWithDriver() {
        return service.returnNamesViaDriver();
    }

    @GetMapping("/driver/readonly")
    public String benchWithDriverReadOnly() {
        return serviceReadonly.returnNamesViaDriver();
    }

}
