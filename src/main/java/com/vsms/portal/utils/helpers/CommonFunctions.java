package com.vsms.portal.utils.helpers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.vsms.portal.data.model.User;
import com.vsms.portal.exception.ApiOperationException;
import com.vsms.portal.utils.enums.ApiStatus;
import com.vsms.portal.utils.enums.Strings;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

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

    public static PageRequest getPageData(Map<String, String> queryParams) {
        int page = Integer.parseInt(queryParams.getOrDefault("page", "0"));
        int size = Integer.parseInt(queryParams.getOrDefault("size", "10"));
        String sortBy = queryParams.getOrDefault("sortBy", "_default");
        String sortDirection = queryParams.getOrDefault("sortDir", "desc");
        Sort sort = Sort.by(getSortDirection(sortDirection), sortBy);
        return PageRequest.of(page, size, sort);
    }

    private static Sort.Direction getSortDirection(String sortDir) {
        if(sortDir.equalsIgnoreCase("asc")) {
            return Sort.Direction.ASC;
        } else {
            return Sort.Direction.DESC;
        }
    }
}
