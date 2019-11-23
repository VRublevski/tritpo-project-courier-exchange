package by.bsuir.exchange.controller;


import by.bsuir.exchange.bean.ImageBean;
import by.bsuir.exchange.provider.ConfigurationProvider;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@MultipartConfig
@WebServlet(urlPatterns = {"/upload", "/send"})
public class ImageController extends HttpServlet implements Servlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageBean image = (ImageBean) request.getAttribute("image");
        String fileName = image.getFileName();
        String baseDir = ConfigurationProvider.getProperty(ConfigurationProvider.IMAGE_PATH);
        String roleDir = image.getRole();
        String idDir = String.valueOf(image.getRoleId());
        Path path = Paths.get(baseDir, roleDir, idDir, fileName);
        response.setContentType(getServletContext().getMimeType(fileName));
        response.setContentLength((int) Files.size(path));
        Files.copy(path, response.getOutputStream());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
