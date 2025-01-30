
package nb.hope.controller;

import nb.hope.service.ChatGptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/internal")
public class InternalApiController {

    @Autowired
    private ChatGptService chatGptService;

    // Internal health check endpoint to validate the service layer
    @GetMapping("/validate-service")
    public String validateService() {
        return chatGptService.isServiceHealthy() ? "Service is healthy" : "Service is down";
    }
}
