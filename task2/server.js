const express = require('express');
const expressHandlebars = require('express-handlebars');
const router = require('./routes/router');

const app = express();

let port = 3000;
// set the vies directory
app.set('views', __dirname + '/views');
// use handlebars as the view angine
app.engine('handlebars', expressHandlebars({defaultLayout:'layout'}));
app.set('view engine', 'handlebars');
// use the same router for any request
app.use('/', router);
// start the server
app.listen(port);
console.log("Listening at port " + port + "...");
