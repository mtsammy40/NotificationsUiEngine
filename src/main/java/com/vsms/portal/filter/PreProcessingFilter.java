package com.vsms.portal.filter;

import com.vsms.portal.controller.AppController;
import com.vsms.portal.data.model.User;
import com.vsms.portal.utils.enums.Roles;
import com.vsms.portal.utils.enums.Strings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class PreProcessingFilter extends OncePerRequestFilter {
    private final Logger LOGGER = LogManager.getLogger(PreProcessingFilter.class);


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        // If user is admin, allow orgId search parameter, if not, always append the user's client id
        User user = (User) httpServletRequest.getAttribute(Strings.REQUEST_ATTRIBUTE_USER_KEY.getValue());
        if (user != null) {
            String queryString = httpServletRequest.getQueryString();
            LOGGER.info("Query String | {}", queryString);
            // If not admin, enforce Client id
            if (user.getRole() == null || !user.getRole().getTitle().equalsIgnoreCase(Roles.ADMIN.name())) {
               //
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
