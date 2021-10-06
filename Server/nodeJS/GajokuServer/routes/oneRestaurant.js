var express = require('express');
var database = require('../database/mysql_connection');
var router = express.Router();
var connection = database.getDatabase();

router.get('/', function(req, res, next) {
    const r_id = req.query.r_id;

    const query =
        "SELECT restaurantName, restaurantNumber, restaurantMenu, restaurantAddress, restaurantDetail, restaurantLeast FROM restaurant WHERE restaurantId = ?";
    database.query(query, [r_id])
        .then(function(results) {
            res.end(JSON.stringify(results));
        });
});

module.exports = router;