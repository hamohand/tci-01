package com.med.tci.service.ai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class OpenAiService {

    /**
     * Pour utiliser GPT-4o ou toute autre API d'OpenAI, vous devez d'abord intégrer leur SDK ou utiliser un client HTTP pour appeler l'API.
     */
    @Value("${openai.api.key}")
    private String OPEN_AI_KEY;

    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions"; // URL corrigée
    //private static final String OPENAI_API_URL = "http://localhost:11434/completions\n"; // URL

    private final String OPENAI_MODEL = "gpt-4o";
    //   private final String OPENAI_MODEL = "llama3";
    private final int MAX_TOKENS = 500;
    private final float TEMPERATURE = 0.1F;

    private final String USER_MESSAGE_SYSTEM = """
             Vous êtes un assistant spécialisé dans le domaine de la recherche des codes douaniers du commerce international.
             Votre tâche est d'extraire à partir de la liste générale des codes, tous les codes et leurs descriptions susceptibles de décrire un produit donné.
             Veuillez répondre uniquement au format JSON avec la clé `code`. Voir exemples de réponses de l'assistant ci-dessous :
             Exemple 1 :
                Assistant=
                     {
                       "code": "08"
                     }
            Exemple 2 :
                Assistant=
                    [
                          {
                              "code": "88",
                          },
                          {
                              "code": "89",
                          }
                    ]
            """;

    //
//    public List<String> demanderAiAide(String question) {
    public String demanderAiAide(String question) {
        //Préparation du client REST
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + OPEN_AI_KEY);
        httpHeaders.add("Content-Type", "application/json");

        // Construction robuste du corps JSON
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", OPENAI_MODEL); // Spécifiez le modèle
        requestBody.put("messages", new Object[]{
                Map.of("role", "system", "content", USER_MESSAGE_SYSTEM),
                Map.of("role", "user", "content", question)
        });
        requestBody.put("max_tokens", MAX_TOKENS);  // 150 // Limite du nombre de tokens
        requestBody.put("temperature", TEMPERATURE); // 0.1 // Ajustement de la créativité

        // Sérialisation en JSON avec ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        String body;
        try {
            body = objectMapper.writeValueAsString(requestBody);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la sérialisation JSON.", e);
        }

        // Envoi de la requête POST
        HttpEntity<String> entity = new HttpEntity<>(body, httpHeaders);
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    OPENAI_API_URL,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            // Lire le contenu JSON
            String responseBody = response.getBody();
            if (responseBody == null) {
                return "Aucune réponse n'a été trouvée.";
            }

            // Extraire le champ `choices[0].message.content` de la réponse de l'API
            JsonNode rootNode = objectMapper.readTree(responseBody); //transforme en JSON
            String assistantMessage = rootNode
                    .path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText();

            System.out.println("AI : Réponse STRING assistantMessage ---- : " + assistantMessage);


            //return listeCodes;
            return assistantMessage;

        } catch (Exception e) {
            // Logs pour un meilleur diagnostic
            System.err.println("Erreur lors de la requête à l'API OpenAI : " + e.getMessage());
            return "L'appel à l'API OpenAI a échoué.";
        }

    }
}

