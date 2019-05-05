package edu.brown.cs.group1.databaseAuthentication;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Arrays;
import java.util.Random;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * This is a class that contains methods that encrypt the password.
 */
public class PasswordUtlitities {
    private static final Random RANDOM = new SecureRandom();
    private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    public PasswordUtlitities() {}
    /**
     * Generates a random salt values that would also be used for the database.
     * @param length
     *              the desired length of the hash
     * @return
     */
    public static String getSalt(int length) {
        StringBuilder toReturn = new StringBuilder(length);
        for (int i = 0; i < length; i++){
         toReturn.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return new String(toReturn);
    }

    /**
     * Hashing function that combines the salt function with the hash function
     * @param password
     *          the users password in the form of a password
     * @param salt
     *          the salt value of the password in the form of a char array.
     * @return the byte[] containing the hash.
     */
    public static byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt,
                        ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory
                    .getInstance("PBKDFWithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException exp) {
            exp.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } finally {
            spec.clearPassword();
        }
        return null;
    }

    /**
     * Generates a secured password with the provided password and salt.
     * @param password
     *                  the users password.
     * @param salt
     *              the salt value associated with that user.
     * @return
     *         a string of the embedded password
     */
   public static String generateSecurePassword(String password, String salt) {
        byte[] securePassword = hash(password.toCharArray(), salt.getBytes());
        return Base64.getEncoder().encodeToString(securePassword);
   }

    /**
     * Verification process that that regenerate the password hash and returns a boolean that
     * represents whether or not that values matches that of the secured password.
     * @param providedPassword
     *                      the password the user provided
     * @param securedPassword
     *                      the secured values present in the database.
     * @param salt
     *              the salt values also contained in the database.
     * @return
     *          a boolean, true is the password does match the secured hash, false otherwise.
     */
   public static boolean verifyUserPassword(String providedPassword, String securedPassword, String salt) {
        String newSecurePassword = generateSecurePassword(providedPassword, salt);
        return newSecurePassword.equalsIgnoreCase(securedPassword);
   }


}
