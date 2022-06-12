package io.github.arrudalabs.mizudo.services;

public record GeneratedPassword(String salt, String hash) {
    public static GeneratedPassword fromSaltAndHash(String salt, String hash){
        return new GeneratedPassword(salt,hash);
    }
}
