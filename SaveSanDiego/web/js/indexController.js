$(document).ready(function() {
    //update the date
    var months = {0: "January",
        1: "February",
        2: "March",
        3: "April",
        4: "May",
        5: "June",
        6: "July",
        7: "August",
        8: "September",
        9: "October",
        10: "November",
        11: "December"}
    var d = new Date();
    var day = d.getDate();
    var month = months[d.getMonth()].toUpperCase();
    var year = d.getFullYear();
    $('#date').text(month + ' ' + day + ', ' + year);
    //event handler for modal dialog

    //event handler for send button
    $('#sendButton').click(function() {
        //do some basic validation

        //create the text object
        var message = $('#messagText').val();
        //send
        $.get("ExchangeServlet", {"command": "sendMail", "message": message}, function() {
            //modal confirmation
            alert("sent mail");
        });
    });



    //cookie functions
    function setCookie(c_name, value, exdays) {
        var exdate = new Date();
        exdate.setDate(exdate.getDate() + exdays);
        var c_value = escape(value) + ((exdays === null) ? "" : "; expires=" + exdate.toUTCString());
        document.cookie = c_name + "=" + c_value;
    }

    function getCookie(c_name) {
        var c_value = document.cookie;
        var c_start = c_value.indexOf(" " + c_name + "=");
        if (c_start === -1) {
            c_start = c_value.indexOf(c_name + "=");
        }
        if (c_start === -1) {
            c_value = null;
        }
        else {
            c_start = c_value.indexOf("=", c_start) + 1;
            var c_end = c_value.indexOf(";", c_start);
            if (c_end === -1) {
                c_end = c_value.length;
            }
            c_value = unescape(c_value.substring(c_start, c_end));
        }
        return c_value;
    }
    function deleteCookie(key) {
// Delete a cookie by setting the date of expiry to yesterday
        date = new Date();
        date.setDate(date.getDate() - 1);
        document.cookie = escape(key) + '=;expires=' + date;
    }

});