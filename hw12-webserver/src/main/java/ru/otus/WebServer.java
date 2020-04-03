package ru.otus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.LoginService;
import ru.otus.dao.UserDaoImpl;
import ru.otus.dao.UserDao;
import ru.otus.helpers.FileSystemHelper;
import ru.otus.server.UsersWebServer;
import ru.otus.server.UsersWebServerImpl;
import ru.otus.services.TemplateProcessor;
import ru.otus.services.TemplateProcessorImpl;

/*
    // Стартовая страница
    http://localhost:8080

    login: admin
    password: 11111

*/
public class WebServer {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    private static final String HASH_LOGIN_SERVICE_CONFIG_NAME = "realm.properties";
    private static final String REALM_NAME = "Password";

    public static void main(String[] args) throws Exception {
        UserDao userDao = new UserDaoImpl();
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        String hashLoginServiceConfigPath = FileSystemHelper.localFileNameOrResourceNameToFullPath(HASH_LOGIN_SERVICE_CONFIG_NAME);
        LoginService loginService = new HashLoginService(REALM_NAME, hashLoginServiceConfigPath);

        UsersWebServer usersWebServer = new UsersWebServerImpl(WEB_SERVER_PORT,
                loginService, userDao, gson, templateProcessor);

        usersWebServer.start();
        usersWebServer.join();
    }
}
