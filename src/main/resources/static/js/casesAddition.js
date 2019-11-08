$(document).ready(function() {

    /*$("li#Addition a").on('click', function (e) {
        e.preventDefault();
        var url = this.href;
        $("li#Addition a#addMenu1").removeClass('')
    })*/

    //初始化amazeui datetimepicker时间选择器
    $(".time").datetimepicker({
        format: "yyyy-mm-dd",
        minView: 2,
        autoclose: true,
        todayBtn: true
    })

    //patients表的插入
    //点击添加按钮的回调函数
    $("#add").on("click", function() {
        $(this).css("cursor", "wait");
        var admissionnumber = $("#admissionnumber").val();
        if (admissionnumber.length < 6 || admissionnumber.length > 7) {
            alert("请输入6/7位住院号......!");
            $("#add").css("cursor", "pointer");
            return;
        }

        var nationality = $("#nationality").val();
        if (nationality === '') {
            alert("请输入名族！");
            $("#add").css("cursor", "pointer");
            return;
        }
        var hos_time=$("#hosTime").val();
        if (hos_time === '') {
            alert("请输入入院时间！");
            $("#add").css("cursor", "pointer");
            return;
        }
        var discharged_time=$("#dischargedTime").val();
        if (discharged_time === '') {
            alert("请输入出院时间！");
            $("#add").css("cursor", "pointer");
            return;
        }


        var radiographyNothing = document.getElementById("radiographyNothing").checked;
        var radiographyNormal = document.getElementById("radiographyNormal").checked;
        var radiographyStricture = document.getElementById("radiographyStricture").checked;
        if (!radiographyNothing && !radiographyNormal && !radiographyStricture) {
            alert("请选择造影结果类型！");
            $("#add").css("cursor", "pointer");
            return;
        }
        var radiographyDate=$("#radiographyDate").val();
        if(radiographyDate==='')
        {
            radiographyDate=null;
        }

        var ctNothing = document.getElementById("ctNothing").checked;
        var ctNormal = document.getElementById("ctNormal").checked;
        var ctStricture = document.getElementById("ctStricture").checked;
        if (!ctNothing && !ctNormal && !ctStricture) {
            alert("请选择CT结果类型！");
            $("#add").css("cursor", "pointer");
            return;
        }
        var CTDate=$("#CTDate").val();
        if(CTDate==='')
        {
            CTDate=null;
        }
        var ownner;
        var hos=$("#owner").val();
        if ($("#loginuser").text() == "shz")
        {
            ownner="石河子医院";
        }
        else{
            if ($("#loginuser").text() == "fuwai")
            {
                ownner="北京阜外医院";
            }else
            {
                if (hos==0)
                {
                    ownner="石河子医院";
                }
                else
                {
                    ownner="北京阜外医院";
                }
            }
        }
        $.ajax({
            "url": "/CasesAddition/findNationalityId",
            "type": "POST",
            "data": { "nationality": nationality},
            "success": function(data) {
                var patientsData = {
                    "name": $("#name").val(),
                    "sex": $("#sex").val(),
                    "age": $("#age").val(),
                    "admissionnumber":$("#admissionnumber").val(),
                    "nationality":data.id,
                    "type_tag":$("#disease").val(),
                    "bir_place": $("#bir_place").val(),
                    "hos_time": $("#hosTime").val(),
                    "discharged_time": $("#dischargedTime").val(),
                    "ownner": ownner
                };
                $.ajax({
                    "headers": {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },
                    "url": "/CasesAddition/insertPatients",
                    "type": "POST",
                    "data": JSON.stringify(patientsData),
                    "dataType": "json",
                    //"async": "false",
                    "success": function(data) {
                        console.log(data.code);
                        if (data.code == 7) {
                            alert(data.message);
                            console.log(data.message);
                            $("#add").css("cursor", "pointer");
                            return;
                        };
                        //return;
                        //basic_case表的插入
                        var basiccase = {
                            "patient_id":data.id,
                            "height": $("#height").val(),
                            "weight": $("#weight").val(),
                            "smoke_level":$("#smoke_level").val(),
                            "drink_level":$("#drink_level").val(),
                            "genetic_history":$("#genetic_history").val(),
                            "drug_history":$("#drug_history").val()
                        };
                        $.ajax({
                            "headers": {
                                'Accept': 'application/json',
                                'Content-Type': 'application/json'
                            },
                            "url": "/CasesAddition/insertBasicCase",
                            "type": "POST",
                            "data": JSON.stringify(basiccase),
                            "dataType": "json",
                            "success": function (data) {
                            },
                            "error": function (jqXHR, exception) {
                                $("#add").css("cursor", "pointer");
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
                        //comprehensive_case表的插入
                        var comprehensivecase = {
                            "patient_id":data.id,
                            "heart_rate": $("#heart_rate").val(),
                            "blood_pressure_high": $("#high_blood_pressure").val(),
                            "blood_pressure_low": $("#low_blood_pressure").val(),
                            "ultrasonicef": $("#ultrasonicEF").val(),
                            "lv":$("#LV").val(),
                            "triglyceride":$("#triglyceride").val(),
                            "blood_sugur":$("#blood_sugur").val(),
                            "creatinine":$("#Creatinine").val(),
                            "ldlc":$("#low_density_lipoprotein_cholesterol").val(),
                            "hdlc": $("#high_density_lipoprotein_cholesterol").val(),
                            "hscp": $("#High_sensitivity_C_reactive_protein").val(),
                            "lipoprotein_a":$("#lipoprotein_a").val(),
                            "glycated_hemoglobin":$("#glycated_hemoglobin").val(),
                            "bmi":$("#BMI").val(),
                            "ffr": $("#FFR").val(),
                            "bnp": $("#bnp").val(),
                            "thyroid_function":$("#thyroid_function").val(),
                            "blood_potassium":$("#Blood_potassium").val(),
                            "blood_sodium":$("#Blood_sodium").val()
                        };
                        $.ajax({
                            "headers": {
                                'Accept': 'application/json',
                                'Content-Type': 'application/json'
                            },
                            "url": "/CasesAddition/insertComprehensiveCase",
                            "type": "POST",
                            "data": JSON.stringify(comprehensivecase),
                            "dataType": "json",
                            "success": function(data) {
                            },
                            "error": function(jqXHR, exception) {
                                $("#add").css("cursor", "pointer");
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
                        //imagingReport表的插入
                        var imagingReport=
                            {
                                "patient_id": data.id,
                                "description": $("#radiography").val(),
                                "result_id": (radiographyNothing ? 0 : (radiographyNormal ? 1 : 2)),
                                "date": radiographyDate
                            }
                        $.ajax({
                            "headers": {
                                'Accept': 'application/json',
                                'Content-Type': 'application/json'
                            },
                            "url": "/CasesAddition/insertRadiography",
                            "type": "POST",
                            "data": JSON.stringify(imagingReport),
                            "dataType": "json",
                            "success": function(data) {
                            },
                            "error": function(jqXHR, exception) {
                                $("#add").css("cursor", "pointer");
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
                        //CT插入
                        var ctReport=
                            {
                                "patient_id": data.id,
                                "description": $("#ct").val(),
                                "result_id": (ctNothing ? 0 : (ctNormal ? 1 : 2)),
                                "date": CTDate
                            }
                        $.ajax({
                            "headers": {
                                'Accept': 'application/json',
                                'Content-Type': 'application/json'
                            },
                            "url": "/CasesAddition/insertCT",
                            "type": "POST",
                            "data": JSON.stringify(ctReport),
                            "dataType": "json",
                            "success": function(data) {
                            },
                            "error": function(jqXHR, exception) {
                                $("#add").css("cursor", "pointer");
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
                        //diagnose_info表的插入
                        var diagnose_info=
                            {
                                "patient_id": data.id,
                                "complaints": $("#complaint").val(),
                                "diagnose_result": $("#diagnosis").val(),
                            }
                        $.ajax({
                            "headers": {
                                'Accept': 'application/json',
                                'Content-Type': 'application/json'
                            },
                            "url": "/CasesAddition/insertDiagnoseInfo",
                            "type": "POST",
                            "data": JSON.stringify(diagnose_info),
                            "dataType": "json",
                            "success": function(data) {
                                alert("添加病历成功！");
                            },
                            "error": function(jqXHR, exception) {
                                $("#add").css("cursor", "pointer");
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
                    },
                    "error": function(jqXHR, exception) {
                        $("#add").css("cursor", "pointer");
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
            },
            "error": function(jqXHR, exception) {
                if (jqXHR.status === 0) {
                    alert('Not connect.\n Verify Network.');
                } else if (jqXHR.status == 500) {
                    alert(jqXHR.responseJSON.message);
                } else {
                    alert('Uncaught Error.\n' + jqXHR.responseText);
                }
            }
        });
    });
    //插入诊断信息
    /*$("#add").on("click", function() {
        $(this).css("cursor", "wait");

        var diagnose_info=
            {
                "patient_id": 48,
                "complaints": $("#complaint").val(),
                "diagnose_result": $("#diagnosis").val(),
            }
        $.ajax({
            "headers": {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            "url": "api/cases/insertDiagnoseInfo",
            "type": "POST",
            "data": JSON.stringify(diagnose_info),
            "dataType": "json",
            "success": function(data) {
                $("#add").css("cursor", "pointer");
                alert("添加病历成功！");
            },
            "error": function(jqXHR, exception) {
                $("#add").css("cursor", "pointer");
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
    });*/
    //插入造影
    /*$("#add").on("click", function() {
        $(this).css("cursor", "wait");
        var radiographyNothing = document.getElementById("radiographyNothing").checked;
        var radiographyNormal = document.getElementById("radiographyNormal").checked;
        var radiographyStricture = document.getElementById("radiographyStricture").checked;
        if (!radiographyNothing && !radiographyNormal && !radiographyStricture) {
            alert("请选择造影结果类型！");
            $("#add").css("cursor", "pointer");
            return;
        }
        var imagingReport=
        {
            "patient_id": 48,
            "description": $("#radiography").val(),
            "result_id": (radiographyNothing ? 3 : (radiographyNormal ? 1 : 2)),
            "date": $("#hosTime").val()
        }
        $.ajax({
            "headers": {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            "url": "api/cases/insertRadiography",
            "type": "POST",
            "data": JSON.stringify(imagingReport),
            "dataType": "json",
            "success": function(data) {
                $("#add").css("cursor", "pointer");
                alert("添加病历成功！");
            },
            "error": function(jqXHR, exception) {
                $("#add").css("cursor", "pointer");
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
    });*/
   //插入ECG
    /*$("#add").on("click", function() {
        $(this).css("cursor", "wait");
        var ecgNormal = document.getElementById("ecgNormal").checked;
        var ecgUnusual = document.getElementById("ecgUnusual").checked;
        var ecgBigUnusual = document.getElementById("ecgBigUnusual").checked;
        var surgerybefore=document.getElementById("surgerybefore").checked;
        var surgeryafter=document.getElementById("surgeryafter").checked;
        if (!ecgNormal && !ecgUnusual && !ecgBigUnusual) {
            alert("请选择心电图类型！");
            $("#add").css("cursor", "pointer");
            return;
        }
        if (!surgerybefore && !surgeryafter) {
            alert("请选择心电图手术类型！");
            $("#add").css("cursor", "pointer");
            return;
        }
        var ecg=
            {
                "patient_id": 50,
                "test_id":$("#ecgId").val(),
                "ecg_data":" ",
                "ecg_result":(ecgNormal ? 0 : (ecgUnusual ? 1:2)),
                "ecg_info": $("#ecg").val(),
                "surgery": (surgerybefore ? 0 : 1),
                "date": null
            }
        $.ajax({
            "headers": {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            "url": "api/ecg/insertEcg",
            "type": "POST",
            "data": JSON.stringify(ecg),
            "dataType": "json",
            "success": function(data) {
                $("#add").css("cursor", "pointer");
                alert("添加病历成功！");
            },
            "error": function(jqXHR, exception) {
                $("#add").css("cursor", "pointer");
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
    });*/
    //插入CT
    /*$("#add").on("click", function() {
        $(this).css("cursor", "wait");
        var ctNothing = document.getElementById("ctNothing").checked;
        var ctNormal = document.getElementById("ctNormal").checked;
        var ctStricture = document.getElementById("ctStricture").checked;
        if (!ctNothing && !ctNormal && !ctStricture) {
            alert("请选择CT结果类型！");
            $("#add").css("cursor", "pointer");
            return;
        }
        var ctReport=
        {
            "patient_id": 48,
            "description": $("#ct").val(),
            "result_id": (ctNothing ? 0 : (ctNormal ? 1 : 2)),
            "date": $("#CTDate").val()
        }
        $.ajax({
            "headers": {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            "url": "api/cases/insertCT",
            "type": "POST",
            "data": JSON.stringify(ctReport),
            "dataType": "json",
            "success": function(data) {
                $("#add").css("cursor", "pointer");
                alert("添加病历成功！");
            },
            "error": function(jqXHR, exception) {
                $("#add").css("cursor", "pointer");
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
    });*/
    //重置则刷新页面
    $("#reset").on("click", function() {
        location.reload();
    });
});
function logOwner() {
    if ($("#loginuser").text() == "shz"){
        var x = document.getElementById("owner");
        x.options[0].selected = true;
        $("#owner").attr("disabled", true);
    }
}
