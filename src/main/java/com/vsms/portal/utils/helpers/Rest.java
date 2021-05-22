package com.vsms.portal.utils.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vsms.portal.exception.RestCallException;
import com.vsms.portal.utils.models.AsyncCoreRequest;
import com.vsms.portal.utils.models.CoreResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.Callable;

@Component
public class Rest {
    private static final Logger LOG = LogManager.getLogger(Rest.class);

    RestTemplate restTemplate;
    Environment env;


    @Autowired
    Rest(RestTemplate restTemplate, Environment env) {
        this.restTemplate = restTemplate;
        this.env = env;
    }

    public CoreResponse smsBalance(Long clientId) throws JsonProcessingException, RestCallException {
        String endpoint = env.getRequiredProperty("sms.core.endpoints.sms-balance")
                .replace("{clientId}", clientId.toString());
        return getFromCore(endpoint, CoreResponse.class);
    }

    public Map dashboardSummary(Long clientId) throws JsonProcessingException, RestCallException {
        String endpoint = env.getRequiredProperty("sms.core.endpoints.dash-summary-client")
                .replace("{clientId}", clientId.toString());
        return getFromCore(endpoint, Map.class);
    }

    private <T> T getFromCore(String endpoint, Class<T> returnClass) throws RestCallException, JsonProcessingException {
        String coreUrl = env.getRequiredProperty("sms.core.url");
        String url = coreUrl + endpoint;
        try {
            ResponseEntity<String> responseEntity = get(url);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(responseEntity.getBody(), returnClass);
            } else {
                throw new RestCallException("Error getting SmsBalance", responseEntity);
            }
        } catch (RestCallException e) {
            throw (e);
        } catch (HttpClientErrorException e) {
            throw new RestCallException("Failed Rest Call to " + url + "! | Status [" + e.getStatusCode() + "] | Body [ "+e.getResponseBodyAsString()+" ]");
        }
    }

    public Object asnyc(AsyncCoreRequest request) throws RestCallException, JsonProcessingException {
        switch (request.getRequestType()) {
            case SMS_BALANCE:
               return smsBalance(request.getClientId());
            case DASH_SUMMARY:
                return dashboardSummary(request.getClientId());
            default:
                throw new RestCallException("This API is Unimplemented!");
        }
    }

    private ResponseEntity<String> get(String url) {
        LOG.info("Get call | Url | {}", url);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        LOG.info("Get call | Response | {}", responseEntity.toString());
        return responseEntity;
    }
}
