package com.vsms.portal.utils.helpers;

import com.vsms.portal.utils.enums.CoreRequestTypes;
import com.vsms.portal.utils.models.AsyncCoreRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;

public class AsyncRest implements Callable<Object> {
    private Rest rest;
    private AsyncCoreRequest request;

    public AsyncRest(Rest rest, AsyncCoreRequest request) {
        this.rest = rest;
        this.request = request;
    }

    @Override
    public Object call() throws Exception {
        return rest.asnyc(request);
    }
}
