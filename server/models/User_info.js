var mongoose = require('mongoose');
//mongoose.connect('mongodb://localhost:27017/Smart', {useNewUrlParser: true});
var con = require('./../con');
var mongo=con.mongo;

mongoose.connect(mongo, {useNewUrlParser: true});

var User_info = new mongoose.Schema({
    id: {
        type: String,
        required: 'id can not be empty'
    },
    password: {
        type: String,
        required: 'Password can not be empty'
    },
    baby:  {
        type: String,
        required: 'baby can not be empty'
    },
    gender: {
        type: String
    },
    Byear: String,
    Bmonth: String,
    Bday: String,

    history: [String],
});

module.exports = mongoose.model('User_info',User_info);