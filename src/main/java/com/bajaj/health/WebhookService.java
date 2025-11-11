\
package com.bajaj.health;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/*
 WebhookService:
 1) POSTs to generateWebhook endpoint
 2) Parses 'webhook' and 'accessToken' from response
 3) Crafts a finalQuery (you will need to replace logic to compute actual SQL)
 4) POSTs finalQuery to returned webhook URL using Authorization header with JWT
 Note: Modify 'regNo' and SQL solving logic as required by the challenge.
*/
@Service
public class WebhookService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    public void executeFlow() throws Exception {
        String genUrl = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";

        String requestBody = \"\"\"
        {
            \"name\": \"John Doe\",
            \"regNo\": \"REG12347\",
            \"email\": \"john@example.com\"
        }
        \"\"\";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(genUrl, HttpMethod.POST, entity, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            System.out.println(\"Failed to generate webhook: \" + response.getStatusCode() + \" - \" + response.getBody());
            return;
        }

        JsonNode json = mapper.readTree(response.getBody());
        String webhookUrl = json.has(\"webhook\") ? json.get(\"webhook\").asText() : null;
        String accessToken = json.has(\"accessToken\") ? json.get(\"accessToken\").asText() : null;

        System.out.println(\"Received webhook: \" + webhookUrl);
        System.out.println(\"Received accessToken: \" + (accessToken != null ? \"(token received)\" : \"(none)\"));

        // TODO: Use regNo to pick question (odd/even), fetch or compute SQL accordingly.
        // For now, provide a placeholder finalQuery. Replace with actual SQL solution.
        String finalQuery = \"SELECT 'replace_with_actual_query' as result;\";

        if (webhookUrl == null || accessToken == null) {
            System.out.println(\"Missing webhook or accessToken in response. Aborting.\"); 
            return;
        }

        // Prepare submission
        HttpHeaders submitHeaders = new HttpHeaders();
        submitHeaders.setContentType(MediaType.APPLICATION_JSON);
        // Use the access token as a Bearer token (if required). Some APIs expect raw token - adjust if needed.
        submitHeaders.setBearerAuth(accessToken);

        String payload = \"{ \\\"finalQuery\\\": \\\"\" + finalQuery.replace("\"", "\\\\\"") + \"\\\" }\";
        HttpEntity<String> submitEntity = new HttpEntity<>(payload, submitHeaders);

        ResponseEntity<String> submitResponse = restTemplate.exchange(webhookUrl, HttpMethod.POST, submitEntity, String.class);
        System.out.println(\"Submission status: \" + submitResponse.getStatusCode());
        System.out.println(\"Submission body: \" + submitResponse.getBody());
    }
}
