$("#uploadfile").click(function () {
    $("#fileTypeError").html('');
    var fileName = $('#file_upload').val();　　　　　　　　　　　　　　　　　　//获得文件名称
    var fileType = fileName.substr(fileName.length-5,fileName.length);　　//截取文件类型,如(.xls)
    if (fileType=='.warc'){
        $("#fileTypeError").html('');
        // $("#uploadfile").removeClass("disabled");
        $.ajax({
            url: 'http://localhost:8080/uploadfile',　　　　　　　　　　//上传地址
            type: 'POST',
            cache: false,
            data: new FormData($('#uploadForm')[0]),　　　　　　　　　　　　　//表单数据
            processData: false,
            contentType: false,
            success:function(data){
                if(data=='fail to upload'){
                    $("#fileTypeError").html('上传失败');　　//根据后端返回值,回显错误信息
                }
                window.location.reload();
            }
        });
    }else{
        $("#fileTypeError").html('*上传文件类型错误,支持类型: .warc');
        // $("#uploadfile").addClass("disabled");
    }
})


