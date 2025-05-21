package com.fitnesstracker.util;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Utility class for password encryption and decryption using AES-GCM with
 * keys derived from passwords via PBKDF2.
 */
public class PasswordUtil {
    private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";

    private static final int TAG_LENGTH_BIT = 128;   // Authentication tag length in bits
    private static final int IV_LENGTH_BYTE = 12;    // GCM recommended IV length in bytes
    private static final int SALT_LENGTH_BYTE = 16;  // Salt length in bytes
    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    /**
     * Generates a random nonce (byte array) of the specified length.
     *
     * @param numBytes length of nonce in bytes
     * @return byte array containing random nonce
     */
    public static byte[] getRandomNonce(int numBytes) {
        byte[] nonce = new byte[numBytes];
        new SecureRandom().nextBytes(nonce);
        return nonce;
    }

    /**
     * Generates a random AES secret key of given key size.
     *
     * @param keysize size of key in bits (e.g., 128, 192, 256)
     * @return SecretKey AES key
     * @throws NoSuchAlgorithmException if AES algorithm is unavailable
     */
    public static SecretKey getAESKey(int keysize) throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(keysize, SecureRandom.getInstanceStrong());
        return keyGen.generateKey();
    }

    /**
     * Derives an AES 256-bit secret key from a password and salt using PBKDF2 with HMAC SHA-256.
     *
     * @param password the password char array
     * @param salt     the salt byte array
     * @return SecretKey derived AES key
     */
    public static SecretKey getAESKeyFromPassword(char[] password, byte[] salt) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            // iterationCount = 65536, keyLength = 256 bits
            KeySpec spec = new PBEKeySpec(password, salt, 65536, 256);
            SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
            return secret;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(PasswordUtil.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Encrypts a password string using AES-GCM with a key derived from the employee_id.
     *
     * @param employee_id key to derive the AES key from (e.g., username)
     * @param password    the plaintext password to encrypt
     * @return base64 encoded encrypted string containing IV + salt + ciphertext
     */
    public static String encrypt(String employee_id, String password) {
        try {
            byte[] salt = getRandomNonce(SALT_LENGTH_BYTE); // 16 bytes salt
            byte[] iv = getRandomNonce(IV_LENGTH_BYTE);     // 12 bytes IV

            SecretKey aesKeyFromPassword = getAESKeyFromPassword(employee_id.toCharArray(), salt);

            Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
            cipher.init(Cipher.ENCRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(TAG_LENGTH_BIT, iv));

            byte[] cipherText = cipher.doFinal(password.getBytes(UTF_8));

            // Concatenate IV + salt + ciphertext
            byte[] cipherTextWithIvSalt = ByteBuffer.allocate(iv.length + salt.length + cipherText.length)
                    .put(iv)
                    .put(salt)
                    .put(cipherText)
                    .array();

            // Return Base64 encoded string
            return Base64.getEncoder().encodeToString(cipherTextWithIvSalt);

        } catch (Exception ex) {
            // Logging can be added here if needed
            return null;
        }
    }

    /**
     * Decrypts an AES-GCM encrypted and Base64 encoded password string.
     *
     * @param encryptedPassword the encrypted Base64 encoded string
     * @param username          the key to derive the AES key (must match encrypt)
     * @return decrypted plaintext password, or null on failure
     */
    public static String decrypt(String encryptedPassword, String username) {
        try {
            byte[] decoded = Base64.getDecoder().decode(encryptedPassword.getBytes(UTF_8));

            ByteBuffer bb = ByteBuffer.wrap(decoded);

            byte[] iv = new byte[IV_LENGTH_BYTE];
            bb.get(iv);

            byte[] salt = new byte[SALT_LENGTH_BYTE];
            bb.get(salt);

            byte[] cipherText = new byte[bb.remaining()];
            bb.get(cipherText);

            SecretKey aesKeyFromPassword = getAESKeyFromPassword(username.toCharArray(), salt);

            Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
            cipher.init(Cipher.DECRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(TAG_LENGTH_BIT, iv));

            byte[] plainText = cipher.doFinal(cipherText);

            return new String(plainText, UTF_8);
        } catch (Exception ex) {
            // Logging can be added here if needed
            return null;
        }
    }
}
