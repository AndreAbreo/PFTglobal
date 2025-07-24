package codigocreativo.uy.servidorapp;

import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.*;

class PasswordUtilsTest {

    @Test
    void generateSalt() {
        String salt1 = PasswordUtils.generateSalt();
        String salt2 = PasswordUtils.generateSalt();
        
        assertNotNull(salt1);
        assertNotNull(salt2);
        assertEquals(24, salt1.length());  // Base64 encoded 16 bytes should be 24 characters
        assertEquals(24, salt2.length());
        assertNotEquals(salt1, salt2); // Salts should be different
    }

    @Test
    void generateSaltedHash() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String password = "password123";
        String saltedHash = PasswordUtils.generateSaltedHash(password);
        
        assertNotNull(saltedHash);
        assertTrue(saltedHash.contains(":"));
        assertTrue(saltedHash.length() > 24); // Should be longer than just the salt
        
        // Test with empty password
        String emptyHash = PasswordUtils.generateSaltedHash("");
        assertNotNull(emptyHash);
        assertTrue(emptyHash.contains(":"));
    }

    @Test
    void generateSaltedHashWithNullPassword() {
        assertThrows(NullPointerException.class, () -> {
            PasswordUtils.generateSaltedHash(null);
        });
    }

    @Test
    void verifyPassword() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String password = "password123";
        String saltedHash = PasswordUtils.generateSaltedHash(password);
        
        assertTrue(PasswordUtils.verifyPassword(password, saltedHash));
        assertFalse(PasswordUtils.verifyPassword("wrongpassword", saltedHash));
        assertFalse(PasswordUtils.verifyPassword("", saltedHash));
    }

    @Test
    void verifyPasswordWithNullStoredHash() throws NoSuchAlgorithmException, InvalidKeySpecException {
        assertFalse(PasswordUtils.verifyPassword("anypassword", null));
    }

    @Test
    void verifyPasswordWithInvalidFormat() {
        // Test with malformed stored hash (no colon separator)
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            PasswordUtils.verifyPassword("password", "invalidhashformat");
        });
    }

    @Test
    void verifyPasswordWithEmptyStoredHash() {
        // Test with empty stored hash
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            PasswordUtils.verifyPassword("password", "");
        });
    }

    @Test
    void verifyPasswordWithOnlySalt() {
        // Test with hash that only has salt part
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            PasswordUtils.verifyPassword("password", "saltonly:");
        });
    }

    @Test
    void verifyPasswordWithEmptyPassword() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String emptyPasswordHash = PasswordUtils.generateSaltedHash("");
        assertTrue(PasswordUtils.verifyPassword("", emptyPasswordHash));
        assertFalse(PasswordUtils.verifyPassword("nonempty", emptyPasswordHash));
    }

    @Test
    void verifyPasswordWithSpecialCharacters() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String specialPassword = "p@ssw0rd!@#$%^&*()";
        String saltedHash = PasswordUtils.generateSaltedHash(specialPassword);
        
        assertTrue(PasswordUtils.verifyPassword(specialPassword, saltedHash));
        assertFalse(PasswordUtils.verifyPassword("p@ssw0rd!@#$%^&*", saltedHash));
    }

    @Test
    void verifyPasswordWithUnicodeCharacters() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String unicodePassword = "pässwördñáéíóú";
        String saltedHash = PasswordUtils.generateSaltedHash(unicodePassword);
        
        assertTrue(PasswordUtils.verifyPassword(unicodePassword, saltedHash));
        assertFalse(PasswordUtils.verifyPassword("passwördñáéíóú", saltedHash));
    }

    @Test
    void verifyPasswordWithVeryLongPassword() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String longPassword = "a".repeat(1000);
        String saltedHash = PasswordUtils.generateSaltedHash(longPassword);
        
        assertTrue(PasswordUtils.verifyPassword(longPassword, saltedHash));
        assertFalse(PasswordUtils.verifyPassword(longPassword + "extra", saltedHash));
    }
}