package com.project.Metier;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public interface IPdfMetier {
    public void generatePDF(HttpServletRequest request,int id) throws IOException;
}
