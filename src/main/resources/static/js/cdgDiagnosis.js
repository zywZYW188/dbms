$(document).ready(function() {
    var  surgery= 0;
    var  ecg_result= 0;
    var  ecg="";
    //上传ECG文件，使用FileUpload插件，详细用法参见FileUpload官网介绍
    $('#filesupload').fileupload({
        //调用的API为ECGResoureces类中的函数
        url: "/CdgDiagnosis/UploadEcg",
        dataType: "json",
        //上传成功的回调函数
        add: function (e, data) {
            var ecgNormal = document.getElementById("ecgNormal").checked;
            var ecgUnusual = document.getElementById("ecgUnusual").checked;
            var ecgBigUnusual = document.getElementById("ecgBigUnusual").checked;
            var surgerybefore=document.getElementById("surgerybefore").checked;
            var surgeryafter=document.getElementById("surgeryafter").checked;
            if (!surgerybefore && !surgeryafter) {
                alert("请选择心电图手术类型！");
                $("#add").css("cursor", "pointer");
                return;
            }
            else{
                if (!ecgNormal && !ecgUnusual && !ecgBigUnusual) {
                    alert("请选择心电图类型！");
                    $("#add").css("cursor", "pointer");
                    return;
                }
                else {
                    ecg_result=(ecgNormal ? 0 : (ecgUnusual ? 1:2));
                    surgery= (surgerybefore ? 0 : 1);
                    ecg=$("#ecg").val();
                    data.submit();
                }
            }
        },
        done: function(e, data) {
            //错误码为0代表上传成功
            if (data.result.code == 0) {

                $("#progress_str").text("上传成功！");
                //点击诊断按钮的回调函数，只有在上传成功后才能被触发
                $("#diagnosis").on("click", function() {

                    //显示诊断中的html样式
                    $("#cdgDiagnosisText").text("CDG诊断中...");
                    $("#loadingGif").css("display", "inline");
                    $("#diagnosis_str").text("");

                    //调用的API为ECGResoureces类中的hadoop函数
                    $.ajax({
                        "url": "api/ecg/hadoop",
                        "type": "GET",
                        "data": { "threadId": data.result.threadId },
                        "dataType": "json",
                        "success": function(data) {

                            $("#cdgDiagnosisText").text("CDG诊断");
                            $("#loadingGif").css("display", "none");

                            if (data.code == 0) {
                                $("#diagnosis_str").text("诊断完成！");
                            } else {
                                $("#diagnosis_str").text("诊断失败：" + data.result.message);
                            }
                        },
                        "error": function(jqXHR, exception) {

                            $("#cdgDiagnosisText").text("CDG诊断");
                            $("#loadingGif").css("display", "none");
                            $("#diagnosis_str").text("诊断失败!");

                            if (jqXHR.status === 0) {
                                alert('Not connect.\n Verify Network.');
                            } else if (jqXHR.status == 401) {
                                $("#ModalLogin").modal("show");
                            } else if (jqXHR.status == 500) {
                                alert(jqXHR.responseJSON.message);
                            } else {
                                alert('Uncaught Error.\n' + jqXHR.responseText);
                            }
                        }

                    });

                });
            } else {
                $("#progress_str").text("上传失败：" + data.result.message);
            }
        },
        fail: function(e, data) {
            $("#progress_str").text("上传失败!");
          /*  $.ajax({
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
            });*/
        },
        //显示进度条
        progressall: function(e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#progress .progress-bar').css(
                'width',
                progress + '%'
            );
            $('#progress_str').text(progress + '%');
        }
    }).prop('disabled', !$.support.fileInput)
        .parent().addClass($.support.fileInput ? undefined : 'disabled');

    $('#filesupload').bind('fileuploadsubmit', function (e, data) {
        data.formData = { param1: ecg,param2:surgery,param3:ecg_result };  //如果需要额外添加参数可以在这里添加
    });
});

function checkBox(obj) {
    if(obj.is(":checked")){
        $('input.mybox').prop('checked', false);
        obj.prop('checked', true);
    }
}
/*
$(document).ready(function() {
    //上传ECG文件，使用FileUpload插件，详细用法参见FileUpload官网介绍
    $('#filesupload').fileupload({
        //调用的API为ECGResoureces类中的uploadFile函数
        url: "/CdgDiagnosis/UploadEcg",
        dataType: 'json',
        //上传成功的回调函数
        done: function(e, data) {
            //错误码为0代表上传成功
            if (data.result.code == 0) {

                $("#progress_str").text("上传成功！");

                //点击诊断按钮的回调函数，只有在上传成功后才能被触发
                $("#diagnosis").on("click", function() {

                    //显示诊断中的html样式
                    $("#cdgDiagnosisText").text("CDG诊断中...");
                    $("#loadingGif").css("display", "inline");
                    $("#diagnosis_str").text("");

                    //调用的API为ECGResoureces类中的hadoop函数
                    $.ajax({
                        "url": "api/ecg/hadoop",
                        "type": "GET",
                        "data": { "threadId": data.result.threadId },
                        "dataType": "json",
                        "success": function(data) {

                            $("#cdgDiagnosisText").text("CDG诊断");
                            $("#loadingGif").css("display", "none");

                            if (data.code == 0) {
                                $("#diagnosis_str").text("诊断完成！");
                            } else {
                                $("#diagnosis_str").text("诊断失败：" + data.result.message);
                            }
                        },
                        "error": function(jqXHR, exception) {

                            $("#cdgDiagnosisText").text("CDG诊断");
                            $("#loadingGif").css("display", "none");
                            $("#diagnosis_str").text("诊断失败!");

                            if (jqXHR.status === 0) {
                                alert('Not connect.\n Verify Network.');
                            } else if (jqXHR.status == 401) {
                                $("#ModalLogin").modal("show");
                            } else if (jqXHR.status == 500) {
                                alert(jqXHR.responseJSON.message);
                            } else {
                                alert('Uncaught Error.\n' + jqXHR.responseText);
                            }
                        }

                    });

                });
            } else {
                $("#progress_str").text("上传失败：" + data.result.message);
            }
        },
        fail: function(e, data) {
            $("#progress_str").text("上传失败!");
          /!*  $.ajax({
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
            });*!/
        },
        //显示进度条
        progressall: function(e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#progress .progress-bar').css(
                'width',
                progress + '%'
            );
            $('#progress_str').text(progress + '%');
        }
    }).prop('disabled', !$.support.fileInput)
        .parent().addClass($.support.fileInput ? undefined : 'disabled');

});
*/
