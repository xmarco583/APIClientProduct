package com.cortez.testapi.testapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;


import javax.crypto.spec.SecretKeySpec;

import org.junit.jupiter.api.Test;



import lombok.RequiredArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

@RequiredArgsConstructor
public class EncryptorTest {
    
    private static final byte[] IV = new byte[]{
        0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
        0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F
    };

    @Test 
    void testEncryptDecrypt() throws Exception {
        String password = "jY4v9r5N8w7F3JkL6pW2u7P0t9xB3mV4";  // Tu clave de encriptación
        String valor = "124";  // El valor a encriptar
        
        // Crear clave
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        SecretKeySpec key = new SecretKeySpec(hash, "AES");
        
        // Configurar cipher
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivSpec = new IvParameterSpec(IV);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        
        // Encriptar
        byte[] encrypted = cipher.doFinal(valor.getBytes());
        String valorEncriptado = Base64.getEncoder().encodeToString(encrypted);
        
        System.out.println("Valor encriptado para 124: " + valorEncriptado);
        
        // Verificar desencriptación
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(valorEncriptado));
        String valorDesencriptado = new String(decrypted);
        System.out.println("Valor desencriptado: " + new String(decrypted));
        assertEquals(valor, valorDesencriptado);
    }
    
    
}


