package nb.hope.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChatGptService {

    @Value("${chatgpt.api.key}")
    private String chatGptApiKey;

    private static final String CHATGPT_API_URL = "https://api.openai.com/v1/completions";

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Method to transform text input to SQL query using ChatGPT
    public String transformToSqlQuery(String userInput) {
        try {
            // Constructing the correct JSON request body using Jackson
            ObjectNode requestBody = objectMapper.createObjectNode();
            requestBody.put("model", "gpt-3.5-turbo");
            requestBody.put("max_tokens", 100);
            requestBody.put("temperature", 0.7);
            requestBody.put("prompt", "Convert the following user query into an SQL statement:\n" + userInput);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + chatGptApiKey);

            HttpEntity<String> requestEntity = new HttpEntity<>(objectMapper.writeValueAsString(requestBody), headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    CHATGPT_API_URL, HttpMethod.POST, requestEntity, String.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                JsonNode jsonResponse = objectMapper.readTree(response.getBody());
                return jsonResponse.get("choices").get(0).get("text").asText().trim();
            } else {
                return "Error: Invalid response from ChatGPT API.";
            }

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // Health check method to validate the service layer
    public boolean isServiceHealthy() {
        // Simple health check, can be expanded with more robust checks
        return chatGptApiKey != null && !chatGptApiKey.isEmpty();
    }

}