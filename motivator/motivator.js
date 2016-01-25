var fs = require('fs');
var path = require('path');
var process = require("process");
var nodemailer = require('nodemailer');
var ini = require('ini');

// =================================================================== VARIABLES
var config = ini.parse(fs.readFileSync('./config.ini', 'utf-8'));

var blog_posts_location = config.variables.blog_posts_location;
var max_days_off = config.variables.max_days_off;
var mail_blogger = config.variables.mail_blogger;

//mail
var config_mail = {
    service: config.config_mail.service,
    auth: {
      user: config.config_mail.user,
      pass: config.config_mail.pwd,
      secure: config.config_mail.secure
    }
  };

// ======================================================================== MAIN
var date_last_post;


// Loop through all the files in the temp directory
fs.readdir(blog_posts_location, function(err, files) {
  if (err) {
    console.error("Could not list the directory.", err);
    process.exit(1);
  }

  for(var i = 0; i < files.length; i++){
    var fromPath = path.join(blog_posts_location, files[i]);
    var stat = fs.statSync(fromPath, function(error) {
      if (error) {
        console.error("Error stating file.", error);
        return;
      }
    });
    if (stat.isFile()) {
      processFile(fromPath);
    }
  }


  var nbDays = getDaysOff(date_last_post);

  if(nbDays > max_days_off){
    sendThreatMail(nbDays);
  }
});


// =================================================================== FUNCTIONS
// processFile : Lit un fichier et extrait
function processFile(filepath) {
  console.log("Processing '%s'", filepath);

  var data = fs.readFileSync(filepath, 'utf8', function(err) {
    if (err) {
      return console.error("Could not read the file.", err);
    }
  });

  var currentLine = '';
  var dateFound = false;

  for(var i = 0; i < data.length; i++){
    if(!dateFound){
      if(data[i] === '\n'){
        date = findDateInLine(currentLine);
        currentLine = '';
        if(date !== undefined){
          // console.log("date found : "+date);
          dateFound = true;
          addPostDate(date);
        }
      }else{
        currentLine = currentLine+data[i];
      }
    }
  }

  return ;
}

// findDateInLine : Regarde si la ligne contient la date du poste
function findDateInLine(line) {
  if (line.substring(0, 6) == "date: ") {
    var dateStr = line.substring(6, line.length);
    return strToDate(dateStr);
  }
  return undefined;
}

//strToDate parse une date
function strToDate(dateStr) {
  var dayTimeSplit = dateStr.split(" ");  
  var day = dayTimeSplit[0];
  var time = dayTimeSplit[1];

  if (day == "Today") {
      day = new Date();
  } else if (day == "Yesterday") {
      day = new Date();
      day.setDate(day.getDate() - 1);
  } else {
      day = new Date(day);
  }

  var hourMinutes = time.substring(0, time.length -2);
  var amPM = time.substring(time.length -2, time.length);

  return new Date((day.getMonth() + 1) + "/" + day.getDate() + "/" + day.getFullYear() 
      + " " + hourMinutes  + " " + amPM);
}

// addPostDate : Met à jour la date de maj
function addPostDate(date) {
  // console.log("addPostDate");
  if( (date_last_post === undefined) || (date_last_post < date) ){
    date_last_post=date;
  }
}

// getDaysOff : Retourne le nombre de jours sans posts
function getDaysOff() {
  var now = new Date();
  var oneDay = 24*60*60*1000; // hours*minutes*seconds*milliseconds

  var diffDays = Math.round(Math.abs((now.getTime() - date_last_post.getTime())/(oneDay)));
  console.log("Aucun poste depuis : "+diffDays+" jour(s).");

  return diffDays;
}
// sendThreatMail : Envoie un mail de menace
function sendThreatMail(nbDays) {
  var transporter = nodemailer.createTransport(config_mail);

  var mailOptions = {
    from: 'Motivator <'+config_mail.auth.user+'>', 
    to: mail_blogger, 
    subject: 'Still alive ?!', 
    text:
      "Salut le blogger, \n"
      +"Tu n'as pas été très actif dérnièrement ("+nbDays+" jour(s))... Bouges toi ! "
      +"Bisous,\n"
      +"Motivator",
    html: 
      "<h2>Salut le blogger,</h2>"
      +"<div>"
      +"<div>"
        +"Tu n'as pas été très actif dérnièrement ("+nbDays+" jour(s))... Bouges toi ! "
      +"</div>"
      +"<div>"
        +"Bisous,<br/>Motivator"
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

}