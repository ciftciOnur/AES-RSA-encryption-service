package tr.edu.yeditepe.encryption.application.impl;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.Cipher;

import org.springframework.stereotype.Component;

import tr.edu.yeditepe.encryption.application.RsaService;


@Component
public class RsaServiceImpl implements RsaService{
	
	@Override
	public String encrypt(PublicKey publicKey,String secretMessage) {
		try {
			Cipher encryptCipher = Cipher.getInstance("RSA");
			encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] secretMessageBytes = secretMessage.getBytes(StandardCharsets.UTF_8);
			byte[] encryptedMessageBytes = encryptCipher.doFinal(secretMessageBytes);
			return Base64.getEncoder().encodeToString(encryptedMessageBytes);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return "something gone wrong in rsaEncrypt";
	}
	
	@Override
	public String decrypt(PrivateKey privateKey,String encryptedMessage) {
		try {
			Cipher decryptCipher = Cipher.getInstance("RSA");
			decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] encryptedMessageBytes = Base64.getDecoder().decode(encryptedMessage);
			byte[] decryptedMessageBytes = decryptCipher.doFinal(encryptedMessageBytes);
			return new String(decryptedMessageBytes, StandardCharsets.UTF_8);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return "something gone wrong in rsaDecrypt";
	}

}
