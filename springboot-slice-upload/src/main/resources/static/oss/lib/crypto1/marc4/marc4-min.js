/*
 * Crypto-JS v1.1.0
 * http://code.google.com/p/crypto-js/
 * Copyright (c) 2009, Jeff Mott. All rights reserved.
 * http://code.google.com/p/crypto-js/wiki/License
 */
(function(){var a=Crypto.util;var b=Crypto.MARC4={encrypt:function(g,f){var c=a.stringToBytes(g),e=a.randomBytes(16),d=Crypto.PBKDF2(f,a.bytesToString(e),32,{asBytes:true});b._marc4(c,d,1536);return a.bytesToBase64(e.concat(c))},decrypt:function(g,f){var h=a.base64ToBytes(g),e=h.splice(0,16),d=Crypto.PBKDF2(f,a.bytesToString(e),32,{asBytes:true});b._marc4(h,d,1536);return a.bytesToString(h)},_marc4:function(c,f,e){var h,g,l,d;for(h=0,l=[];h<256;h++){l[h]=h}for(h=0,g=0;h<256;h++){g=(g+l[h]+f[h%f.length])%256;d=l[h];l[h]=l[g];l[g]=d}h=g=0;for(var f=0-e;f<c.length;f++){h=(h+1)%256;g=(g+l[h])%256;d=l[h];l[h]=l[g];l[g]=d;if(f<0){continue}c[f]^=l[(l[h]+l[g])%256]}}}})();