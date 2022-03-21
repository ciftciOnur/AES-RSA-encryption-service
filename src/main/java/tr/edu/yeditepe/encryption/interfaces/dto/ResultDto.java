package tr.edu.yeditepe.encryption.interfaces.dto;

public class ResultDto {
	
	private String inputString;
	
	private String encryptedString;
	
	private String outputString;
	
	private Boolean isSame;
	
	private Long duration;
	
	private String aesKey;
	
	private String rsaPublicKey;
	
	private String rsaPrivateKey;

	/**
	 * @return the inputString
	 */
	public String getInputString() {
		return inputString;
	}

	/**
	 * @return the encryptedString
	 */
	public String getEncryptedString() {
		return encryptedString;
	}

	/**
	 * @return the outputString
	 */
	public String getOutputString() {
		return outputString;
	}

	/**
	 * @return the isSame
	 */
	public Boolean getIsSame() {
		return isSame;
	}

	/**
	 * @return the duration
	 */
	public Long getDuration() {
		return duration;
	}

	/**
	 * @return the aesKey
	 */
	public String getAesKey() {
		return aesKey;
	}

	/**
	 * @return the rsaPublicKey
	 */
	public String getRsaPublicKey() {
		return rsaPublicKey;
	}

	/**
	 * @return the rsaPrivateKey
	 */
	public String getRsaPrivateKey() {
		return rsaPrivateKey;
	}

	/**
	 * @param inputString the inputString to set
	 */
	public void setInputString(String inputString) {
		this.inputString = inputString;
	}

	/**
	 * @param encryptedString the encryptedString to set
	 */
	public void setEncryptedString(String encryptedString) {
		this.encryptedString = encryptedString;
	}

	/**
	 * @param outputString the outputString to set
	 */
	public void setOutputString(String outputString) {
		this.outputString = outputString;
	}

	/**
	 * @param isSame the isSame to set
	 */
	public void setIsSame(Boolean isSame) {
		this.isSame = isSame;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(Long duration) {
		this.duration = duration;
	}

	/**
	 * @param aesKey the aesKey to set
	 */
	public void setAesKey(String aesKey) {
		this.aesKey = aesKey;
	}

	/**
	 * @param rsaPublicKey the rsaPublicKey to set
	 */
	public void setRsaPublicKey(String rsaPublicKey) {
		this.rsaPublicKey = rsaPublicKey;
	}

	/**
	 * @param rsaPrivateKey the rsaPrivateKey to set
	 */
	public void setRsaPrivateKey(String rsaPrivateKey) {
		this.rsaPrivateKey = rsaPrivateKey;
	}

	
}
