/*!
 * Crypto-JS v1.1.0
 * http://code.google.com/p/crypto-js/
 * Copyright (c) 2009, Jeff Mott. All rights reserved.
 * http://code.google.com/p/crypto-js/wiki/License
 */
Crypto.mode.CBC = {

	encrypt: function (cipher, m, iv) {

		var blockSizeInBytes = cipher._blocksize * 4;

		// Pad
		m.push(0x80);

		// Encrypt each block
		for (var offset = 0; offset < m.length; offset += blockSizeInBytes) {

			if (offset == 0) {
				// XOR first block using IV
				for (var i = 0; i < blockSizeInBytes; i++)
					m[i] ^= iv[i];
			}
			else {
				// XOR this block using previous crypted block
				for (var i = 0; i < blockSizeInBytes; i++)
					m[offset + i] ^= m[offset + i - blockSizeInBytes];
			}

			// Encrypt block
			cipher._encryptblock(m, offset);

		}

	},

	decrypt: function (cipher, c, iv) {

		var blockSizeInBytes = cipher._blocksize * 4;

		// Decrypt each block
		for (var offset = 0; offset < c.length; offset += blockSizeInBytes) {

			// Save this crypted block
			var thisCryptedBlock = c.slice(offset, offset + blockSizeInBytes);

			// Decrypt block
			cipher._decryptblock(c, offset);

			if (offset == 0) {
				// XOR first block using IV
				for (var i = 0; i < blockSizeInBytes; i++)
					c[i] ^= iv[i];
			}
			else {
				// XOR decrypted block using previous crypted block
				for (var i = 0; i < blockSizeInBytes; i++)
					c[offset + i] ^= prevCryptedBlock[i];
			}

			// This crypted block is the new previous crypted block
			var prevCryptedBlock = thisCryptedBlock;

		}

		// Strip padding
		while (c.pop() != 0x80) ;

	}

};
