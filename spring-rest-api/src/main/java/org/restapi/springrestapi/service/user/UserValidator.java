package org.restapi.springrestapi.service.user;

public interface UserValidator {
    void validateDuplicateEmail(String email);
    void validateDuplicateNickname(String nickname);
    default void validateOnRegister(String email, String nickname) {
        validateDuplicateEmail(email);
        validateDuplicateNickname(nickname);
    }
    void validatePasswordChange(String newPassword, String confirmPassword);
}
