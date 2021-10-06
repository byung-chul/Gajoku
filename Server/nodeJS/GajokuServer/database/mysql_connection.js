var mysql = require('promise-mysql');
var config = require('../conf/SQL_conf.json');

module.exports = {
    getDatabase: function() {
        this.connection = mysql.createConnection({
            host: config.sdb_host,
            user: config.sdb_user,
            password: config.sdb_password,
            port: config.sdb_port,
            database: config.sdb_database
        });
        return this.connection;
    },
    query: function(query, arr) {
        var self = this;
        return this.connection.then(function(conn) {
            return conn.query(query, arr);
        })
            .catch(function(result) {
                console.log(result);
                return;
            });
    }
}