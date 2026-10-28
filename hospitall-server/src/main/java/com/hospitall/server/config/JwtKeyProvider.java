package com.hospitall.server.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Base64;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.security.Keys;

@Component
public class JwtKeyProvider {

    private final SecretKey secretKey;

    public JwtKeyProvider() {
        this.secretKey = loadOrCreateKey();
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }

    private SecretKey loadOrCreateKey() {
        // Support legacy JWT_SECRET environment variable first (if set)
        String legacySecret = System.getenv("JWT_SECRET");
        if (legacySecret != null && !legacySecret.isEmpty()) {
            return Keys.hmacShaKeyFor(legacySecret.getBytes());
        }

        // Use system property or environment variable for persistent storage path
        String storageDir = System.getProperty("JWT_KEY_PATH", System.getenv("JWT_KEY_PATH"));
        if (storageDir == null || storageDir.isEmpty()) {
            storageDir = Paths.get(System.getProperty("user.home"), ".hospitall").toString();
        }
        
        Path path = Paths.get(storageDir, "jwt.secret");
        
        try {
            if (Files.exists(path)) {
                byte[] keyBytes = Files.readAllBytes(path);
                return Keys.hmacShaKeyFor(Base64.getDecoder().decode(keyBytes));
            } else {
                Files.createDirectories(path.getParent());
                SecretKey newKey = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
                byte[] encodedKey = Base64.getEncoder().encode(newKey.getEncoded());
                Files.write(path, encodedKey);
                
                // Set file permissions if on a POSIX system (Linux/macOS)
                try {
                    Files.setPosixFilePermissions(path, Set.of(
                        PosixFilePermission.OWNER_READ,
                        PosixFilePermission.OWNER_WRITE
                    ));
                } catch (UnsupportedOperationException e) {
                    // Ignore on Windows
                }
                
                return newKey;
            }
        } catch (IOException e) {
            throw new IllegalStateException("Could not load or create JWT signing key at " + path, e);
        }
    }
}
