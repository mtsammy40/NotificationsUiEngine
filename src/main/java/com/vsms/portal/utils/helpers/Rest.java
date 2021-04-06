package com.vsms.portal.utils.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vsms.portal.exception.RestCallException;
import com.vsms.portal.utils.models.CoreResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class Rest {
    private static final Logger LOG = LogManager.getLogger(Rest.class);

    RestTemplate restTemplate;
    Environment env;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    Rest(RestTemplate restTemplate, Environment env) {
        this.restTemplate = restTemplate;
        this.env = env;
    }

    public CoreResponse smsBalance(Long clientId) throws JsonProcessingException, RestCallException {
        String coreUrl = env.getRequiredProperty("sms.core.url");
        String endpoint = env.getRequiredProperty("sms.core.endpoints.sms-balance")
                .replace("{clientId}", clientId.toString());
        String url = coreUrl + endpoint;
        ResponseEntity<String> responseEntity = get(url);
        if(responseEntity.getStatusCode().is2xxSuccessful()) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(responseEntity.getBody(), CoreResponse.class);
        } else {
            throw new RestCallException("Error getting SmsBalance", responseEntity);
        }
    }

    private ResponseEntity<String> get(String url) {
        LOG.info("Get call | Url | {}", url);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        LOG.info("Get call | Response | {}", responseEntity.toString());
        return responseEntity;
    }
}
