package tr.edu.yeditepe.encryption.interfaces;

import tr.edu.yeditepe.encryption.interfaces.dto.ResultDto;

public interface EncryptionComparisonFacade {

	ResultDto aesEncryptionSpeedTest(String input, int key);

	ResultDto rsaEncryptionSpeedTest(String input, int key);

	void createRsaKeys(int keySize);

	ResultDto applyRsaReverseEncryption(String input);

	ResultDto applyRsaEncryption(String input);

	ResultDto applyAesReverseEncryption(String input, String key);

	ResultDto applyAesEncryption(String input, int keySize);
}
