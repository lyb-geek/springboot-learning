/*!
 * Crypto-JS v1.1.0
 * http://code.google.com/p/crypto-js/
 * Copyright (c) 2009, Jeff Mott. All rights reserved.
 * http://code.google.com/p/crypto-js/wiki/License
 */
(function(){

// Shortcut
var util = Crypto.util;

var MARC4 = Crypto.MARC4 = {

	/**
	 * Public API
	 */

	encrypt: function (message, password) {

		var

		    // Convert to bytes
		    m = util.stringToBytes(message),

		    // Generate random IV
		    iv = util.randomBytes(16),

		    // Generate key
		    k = Crypto.PBKDF2(password, util.bytesToString(iv), 32, { asBytes: true });

		// Encrypt
		MARC4._marc4(m, k, 1536);

		// Return ciphertext
		return util.bytesToBase64(iv.concat(m));

	},

	decrypt: function (ciphertext, password) {

		var

		    // Convert to bytes
		    c = util.base64ToBytes(ciphertext),

		    // Separate IV and message
		    iv = c.splice(0, 16),

		    // Generate key
		    k = Crypto.PBKDF2(password, util.bytesToString(iv), 32, { asBytes: true });

		// Decrypt
		MARC4._marc4(c, k, 1536);

		// Return plaintext
		return util.bytesToString(c);

	},


	/**
	 * Internal methods
	 */

	// The core
	_marc4: function (m, k, drop) {

		// State variables
		var i, j, s, temp;

		// Key setup
		for (i = 0, s = []; i < 256; i++) s[i] = i;
		for (i = 0, j = 0;  i < 256; i++) {

			j = (j + s[i] + k[i % k.length]) % 256;

			// Swap
			temp = s[i];
			s[i] = s[j];
			s[j] = temp;

		}

		// Clear counters
		i = j = 0;

		// Encryption
		for (var k = 0 - drop; k < m.length; k++) {

			i = (i + 1) % 256;
			j = (j + s[i]) % 256;

			// Swap
			temp = s[i];
			s[i] = s[j];
			s[j] = temp;

			// Stop here if we're still dropping keystream
			if (k < 0) continue;

			// Encrypt
			m[k] ^= s[(s[i] + s[j]) % 256];

		}

	}

};

})();
