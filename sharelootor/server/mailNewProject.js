// mailNewProject.js
// ========


// === fSendMailNewProject
//Send a mail reminding project properies
var fSendMailNewProject = function (req, res, responseJSON, next) {
	console.log("fSendMailNewMember");

	var utils = require('./mailUtils');
	var transporter = utils.fCreateTransporter();

	var email 		= req.body.email;
	var login 		= req.body.login;
	var project     = req.body.project;
	
	var mailOptions = {
		from: 'Share lootor <puddi@luce.eu.com>', 
		to: email, 
		subject: 'New project created on Sharelootor !', 
		text:
			"Hello "+login+", \n"
			+"You have just created the new project '"+project+"' on www.sharelootor.luce.eu.com.\n\n"
			+"Thanks for using this tool, you can invite your friends thanks to the part 'Members administration'.\n\n"
			+"Regards,\n"
			+"Sharelootor",
		html: 
		  "<h2>Hello "+login+",</h2>"
		  +"<div>"
			+"<div>"
			  +"You have just created the new project '"+project+"' on www.sharelootor.luce.eu.com."
			+"</div>"
			+"<div>"
			  +"Thanks for using this tool, you can invite your friends thanks to the part 'Members administration'."
			+"</div>"
			+"<div>"
			  +"Regards,<br/>Sharelootor"
			+"</div>"
		  +"</div>"
	};

	// send mail with defined transport object
	transporter.sendMail(mailOptions, function(error, info){
		if(error){
			console.log(error);
		}else{
			console.log('Message sent: ' + info.response);
		}
	});
	next(req, res, responseJSON);
};
exports.fSendMailNewProject = fSendMailNewProject;

