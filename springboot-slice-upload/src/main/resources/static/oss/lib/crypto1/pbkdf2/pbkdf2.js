/*!
 * Crypto-JS v1.1.0
 * http://code.google.com/p/crypto-js/
 * Copyright (c) 2009, Jeff Mott. All rights reserved.
 * http://code.google.com/p/crypto-js/wiki/License
 */
(function(){

// Shortcut
var util = Crypto.util;

Crypto.PBKDF2 = function (password, salt, keylen, options) {

	// Defaults
	var hasher = options && options.hasher || Crypto.SHA1,
	    iterations = options && options.iterations || 1;

	// Pseudo-random function
	function PRF(password, salt) {
		return Crypto.HMAC(hasher, salt, password, { asBytes: true });
	}

	// Generate key
	var derivedKeyBytes = [],
	    blockindex = 1;
	while (derivedKeyBytes.length < keylen) {

		var block = PRF(password, salt + util.bytesToString(
		                                 util.wordsToBytes([blockindex])));

		for (var u = block, i = 1; i < iterations; i++) {
			u = PRF(password, util.bytesToString(u));
			for (var j = 0; j < block.length; j++) block[j] ^= u[j];
		}

		derivedKeyBytes = derivedKeyBytes.concat(block);
		blockindex++;

	}

	// Truncate excess bytes
	derivedKeyBytes.length = keylen;

	return options && options.asBytes ? derivedKeyBytes :
	       options && options.asString ? util.bytesToString(derivedKeyBytes) :
	       util.bytesToHex(derivedKeyBytes);

};

})();
