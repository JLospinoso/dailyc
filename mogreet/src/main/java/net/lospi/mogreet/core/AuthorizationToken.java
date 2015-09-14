package net.lospi.mogreet.core;

public class AuthorizationToken {
    private final int clientId;
    private final String token;

    public AuthorizationToken(int clientId, String token) {
        this.clientId = clientId;
        this.token = token;
    }

    public int getClientId() {
        return clientId;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AuthorizationToken{");
        sb.append("clientId=").append(clientId);
        sb.append(", token='").append(token).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
