package tr.edu.yeditepe.encryption.interfaces.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tr.edu.yeditepe.encryption.interfaces.EncryptionComparisonFacade;
import tr.edu.yeditepe.encryption.interfaces.dto.ResultDto;

@RestController
@RequestMapping("/api/aes-service")
public class AesEncryptionServiceController {
	
	@Autowired
	EncryptionComparisonFacade encryptionComparisonFacade;
	
	@RequestMapping(value = "/encrypt", method = RequestMethod.GET)
	public ResponseEntity<ResultDto> aesEncryption(@RequestParam("encyptionString") String encyptionString,@RequestParam("key") Integer keySize) {
		ResultDto data = encryptionComparisonFacade.applyAesEncryption(encyptionString, keySize);
		return new ResponseEntity<ResultDto>(data, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/decrypt", method = RequestMethod.GET)
	public ResponseEntity<ResultDto> aesDecryption(@RequestParam("encyptionString") String decryptionString,@RequestParam("key") String key) {
		ResultDto data = encryptionComparisonFacade.applyAesReverseEncryption(decryptionString, key);
		return new ResponseEntity<ResultDto>(data, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/encrypt-test", method = RequestMethod.GET)
	public ResponseEntity<ResultDto> aesEcryptionTest(@RequestParam(value = "encyptionString", required=false) String encyptionString ,@RequestParam("key") Integer key) {
		ResultDto resultDto = encryptionComparisonFacade.aesEncryptionSpeedTest(encyptionString, key);
		return new ResponseEntity<ResultDto>(resultDto, HttpStatus.OK);
	}

}
