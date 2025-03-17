package com.backend;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/arduino")
public class ArduinoController {

    private final SerialCommunicationService serialService;

    public ArduinoController(SerialCommunicationService serialService) {
        this.serialService = serialService;
    }

    @PostMapping("/send")
    public String sendToArduino(@RequestParam String command) {
        serialService.sendCommand(command);
        return "Sent command: " + command;
    }

    @GetMapping("/read")
    public String readFromArduino() {
        return serialService.readResponse();
    }
}
