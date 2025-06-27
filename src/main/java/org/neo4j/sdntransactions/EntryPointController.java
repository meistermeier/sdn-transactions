package org.neo4j.sdntransactions;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return service.returnNamesViaRepository();
    }

    @GetMapping("/sdn/readonly")
    public String benchWithSDNReadOnly() {
        return serviceReadonly.returnNamesViaRepository();
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
