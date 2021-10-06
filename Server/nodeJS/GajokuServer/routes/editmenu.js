var express = require('express');
var database = require('../database/mysql_connection');
var router = express.Router();
var connection = database.getDatabase();

/* GET users listing. */
//localhost:3000/users
router.get('/', function(req, res, next) {
    const r_admin = req.query.r_admin;

    const query =
        "SELECT restaurantMenu FROM restaurant WHERE restaurantAdmin = ?";
    database.query(query, [r_admin])
        .then(function(results) {
            res.end(JSON.stringify(results));
        });
});

router.post('/', function(req, res, next) {
    const r_admin = req.body.r_admin;
    const r_menu = req.body.r_menu;

    const query =
        "UPDATE restaurant SET restaurantMenu = ? WHERE restaurantAdmin = ?";
    database.query(query, [r_menu, r_admin])
        .then(function(results) {
            if(results.affectedRows >= 1){
                res.end("Success");
            }
            else {
                res.end("Fail");
            }
        });
});

module.exports = router;