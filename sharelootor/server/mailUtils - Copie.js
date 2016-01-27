//mailsUtils.js

//TODO put conf password in separate file

//Init mail transporter
var createTransporter=function(){
	var nodemailer = require('nodemailer');
	var transporter = nodemailer.createTransport({
		service: 'GandiMail',
		auth: {
			user: 'puddi@luce.eu.com',
			pass: '37prout42',
			secure: true
		}
	});
	return transporter;
}
exports.fCreateTransporter = createTransporter;