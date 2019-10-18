/*
 * Crypto-JS v1.1.0
 * http://code.google.com/p/crypto-js/
 * Copyright (c) 2009, Jeff Mott. All rights reserved.
 * http://code.google.com/p/crypto-js/wiki/License
 */
Crypto.mode.CBC={encrypt:function(b,a,c){var f=b._blocksize*4;a.push(128);for(var e=0;e<a.length;e+=f){if(e==0){for(var d=0;d<f;d++){a[d]^=c[d]}}else{for(var d=0;d<f;d++){a[e+d]^=a[e+d-f]}}b._encryptblock(a,e)}},decrypt:function(a,j,d){var h=a._blocksize*4;for(var g=0;g<j.length;g+=h){var b=j.slice(g,g+h);a._decryptblock(j,g);if(g==0){for(var e=0;e<h;e++){j[e]^=d[e]}}else{for(var e=0;e<h;e++){j[g+e]^=f[e]}}var f=b}while(j.pop()!=128){}}};