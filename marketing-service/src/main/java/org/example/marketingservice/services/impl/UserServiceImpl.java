package org.example.marketingservice.services.impl;

import org.example.marketingservice.exceptions.InvalidRequest;
import org.example.marketingservice.models.User;
import org.example.marketingservice.repositories.UserRepository;
import org.example.marketingservice.services.interfaces.UserService;
import org.springframework.stereotype.Service;
import shaded_package.com.google.i18n.phonenumbers.NumberParseException;
import shaded_package.com.google.i18n.phonenumbers.PhoneNumberUtil;
import shaded_package.com.google.i18n.phonenumbers.Phonenumber;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        if (Objects.isNull(user)) {
            throw new InvalidRequest("Invalid user details");
        }
        validateUserInfo(user);
        return userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> findAll() {
       return userRepository.findAll();
    }

    @Override
    public List<User> findAllByUserIds(List<Long> userId) {
        return userRepository.findByUserIdIn(userId);
    }

    private void validateUserInfo(User user){
        PhoneNumberUtil numberUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber phoneNumber = numberUtil.parse(user.getPhone(), "IN");
            boolean isValidPhoneNumber = numberUtil.isValidNumber(phoneNumber);

            String emailRegex = "^(.+)@(.+)$";
            Pattern emailPattern = Pattern.compile(emailRegex);
            Matcher emailMatcher = emailPattern.matcher(user.getEmail());

            if(!emailMatcher.matches() || !isValidPhoneNumber){
                throw new InvalidRequest("Invalid user Info");
            }
        } catch (NumberParseException e) {
            throw new RuntimeException(e);
        }

    }
}
