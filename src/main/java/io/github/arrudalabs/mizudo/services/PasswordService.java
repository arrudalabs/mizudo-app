package io.github.arrudalabs.mizudo.services;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PasswordService {

    public GeneratedPassword newPassword(String rawPassword) {
        return passwordFrom(createSalt(), rawPassword);
    }

    private byte[] createSalt() {
        // TODO
        throw new UnsupportedOperationException("must be implemented!");
    }

    public GeneratedPassword passwordFrom(byte[] salt, String rawPassword) {
        // TODO
        throw new UnsupportedOperationException("must be implemented!");
    }
}
