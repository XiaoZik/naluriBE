package com.example.naluri.pi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "https://nalurispace.netlify.app/")
@RestController
@RequestMapping(path = "api/pi")
public class PiController {
    private final PiServices piServices;

    @Autowired
    public PiController(PiServices piServices) {
        this.piServices = piServices;
    }

    @GetMapping(path = "{id}")
    public Pi getPi(@PathVariable("id") int id) {
        piServices.updatePi(id);
        return piServices.getPi(id);
    }
}
