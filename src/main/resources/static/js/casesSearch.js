var myEchartObj = null;
var myEchartObj2 = null;
var patientsDataTableRow = null;
var tr = null;
var patientsSub = null;
$(document).ready(function() {
    /**
     * 初始化表格
     * @type {Object}
     */
    var patientsDataTable = $('#patientsData').DataTable({
        "deferRender": true,
        /*"scrollY": "200px",
        "scroller": true,
        "scrollCollapse": true,
        "paging": false,//是否开启本地分页
        "bFilter": false,//去掉搜索框*/
        "bLengthChange": false,//去掉每页显示多少条数据
        'pagingType': 'input',
        //"sPaginationType": "extStyle",
        language: {
            "sProcessing": "<img src='../images/ajax-loader.gif'>",
            "sLengthMenu": "显示 _MENU_ 项结果",
            "sZeroRecords": "没有匹配结果",
            "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
            "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
            "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
            "sInfoPostFix": "",
            "sSearch": "搜索:",
            "sUrl": "",
            "sEmptyTable": "表中数据为空",
            "sLoadingRecords": "载入中...",
            "sInfoThousands": ",",
            /*"oPaginate": {
                "sFirst": "首页",
                "sPrevious": "上页",
                "sNext": "下页",
                "sLast": "末页"
            },*/
            "oAria": {
                "sSortAscending": ": 以升序排列此列",
                "sSortDescending": ": 以降序排列此列"
            }
        },
        //为每一列设置数据和列名
        columns: [{
            "className": "content",
            "data": 'name'
        }, {
            "className": "content",
            "data": 'sex'
        }, {
            "className": "content",
            "data": 'age'
        }, {
            "className": "content",
            "data": 'admissionnumber'
        }, {
            "className": "content",
            "data": 'hos_time'
        }]

    });
    //第二个表格初始化
    var patientsSubTable = $('#patientsSub').DataTable({
        "deferRender": true,
        //"scrollY": "200px",
        //"scrollCollapse": true,
        //"paging": false,//是否开启本地分页
        //"bFilter": false,//去掉搜索框
        "bLengthChange": false,//去掉每页显示多少条数据
        'pagingType': 'input',
        language: {
            "sProcessing": "<img src='../images/ajax-loader.gif'>",
            //"sLengthMenu": "显示 _MENU_ 项结果",
            "sZeroRecords": "没有匹配结果",
            //"sInfo": "共 _TOTAL_ 项",
            "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
            //"sInfoEmpty": "共 0 项",
            "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
            "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
            "sInfoPostFix": "",
            "sSearch": "搜索:",
            "sUrl": "",
            "sEmptyTable": "表中数据为空",
            "sLoadingRecords": "载入中...",
            "sInfoThousands": ",",

            "oAria": {
                "sSortAscending": ": 以升序排列此列",
                "sSortDescending": ": 以降序排列此列"
            }
        },
        columns: [
            {
                "className": "testId",
                "data": 'testId'
            },{
                "className": "cdgResults",
                "data": 'cdgResults'
            },{
                "className": "ecgTag",
                "data": 'ecgTag'
            }]
    });
    $(function () {
        $("#navigationDiv").hide();
        $("#navigationButton").click(function () {
            $("#navigationDiv").show();
        })
    })
    /**
     * 搜索事件，根据复选框筛选病人信息。
     * @param  {Object}
     * @return {[type]}
     */
    $("#search").on("click", function() {
        //点击按钮后鼠标变等待样式
        $(this).css("cursor", "wait");
        var cdg_Index1=$("#less").val();
        if(cdg_Index1==="")
            cdg_Index1=0;
        var cdg_Index2= $("#more").val();
        if(cdg_Index2==="")
            cdg_Index2=0;
        //获取筛选条件值
        var params = {
            "type_tag":(document.getElementById("ar").checked ? 0 :(document.getElementById("im").checked ? 1 : 5)),
            "admissionnumber": $("#admissionnumber").val(),
            "name": $("#name").val(),
            "sex": (document.getElementById("male").checked ? "男" : (document.getElementById("female").checked ? "女" : null)),
            "ecg_result":(document.getElementById("ecgNormal").checked ? 0 : (document.getElementById("ecgUnusual").checked ? 1 : (document.getElementById("ecgBigUnusual").checked ? 2 : 5))),
            "imaging_result":(document.getElementById("radiographyNothing").checked ? 0 : (document.getElementById("radiographyNormal").checked ? 1 : (document.getElementById("radiographyStricture").checked ? 2 : 5))),
            "cdg_result":(document.getElementById("positive").checked ? 2 : (document.getElementById("probablePositive").checked ? 1 : (document.getElementById("negative").checked ? 0 : 5))),
            "cdg_Index1":cdg_Index1,
            "cdg_Index2":cdg_Index2,
        };
        /**
         * 异步获取病人信息，并填充表格。调用的API是PatientsInfoResources类中的queryPatientsInfo函数
         * @param  {[type]}
         * @return {[type]}
         */
        $.ajax({
            "url": "/CasesSearch/patientsinfo",
            "type": "GET",
            "data": params,
            "dataType": "json",
            "success": function(data) {
                //获取数据成功后恢复鼠标样式
                $("#search").css("cursor", "pointer");

                var patientsDataTable = $("#patientsData").DataTable();
                //先清空表格再填充。
                patientsDataTable.clear();
                for (var i = 0, len = data.length; i < len; i++) {"patientsData"
                    patientsDataTable.row.add(data[i]);
                }
                patientsDataTable.draw();
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
    //重置筛选条件
    $("#reset").on("click", function() {
        document.getElementById("im").checked = false;
        document.getElementById("ar").checked = false;
        $("#admissionnumber").val("");
        $("#name").val("");
        document.getElementById("male").checked = false;
        document.getElementById("female").checked = false;
        document.getElementById("ecgNormal").checked = false;
        document.getElementById("ecgUnusual").checked = false;
        document.getElementById("ecgBigUnusual").checked = false;
        document.getElementById("radiographyNormal").checked = false;
        document.getElementById("radiographyStricture").checked = false;
        document.getElementById("radiographyNothing").checked = false;
        document.getElementById("negative").checked = false;
        document.getElementById("positive").checked = false;
        document.getElementById("probablePositive").checked = false;
        $("#less").val("") ;
        $("#more").val("");
    });

    /**
     * diagnosis查看测试号与CDG诊断结果的事件，cases/abnum查看病历信息,点击表格每一行触发函数。
     */
    $("#patientsData tbody").on("click", "td.content", function() {
        //点击按钮后鼠标变等待样式
        $(this).css("cursor", "wait");
        var patientsDataTable = $("#patientsData").DataTable();
        var tr = $(this).closest("tr");
        var row = patientsDataTable.row(tr);
        //var cdgInfo = {};
        $.ajax({
            "url": "/CasesSearch/diagnosis",
            "type": "GET",
            //关闭异步，确保获得数据后才能执行子表查看。
            "async": true,
            "data": { "admissionnumber": row.data().admissionnumber },
            "dataType": "json",
            "success": function(data) {
                //cdgInfo = data;
                $("#patientsData tbody").css("cursor", "pointer");
                var patientsSubTable = $('#patientsSub').DataTable();
                patientsSubTable.clear();
                for (var i = 0, len = data.length; i < len; i++)
                {"patientsSub"
                    patientsSubTable.row.add(data[i]);
                }
                patientsSubTable.draw();
            },
            "error": function(jqXHR, exception) {
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
            "url": "/CasesSearch/cases",
            "type": "GET",
            "data": { "admissionnumber": row.data().admissionnumber },
            "dataType": "json",
            "success": function(data) {
                //填充病历显示窗口，并显示
                if(data==null)
                {
                    $(".content").css("cursor", "default");
                    $("#caseTitle").text(row.data().name + "的病历");
                    $("#ecgTag").text("无");
                    $("#reportTag").text("无");
                    $("#complaints").text("无");
                    $("#diagnose_result").text("无");
                    $("#nationality").text("无");
                    $("#birPlace").text("无");
                    $("#smoke").text("无");
                    $("#drinkTag").text("无");
                    $("#genetic").text("无");
                    $("#drug").text("无");
                    $("#heartRate").text("无");
                    $("#ultrasonic").text("无");
                    $("#lv").text("无");
                    $("#triglyceride").text("无");
                    $("#bloodSugar").text("无");
                    $("#creatinine").text("无");
                    $("#ldlc").text("无");
                    $("#hdlc").text("无");
                    $("#hscp").text("无");
                    $("#lipoprotein").text("无");
                    $("#glycated").text("无");
                    $("#bmi").text("无");
                    $("#ffr").text("无");
                    $("#bnp").text("无");
                    $("#thyroid").text("无");
                    $("#bloodPotassium").text("无");
                    $("#bloodSodium").text("无");
                    $("#blood_pressure_high").text("无");
                    $("#blood_pressure_low").text("无");
                    $("#patientCase").modal("show");
                }
                else {
                    $(".content").css("cursor", "default");
                    $("#caseTitle").text(row.data().name + "的病历");
                    $("#reportTag").text(data.reportTag);
                    $("#ctTag").text(data.ctTag);
                    $("#complaints").text(data.complaints);
                    $("#diagnosisCase").text(data.diagnose_result);
                    $("#nationality").text(data.nationality);
                    $("#birPlace").text(data.bir_place);
                    $("#smoke").text(data.smokeTag);
                    $("#drinkTag").text(data.drinkTag);
                    $("#genetic").text(data.genetic_history);
                    $("#drug").text(data.drug_history);
                    $("#heartRate").text(data.heart_rate + "次/分钟  " + "(60～100 次/分钟)");
                    $("#ultrasonic").text(data.ultrasonicef + "%  " + "(50～70 %)");
                    $("#lv").text(data.lv + "mm  " + "(男:37～55 mm 女:37～50 mm)");
                    $("#triglyceride").text(data.triglyceride + "mmol/L  " +  "(<1.71 mmol/L)");
                    $("#bloodSugar").text(data.blood_sugur + "mmol/L  " + "(3.9～6.1 mmol/L)");
                    $("#creatinine").text(data.creatinine + "nmol/L  " + "(男:54～106 umol/L 女:44～97 umol/L)");
                    $("#ldlc").text(data.ldlc + "mmol/L  " + "(2.58～3.36 mmol/L)");
                    $("#hdlc").text(data.hdlc + "mmol/L  " + "(男:1.04～1.29 mmol/L 女:1.3～1.54 mmol/L)");
                    $("#hscp").text(data.hscp + "mg/L  " + "(1.0～3.0 mg/L)");
                    $("#lipoprotein").text(data.lipoprotein_a + "mmol/L  " + "(300～500 mmol/L)");
                    $("#glycated").text(data.glycated_hemoglobin + "g/L  " + "(4.5～6.4 g/L)");
                    $("#bmi").text(data.bmi + "(18.5～23.9)");
                    $("#ffr").text(data.ffr + "(0.75～0.8)");
                    $("#bnp").text(data.bnp + "fmol/mL  " + "(0.9±0.07fmol/ml)");
                    $("#thyroid").text(data.thyroid_function + "(T4:65～155 nmol/L FT4:10.3～31.0 pmol/L T3:1.8～2.9 nmol/L FT3:2.0～6.6 pmol/L TSH:0.3～5.0 mU/L)");
                    $("#bloodPotassium").text(data.blood_potassium + "mmol/L  " + "(3.5～5.3 mmol/L)");
                    $("#bloodSodium").text(data.blood_sodium + "mmol/L  " + "(135～145 mmol/L)");
                    $("#blood_pressure_high").text(data.blood_pressure_high + "mmHg  " + "(90～119 mmHg)");
                    $("#blood_pressure_low").text(data.blood_pressure_low + "mmHg  " + "(60～79 mmHg)");
                    $("#patientCase").modal("show");
                }

            },
            "error": function(jqXHR, exception) {
                $(".cdgInfo").css("cursor", "default");
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
    $("#patientsSub tbody").on("click", "td.testId", function() {
        $(this).css("cursor", "wait");
        var patientsSubTable = $('#patientsSub').DataTable();
        //var cdgInfoTable = $(".cdgInfo").DataTable();
        //获取鼠标点击所在行的数据
        var tr = $(this).closest("tr");
        var cdgInfoTableRow = patientsSubTable.row(tr);
        var cdgResult=cdgInfoTableRow.data().cdgResults;
        var cdgInfoTableColumns = patientsSubTable.column(1);
        if ((cdgResult != "未计算") && (cdgResult != "计算中")){
            $.ajax({
                "url": "/CasesSearch/getEcg",
                "type": "GET",
                "data": { "testId": cdgInfoTableRow.data().testId}, //tr.children()[0].textContent
                "dataType": "json",
                "success": function(data) {
                    var ecg12 = ['I', 'II', 'III', 'aVR', 'aVL', 'aVF', 'V1', 'V2', 'V3', 'V4', 'V5', 'V6'];
                    var canvas = document.getElementById("gridCan");
                    //window.onresize = function() {
                    //$(window).resize(function () {
                    resizeCanvas();
                    drawCanvas(canvas);
                    for(var i = 1; i <= 12; i++){
                        drawElectrocardiogram("ECGgrid", data["ecgdata" + i]);
                        fillMsg("ECGgrid", ecg12[i - 1], data["ecgdata" + i]);
                    }
                    window.addEventListener('resize', function () {
                        resizeCanvas();
                        drawCanvas(canvas);
                        for(var i = 1; i <= 12; i++){
                            drawElectrocardiogram("ECGgrid", data["ecgdata" + i]);
                            fillMsg("ECGgrid", ecg12[i - 1], data["ecgdata" + i]);
                        }
                    });
                    //})
                    //};
                }
            });
            $.ajax({
                "url": "/CasesSearch/getCdg",
                "type": "GET",
                "data": { "testId": cdgInfoTableRow.data().testId },
                "dataType": "json",
                "success": function(data) {
                    $("#patientsSub").css("cursor", "default");
                    var cdgData = JSON.parse(data.cdg_data);//cdg_data本身就是一个数组
                    var x = cdgData[0];
                    var y = cdgData[1];
                    var z = cdgData[2];
                    var datacdg = [];
                    for (var i = 0; i < x.length; i += 1) {
                        datacdg.push([x[i], y[i], z[i]]);
                    }
                    initEchart();
                    //echarts画图
                    var chart = echarts.init(document.getElementById('graphCDG'));
                    chart.setOption({
                        title: {
                            show : true,
                            text: '',
                            textStyle:{
                                fontsize: 6,
                                align:'center'
                            },
                            left: 'right'
                        },
                        tooltip: {},
                        backgroundColor: '#EAEAEA',
                        visualMap: {
                            show: false,
                            dimension: 2,
                            min: 0,
                            max: 30,
                            inRange: {
                                color: ['red'],
                                //symbolSize:[50,50]
                            }
                        },
                        xAxis3D: {
                            type: 'value',
                            splitLine: {show: false},//去掉背景的网格线
                            name:' '
                        },
                        yAxis3D: {
                            type: 'value',
                            splitLine: {show: false},
                            name: ' '
                        },
                        zAxis3D: {
                            type: 'value',
                            splitLine: {show: false},
                            name: ' '
                        },

                        grid3D: {
                            show: true,
                            splitLine:{
                                show: true
                            },
                            axisTick:{
                                show: true
                            },
                            axisLine:{
                                lineStyle:{
                                    color: 'rbg(255,255,255)'
                                }
                            },
                            axisLabel:{
                                show: false
                            },

                            viewControl: {
                                projection: 'orthographic'
                            }
                        },
                        series: [{
                            type: 'line3D',
                            data: datacdg,
                            lineStyle: {
                                width: 1
                            }
                        }],


                    });


                    //$("#CDGData").modal("show");
                    //更新echart数据点
                    var dataPoint = [];//数据点
                    var point = [];
                    var cdgValue = (-1)*(25*data.thi-13850*data.shi+1246)/Math.sqrt(25^2+13850^2)*10/((-1)*(25*0-13850*5740/13850+1246)/Math.sqrt(25^2+13850^2));
                    point.push(data.thi);
                    point.push(data.shi);
                    dataPoint.push(point);
                    myEchartObj.updateDataPoint(dataPoint);
                    var cdg_result;
                    if(data.result_id==0)
                    {
                        cdg_result="阴性";
                    }
                    else {
                        if(data.result_id==1)
                        {
                            cdg_result="可疑阴性";
                        }
                        else {
                            cdg_result="阳性";
                        }
                    }

                    myEchartObj.updateTitle(cdg_result+"；CDG："+cdgValue.toFixed(4)+"正常<-0.5");
                    window.addEventListener('resize', function () {
                        resizeCDG();
                        chart.resize();
                        myEchartObj.echartObj.resize();
                    });
                }
            });
            //获取cdg数据，调用的是CDGResources类中的findByTestId函数

        }else if (cdgResult == "未计算"){
            //开始计算CDG
            //alert("开始计算");
            patientsSubTable.cell( cdgInfoTableRow, cdgInfoTableColumns ).data("计算中");
            var cdgData = null;
            //获取cdg数据，调用的是CDGResources类中的findByTestId函数
            $.ajax({
                "url": "/CasesSearch/getCdg",
                "type": "GET",
                "data": { "testId": cdgInfoTableRow.data().testId },
                "async": true,
                "dataType": "json",
                "success": function(data) {

                    cdgData = data.cdg_data;
                    //alert(cdgData);
                    //调用的API为ECGResoureces类中的hadoop函数
                    $.ajax({
                        "url": "/CasesSearch/hadoop",
                        "type": "GET",
                        "data": { "threadId": cdgData },
                        "async": true,
                        "dataType": "json",
                        "success": function(data) {

                            //tr.context.innerHTML = data.calResult;
                            patientsSubTable.cell( cdgInfoTableRow, cdgInfoTableColumns ).data(data.calResult);
                            //alert(data.cdgResults);
                        },
                        "error": function(jqXHR, exception) {

                            patientsSubTable.cell( cdgInfoTableRow, cdgInfoTableColumns ).data("未计算1");
                            alert('计算失败.');
                        }
                    });
                }
            });
        }else{
            alert("正在计算，请稍等！");//什么也不做，就警告说正在计算
        }


    });
    $("#patientsSub tbody").on("click", "td.cdgResults", function() {
        $(this).css("cursor", "wait");
        var patientsSubTable = $('#patientsSub').DataTable();
        //var cdgInfoTable = $(".cdgInfo").DataTable();

        //获取鼠标点击所在行的数据
        var tr = $(this).closest("tr");
        var cdgInfoTableRow = patientsSubTable.row(tr);


        var patientsDataTable = $("#patientsData").DataTable();
        var tr = $(this).closest("tr");
        var row = patientsDataTable.row(tr);
        //获取cdg数据，调用的是CDGResources类中的findByTestId函数
        $.ajax({
            "url": "api/cdg",
            "type": "GET",
            "data": { "testId": cdgInfoTableRow.data().testId },
            "dataType": "json",
            "success": function(data) {
                var cdgData = JSON.parse(data.cdg_data);
                //FileSaver.js插件使用方法，网上有介绍
                var blob = new Blob([cdgData[0], '\r\n', cdgData[1], '\r\n', cdgData[2], '\r\n'], { type: "text/plain;charset=utf-8" });
                var filename = 'CDG_' + row.data().admissionnumber + '_' + cdgInfoTableRow.data().testId;
                saveAs(blob, filename);
            },
            "error": function(jqXHR, exception) {

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
    $("#patientsSub tbody").on("click", "td.ecgTag", function() {
        $(this).css("cursor", "wait");
        var patientsSubTable = $('#patientsSub').DataTable();
        //var cdgInfoTable = $(".cdgInfo").DataTable();
        //获取鼠标点击所在行的数据
        var tr = $(this).closest("tr");
        var cdgInfoTableRow = patientsSubTable.row(tr);

        var patientsDataTable = $("#patientsData").DataTable();
        var tr = $(this).closest("tr");
        var row = patientsDataTable.row(tr);
        //获取ecg数据，调用的是ECGResources类中的findByTestId函数
        $.ajax({
            "url": "api/ecg/download",
            "type": "GET",
            "data": { "testId": cdgInfoTableRow.data().testId },
            "dataType": "json",
            "success": function(data) {
                var blob = new Blob([data.ecg_data], { type: "text/plain;charset=utf-8" });
                var filename = 'ECG_' + row.data().admissionnumber + '_' + cdgInfoTableRow.data().testId;
                saveAs(blob, filename);
            },
            "error": function(jqXHR, exception) {

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

    $("#distribution").on("click", function () {
        $(this).css("cursor", "wait");
        $.ajax({
            "url": "api/analyse",
            "type": "GET",
            "dataType": "json",
            "success": function (data) {
                $("#distribution").css("cursor", "default");
                //var dataList = JSON.stringify(data);//不可直接用JSON字符串
                //console.log(dataList);
                var dataList = new Array();
                for(var i = 0; i < data.length; i++){
                    dataList[i] = new Array();
                    for(var j = 0; j < 3; j++){
                        dataList[i][j] = i;
                    }
                }
                for(var i = 0; i < data.length; i++){
                    dataList[i][0] = data[i].age;
                    dataList[i][2] = data[i].sex;
                    /*if(data[i].sex == '女'){
                        dataList[i][1] = 0;
                    }
                    else{
                        dataList[i][1] = 1;
                    }*/
                    //dataList[i][2] = data[i].smoke_level;
                    //dataList[i][3] = data[i].drink_level;
                    if(data[i].cdgTag == 3){
                        dataList[i][1] = 2;
                    }
                    else {
                        dataList[i][1] = data[i].cdgTag;
                    }
                    dataList[i][3] = data[i].ecgTag;
                }
                var dataList1 = new Array();
                for(var i = 0; i < data.length; i++){
                    dataList1[i] = new Array();
                    for(var j = 0; j < 3; j++){
                        dataList1[i][j] = i;
                    }
                }
                for(var i = 0; i < data.length; i++){
                    dataList1[i][0] = data[i].smoke_level;
                    if(data[i].cdgTag == 3){
                        dataList1[i][1] = 2;
                    }
                    else {
                        dataList1[i][1] = data[i].cdgTag;
                    }
                    dataList1[i][2] = data[i].sex;
                    dataList1[i][3] = data[i].ecgTag;
                }

                //var dataList1 = JSON.stringify(dataList);
                //console.log(dataList);
                //console.log(dataList1);
                var sex1 = new Array();
                for(var m = 0; m < 2; m++){
                    sex1[m] = new Array();
                }
                for(var i = 0; i < data.length; i++){
                    if(dataList[i][2] == '女'){
                        sex1[0].push(dataList[i]);
                    }
                    else{
                        sex1[1].push(dataList[i]);
                    }
                }
                sex1 = JSON.stringify(sex1);//将数据按性别分
                console.log(sex1);
                var sex2 = new Array();
                for(var m = 0; m < 2; m++){
                    sex2[m] = new Array();
                }
                for(var i = 0; i < data.length; i++){
                    if(dataList1[i][2] == '女'){
                        sex2[0].push(dataList1[i]);
                    }
                    else{
                        sex2[1].push(dataList1[i]);
                    }
                }
                sex2 = JSON.stringify(sex2);
                //console.log(sex2);
                var ecgTag = [];
                var cdgTag = [];
                for(var m = 0; m < 3; m++){
                    ecgTag[m] = new Array();
                }
                for(var i = 0; i < data.length; i++){
                    if(dataList[i][4] == '正常'){
                        ecgTag[0].push(dataList[i]);
                    }
                    else if(dataList[i][4] == '明显异常'){
                        ecgTag[1].push(dataList[i]);
                    }
                    else
                        ecgTag[2].push(dataList[i])
                }
                ecgTag = JSON.stringify(ecgTag);//将数据按ecgTag分
                //console.log(ecgTag);

                for(var m = 0; m < 4; m++){
                    cdgTag[m] = new Array();
                }
                for(var i = 0; i < data.length; i++){
                    if(dataList[i][5] == '阴性'){
                        cdgTag[0].push(dataList[i]);
                    }
                    else if(dataList[i][5] == '可疑阳性'){
                        cdgTag[1].push(dataList[i]);
                    }
                    else if(dataList[i][5] == '阳性'){
                        cdgTag[2].push(dataList[i]);
                    }
                    else{
                        cdgTag[3].push(dataList[i]);
                    }
                }
                cdgTag = JSON.stringify(cdgTag);//将数据按cdgTag分

                var chartDistr = echarts.init(document.getElementById('analyse'));
                chartDistr.setOption({
                    title: {},
                    backgroundColor: '#EAEAEA',
                    legend: {
                        right: 10,
                        data: ['女', '男']
                    },
                    xAxis: {
                        splitLine: {
                            lineStyle: {
                                type: 'dashed'
                            }
                        }
                    },
                    yAxis: {
                        splitLine: {
                            lineStyle: {
                                type: 'dashed'
                            }
                        },
                        scale: true
                    },
                    series: [
                        {
                            name: '女',
                            data: sex[0],
                            type: 'scatter'

                        },
                        {
                            name: '男',
                            data: sex[1],
                            type: 'scatter'

                        },
                        {
                            name: '异常',
                            data: ecgTag[2],
                            type: 'scatter'
                        }/*,
                        {
                            name: '阴性',
                            data: cdgTag[0],
                            type: 'scatter'
                        },
                        {
                            name: '可疑阳性',
                            data: cdgTag[1],
                            type: 'scatter'
                        },
                        {
                            name: '阳性',
                            data: cdgTag[2],
                            type: 'scatter'
                        },
                        {
                            name: '未计算',
                            data: cdgTag[3],
                            type: 'scatter'
                        }*/

                    ]
                });
            }
        })
    })

});
        /**
         * 获取cdg数据，点击第二个表格每一行触发函数。
         */


function initEchart(){
    myEchartObj = new Polyline();
    myEchartObj.init('cdgIndex');
}

//生成绘制cdg指标图需要的属性

function Polyline() {
    this._domdivId;
}

Polyline.prototype = {

    constructor : Polyline,
    init : function(domdivId) {
        this._domdivId = domdivId;
        this.dataPoint = [
            [30,0.3]
        ];
        this.maxXline = [
            [0,0.4149],
            [180,0.4149]
        ];
        this.slashDownLine = [
            [9.0146,0.0900],
            [180,0.3987]
        ];
        this.slashDashLine = [
            [0,0.0900],
            [180,0.4149]
        ];
        this.slashMediumLine =[
            [171.0367,0.4149],
            [180,0.4149]
        ];
        this.slashUpLine = [
            [0,0.1062],
            [171.0367,0.4149]
        ];
        this.seriesObj = {

        }

        this.initEchart();
    },

    initEchart : function() {
        this.echartObj = echarts.init(document.getElementById(this._domdivId));
        this.echartOpt = {
            title: {
                show : true,
                text: '',
                textStyle:{
                    fontsize: 5,
                    align:'center'
                },
                left: 'center'
            },
            tooltip: {//移动过去的虚线设置
                trigger: 'axis',//坐标轴触发，像散点图这种就要用item，数据项图形触发
                axisPointer: {
                    type: 'cross',
                    label: {
                        backgroundColor: '#EAEAEA'
                    }
                }
            },
            xAxis: [],
            yAxis: [],
            series: []
        }
        this.configxAxis();
        this.configyAxis();
        this.configSeries();

        this.echartObj.setOption(this.echartOpt);
    },

    configxAxis : function(){
        this.echartOpt.xAxis.push({
            type: 'value',
            boundaryGap: false,
            min:0,
            max:180,
            //interval:20,
            splitLine: {
                show:false
            },
            axisLabel: { //x轴字体设置
                textStyle:{
                    color: '#666',
                    fontSize:10 // 让字体变大
                }
            },
            axisLine: {//x轴设置
                lineStyle: {
                    color: '#666',
                    width: 1
                }
            }
        })
    },

    configyAxis : function(){
        this.echartOpt.yAxis.push({
            type: 'value',
            min:0.0900,
            max:0.4149,
            //interval:0.0812,
            splitLine: {
                show:false
            },
            axisLabel:{ //y轴字体设置
                textStyle:{
                    color: '#666',
                    fontSize:10 // 让字体变大
                }
            },
            axisLine: {//y轴设置
                lineStyle: {
                    color: '#666',
                    width: 1
                }
            },
        })
    },

    configSeries : function(){
        this.setDataPoint();
        this.setSlashLine(this.maxXline,"#8B7765");
        this.setSlashLine(this.slashUpLine,"#CDCDB4");
        this.setSlashLine(this.slashMediumLine,"#CDCDB4");
        this.setSlashLine(this.slashDownLine,"#607B8B");
        this.setDashLine(this.slashDashLine);
    },

    setDataPoint : function(){
        this.echartOpt.series.push({
            type: 'scatter',
            symbolSize: 16,
            itemStyle: {
                normal: {
                    color:'#000',//折点颜色
                }
            },
            data: this.dataPoint
        });
    },

    updateDataPoint : function(dataPoint){
        this.echartOpt.series[0].data = dataPoint;
        this.echartObj.setOption(this.echartOpt);
    },
    updateTitle : function(title) {
        this.echartOpt.title.text = title;
        this.echartObj.setOption(this.echartOpt);
    },
    updateSaveName : function(saveName){
        this.echartOpt.toolbox.feature.saveAsImage.name = saveName;
        this.echartObj.setOption(this.echartOpt);
    },
    setSlashLine : function(data,areaColor){
        this.echartOpt.series.push({
            type: 'line',
            symbolSize: 0,
            itemStyle: {
                normal: {
                    color:'#000',//折点颜色
                    lineStyle : {
                        color : '#000',
                        width: 0,
                    },
                    areaStyle: {
                        color : areaColor
                    },
                }
            },
            data: data
        })
    },

    setDashLine : function(data){
        this.echartOpt.series.push({
            type: 'line',
            symbolSize: 0,
            itemStyle: {
                normal: {
                    color:'#000',//折点颜色
                    lineStyle : {
                        color : '#000',
                        type  : 'dashed',
                        width :  0.8
                    },
                }
            },
            data: data
        })
    }
}


//调整canvas的长宽
function resizeCanvas() {
    $("#gridCan").attr("width", $("#ECG1").width());
    $("#gridCan").attr("height", $("#ECG1").height());
}
function resizeCDG() {
    $("#graphCDG").attr("width", $("#CDGContainer").width());
    $("#graphCDG").attr("height", $("#CDGContainer").width());
    $("#cdgIndex").attr("width", $("#indexContainer").width());
    $("#cdgIndex").attr("height", $("#indexContainer").width());
}
function resizeAna() {
    $("#analyse").attr("width", $(document.body).width() * 0.8);
    $("#analyse").attr("height", $(document.body).height() * 0.8);
}
//画ECG图
//画网格线
function drawCanvas(canvas) {
    //resizeCanvas();

    if (canvas.getContext) {
        var context = canvas.getContext('2d'); //检测getContext是否存在
        context.fillStyle = "rgba(255,255,255,1)";
        var initSetx = 50;
        var initSety = 10;
        var width = canvas.width;
        var height = canvas.height;
        context.strokeStyle = "#ffbebe";//网格的颜色
        context.fillRect(initSetx, initSety, width, height - 50);//画布填充范围
        var d = 5;
        var ordinateN = width / d;//大格子的竖线个数
        var abscissaN = (height - 50) / d;//大格子的横线个数
        var startPoint = {x: 50, y: 10};//起点坐标

        //绘制竖线
        for (var i = 0; i <= ordinateN; i++) {
            //每5格为一个大格，线宽为2,中间5个小格线宽为1
            if (i % 5 == 0) {
                context.lineWidth = 2;
            }
            else {
                context.lineWidth = 1;
            }
            context.beginPath();//开始路径
            context.moveTo(startPoint.x, startPoint.y);
            context.lineTo(startPoint.x, startPoint.y + height - 50);
            startPoint.x += d;
            context.stroke();
        }
        //绘制横线
        var startPoint = {x: 50, y: 10};
        for (var i = 0; i <= abscissaN; i++) {
            if (i % 5 == 0) {
                context.lineWidth = 2;
            }
            else {
                context.lineWidth = 1;
            }
            context.beginPath();//开始路径
            context.moveTo(startPoint.x, startPoint.y);
            context.lineTo(startPoint.x + width, startPoint.y);
            startPoint.y += d;
            context.stroke();
        }
        context.closePath();
    }
}
//绘制每个导联的名字
function fillMsg(domClassName, ecgLead, data) {
    var canvas = $("."+domClassName).get(0);
    var ctx = canvas.getContext("2d");
    ctx.font = "12px serif";
    ctx.textBaseline = "hanging";
    ctx.strokeText(ecgLead, data[0].x + 50, data[0].y - 80);//目前的大小只能适应平板
    var basicMsg = " 横向：0.04S/格 纵向：0.1mV/格";
    ctx.fillStyle = "black";
    var textPosition1 = (canvas.width - ctx.measureText(basicMsg).width);
    ctx.fillText(basicMsg, textPosition1, 10);
}
//绘制12导联图
function drawElectrocardiogram(domClassName, data) {
    var canvas = $("."+domClassName).get(0);
    var width = canvas.width;
    var ctx = canvas.getContext("2d");
	ctx.lineWidth = 1;
    ctx.beginPath();
    ctx.strokeStyle = "#000000";//ECG线条的颜色
    ctx.moveTo(data[0].x, data[0].y - 60);
    for(var i = 2; data[i+1].x < width; i++){
    //for(var i = 1; i < 3000; i++){

        ctx.lineTo(data[i+1].x, data[i+1].y - 60);

    }
    ctx.stroke();
    ctx.closePath();
}

function Line(domClassName, width, height, imageObj){
    this.canvas = null;
    this.ctx = null;
    this.height = 0;
    this.width = 0;
    this.init(domClassName, width, height, imageObj);
}

//年龄、ecg结果、cdg结果的分布

/*function cutST(dataIn, sfreq) {
    var WW, p;
    switch (sfreq) {
        case 500:
            WW = 100;
            p = 8;
            break;
        case 1000:
            WW = 200;
            p = 16;
    }
    var X = dataIn;
    var thresd = 0.65;
    var len = X.length;
    var Xcut = X.slice(round(len/4),round(3*len/4));
    var max_h = Math.max(null, Xcut);
    var pre_g = X >= (thresd * max_h);
    var left = find(diff([0 pre_g]))
}*/

