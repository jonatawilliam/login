package PM.login.PM;

import PM.login.model.UserType;
import PM.login.model.User;
import PM.login.DAO.UserDAO;

/**
 *
 * @author andreendo
 */
public class PerformLoginPM {
    String login;
    String password;
    UserDAO userDao;
    int countBlocked;
    
    public boolean getBlocked() throws Exception{
        User user = userDao.getByName(login);
        if(user == null) {
            throw new Exception("Inexistent username");
        } else {
            return user.getBlocked();
        }     
    }

    public PerformLoginPM() {
        login = "";
        password = "";
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }    
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getPassword() {
        return password;
    }    

    public void clear() {
        login = "";
        password = "";
        System.out.println("PM.login.EfetuarLoginPM.clear()");
    }
    
    public PagePM pressLogin() throws Exception {
        login = login.trim();
        password = password.trim();
        countBlocked = 0;
        if(login.isEmpty() || password.isEmpty())
            throw new Exception("Empty fields");
        
        User user = userDao.getByName(login);
        if(user == null)
            throw new Exception("Inexistent username");
        
        if(!user.getPassword().equals(password)){
            countBlocked =+ countBlocked;
            if(countBlocked  >= 3){    
                user.setBlocked(true);
                throw new Exception("User blocked! Contact admin.");
            } else {
                throw new Exception("Wrong password");
            }
        }
        
        PagePM pagePM = null;
        if(user.getType() == UserType.ADMIN)
            pagePM = new AdminMainPagePM();
        else
            pagePM = new NormalUserMainPagePM();
        
        pagePM.setLoggedUser(user);
        
        return pagePM;
    }

    void setUserDao(UserDAO userDao) {
        this.userDao = userDao;
    }

}
