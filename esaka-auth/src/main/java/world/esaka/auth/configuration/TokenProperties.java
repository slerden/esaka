package world.esaka.auth.configuration;


public class TokenProperties {
    private long maxAgeSeconds;
    private long maxAgeRefreshTokenSeconds;
    private String secret;

    public TokenProperties() {
    }

    public TokenProperties(long maxAgeSeconds, String secret) {
        this.maxAgeSeconds = maxAgeSeconds;
        this.secret = secret;
    }

    public long getMaxAgeSeconds() {
        return maxAgeSeconds;
    }

    public void setMaxAgeSeconds(long maxAgeSeconds) {
        this.maxAgeSeconds = maxAgeSeconds;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getMaxAgeRefreshTokenSeconds() {
        return maxAgeRefreshTokenSeconds;
    }

    public void setMaxAgeRefreshTokenSeconds(long maxAgeRefreshTokenSeconds) {
        this.maxAgeRefreshTokenSeconds = maxAgeRefreshTokenSeconds;
    }
}
