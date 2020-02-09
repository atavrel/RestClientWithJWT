package ru.atavrel.restclientdemo.util;

import org.apache.commons.text.RandomStringGenerator;
import org.springframework.stereotype.Component;

@Component
public class PasswordGenerator {

    public String generateRandomSpecialCharacters(int length) {
        RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder().withinRange(33, 45).build();
        return pwdGenerator.generate(length);
    }
}
