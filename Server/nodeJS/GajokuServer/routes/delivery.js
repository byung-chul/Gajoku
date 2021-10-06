var express = require('express');
var database = require('../database/mysql_connection');
var router = express.Router();
var connection = database.getDatabase();
var googleMapsClient = require('@google/maps').createClient({
    key: 'AIzaSyDULFSjnVfeYArthagkn-lIeR_vXrR5D6w'
});

/* GET users listing. */
//localhost:3000/users
router.get('/', function(req, res, next) {

    const query =
        "SELECT * FROM delivery WHERE type = 0 limit 1";
    database.query(query)
        .then(function(results) {
            res.end(JSON.stringify(results));
        });
});

router.get('/reject', function(req, res, next) {

    const id = req.query.delivery_id;

    const query =
        "UPDATE delivery SET type = 1 WHERE deliveryId = ?";
    database.query(query, [id])
        .then(function(results) {
            if(results.affectedRows >= 1){
                res.end("Success");
            }
            else {
                res.end("Fail");
            }
        });
});

router.get('/accept', function(req, res, next) {

    const id = req.query.delivery_id;

    const query =
        "UPDATE delivery SET type = 2 WHERE deliveryId = ?";
    database.query(query, [id])
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