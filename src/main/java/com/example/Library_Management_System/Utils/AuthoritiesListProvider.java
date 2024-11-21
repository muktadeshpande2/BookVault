package com.example.Library_Management_System.Utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class AuthoritiesListProvider {

    private static HashMap<String, String> authoritiesMap;

    public static String getAuthorities(String userType) {

        if(authoritiesMap == null) {
            authoritiesMap = new HashMap<>();
            List<String> studentAuthorities = Arrays.asList(
                    Constants.STUDENT_SELF_INFO_AUTHORITY,
                    Constants.READ_BOOK_AUTHORITY,
                    Constants.MAKE_PAYMENT_AUTHORITY
            );

            List<String> adminAuthorities = Arrays.asList(
                    Constants.STUDENT_INFO_AUTHORITY,
                    Constants.CREATE_ADMIN_AUTHORITY,
                    Constants.CREATE_BOOK_AUTHORITY,
                    Constants.DELETE_BOOK_AUTHORITY,
                    Constants.UPDATE_BOOK_AUTHORITY,
                    Constants.READ_BOOK_AUTHORITY,
                    Constants.INITIATE_TRANSACTION_AUTHORITY
            );

            String adminAuthoritiesAsString = String.join(Constants.DELIMITER, adminAuthorities);
            String studentAuthoritiesAsString = String.join(Constants.DELIMITER, studentAuthorities);

            authoritiesMap.put(Constants.STUDENT_USER, studentAuthoritiesAsString);
            authoritiesMap.put(Constants.ADMIN_USER, adminAuthoritiesAsString);
        }

        return authoritiesMap.getOrDefault(userType, Constants.INVALID_USER);
    }
}

/*
API -> Mapped Permission/authority -> Assigning authorities to a type of user
Instead of binding API's to a type of user

 */
