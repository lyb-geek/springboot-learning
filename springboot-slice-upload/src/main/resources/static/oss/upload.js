var policyText = {
    "expiration": "2115-01-27T10:56:19Z", //设置该Policy的失效时间，超过这个失效时间之后，就没有办法通过这个policy上传文件了
    "conditions": [
    ["content-length-range", 0, 1048576000] // 设置上传文件的大小限制
    ]
};

var base = document.getElementById('base').getAttribute('base');

accessid= 'LTAIIHLzHA0z9pqh';
accesskey= 'gXYIkhxtxd0XyY0cib58HKBdg5r1sl';
host = 'http://clouduploadstore.oss-cn-hangzhou.aliyuncs.com';


var policyBase64 = Base64.encode(JSON.stringify(policyText))
message = policyBase64
var bytes = Crypto.HMAC(Crypto.SHA1, message, accesskey, { asBytes: true }) ;
var signature = Crypto.util.bytesToBase64(bytes);
var uploader = new plupload.Uploader({
	runtimes : 'html5,flash,silverlight,html4',
	browse_button : 'selectfiles', 
    //runtimes : 'flash',
	container: document.getElementById('container'),
	flash_swf_url : base+'/oss/lib/plupload-2.1.2/js/Moxie.swf',
	silverlight_xap_url : base+'/oss/lib/plupload-2.1.2/js/Moxie.xap',

    url : host,

	multipart_params: {
		'Filename': '${filename}', 
        'key' : '${filename}',
		'policy': policyBase64,
        'OSSAccessKeyId': accessid, 
        'success_action_status' : '200', //让服务端返回200,不然，默认会返回204
		'signature': signature,
	},

	init: {
		PostInit: function() {
			document.getElementById('ossfile').innerHTML = '';
			document.getElementById('postfiles').onclick = function() {
				uploader.start();
				return false;
			};
		},

		FilesAdded: function(up, files) {
			plupload.each(files, function(file) {
				document.getElementById('ossfile').innerHTML += '<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ')<b></b>'
				+'<div class="progress"><div class="progress-bar" style="width: 0%"></div></div>'
				+'</div>';
			});
		},

		UploadProgress: function(up, file) {
			var d = document.getElementById(file.id);
			d.getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent + "%</span>";
            
			var prog = d.getElementsByTagName('div')[0];
			var progBar = prog.getElementsByTagName('div')[0]
			progBar.style.width= 2*file.percent+'px';
			progBar.setAttribute('aria-valuenow', file.percent);
		},

		FileUploaded: function(up, file, info) {
            //alert(info.status)
            if (info.status >= 200 || info.status < 200)
            {
                document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = '<br/><font color="lime">上传成功</font>';
                setTimeout(function () {
                  //document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = ' ';
                  document.getElementById(file.id).getElementsByClassName('progress')[0].style.display="none";
                },1000)
            }
            else
            {
                document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = info.response;
            } 
		},

		Error: function(up, err) {
			document.getElementById('console').appendChild(document.createTextNode("\nError xml:" + err.response));
		}
	}
});

uploader.init();
