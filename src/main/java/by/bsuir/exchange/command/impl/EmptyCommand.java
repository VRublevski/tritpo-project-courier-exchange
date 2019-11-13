package by.bsuir.exchange.command.impl;

import by.bsuir.exchange.command.Command;
import by.bsuir.exchange.provider.ConfigurationProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EmptyCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return ConfigurationProvider.getProperty(ConfigurationProvider.LOGIN_PAGE_PATH);
    }
}
