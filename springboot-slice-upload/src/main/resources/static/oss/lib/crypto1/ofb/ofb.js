/*!
 * Crypto-JS v1.1.0
 * http://code.google.com/p/crypto-js/
 * Copyright (c) 2009, Jeff Mott. All rights reserved.
 * http://code.google.com/p/crypto-js/wiki/License
 */
(function(){

// Public API
Crypto.mode.OFB = {
	encrypt: OFB,
	decrypt: OFB
};

// The mode function
function OFB(cipher, m, iv) {

	var blockSizeInBytes = cipher._blocksize * 4,
	    keystream = iv.slice(0);

	// Encrypt each byte
	for (var i = 0; i < m.length; i++) {

		// Generate keystream
		if (i % blockSizeInBytes == 0)
			cipher._encryptblock(keystream, 0);

		// Encrypt byte
		m[i] ^= keystream[i % blockSizeInBytes];

	}

}

})();
