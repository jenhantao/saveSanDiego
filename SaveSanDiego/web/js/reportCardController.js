$(document).ready(function() {
    var areaMap = {"Pendleton": "District5", "Fallbrook": "District5", "Pauma": "District5", "Palomar_Julian": "District5", "Valley_Center": "District5", "Vista": "District5", "Oceanside": "District5", "Carlsbad": "District5", "San_Marcos": "District5", "San_Dieguito": "District3", "Escondido": "District3", "North_SanDiego": "District3", "Del_Mar_Mira_Mesa": "District3", "University": "District3", "Miramar": "District3", "Elliott-Navajo": "District3", "Poway": "District2", "Ramona": "District2", "Laguna-Pine_Valley": "District2", "Mountain_Empire": "District2", "Lakeside": "District2", "Alpine": "District2", "Harbison_Crest": "District2", "Santee": "District2", "El_Cajon": "District2", "La_Mesa": "District2", "Spring_Valley": "District2", "Lemon_Grove": "District2", "Coastal": "District4", "Kearny_Mesa": "District4", "Peninsula": "District4", "Mid_City": "District4", "Southeastern_San_Diego": "District4", "Coronado": "District1", "Sweetwater": "District1", "Chula_Vista": "District1", "South_Bay": "District1", "National_City": "District1", "Central_San_Diego": "District1"};


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
    var location = getCookie("location");
    var timeCourseLocation = "data/figs/" + location + "_timecourse.png"
    $('#timeCourse').attr("src", timeCourseLocation)
    var lives = "data/figs/Lives_Saved_by_District_Normalized_" + location + ".png"
    $('#lives').attr("src", lives)
    var district = areaMap[location]
    $.get("ExchangeServlet", {"command": "getAreaEmail", "location": location, "district": district}, function(data) {
//        $('#representativeName').text(data["representative"]);
        $('#text').text(data["message"]);
    });

});