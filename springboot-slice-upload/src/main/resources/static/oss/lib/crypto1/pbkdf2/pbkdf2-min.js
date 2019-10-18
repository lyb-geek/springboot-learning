/*
 * Crypto-JS v1.1.0
 * http://code.google.com/p/crypto-js/
 * Copyright (c) 2009, Jeff Mott. All rights reserved.
 * http://code.google.com/p/crypto-js/wiki/License
 */
(function(){var a=Crypto.util;Crypto.PBKDF2=function(m,k,b,p){var o=p&&p.hasher||Crypto.SHA1,e=p&&p.iterations||1;function l(i,j){return Crypto.HMAC(o,j,i,{asBytes:true})}var d=[],c=1;while(d.length<b){var f=l(m,k+a.bytesToString(a.wordsToBytes([c])));for(var n=f,h=1;h<e;h++){n=l(m,a.bytesToString(n));for(var g=0;g<f.length;g++){f[g]^=n[g]}}d=d.concat(f);c++}d.length=b;return p&&p.asBytes?d:p&&p.asString?a.bytesToString(d):a.bytesToHex(d)}})();