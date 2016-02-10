// dbUtils.js
// ========

var fConnectdB = function(req, res, responseJSON, after){
	// Test if DB exists
	var project     = req.body.project;
	var fs = require('fs');
	var dbPath="./dbs/db_"+project+".sqlite";
	if ( fs.existsSync(dbPath) ){
		var sqlite3 = require("sqlite3").verbose();
		var db = new sqlite3.Database(dbPath);
		after(db);
	}else{
		responseJSON.succeed = false;
		responseJSON.msg = "Project '"+project+"' does not exist.";
		responseJSON.errCode = "ERR_PROJECT_NOT_EXIST";
		responseJSON.errArgs=[project];
		sendResponse(req, res, responseJSON);
	}
}
exports.fConnectdB = fConnectdB;

//Callback response end
var sendResponse = function(req, res, responseJSON){
    console.log("Sending response");
    res.json(responseJSON);
};