package ro.gdgs.crawler.services;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import ro.gdgs.crawler.utils.JsfUtils;

import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

/**
 * @author hasna
 * @since 1.1
 */
//@ManagedBean(name = "userController")
//@RequestScoped
public class AuthenticationService implements Serializable {
    private UserService userService;

    public AuthenticationService() {
        userService = UserServiceFactory.getUserService();
    }

    public String getName() {
        return "asd";
    }

    public boolean isLoggedIn() {
        return userService.isUserLoggedIn();
    }

    public void login() {
        Map<String, String> params =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        login(params.get("url"));
    }

    public void login(String returnUrl) {
        try {
            if (!isLoggedIn()) {
                String url = userService.createLoginURL(returnUrl);
                JsfUtils.redirect(url);
            } else {
                JsfUtils.addErrorMessage("Esti deja autentificat.");
            }
        } catch (IOException e) {
            JsfUtils.addErrorMessage(e, "Nu s-a putut face redirectarea.");
        }
    }

    public void logout() {
        Map<String, String> params =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        logout("/");
    }

    public void logout(String returnUrl) {
        try {
            if (isLoggedIn()) {
                String url = userService.createLogoutURL(returnUrl);
                JsfUtils.redirect(url);
            } else {
                JsfUtils.addErrorMessage("Esti deja neautentificat.");
            }
        } catch (IOException e) {
            JsfUtils.addErrorMessage(e, "Nu s-a putut face redirectarea.");
        }
    }
}