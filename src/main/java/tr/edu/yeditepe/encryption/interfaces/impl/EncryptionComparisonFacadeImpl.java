package tr.edu.yeditepe.encryption.interfaces.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tr.edu.yeditepe.encryption.application.AesService;
import tr.edu.yeditepe.encryption.application.RsaService;
import tr.edu.yeditepe.encryption.interfaces.EncryptionComparisonFacade;
import tr.edu.yeditepe.encryption.interfaces.dto.ResultDto;

@Component
public class EncryptionComparisonFacadeImpl implements EncryptionComparisonFacade{
	
	@Autowired
	AesService aesService;
	
	@Autowired
	RsaService rsaService;
	
	@Override
	public ResultDto applyAesEncryption(String input,int keySize) {
		String algorithm = "AES/ECB/PKCS5Padding";
		ResultDto resultDto = new ResultDto();
		resultDto.setInputString(input);
		try {
			SecretKey secretKey = aesService.generateKey(keySize);
			resultDto.setAesKey(Base64.getEncoder().encodeToString(secretKey.getEncoded()));
			Instant start = Instant.now();
			resultDto.setOutputString(aesService.encrypt(algorithm, input, secretKey));
			Instant end = Instant.now();
			Duration timeElapsed = Duration.between(start,end);
			resultDto.setDuration(timeElapsed.toMillis());
			return resultDto;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultDto;
	}
	
	@Override
	public ResultDto applyAesReverseEncryption(String input,String key) {
		String algorithm = "AES/ECB/PKCS5Padding";
		ResultDto resultDto = new ResultDto();
		resultDto.setAesKey(key);
		resultDto.setInputString(input);
		try {
			byte[] decodedKey = Base64.getDecoder().decode(key);
			SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES"); 
			Instant start = Instant.now();
			resultDto.setOutputString(aesService.decrypt(algorithm, input, originalKey));
			Instant end = Instant.now();
			Duration timeElapsed = Duration.between(start,end);
			resultDto.setDuration(timeElapsed.toMillis());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultDto;
	}
	
	@Override
	public ResultDto aesEncryptionSpeedTest(String input,int key) {
		String algorithm = "AES/ECB/PKCS5Padding";
		ResultDto resultDto = new ResultDto();
		
		if(input==null) {
			try {
				byte[] encoded = Files.readAllBytes(Paths.get("HeavyPayload.txt"));
				input = new String(encoded, StandardCharsets.UTF_8);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		resultDto.setInputString(input);
		resultDto.setIsSame(false);
		Instant start = Instant.now();
		try {
			SecretKey secretKey = aesService.generateKey(key);
			resultDto.setAesKey(Base64.getEncoder().encodeToString(secretKey.getEncoded()));
			resultDto.setEncryptedString(aesService.encrypt(algorithm, input, secretKey));
			resultDto.setOutputString(aesService.decrypt(algorithm, resultDto.getEncryptedString(), secretKey));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(resultDto.getInputString().equals(resultDto.getOutputString()))
			resultDto.setIsSame(true);
			
		Instant end = Instant.now();
		Duration timeElapsed = Duration.between(start,end);
		resultDto.setDuration(timeElapsed.toMillis());
		return resultDto;
	}
	
	@Override
	public ResultDto applyRsaEncryption(String input) {
		ResultDto resultDto = new ResultDto();
		if(input==null) {
			try {
				byte[] encoded = Files.readAllBytes(Paths.get("MiddlePayload.txt"));
				input = new String(encoded, StandardCharsets.UTF_8);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		resultDto.setInputString(input);
		try{
			
			File publicKeyFile = new File("public.key");
			byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
			Instant start = Instant.now();
			resultDto.setEncryptedString(rsaService.encrypt(keyFactory.generatePublic(publicKeySpec), input));
			Instant end = Instant.now();
			Duration timeElapsed = Duration.between(start,end);
			resultDto.setDuration(timeElapsed.toMillis());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return resultDto;
	}
	
	@Override
	public ResultDto applyRsaReverseEncryption(String input) {
		ResultDto resultDto = new ResultDto();
		if(input==null) {
			try {
				byte[] encoded = Files.readAllBytes(Paths.get("MiddlePayload.txt"));
				input = new String(encoded, StandardCharsets.UTF_8);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		resultDto.setInputString(input);
		try {

			File privateKeyFile = new File("private.key");
			byte[] privateKeyBytes = Files.readAllBytes(privateKeyFile.toPath());
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
			Instant start = Instant.now();
			resultDto.setOutputString(rsaService.decrypt(keyFactory.generatePrivate(privateKeySpec), input));
			Instant end = Instant.now();
			Duration timeElapsed = Duration.between(start,end);
			resultDto.setDuration(timeElapsed.toMillis());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return resultDto;
	}
	
	@Override
	public void createRsaKeys(int keySize) {
		try {
			KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
			generator.initialize(keySize);
			KeyPair pair = generator.generateKeyPair();
			PrivateKey privateKey = pair.getPrivate();
			PublicKey publicKey = pair.getPublic();
			FileOutputStream fos = new FileOutputStream("public.key");
		    fos.write(publicKey.getEncoded());
		    fos = new FileOutputStream("private.key");
		    fos.write(privateKey.getEncoded());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public ResultDto rsaEncryptionSpeedTest(String input,int keySize) {
		ResultDto resultDto = new ResultDto();
		resultDto.setIsSame(false);
		if(input==null) {
			try {
				byte[] encoded = Files.readAllBytes(Paths.get("MiddlePayload.txt"));
				input = new String(encoded, StandardCharsets.UTF_8);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		resultDto.setInputString(input);
		try {
			KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
			generator.initialize(keySize);
			KeyPair pair = generator.generateKeyPair();
			PrivateKey privateKey = pair.getPrivate();
			PublicKey publicKey = pair.getPublic();
			resultDto.setRsaPrivateKey(Base64.getEncoder().encodeToString(privateKey.getEncoded()));
			resultDto.setRsaPublicKey(Base64.getEncoder().encodeToString(publicKey.getEncoded()));
			Instant start = Instant.now();
			resultDto.setEncryptedString(rsaService.encrypt(publicKey, input));
			resultDto.setOutputString(rsaService.decrypt(privateKey, resultDto.getEncryptedString()));
			if(resultDto.getInputString().equals(resultDto.getOutputString()))
				resultDto.setIsSame(true);
			Instant end = Instant.now();
			Duration timeElapsed = Duration.between(start,end);
			resultDto.setDuration(timeElapsed.toMillis());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	return resultDto;	
	}

}
