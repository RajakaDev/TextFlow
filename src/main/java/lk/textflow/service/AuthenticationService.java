package lk.textflow.service;

import lk.textflow.dao.UserDAO;
import lk.textflow.model.User;
import lk.textflow.util.PasswordUtil;

public class AuthenticationService {

    private UserDAO userDAO = new UserDAO();

    public User login(String username, String password) {

        User user = userDAO.findUserByUsername(username);

        if (user == null) {
            return null;
        }

        if (!"ACTIVE".equalsIgnoreCase(user.getStatus())) {
            return null;
        }

        boolean passwordMatches =
                PasswordUtil.checkPassword(
                        password,
                        user.getPasswordHash()
                );

        if (!passwordMatches) {
            return null;
        }

        return user;
    }
}