$(document).ready(function() {
    //初始化时间选择器
    $(".time").datetimepicker({
        format: "yyyy-mm-dd",
        minView: 2,
        autoclose: true,
        todayBtn: true
    })

    /**
     * 根据住院号查询病人
     */
    $("#search").on("click", function() {
        $(this).css("cursor", "wait");

        //住院号必须为6位
        var admissionnumber = $("#admissionnumberSearch").val();
        //console.log(admissionnumber);
        if (admissionnumber.length < 6 || admissionnumber.length > 7) {
            alert("请输入正确的住院号:六/七位数字");
            return;
        }
        //查询病人是否存在，调用的API述PatientsResources类中的findByAdmissionnumber函数
        $.ajax({
            "url": "/CasesModification/getPatients",
            "type": "GET",
            "data": { "admissionnumber": admissionnumber },
            "dataType": "json",
            "success": function(patients) {
                //如果结果集为空，则警告并返回
                if (patients === undefined) {
                    alert("无此病人信息");
                    return;
                }
                var hospital;
                if ($("#loginuser").text() == "shz")
                {
                    hospital="石河子医院";
                    if(patients.ownner!=hospital)
                    {
                        alert("无此病人信息权限");
                        return;
                    }
                }
                if($("#loginuser").text() == "fuwai")
                {
                    hospital="北京阜外医院";
                    if(patients.ownner!=hospital)
                    {
                        alert("无此病人信息权限");
                        return;
                    }
                }

                setPatients(patients);
                $.ajax({
                    "url": "/CasesModification/getBasiccase",
                    "type": "GET",
                    "data": { "patientId": patients.id },
                    "dataType": "json",
                    "success": function(basicCase) {
                        $("#search").css("cursor", "pointer");
                        //在修改窗口中填充病人病历信息，setCases函数在后面有定义
                        setBasicCase(basicCase);
                       // $("#modification").modal("show");
                        //根据paiemts和cases表中的id列，更新修改后的信息，函数在后面有定义
                        //modifyCases(patients.id, cases.id);
                    },
                    "error": function(jqXHR, exception) {
                        $("#search").css("cursor", "pointer");
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
                $.ajax({
                    "url": "/CasesModification/getComprehensiveCase",
                    "type": "GET",
                    "data": { "patientId": patients.id },
                    "dataType": "json",
                    "success": function(ComprehensiveCase) {
                        $("#search").css("cursor", "pointer");
                        //在修改窗口中填充病人病历信息，setCases函数在后面有定义
                        setComprehensiveCase(ComprehensiveCase)
                        //$("#modification").modal("show");
                        //根据paiemts和cases表中的id列，更新修改后的信息，函数在后面有定义
                        //modifyCases(patients.id, cases.id);
                    },
                    "error": function(jqXHR, exception) {
                        $("#search").css("cursor", "pointer");
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
               /* $.ajax({
                    "url": "api/ecg/getEcg",
                    "type": "GET",
                    "data": { "patient_id": patients.id },
                    "dataType": "json",
                    "success": function(ecg) {
                        $("#search").css("cursor", "pointer");
                        //在修改窗口中填充病人病历信息，setCases函数在后面有定义
                        setEcg(ecg)
                        //$("#modification").modal("show");
                        //根据paiemts和cases表中的id列，更新修改后的信息，函数在后面有定义
                        //modifyCases(patients.id, cases.id);
                    },
                    "error": function(jqXHR, exception) {
                        $("#search").css("cursor", "pointer");
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
                });*/
                $.ajax({
                    "url": "/CasesModification/getRadiography",
                    "type": "GET",
                    "data": { "patient_id": patients.id },
                    "dataType": "json",
                    "success": function(imagingReport) {
                        $("#search").css("cursor", "pointer");
                        //在修改窗口中填充病人病历信息，setCases函数在后面有定义
                        setImagingReport(imagingReport);
                        $("#modification").modal("show");
                        //根据paiemts和cases表中的id列，更新修改后的信息，函数在后面有定义
                        //modifyCases(patients.id, cases.id);
                    },
                    "error": function(jqXHR, exception) {
                        $("#search").css("cursor", "pointer");
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

                $.ajax({
                    "url": "/CasesModification/getCT",
                    "type": "GET",
                    "data": { "patient_id": patients.id },
                    "dataType": "json",
                    "success": function(ct_report) {
                        $("#search").css("cursor", "pointer");
                        //在修改窗口中填充病人病历信息，setCases函数在后面有定义
                        setCT(ct_report);
                        $("#modification").modal("show");
                        //根据paiemts和cases表中的id列，更新修改后的信息，函数在后面有定义
                        //modifyCases(patients.id, cases.id);
                    },
                    "error": function(jqXHR, exception) {
                        $("#search").css("cursor", "pointer");
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
                $.ajax({
                    "url": "/CasesModification/getDiagnoseInfo",
                    "type": "GET",
                    "data": { "patient_id": patients.id },
                    "dataType": "json",
                    "success": function(diagnose_info) {
                        $("#search").css("cursor", "pointer");
                        //在修改窗口中填充病人病历信息，setCases函数在后面有定义
                        setDiagnose_info(diagnose_info);
                        $("#modification").modal("show");
                        //根据paiemts和cases表中的id列，更新修改后的信息，函数在后面有定义
                        modifyCases(patients.id);
                    },
                    "error": function(jqXHR, exception) {
                        $("#search").css("cursor", "pointer");
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
                $("#search").css("cursor", "pointer");
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

});

//在修改病历页面中设置病人基本信息
function setPatients(patients) {
    $.ajax({
        "url": "/CasesAddition/findNationality",
        "type": "POST",
        "data": { "id": patients.nationality},
        "success": function(data) {
            $("#disease").val(patients.type_tag);
            $("#admissionnumber").val(patients.admissionnumber);
            $("#name").val(patients.name);
            $("#age").val(patients.age);
            $("#sex").val(patients.sex);
            $("#nationality").val(data.nationality);
            $("#bir_place").val(patients.bir_place);
            $("#hosTime").val(patients.hos_time);
            $("#dischargedTime").val(patients.discharged_time);
            if(patients.ownner=="石河子医院")
            {
                $("#owner").val(0);
            }
            else {
                $("#owner").val(1);
            }
            //$("#owner").val(patients.ownner);
            //name, sex, age, admissionnumber,nationality,type_tag,bir_place,hos_time,discharged_time,ownner
        },
        "error": function(jqXHR, exception) {
            $("#search").css("cursor", "pointer");
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
}

//在修改病历页面中设置病人病历信息
function setBasicCase(basicCase) {
    $("#height").val(basicCase.height);
    $("#weight").val(basicCase.weight);
    $("#smoke_level").val(basicCase.smoke_level);
    $("#drink_level").val(basicCase.drink_level);
    $("#genetic_history").val(basicCase.genetic_history);
    $("#drug_history").val(basicCase.drug_history);
}
function setComprehensiveCase(ComprehensiveCase) {
    $("#heart_rate").val(ComprehensiveCase.heart_rate);
    $("#high_blood_pressure").val(ComprehensiveCase.blood_pressure_high);
    $("#low_blood_pressure").val(ComprehensiveCase.blood_pressure_low);
    $("#ultrasonicEF").val(ComprehensiveCase.ultrasonicef);
    $("#LV").val(ComprehensiveCase.lv);
    $("#triglyceride").val(ComprehensiveCase.triglyceride);
    $("#blood_sugur").val(ComprehensiveCase.blood_sugur);
    $("#Creatinine").val(ComprehensiveCase.creatinine);
    $("#low_density_lipoprotein_cholesterol").val(ComprehensiveCase.ldlc);
    $("#high_density_lipoprotein_cholesterol").val(ComprehensiveCase.hdlc);
    $("#High_sensitivity_C_reactive_protein").val(ComprehensiveCase.hscp);
    $("#lipoprotein_a").val(ComprehensiveCase.lipoprotein_a);
    $("#glycated_hemoglobin").val(ComprehensiveCase.glycated_hemoglobin);
    $("#BMI").val(ComprehensiveCase.bmi);
    $("#FFR").val(ComprehensiveCase.ffr);
    $("#bnp").val(ComprehensiveCase.bnp);
    $("#thyroid_function").val(ComprehensiveCase.thyroid_function);
    $("#Blood_potassium").val(ComprehensiveCase.blood_potassium);
    $("#Blood_sodium").val(ComprehensiveCase.blood_sodium);
}
function setEcg(ecg)
{
    $("#ecgId").val(ecg.test_id);
    $("#ecg").val(ecg.ecg_info);
    $("#ecgDate").val(ecg.date);
    if (ecg.ecg_result== 0) {
        document.getElementById("ecgNormal").checked = true;
    } else if (ecg.ecg_result == 1) {
        document.getElementById("ecgUnusual").checked = true;
    } else {
        document.getElementById("ecgBigUnusual").checked = true;
    }
    if (ecg.surgery== 0) {
        document.getElementById("surgerybefore").checked = true;
    } else if (ecg.surgery == 1) {
        document.getElementById("surgeryafter").checked = true;
    }
}
function setImagingReport(imagingReport)
{
    $("#radiography").val(imagingReport.description);
    $("#radiographydate").val(imagingReport.date);
    if (imagingReport.result_id== 0) {
        document.getElementById("radiographyNothing").checked = true;
    } else if (imagingReport.result_id == 1) {
        document.getElementById("radiographyNormal").checked = true;

    } else {
        document.getElementById("radiographyStricture").checked = true;
    }
    //description,result_id,date
}

function setCT(ct_report) {
    $("#ct").val(ct_report.description);
    $("#CTDate").val(ct_report.date);
    if (ct_report.result_id== 0) {
        document.getElementById("ctNothing").checked = true;
    } else if (ct_report.result_id == 1) {
        document.getElementById("ctNormal").checked = true;
    } else {
        document.getElementById("ctStricture").checked = true;
    }

}
function setDiagnose_info(diagnose_info)
{
    $("#complaint").val(diagnose_info.complaints);
    $("#diagnosis").val(diagnose_info.diagnose_result);
    //complaints,diagnose_result
}
//根据paiemts和cases表中的id列，更新修改后的信息
function modifyCases(patientId)
{
    //点击修改按钮回调函数
    $("#modify").on("click", function() {

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
        var ownner;
        if ($("#loginuser").text() == "shz")
        {
            ownner="石河子医院";
        }
        else{
            if ($("#loginuser").text() == "fuwai")
            {
                ownner="北京阜外医院";
            }
            else
            {
                var hos=$("#owner").val();
                if (hos==0)
                {
                    ownner="石河子医院";
                }
                else {
                    ownner="北京阜外医院";
                }

            }
        }
        var radiographyNothing = document.getElementById("radiographyNothing").checked;
        var radiographyNormal = document.getElementById("radiographyNormal").checked;
        var radiographyStricture = document.getElementById("radiographyStricture").checked;
        if (!radiographyNothing && !radiographyNormal && !radiographyStricture) {
            alert("请选择造影结果类型！");
            $("#add").css("cursor", "pointer");
            return;
        }
        var radiographyDate=$("#radiographydate").val();
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
       /* var ecgNormal = document.getElementById("ecgNormal").checked;
        var ecgUnusual = document.getElementById("ecgUnusual").checked;
        var ecgBigUnusual = document.getElementById("ecgBigUnusual").checked;
        var surgerybefore=document.getElementById("surgerybefore").checked;
        var surgeryafter=document.getElementById("surgeryafter").checked;
        var ecgDate=$("#ecgDate").val();
        var test_id=$("#ecgId").val();
        if ( test_id==='') {
            alert("请输入心电图测试号！");
            $("#add").css("cursor", "pointer");
            return;
        }
        if(ecgDate==='')
        {
            ecgDate=null;
        }
        if (!ecgNormal && !ecgUnusual && !ecgBigUnusual) {
            alert("请选择心电图类型！");
            $("#add").css("cursor", "pointer");
            return;
        }
        if (!surgerybefore && !surgeryafter) {
            alert("请选择心电图手术类型！");
            $("#add").css("cursor", "pointer");
            return;
        }*/
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
                    "url": "/CasesModification/updatePatients"+ "?id=" + patientId,
                    "type": "POST",
                    "data": JSON.stringify(patientsData),
                    "dataType": "json",
                    "success": function(data) {
                        console.log(data.code);
                        if (data.code == 7) {
                            alert(data.message);
                            console.log(data.message);
                            $("#add").css("cursor", "pointer");
                            return;
                        };

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
                //修改basiccase_case表
              var basiccase = {
                      //"patient_id":patientId,
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
                      "url": "/CasesModification/updateBasicCase"+ "?id=" + patientId,
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
                  //修改comprehensive_case表
                var comprehensivecase = {
                    //"patient_id":data.id,
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
                    "url": "/CasesModification/updateComprehensive_case"+"?id="+ patientId,
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
                       "description": $("#radiography").val(),
                       "result_id": (radiographyNothing ? 0 : (radiographyNormal ? 1 : 2)),
                       "date": radiographyDate
                   }
               $.ajax({
                   "headers": {
                       'Accept': 'application/json',
                       'Content-Type': 'application/json'
                   },
                   "url": "/CasesModification/updateImagingReport"+"?id="+patientId,
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
               //CT更新
                var ctReport=
                     {
                         "description": $("#ct").val(),
                         "result_id": (ctNothing ? 0 : (ctNormal ? 1 : 2)),
                         "date": CTDate
                     }
                 $.ajax({
                     "headers": {
                         'Accept': 'application/json',
                         'Content-Type': 'application/json'
                     },
                     "url": "/CasesModification/updateCT"+"?id="+patientId,
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
                 //diagnose_info表的更新
                 var diagnose_info=
                     {
                         "complaints": $("#complaint").val(),
                         "diagnose_result": $("#diagnosis").val(),
                     }
                 $.ajax({
                     "headers": {
                         'Accept': 'application/json',
                         'Content-Type': 'application/json'
                     },
                     "url": "/CasesModification/updateDiagnoseInfo"+"?id="+patientId,
                     "type": "POST",
                     "data": JSON.stringify(diagnose_info),
                     "dataType": "json",
                     "success": function(data) {
                         alert("修改病历成功！");
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
                /*var ecg=
                    {
                        "test_id":$("#ecgId").val(),
                        "ecg_data":" ",
                        "ecg_result":(ecgNormal ? 0 : (ecgUnusual ? 1:2)),
                        "ecg_info": $("#ecg").val(),
                        "surgery": (surgerybefore ? 0 : 1),
                        "date": ecgDate
                    }
                $.ajax({
                    "headers": {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },
                    "url": "api/ecg/updateEcg"+"?id="+patientId,
                    "type": "POST",
                    "data": JSON.stringify(ecg),
                    "dataType": "json",
                    "success": function(data) {
                        $("#add").css("cursor", "pointer");
                        alert("修改病历成功！");
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
                });*/
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
        })
    })
}
