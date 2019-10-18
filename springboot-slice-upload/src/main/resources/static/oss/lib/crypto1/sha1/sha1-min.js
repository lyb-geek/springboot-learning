/*
 * Crypto-JS v1.1.0
 * http://code.google.com/p/crypto-js/
 * Copyright (c) 2009, Jeff Mott. All rights reserved.
 * http://code.google.com/p/crypto-js/wiki/License
 */
(function(){var a=Crypto.util;var b=Crypto.SHA1=function(e,c){var d=a.wordsToBytes(b._sha1(e));return c&&c.asBytes?d:c&&c.asString?a.bytesToString(d):a.bytesToHex(d)};b._sha1=function(k){var u=a.stringToWords(k),v=k.length*8,o=[],q=1732584193,p=-271733879,h=-1732584194,g=271733878,f=-1009589776;u[v>>5]|=128<<(24-v%32);u[((v+64>>>9)<<4)+15]=v;for(var y=0;y<u.length;y+=16){var D=q,C=p,B=h,A=g,z=f;for(var x=0;x<80;x++){if(x<16){o[x]=u[y+x]}else{var s=o[x-3]^o[x-8]^o[x-14]^o[x-16];o[x]=(s<<1)|(s>>>31)}var r=((q<<5)|(q>>>27))+f+(o[x]>>>0)+(x<20?(p&h|~p&g)+1518500249:x<40?(p^h^g)+1859775393:x<60?(p&h|p&g|h&g)-1894007588:(p^h^g)-899497514);f=g;g=h;h=(p<<30)|(p>>>2);p=q;q=r}q+=D;p+=C;h+=B;g+=A;f+=z}return[q,p,h,g,f]};b._blocksize=16})();