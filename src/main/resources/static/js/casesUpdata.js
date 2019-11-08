$(document).ready(function() {
	 $('#casefilesupload').fileupload({
         //调用的API为casesResoureces类中的uploadFile函数
         url: '/api/cases/upload',
         dataType: 'json',
         //上传成功的回调函数
         done: function(e, data) {
             //错误码为0代表上传成功
             if (data.result.code == 0) {

                 $("#caseprogress_str").text("上传成功！");

                
             } else {
                 $("#caseprogress_str").text("上传失败：" + data.result.message);
             }
         },
         fail: function(e, data) {
             $("#caseprogress_str").text("上传失败!");
             $.ajax({
                 "url": "api/auth/test",
                 "type": "GET",
                 "dataType": "json",

                 "error": function(jqXHR, exception) {
                     if (jqXHR.status === 0) {
                         alert('Not connect.\n Verify Network.');
                     } else if (jqXHR.status == 401) {
                         $("#ModalLogin").modal("show");
                     } else {
                         alert('Uncaught Error.\n' + jqXHR.responseText);
                     }
                 }
             });
         },
         //显示进度条
         progressall: function(e, data) {
             var progress = parseInt(data.loaded / data.total * 100, 10);
             $('#caseprogress .progress-bar').css(
                 'width',
                 progress + '%'
             );
             $('#caseprogress_str').text(progress + '%');
         }
     }).prop('disabled', !$.support.fileInput)
     .parent().addClass($.support.fileInput ? undefined : 'disabled');

});
