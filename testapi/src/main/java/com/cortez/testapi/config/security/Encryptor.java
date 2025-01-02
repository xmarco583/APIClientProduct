package com.cortez.testapi.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

@Component
public class Encryptor {

    @Value("${jasypt.encryptor.password}")
    private String password;

    // Vector de inicialización fijo (16 bytes para AES)
    private static final byte[] IV = new byte[]{
        0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
        0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F
    };

    public String encriptar(String valor) {
        try {
            // Crear clave usando SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec key = new SecretKeySpec(hash, "AES");

            // Configurar cipher con IV fijo
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivSpec = new IvParameterSpec(IV);
            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);

            // Encriptar
            byte[] encrypted = cipher.doFinal(valor.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Error en la encriptación", e);
        }
    }  

    public String desencriptar(String valorEncriptado) {
        try {
            // Crear clave usando SHA-256 (igual que en encriptar)
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec key = new SecretKeySpec(hash, "AES");

            // Configurar cipher con IV fijo para desencriptar
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivSpec = new IvParameterSpec(IV);
            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);

            // Desencriptar
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(valorEncriptado));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Error en la desencriptación", e);
        }
    }


    // Método conveniente para encriptar Long
    public String encriptar(Long valor) {
        return encriptar(valor.toString());
    }
    
    public Long desencriptarLong(String valorEncriptado) {
        return Long.parseLong(desencriptar(valorEncriptado));
    }

}

