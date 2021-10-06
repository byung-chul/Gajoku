var express = require('express');
var database = require('../database/mysql_connection');
var router = express.Router();
var connection = database.getDatabase();
var googleMapsClient = require('@google/maps').createClient({
    key: 'AIzaSyDULFSjnVfeYArthagkn-lIeR_vXrR5D6w'
});

router.get('/', function(req, res, next) {
    const r_admin = req.query.r_admin;
    const r_password = req.query.r_password;

    const query =
        "SELECT restaurantAdmin, restaurantPassword, restaurantId FROM restaurant WHERE restaurantAdmin = ? AND restaurantPassword = ?";
    database.query(query, [r_admin, r_password])
        .then(function(results) {
            res.end(JSON.stringify(results));
            /*
            if (results.affectedRows===1){
                res.end(true);
            }
            else{
                res.end(false);
            }
            */
        });
});

router.post('/', function(req, res, next) {

    const r_admin = req.body.r_admin;
    const r_password = req.body.r_password;
    const r_type = req.body.r_type;
    const r_name = req.body.r_name;
    const r_number = req.body.r_number;
    const r_address = req.body.r_address;
    const r_detail = req.body.r_detail;
    const r_least = req.body.r_least;

    const query =
        'SELECT restaurantAdmin FROM restaurant WHERE restaurantAdmin=?';
    database.query(query, [r_admin])
        .then(function (results) {
            if (results.length === 0){
                const query =
                    "SELECT restaurantNumber FROM restaurant WHERE restaurantAddress = ?";
                return database.query(query, [r_address]);
            } else{
                res.end("id is duplicated");
            }
            return "duplicated";
        })
        .then(function (results) {
            if (results === "duplicated") {
                return "duplicated";
            } else {
                if (results.length === 0){
                    if (r_number.length !== 0){
                        googleMapsClient.geocode({
                            address: r_address
                        },function (err,response){
                            if (!err){
                                const r_lat = response.json.results[0].geometry.location.lat;
                                const r_lng = response.json.results[0].geometry.location.lng;
                                const query = "INSERT INTO restaurant " +
                                    "(restaurantAdmin, restaurantPassword, restaurantType, restaurantName, restaurantNumber, restaurantAddress, restaurantDetail, restaurantLat, restaurantLng, restaurantLeast) " +
                                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                                return database.query(query, [r_admin, r_password, r_type, r_name, r_number, r_address, r_detail, r_lat, r_lng, r_least]);
                            }
                        });
                    }
                    else {
                        googleMapsClient.geocode({
                            address: r_address
                        },function (err,response){
                            if (!err){
                                const r_lat = response.json.results[0].geometry.location.lat;
                                const r_lng = response.json.results[0].geometry.location.lng;
                                const query = "INSERT INTO restaurant " +
                                    "(restaurantAdmin,restaurantPassword,restaurantType,restaurantName, restaurantAddress, restaurantDetail, restaurantLat, restaurantLng, restaurantLeast) " +
                                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                                return database.query(query, [r_admin, r_password, r_type, r_name, r_address, r_detail, r_lat, r_lng, r_least]);
                            }
                        });

                    }

                } else{
                    res.end("address is duplicated");
                }
            }
            return true;
        })
        .then(function (results) {
            if (results) {
                res.end("success");
            } else {
                res.end("fail");
            }
        })
});

module.exports = router;