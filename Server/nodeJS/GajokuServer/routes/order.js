var express = require('express');
var database = require('../database/mysql_connection');
var router = express.Router();
var connection = database.getDatabase();
var googleMapsClient = require('@google/maps').createClient({
    key: 'AIzaSyDULFSjnVfeYArthagkn-lIeR_vXrR5D6w'
});

//가게에서 주문에 대한 list 받아오기
router.get('/', function(req, res, next) {
    const r_id = req.query.r_id;

    const query =
        "SELECT * FROM orderlist WHERE restaurantId = ? ORDER BY timestamp ASC";
    database.query(query, [r_id])
        .then(function(results) {
            res.end(JSON.stringify(results));
        });
});

//사용자가 가게로 주문을 하기(order테이블에 삽입)
router.post('/', function(req, res, next) {
    const c_id = req.body.c_id;
    const r_id = req.body.r_id;
    const o_content = req.body.o_content;
    const o_cost = req.body.o_cost;
    const o_surplus = req.body.o_surplus;
    const c_address = req.body.c_address;

    var c_lat;
    var c_lng;

    googleMapsClient.geocode({
        address: c_address
    },function (err,response){
        if (!err){
            c_lat = response.json.results[0].geometry.location.lat;
            c_lng = response.json.results[0].geometry.location.lng;

            const query =
                "SELECT customerLat, customerLng, orderId FROM orderlist WHERE restaurantId = ? AND orderSurplus > 0 ORDER BY timestamp DESC";
            database.query(query, [r_id])
                .then(function(results) {
                    if (results.length === 0){

                        const query =
                            "INSERT INTO orderlist " +
                            "(customerId, restaurantId, orderContent, orderCost, orderSurplus, customerAddress, customerLat, customerLng) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                        database.query(query, [c_id, r_id, o_content, o_cost, o_surplus, c_address, c_lat, c_lng])
                            .then(function(results) {
                                if(results.affectedRows >= 1){
                                    const query1 =
                                        "SELECT orderId FROM orderlist WHERE customerId = ? AND restaurantId = ?";
                                    database.query(query1, [c_id, r_id])
                                        .then(function(results) {
                                            res.end(JSON.stringify(results));

                                        });
                                }
                                else {
                                    res.end("Fail");
                                }
                            });
                    }
                    else{
                        var w_lat = results[0].customerLat;
                        var w_lng = results[0].customerLng;

                        const query1 =
                            "SELECT orderId, orderSurplus, ( 6371 * acos( cos( radians(?) ) * cos( radians( ? ) ) * cos( radians( ? ) - radians(?) ) + sin( radians(?) ) * sin( radians( ? ) ) ) ) AS distance" +
                            " FROM orderlist WHERE orderSurplus > 0 AND restaurantId = ? HAVING distance < 1";
                        database.query(query1, [c_lat,w_lat, w_lng, c_lng, c_lat, w_lat, r_id])
                            .then(function(results) {

                                if(results.length === 0){
                                    const query =
                                        "INSERT INTO orderlist " +
                                        "(customerId, restaurantId, orderContent, orderCost, orderSurplus, customerAddress, customerLat, customerLng) " +
                                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                                    database.query(query, [c_id, r_id, o_content, o_cost, o_surplus, c_address, c_lat, c_lng])
                                        .then(function(results) {
                                            if(results.affectedRows >= 1){
                                                const query1 =
                                                    "SELECT orderId FROM orderlist WHERE customerId = ? AND restaurantId = ?";
                                                database.query(query1, [c_id, r_id])
                                                    .then(function(results) {
                                                        res.end(JSON.stringify(results));

                                                    });
                                            }
                                            else {
                                                res.end("Fail");
                                            }
                                        });
                                }
                                else{
                                    var matchingId = results[0].orderId;
                                    var surplus = results[0].orderSurplus;
                                    if(o_cost >= surplus){
                                        const query1 =
                                            "INSERT INTO orderlist " +
                                            "(matchingId, customerId, restaurantId, orderContent, orderCost, orderSurplus, customerAddress, customerLat, customerLng) " +
                                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                                        database.query(query1, [matchingId, c_id, r_id, o_content, o_cost, o_surplus, c_address, c_lat, c_lng])
                                            .then(function(results) {
                                                if(results.affectedRows >= 1){
                                                    const query1 =
                                                        "SELECT orderId FROM orderlist WHERE customerId = ? AND restaurantId = ?";
                                                    database.query(query1, [c_id, r_id])
                                                        .then(function(results) {
                                                            res.end(JSON.stringify(results));

                                                        });
                                                }
                                                else {
                                                    res.end("Fail");
                                                }
                                            });
                                    }
                                    else{
                                        const query =
                                            "INSERT INTO orderlist " +
                                            "(customerId, restaurantId, orderContent, orderCost, orderSurplus, customerAddress, customerLat, customerLng) " +
                                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                                        database.query(query, [c_id, r_id, o_content, o_cost, o_surplus, c_address, c_lat, c_lng])
                                            .then(function(results) {
                                                if(results.affectedRows >= 1){
                                                    const query1 =
                                                        "SELECT orderId FROM orderlist WHERE customerId = ? AND restaurantId = ?";
                                                    database.query(query1, [c_id, r_id])
                                                        .then(function(results) {
                                                            res.end(JSON.stringify(results));

                                                        });
                                                }
                                                else {
                                                    res.end("Fail");
                                                }
                                            });
                                    }
                                }
                            });
                    }
                });
        }
    });
});

//사용자가 가게의 다른 대기주문들을 받아옴
router.get('/waiting', function(req, res, next) {
    const r_id = req.query.r_id;
    const u_address = req.query.u_address;
    var u_lat;
    var u_lng;
    var w_lat;
    var w_lng;

    googleMapsClient.geocode({
        address: u_address
    },function (err,response){
        if (!err){
            u_lat = response.json.results[0].geometry.location.lat;
            u_lng = response.json.results[0].geometry.location.lng;

            const query =
                "SELECT customerLat, customerLng FROM orderlist WHERE restaurantId = ? AND orderSurplus > 0 ORDER BY timestamp DESC";
            database.query(query, [r_id])
                .then(function(results) {
                    if (results.length === 0){
                        res.end("nothing");
                    }
                    else{
                        w_lat = results[0].customerLat;
                        w_lng = results[0].customerLng;

                        const query1 =
                            "SELECT orderId, orderSurplus, ( 6371 * acos( cos( radians(?) ) * cos( radians( ? ) ) * cos( radians( ? ) - radians(?) ) + sin( radians(?) ) * sin( radians( ? ) ) ) ) AS distance" +
                            " FROM orderlist WHERE orderSurplus > 0 AND restaurantId = ? HAVING distance < 1";
                        database.query(query1, [u_lat,w_lat, w_lng, u_lng, u_lat, w_lat, r_id])
                            .then(function(results) {
                                res.end(JSON.stringify(results));
                            });
                    }
                });
        }
    });
});

//시간 지나면 삭제
router.post('/delete', function(req, res, next) {

    const c_id = req.body.c_id;
    const r_id = req.body.r_id;

    const query =
        "DELETE FROM orderlist WHERE customerId = ? AND restaurantId = ? ";
    database.query(query, [c_id, r_id])
        .then(function(results) {
            if(results.affectedRows >= 1){
                res.end("Success");
            }
            else {
                res.end("Fail");
            }
        });
});

//가게에서 주문접수를 하면 order 테이블에 잇는 orderType값을 1로 바꿈
router.post('/change', function(req, res, next) {
    const o_id = req.body.o_id;

    const query =
        "UPDATE orderlist SET orderType = 1 WHERE orderId = ?";
    database.query(query, [o_id])
        .then(function(results) {
            if(results.affectedRows >= 1){
                res.end("Success");
            }
            else {
                res.end("Fail");
            }
        });
});

//제조가 완료되면 list에서 삭제 후 delivery table insert
router.post('/end', function(req, res, next) {
    const o_id = req.body.o_id;
    var u1_lat;
    var u1_lng;
    var u2_lat;
    var u2_lng;

    const query =
        "SELECT * FROM orderlist WHERE orderId = ? OR matchingId = ?";
    database.query(query, [o_id, o_id])
        .then(function(results) {
            var r_id = results[0].restaurantId;
            var u1_address = results[0].customerAddress;

            if (results.length !== 1){
                googleMapsClient.geocode({
                    address: u1_address
                },function (err,response){
                    if (!err) {
                        u1_lat = response.json.results[0].geometry.location.lat;
                        u1_lng = response.json.results[0].geometry.location.lng;
                        var u2_address = results[1].customerAddress;

                        googleMapsClient.geocode({
                            address: u2_address
                        },function (err,response){
                            if (!err) {
                                u2_lat = response.json.results[0].geometry.location.lat;
                                u2_lng = response.json.results[0].geometry.location.lng;

                                const query =
                                    "SELECT restaurantName, restaurantLat, restaurantLng FROM restaurant WHERE restaurantId = ?";
                                database.query(query, [r_id])
                                    .then(function(results) {
                                        var r_name = results[0].restaurantName;
                                        var r_lat = results[0].restaurantLat;
                                        var r_lng = results[0].restaurantLng;

                                        const query =
                                            "INSERT INTO delivery " +
                                            "(r_name, r_lat, r_lng, c1_address, c1_lat, c1_lng, c2_address, c2_lat, c2_lng) " +
                                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                                        database.query(query, [r_name, r_lat, r_lng, u1_address, u1_lat, u1_lng, u2_address, u2_lat, u2_lng])
                                            .then(function(results) {
                                                if (results.affectedRows === 1) {
                                                    const query =
                                                        "DELETE FROM orderlist WHERE orderId = ? OR matchingId = ? ";
                                                    database.query(query, [o_id, o_id])
                                                        .then(function(results) {
                                                            if(results.affectedRows >= 1){
                                                                res.end("Success");
                                                            }
                                                            else {
                                                                res.end("Fail");
                                                            }
                                                        });
                                                } else {
                                                    res.end("fail");
                                                }
                                            });
                                    });
                            }
                        });
                    }
                });
            }
            else {

                googleMapsClient.geocode({
                    address: u1_address
                },function (err,response){
                    if (!err) {
                        u1_lat = response.json.results[0].geometry.location.lat;
                        u1_lng = response.json.results[0].geometry.location.lng;
                        const query =
                            "SELECT restaurantName, restaurantLat, restaurantLng FROM restaurant WHERE restaurantId = ?";
                        database.query(query, [r_id])
                            .then(function(results) {
                                var r_name = results[0].restaurantName;
                                var r_lat = results[0].restaurantLat;
                                var r_lng = results[0].restaurantLng;

                                const query =
                                    "INSERT INTO delivery " +
                                    "(r_name, r_lat, r_lng, c1_address, c1_lat, c1_lng) " +
                                    "VALUES (?, ?, ?, ?, ?, ?)";
                                database.query(query, [r_name, r_lat, r_lng, u1_address, u1_lat, u1_lng])
                                    .then(function(results) {
                                        if (results.affectedRows === 1) {
                                            const query =
                                                "DELETE FROM orderlist WHERE orderId = ? OR matchingId = ? ";
                                            database.query(query, [o_id, o_id])
                                                .then(function(results) {
                                                    if(results.affectedRows >= 1){
                                                        res.end("Success");
                                                    }
                                                    else {
                                                        res.end("Fail");
                                                    }
                                                });
                                        } else {
                                            res.end("fail");
                                        }
                                    });
                            });
                    }
                });
            }
        });
});

//본인 주문 확인하기
router.get('/myorder', function(req, res, next) {
    const u_id = req.query.u_id;

    const query =
        "SELECT * FROM orderlist WHERE customerId = ?";
    database.query(query, [u_id])
        .then(function(results) {
            res.end(JSON.stringify(results));
        });
});

module.exports = router;