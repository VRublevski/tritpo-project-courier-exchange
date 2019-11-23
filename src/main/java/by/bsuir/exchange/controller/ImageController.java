package by.bsuir.exchange.controller;


import by.bsuir.exchange.bean.ImageBean;
import by.bsuir.exchange.entity.RoleEnum;
import by.bsuir.exchange.provider.ConfigurationProvider;
import by.bsuir.exchange.provider.PageAttributesNameProvider;
import by.bsuir.exchange.provider.RequestAttributesNameProvider;
import by.bsuir.exchange.provider.SessionAttributesNameProvider;
import by.bsuir.exchange.repository.exception.RepositoryInitializationException;
import by.bsuir.exchange.repository.exception.RepositoryOperationException;
import by.bsuir.exchange.repository.impl.ImageSqlRepository;
import by.bsuir.exchange.specification.image.ImageByRoleIdSpecification;
import by.bsuir.exchange.specification.Specification;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

//Accepts page to forward in request
@MultipartConfig
@WebServlet(urlPatterns = {"/content"})
public class ImageController extends HttpServlet implements Servlet {
    private static final String DEFAULT_LOGO = "/home/vlad/IdeaProjects/JavaWeb/exchange-photo/default_logo.jpeg";
    private static final String DEFAULT_LOGO_NAME = "default_logo.jpeg";

    private ImageSqlRepository repository;

    @Override
    public void init() throws ServletException {
        try {
            repository = new ImageSqlRepository();
            super.init();
        } catch (RepositoryInitializationException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String role = request.getParameter(PageAttributesNameProvider.ROLE);
        String roleIdString = request.getParameter(PageAttributesNameProvider.ROLE_ID);
        long roleId = Long.parseLong(roleIdString);
        Specification<ImageBean, PreparedStatement, Connection> specification = new ImageByRoleIdSpecification(role, roleId);

        try {
            Optional<List< ImageBean >> imagesOptional = repository.find(specification);
            Path path;
            String fileName;
            if (imagesOptional.isPresent()){
                ImageBean image = imagesOptional.get().get(0);
                fileName = image.getFileName();
                String baseDir = ConfigurationProvider.getProperty(ConfigurationProvider.IMAGE_PATH);
                String roleDir = image.getRole();
                String idDir = String.valueOf(image.getRoleId());
                path = Paths.get(baseDir, roleDir, idDir, fileName);
            }else{
                path = Paths.get(DEFAULT_LOGO);
                fileName = DEFAULT_LOGO_NAME;
            }

            response.setContentType(getServletContext().getMimeType(fileName));
            response.setContentLength((int) Files.size(path));
            Files.copy(path, response.getOutputStream());
        } catch (RepositoryOperationException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        RoleEnum role = (RoleEnum) session.getAttribute(SessionAttributesNameProvider.ROLE);
        String roleString = role.toString().toUpperCase();

        long roleId = (long) session.getAttribute(SessionAttributesNameProvider.ID);

        Part filePart = request.getPart(PageAttributesNameProvider.AVATAR);
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        ImageBean imageBean = new ImageBean();
        imageBean.setRole(roleString);
        imageBean.setRoleId(roleId);
        imageBean.setFileName(fileName);
        try {
            repository.add(imageBean);
        } catch (RepositoryOperationException e) {
            throw new ServletException(e);
        }

        InputStream fileContent = filePart.getInputStream();

        String baseDir = ConfigurationProvider.getProperty(ConfigurationProvider.IMAGE_PATH);
        String roleDir = imageBean.getRole();
        String idDir = String.valueOf(imageBean.getRoleId());

        Path dirPath = Paths.get(baseDir, roleDir, idDir);
        Files.createDirectories(dirPath);

        Path path = Paths.get(baseDir, roleDir, idDir, fileName);

        Files.copy(fileContent, path);

        String page = (String) request.getAttribute(RequestAttributesNameProvider.PAGE);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }

}
