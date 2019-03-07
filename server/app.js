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

mongoose.connect(mongo, {useNewUrlParser: true}, (err) => {
  if(!err) { console.log('MongoDB connection succeeded'); }
  else { console.log('MongoDB connection is error'+ JSON.stringify(err, undefined, 2)); }
});

var indexRouter = require('./routes/index');
var usersRouter = require('./routes/users');
var boardRouter = require('./routes/board');

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
app.use(cors( { origin: 'http://localhost:3000' }));  //바꿔줘야함


app.use('/', indexRouter);
app.use('/users', usersRouter);
app.use('/boards', boardRouter);

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

//app.listen(process.env.PORT, () => console.log(`Server started at port : ${process.env.PORT}`));


module.exports = app;
