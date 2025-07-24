package codigocreativo.uy.servidorapp;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordUtils {

    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";

    // Private constructor to prevent instantiation
    private PasswordUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    // Genera un salt aleatorio
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    // Aplica hashing a la contraseña usando PBKDF2, con el salt ya concatenado
    private static String hashPasswordWithSalt(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] saltBytes = Base64.getDecoder().decode(salt);
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, ITERATIONS, KEY_LENGTH);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
        byte[] hash = factory.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(hash);
    }

    // Combina salt y hash en una sola cadena
    public static String generateSaltedHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String salt = generateSalt();
        String hash = hashPasswordWithSalt(password, salt);
        return salt + ":" + hash;  // Concatenamos salt y hash separados por ':'
    }

    // Extrae el salt y verifica la contraseña
    public static boolean verifyPassword(String password, String storedSaltedHash) throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (storedSaltedHash == null) {
            return false;
        }
        String[] parts = storedSaltedHash.split(":");
        String salt = parts[0];
        String storedHash = parts[1];
        String computedHash = hashPasswordWithSalt(password, salt);

        return storedHash.equals(computedHash);
    }
}