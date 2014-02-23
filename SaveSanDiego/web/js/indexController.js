$(document).ready(function() {
    //update the date
    var areaMap = {"Pendleton": "District5", "Fallbrook": "District5", "Pauma": "District5", "PalomarJulian": "District5", "ValleyCenter": "District5", "Vista": "District5", "Oceanside": "District5", "Carlsbad": "District5", "SanMarcos": "District5", "SanDieguito": "District3", "Escondido": "District3", "NorthSanDiego": "District3", "DelMar_Mira Mesa": "District3", "University": "District3", "Miramar": "District3", "Elliott-Navajo": "District3", "Poway": "District2", "Ramona": "District2", "Laguna-Pine Valley": "District2", "MountainEmpire": "District2", "Lakeside": "District2", "Alpine": "District2", "HarbisonCrest": "District2", "Santee": "District2", "ElCajon": "District2", "LaMesa": "District2", "SpringValley": "District2", "LemonGrove": "District2", "Coastal": "District4", "KearnyMesa": "District4", "Peninsula": "District4", "Mid City": "District4", "SoutheasternSanDiego": "District4", "Coronado": "District1", "Sweetwater": "District1", "ChulaVista": "District1", "SouthBay": "District1", "NationalCity": "District1", "CentralSanDiego": "District1"};
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
        11: "December"};
    var d = new Date();
    var day = d.getDate();
    var month = months[d.getMonth()].toUpperCase();
    var year = d.getFullYear();
    $('#date').text(month + ' ' + day + ', ' + year);

    //event handler for send button
    $('#sendButton').click(function() {
        //do some basic validation

        //create the text object
        var name = $('#nameInput').val();
        var message = $('#messageText').val();
        var user = getCookie("user");
        var location = $('#areaSelect').val();
        var district = areaMap[location]
        //send
        var toSend = {"command": "sendMail", "user": user, "name": name, "location": location, "message": message, "district": district};
        $.get("ExchangeServlet", toSend, function() {
            //modal confirmation
        });
    });

    //event handler for name input
    $('#nameInput').keyup(function() {
        $('#signature').text($('#nameInput').val());
    });

    //event handler for location select
    $('#areaSelect').change(function() {
        var area = $(this).val().replace(/ /g, '');
        var district = areaMap[area]

        $.get("ExchangeServlet", {"command": "getAreaEmail", "location": area, "district": district}, function(data) {
            $('#representativeName').text(data["representative"]);
            $('#messageText').val(data["message"]);
        });
    });

    $('#textButton').click(function() {
        var name = $('#nameInput').val();
        var message = $('#messageText').val();
        var user = getCookie("user");
        var location = $('#areaSelect').val().replace(/ /g, '');
        var district = areaMap[location]
        //send
        var toSend = {"command": "getText", "user": user, "name": name, "location": location, "message": message, "district": district};
        $.get("ExchangeServlet", toSend, function(data) {
            window.location = data["filePath"];
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

    function generateID() {
        var toReturn = "";
        var alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for (var i = 0; i < 10; i++)
            toReturn += alphabet.charAt(Math.floor(Math.random() * alphabet.length));
        return toReturn;
    }

    setCookie("user", generateID(), 1000);
    $('#textButton').attr("href", getCookie("user") + ".txt");

});