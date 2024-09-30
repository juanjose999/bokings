package com.booking;

import io.jsonwebtoken.Jwts;
import jakarta.xml.bind.DatatypeConverter;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.util.Base64;

public class SecretMaker {
    @Test
    public void generateSecret(){
        SecretKey sk = Jwts.SIG.HS512.key().build();
        String skString = DatatypeConverter.printHexBinary(sk.getEncoded());
        System.out.println(skString);
    }
}
