package com.vsms.portal.utils.models;

import com.vsms.portal.utils.enums.CoreRequestTypes;

public class AsyncCoreRequest {
    private Long clientId;
    private CoreRequestTypes requestType;

    public AsyncCoreRequest(Long clientId, CoreRequestTypes requestType) {
        this.clientId = clientId;
        this.requestType = requestType;
    }
    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public CoreRequestTypes getRequestType() {
        return requestType;
    }

    public void setRequestType(CoreRequestTypes requestType) {
        this.requestType = requestType;
    }
}
