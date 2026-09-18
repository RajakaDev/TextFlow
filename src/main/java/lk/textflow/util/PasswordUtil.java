package lk.textflow.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    public static String hashPassword(
            String password
    ) {

        return BCrypt.hashpw(
                password,
                BCrypt.gensalt()
        );
    }


    public static boolean checkPassword(
            String password,
            String hashedPassword
    ) {

        if (hashedPassword == null) {
            return false;
        }


        if (!hashedPassword.startsWith("$2")) {
            return false;
        }


        try {

            return BCrypt.checkpw(
                    password,
                    hashedPassword
            );

        } catch (
                IllegalArgumentException e
        ) {

            return false;
        }
    }
}