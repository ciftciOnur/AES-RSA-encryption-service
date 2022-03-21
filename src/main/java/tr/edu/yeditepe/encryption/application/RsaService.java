package tr.edu.yeditepe.encryption.application;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface RsaService {

	String encrypt(PublicKey publicKey, String secretMessage);

	String decrypt(PrivateKey privateKey, String encryptedMessage);

}
