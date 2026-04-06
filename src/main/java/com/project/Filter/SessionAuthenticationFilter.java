package com.project.Filter;

import com.project.DAO.AdminRepository;
import com.project.DAO.ClientRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)

public class SessionAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    ClientRepository clientRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path= request.getRequestURI();
        if(path.startsWith("/cssAdmin/") || path.startsWith("/cssClient/")){
            filterChain.doFilter(request, response);
        }

        //////////////////////////////////////////

        HttpSession session = request.getSession(false);
        int idAdmin = 0;
        int idClient = 0;
        if(session != null) {
            if(session.getAttribute("id") != null) {
                idAdmin = (int) session.getAttribute("id");
            }
            if(session.getAttribute("idClient") != null) {
                idClient = (int) session.getAttribute("idClient");
            }
        }

        if (idAdmin != 0 || request.getRequestURI().equals("/Login_Admin")) {

            String getpath =request.getRequestURI();
            if(idClient == 0 &&(path.startsWith("/Voitures_Selectionner/")  || path.startsWith("/pdfgenerer/")  || path.startsWith("/HistoriqueClient")  || path.startsWith("/Mon_Profile") )) {

                    System.out.println(path);
                    response.sendRedirect("/Login_Client");


            }else {
                filterChain.doFilter(request, response);

            }
        } else if(idClient != 0 || request.getRequestURI().equals("/Login_Client") || request.getRequestURI().equals("/Creer_Compte")){
                String getpath =request.getRequestURI();
                if(path.startsWith("/Admin/") == false)
                {
                    filterChain.doFilter(request, response);
                }else {
                    response.sendRedirect("/Login_Admin");
                }
        }
        else {
            response.sendRedirect("/Login_Client");
        }

    }
}

