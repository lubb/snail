package com.zlll.winner.common;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BaseController {

    @Autowired(required = false)
    protected HttpServletRequest request;
    @Autowired(required = false)
    protected HttpServletResponse response;
    @Autowired(required = false)
    protected HttpSession session;
}
