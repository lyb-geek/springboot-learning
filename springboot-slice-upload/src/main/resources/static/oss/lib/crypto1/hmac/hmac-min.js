/*
 * Crypto-JS v1.1.0
 * http://code.google.com/p/crypto-js/
 * Copyright (c) 2009, Jeff Mott. All rights reserved.
 * http://code.google.com/p/crypto-js/wiki/License
 */
(function(){var a=Crypto.util;Crypto.HMAC=function(g,h,f,d){f=f.length>g._blocksize*4?g(f,{asBytes:true}):a.stringToBytes(f);var c=f,j=f.slice(0);for(var e=0;e<g._blocksize*4;e++){c[e]^=92;j[e]^=54}var b=g(a.bytesToString(c)+g(a.bytesToString(j)+h,{asString:true}),{asBytes:true});return d&&d.asBytes?b:d&&d.asString?a.bytesToString(b):a.bytesToHex(b)}})();