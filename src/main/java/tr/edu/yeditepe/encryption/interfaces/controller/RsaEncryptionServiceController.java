package tr.edu.yeditepe.encryption.interfaces.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tr.edu.yeditepe.encryption.interfaces.EncryptionComparisonFacade;
import tr.edu.yeditepe.encryption.interfaces.dto.ResultDto;

@RestController
@RequestMapping("/api/rsa-service")
public class RsaEncryptionServiceController {
	
	@Autowired
	EncryptionComparisonFacade encryptionComparisonFacade;
	
	
	@RequestMapping(value = "/encrypt-test", method = RequestMethod.GET)
	public ResponseEntity<ResultDto> rsaEncryptionTest(@RequestParam(value = "encyptionString", required=false) String encyptionString ,@RequestParam("keySize") Integer keySize) {
		ResultDto resultDto = encryptionComparisonFacade.rsaEncryptionSpeedTest(encyptionString,keySize);
		return new ResponseEntity<ResultDto>(resultDto, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/create-keys", method = RequestMethod.GET)
	public ResponseEntity<Boolean> rsaKeyCreate(@RequestParam("keySize") Integer keySize) {
		encryptionComparisonFacade.createRsaKeys(keySize);
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/encrypt", method = RequestMethod.GET)
	public ResponseEntity<ResultDto> rsaEncryption(@RequestParam(value = "encyptionString", required=false) String input ) {
		return new ResponseEntity<ResultDto>(encryptionComparisonFacade.applyRsaEncryption(input), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/decrypt", method = RequestMethod.GET)
	public ResponseEntity<ResultDto> rsaDecryption(@RequestParam(value = "encyptionString", required=false) String input ) {
		return new ResponseEntity<ResultDto>(encryptionComparisonFacade.applyRsaReverseEncryption(input), HttpStatus.OK);
	}
}