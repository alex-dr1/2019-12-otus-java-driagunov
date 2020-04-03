package ru.otus;

import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.LoginService;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.UserDao;
import ru.otus.core.dbservice.DBServiceUser;
import ru.otus.core.dbservice.DbServiceUserImpl;
import ru.otus.core.helpers.FileSystemHelper;
import ru.otus.core.model.Address;
import ru.otus.core.model.Phone;
import ru.otus.core.model.User;
import ru.otus.core.server.UsersWebServer;
import ru.otus.core.server.UsersWebServerImpl;
import ru.otus.core.templates.TemplateProcessor;
import ru.otus.core.templates.TemplateProcessorImpl;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.List;

/*
    // Стартовая страница
    http://localhost:8080

    login: admin
    password: 11111

*/
public class WebServer {
    private static Logger logger = LoggerFactory.getLogger(WebServer.class);

    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    private static final String HASH_LOGIN_SERVICE_CONFIG_NAME = "realm.properties";
    private static final String REALM_NAME = "Password";

    public static void main(String[] args) throws Exception {

        DBServiceUser dbServiceUser = createDbServiceUser();

        User vasia = createUser("Вася Пупкин", "НИЖНИЙ ТАГИЛ", "+72390423094", "+5324343433");
        User lusia = createUser("Люся Педалькина", "ЕКАТЕРИНБУРГ", "+79890238256", "+743382752930");


        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        String hashLoginServiceConfigPath = FileSystemHelper.localFileNameOrResourceNameToFullPath(HASH_LOGIN_SERVICE_CONFIG_NAME);
        LoginService loginService = new HashLoginService(REALM_NAME, hashLoginServiceConfigPath);

        UsersWebServer usersWebServer = new UsersWebServerImpl(WEB_SERVER_PORT,
                loginService, userDao, gson, templateProcessor);

        usersWebServer.start();
        usersWebServer.join();
    }

    private static DBServiceUser createDbServiceUser() {
        Class<?>[] annotatedClasses = {User.class, Address.class, Phone.class};
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", annotatedClasses);

        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoHibernate(sessionManager);
        return new DbServiceUserImpl(userDao);
    }

    private static User createUser(String name, String address, String phone1, String phone2) {
        User user = new User();
        user.setName(name);
        Address addressUser = new Address(address, user);
        Phone phone1User = new Phone(phone1, user);
        Phone phone2User = new Phone(phone2, user);
        user.setAddress(addressUser);
        user.setPhones(List.of(phone1User, phone2User));
        return user;
    }
}
