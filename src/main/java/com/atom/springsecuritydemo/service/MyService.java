package com.atom.springsecuritydemo.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Atom
 */
public interface MyService {

    boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
