var createError = require('http-errors');
var express = require('express');
var bodyParser = require('body-parser')
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');
var mongoose = require('mongoose');
var cors = require('cors');
var con = require('./con');
var mongo = con.mongo;


var MongoClient = require('mongodb').MongoClient;
function connect(mongo) {
  return new Promise(function(resolve, reject) {
    MongoClient.connect(mongo, function(err, db){
      if (err) {
        console.log('mongo connection error: ', err.message);
        reject(err);
      }
      else {
        console.log('connected to mongo');
        resolve(db);
      }
    })
  })
}

var indexRouter = require('./routes/index');
var usersRouter = require('./routes/users');
var boardRouter = require('./routes/boards');
var uploadimageR = require('./routes/uploadimage');
var controlRouter = require('./routes/controls');
var uploadsoundR = require('./routes/uploadsound')
var cryRouter = require('./routes/cry');

var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'pug');

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));
app.use(bodyParser.urlencoded({ extended: true }));
app.use('/uploads',express.static('uploads'))
app.use('/boards', express.static('uploads'));


app.use('/', indexRouter);
app.use('/users', usersRouter);
app.use('/boards', boardRouter);
app.use('/uploadimage',uploadimageR);
app.use('/ctl', controlRouter);
app.use('/uploadsound', uploadsoundR);//upload sound post
app.use('/cry',cryRouter);

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

var server = app.listen(80,function() {
  console.log("Express server has started on port 80");
})

module.exports = app;
