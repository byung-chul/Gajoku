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
    const id = req.query.id;
    const password = req.query.password;

    const query =
        "SELECT id,pw FROM user WHERE id = ? AND pw = ?";
    database.query(query, [id, password])
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
    const id = req.body.id;
    const password = req.body.password;
    const phone_number = req.body.phone_number;

    const query =
        'SELECT id FROM user WHERE id=?';
    database.query(query, [id])
        .then(function (results) {
            if (results.length === 0){
                const query =
                    "SELECT pNumber FROM user WHERE pNumber = ?";
                return database.query(query, [phone_number]);
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
                    const query = "INSERT INTO user (id, pw, pNumber) VALUES (?, ?, ?)";
                    return database.query(query, [id, password, phone_number]);
                } else{
                    res.end("phone_number is duplicated");
                }
            }
            return "duplicated";
        })
        .then(function (results) {
            if (results.affectedRows === 1) {
                res.end("success");
            } else {
                res.end("fail");
            }
        })
});

router.get('/detail/', function (req, res, next) {
    const address = req.query.address;

    googleMapsClient.geocode({
        address: address
    },function (err,response){
        if (!err){
            res.end(JSON.stringify(response.json.results[0].geometry.location));
        }
    });
});

module.exports = router;