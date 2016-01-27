// mailNewMemberFunction.js
// ========


// === fSendMailNewMember
var fSendMailNewMember = function (req, res, email, project, userParent, name, tmpPwd, responseJSON, next) {
	console.log("fSendMailNewMember");

	var utils = require('./mailUtils');
	var transporter = utils.fCreateTransporter();

	var mailOptions = {
		from: 'Share lootor <puddi@luce.eu.com>', 
		to: email, 
		subject: 'Welcome on Sharelootor !', 
		text:
			"Hello "+name+", \n"
			+"'"+userParent+"' added you to project '"+project+"' on www.sharelootor.luce.eu.com. \n\n"
			+"Informations required in order to join the project are listed below :\n"
			+"\t - Project : "+project+"\n"
			+"\t - Login : "+name+"\n"
			+"\t - Password :"+tmpPwd+"\n\n"
			+"Please note that this password has been randomly generated, please change it on your first connection.",
		html: 
		  "<h2>Hello "+name+",</h2>"
		  +"<div>"
			+"<div>"
			  +"'"+userParent+"' added you to project '"+project+"' on <a href='www.sharelootor.luce.eu.com'>www.sharelootor.luce.eu.com</a>.<br/>"
			+"</div>"
			+"<div>"
			  +"Informations required in order to join the project are listed below :"
			+"</div>"
			+"<ul>"
			  +"<li>Project : "+project+"</li>"
			  +"<li>Login : "+name+"</li>"
			  +"<li>Password :"+tmpPwd+"</li>"
			+"</ul>"
			+"<div>"
			  +"Please note that this password has been randomly generated, please change it on your first connection."
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
exports.fSendMailNewMember = fSendMailNewMember;

