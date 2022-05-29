package io.github.arrudalabs.mizudo.services;

public record GeneratedPassword(byte[] salt, byte[] hash) {
    public static GeneratedPassword fromSaltAndHash(byte[] salt, byte[] hash){
        return new GeneratedPassword(salt,hash);
    }
}
