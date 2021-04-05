package com.vsms.portal.utils.helpers;

import javax.servlet.http.HttpServletRequest;

import com.vsms.portal.data.model.User;
import com.vsms.portal.exception.ApiOperationException;
import com.vsms.portal.utils.enums.ApiStatus;
import com.vsms.portal.utils.enums.Strings;

import org.apache.commons.logging.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommonFunctions {
    private static final Logger LOG = LogManager.getLogger(CommonFunctions.class);

    public static User extractUser(HttpServletRequest request) throws ApiOperationException {
        LOG.debug("Attempting to extract user object from HttpServletRequest...");
        User user = (User) request.getAttribute(Strings.REQUEST_ATTRIBUTE_USER_KEY.getValue());
        if(user == null) {
            throw new ApiOperationException("User details missing.", ApiStatus.INSUFFICIENT_PERMISSIONS);
        }
        LOG.debug("User extracted successfully ... | {} ", user.toString());
        return user;
    }
}
