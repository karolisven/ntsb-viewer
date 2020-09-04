$(document).ready(function() {
    $.ajax({
        url: "http://localhost:8080/greeting",
        success: function (data, status, jqxhr) {// success callback function
            $('.greeting-id').append(data.id);
            $('.greeting-content').append(data.content);
            console.log(jqxhr);
        },
        error: function(xhr, errmsg, err){
            console.log("error");
        }
    });
});
