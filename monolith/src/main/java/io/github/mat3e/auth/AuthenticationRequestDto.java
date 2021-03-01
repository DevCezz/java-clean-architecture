package io.github.mat3e.auth;

class AuthenticationRequestDto {
    private String username;
    private String password;

    String getUsername() {
        return username;
    }

    void setUsername(String username) {
        this.username = username;
    }

    String getPassword() {
        return password;
    }

    void setPassword(String password) {
        this.password = password;
    }
}
