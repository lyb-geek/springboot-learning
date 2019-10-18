/*
 * Crypto-JS v1.1.0
 * http://code.google.com/p/crypto-js/
 * Copyright (c) 2009, Jeff Mott. All rights reserved.
 * http://code.google.com/p/crypto-js/wiki/License
 */
(function(){Crypto.mode.OFB={encrypt:a,decrypt:a};function a(c,b,d){var g=c._blocksize*4,f=d.slice(0);for(var e=0;e<b.length;e++){if(e%g==0){c._encryptblock(f,0)}b[e]^=f[e%g]}}})();