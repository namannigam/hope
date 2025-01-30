package nb.hope.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GPT4AllService {

    // deploy and host local gpt server with model name
    private static final String GPT4ALL_API_URL = "http://localhost:4891/v1/completions";
    private static final String MODEL_NAME = "mistral-7b.Q4_K_M.gguf";

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String transformToSqlQuery(String userInput) {
        try {
            // Construct JSON request body
            ObjectNode requestBody = objectMapper.createObjectNode();
            requestBody.put("model", MODEL_NAME);
            requestBody.put("prompt", "Convert this to SQL:\n" + userInput);
            requestBody.put("max_tokens", 100);
            requestBody.put("temperature", 0.7);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> requestEntity = new HttpEntity<>(objectMapper.writeValueAsString(requestBody), headers);

            ResponseEntity<String> response = restTemplate.exchange(GPT4ALL_API_URL, HttpMethod.POST, requestEntity, String.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                JsonNode jsonResponse = objectMapper.readTree(response.getBody());
                return jsonResponse.get("choices").get(0).get("text").asText().trim();
            } else {
                return "Error: GPT4All did not return a valid response.";
            }

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}