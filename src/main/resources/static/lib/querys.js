function callRestService(url) {
    var result;

    $.ajax({
        url: url,
        async: false,
        success: function (data) {// success callback function
            result = data;
        },
        error: function(xhr, errmsg, err){
            result = "error";
        }
    });
    return result;
}