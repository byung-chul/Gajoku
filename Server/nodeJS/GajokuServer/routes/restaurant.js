var express = require('express');
var database = require('../database/mysql_connection');
var router = express.Router();
var connection = database.getDatabase();
var googleMapsClient = require('@google/maps').createClient({
    key: 'AIzaSyDULFSjnVfeYArthagkn-lIeR_vXrR5D6w'
});

router.get('/', function(req, res, next) {
    const u_address = req.query.u_address;
    const type = req.query.r_type;
    var u_lat;
    var u_lng;

    googleMapsClient.geocode({
        address: u_address
    },function (err,response){
        if (!err){
            u_lat = response.json.results[0].geometry.location.lat;
            u_lng = response.json.results[0].geometry.location.lng;

            const query =
                "SELECT restaurantId, restaurantName, restaurantType, restaurantLeast, ( 6371 * acos( cos( radians(?) ) * cos( radians( restaurantLat ) ) * cos( radians( restaurantLng ) - radians(?) ) + sin( radians(?) ) * sin( radians( restaurantLat ) ) ) )" +
                "AS distance FROM restaurant WHERE restaurantType = ? HAVING distance < 2";
            database.query(query, [u_lat, u_lng, u_lat, type])
                .then(function(results) {
                    res.end(JSON.stringify(results));
                });
        }
    });
});

module.exports = router;