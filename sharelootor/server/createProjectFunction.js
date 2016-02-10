// createProjectFunction.js
// ========

// === create project
exports.postCreateProject= function (req, res) {
	var responseJSON={succeed:true, msg:""};
	var project     = req.body.project;
	var login       = req.body.login;
	var pwd         = req.body.pwd;
	var startDay    = req.body.startDay;
	var email    	= req.body.email;
	
	//Hashing pwd
	var crypto = require('crypto');
	var shasum = crypto.createHash('sha1');
	shasum.update(pwd);
	var hashedPwd = shasum.digest('hex');


	// Test if DB exists
	var fs = require('fs');
	var dbPath="./dbs/db_"+project+".sqlite";
	if ( ! fs.existsSync(dbPath) ){
		var sqlite3 = require("sqlite3").verbose();
		var db = new sqlite3.Database(dbPath);
		db.serialize(function() {
			//Project table
			db.run("CREATE TABLE Project ("
				+"name TEXT NOT NULL PRIMARY KEY, "
				+"startDay DATETIME NOT NULL, "
				+"currencySymbol TEXT DEFAULT '' , "
				+"estimatedIncome BIGINT DEFAULT 1000, "
				+"monday    INT DEFAULT 2, "
				+"tuesday   INT DEFAULT 2, "
				+"wednesday INT DEFAULT 2, "
				+"thursday  INT DEFAULT 2, "
				+"friday    INT DEFAULT 2, "
				+"saturday  INT DEFAULT 4, "
				+"sunday    INT DEFAULT 4 "
				+")");
			db.run("INSERT INTO Project VALUES(?, ?, '', 1000, 2, 2, 2, 2, 2, 4, 4)", [project, startDay]);

			//User table
			db.run("CREATE TABLE Users("
				+"login TEXT PRIMARY KEY NOT NULL, "
				+"password TEXT NOT NULL, "
				+"rightAdmin INT NOT NULL, "
				+"rightEditUsers INT NOT NULL, "
				+"email TEXT NOT NULL"
				+")");
			db.run("INSERT INTO Users VALUES(?, ?, 1, 1, ?)", [login, hashedPwd, email]);
			//tables work schedule
			db.run("CREATE TABLE WorkSchedules ("
					+"startDay DATETIME NOT NULL, "
					+"userLinked TEXT NOT NULL, "
					+"monday    INT NOT NULL, "
					+"tuesday   INT NOT NULL, "
					+"wednesday INT NOT NULL, "
					+"thursday  INT NOT NULL, "
					+"friday    INT NOT NULL, "
					+"saturday  INT NOT NULL, " 
					+"sunday    INT NOT NULL, "
					+"PRIMARY KEY(startDay, userLinked), "
					+"FOREIGN KEY (userLinked) REFERENCES Users(login) "
					+")");
			db.run("INSERT INTO WorkSchedules VALUES(?, ?, 2, 2, 2, 2, 2, 4, 4)",
					[startDay, login]);
			fLoadProject(req, res, responseJSON, db, nextCreateProject);
		});
		db.close();
	}else{
		responseJSON.succeed = false;
		responseJSON.msg = "Project '"+project+"' already exists.";
		responseJSON.errCode = "ERR_PROJECT_ALREADY_EXISTS";
		responseJSON.errArgs=[project];
		sendResponse(req, res, responseJSON);
	}
};

// fLoadprojct, load project properties from DB
var fLoadProject = function(req, res, responseJSON, db, next){
    console.log("Loading project...");

	//Connect to db
	var sql = 'SELECT name, startDay, currencySymbol, estimatedIncome, monday, tuesday, wednesday, thursday, friday, saturday, sunday FROM Project LIMIT 1';

	db.all(sql, [], function(err, rows) {
		if( !err ) {
			var project ={	name: rows[0].name, 
							startDay : rows[0].startDay,
							currencySymbol : rows[0].currencySymbol, 
							estimatedIncome : rows[0].estimatedIncome,
							monday : rows[0].monday,
							tuesday : rows[0].tuesday,
							wednesday : rows[0].wednesday,
							thursday : rows[0].thursday,
							friday : rows[0].friday,
							saturday : rows[0].saturday,
							sunday : rows[0].sunday};
			responseJSON.project=project;
			next(req, res, responseJSON);
		}else{
			responseJSON.succeed=false;
			responseJSON.msg=err;
			sendResponse(req, res, responseJSON);
			throw err;
		}
	});
};
exports.fLoadProject = fLoadProject;

var nextCreateProject = function(req, res, responseJSON){
	var loginModule = require("./loginFunction");
    console.log("nextCreateProject");
    loginModule.fLogin(req, res, responseJSON, function(req, res, responseJSON){
		var loadMembers = require("./loadMembersFunction");
		console.log("nextCreateProject2");
		loadMembers.fLoadMembers(req, res, responseJSON, function(req, res, responseJSON){
			var mail = require('./mailNewProject');
			mail.fSendMailNewProject(req, res, responseJSON, sendResponse);
		});
	});
};


//Callback response end
var sendResponse = function(req, res, responseJSON){
    console.log("Sending response createProject");
    res.json(responseJSON);
};



