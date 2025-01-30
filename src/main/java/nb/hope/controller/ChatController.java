package nb.hope.controller;

import nb.hope.service.GPT4AllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatController {

    @Autowired
//    private ChatGptService chatGptService;
    private GPT4AllService chatGptService;

    // Public endpoint to accept user input and process it into SQL
    @PostMapping("/process-text")
    public ResponseEntity<String> processChatText(
            @RequestBody String userInput) {
        String sqlQuery = chatGptService.transformToSqlQuery(userInput);
        return ResponseEntity.ok(sqlQuery);
    }
}
