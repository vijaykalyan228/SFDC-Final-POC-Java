/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import com.helpers.AuthenticateAuthorizeTest;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author VijayKalyan
 */
public class RedirectUrlServlet extends HttpServlet {

    private static final long serialVersionUID = 4635640353721010779L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.getWriter().write(AuthenticateAuthorizeTest.getURI().toString());
        } catch (Exception ex) { }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.getWriter().write(AuthenticateAuthorizeTest.getURI().toString());
        } catch (Exception ex) { }
    }
}
