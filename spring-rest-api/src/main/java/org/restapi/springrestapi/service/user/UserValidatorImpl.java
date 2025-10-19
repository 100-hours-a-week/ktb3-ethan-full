package org.restapi.springrestapi.service.user;

import org.restapi.springrestapi.exception.AppException;
import org.restapi.springrestapi.exception.code.UserErrorCode;
import org.restapi.springrestapi.finder.UserFinder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserValidatorImpl implements UserValidator {
    private final UserFinder userFinder;

    @Override
    public void validateDuplicateEmail(String email) {
        if (userFinder.existsByEmail(email)) {
            throw new AppException(UserErrorCode.EMAIL_CONFLICT);
        }
    }

    @Override
    public void validateDuplicateNickname(String nickname) {
        if (userFinder.existsByNickName(nickname)) {
            throw new AppException(UserErrorCode.NICKNAME_CONFLICT);
        }
    }

    @Override
    public void validatePasswordChange(String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            throw new AppException(UserErrorCode.NOT_MATCH_NEW_PASSWORD);
        }
    }
}
