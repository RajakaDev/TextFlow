package lk.textflow.util;

import lk.textflow.dao.UserDAO;
import lk.textflow.model.User;

public class DefaultOwnerSetup {

    public static void ensureOwnerExists() {

        UserDAO userDAO = new UserDAO();

        User existingOwner =
                userDAO.findUserByUsername("owner");

        if (existingOwner != null) {
            return;
        }

        User owner = new User();

        owner.setName("System Owner");
        owner.setUsername("owner");

        // UserDAO.addUser() will hash this password
        owner.setPasswordHash("Owner@123");

        owner.setRole("OWNER");
        owner.setPosition("Owner");
        owner.setContactNumber("");
        owner.setStatus("ACTIVE");

        boolean added =
                userDAO.addUser(owner);

        if (added) {
            System.out.println(
                    "Default owner account created."
            );
        }
    }
}