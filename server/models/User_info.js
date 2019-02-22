var mongoose = require('mongoose');
mongoose.connect('mongodb://localhost:27017/Smart', {useNewUrlParser: true});

var User_info = new mongoose.Schema({
    id: {
        type: String,
        required: 'id can not be empty'
    },
    password: {
        type: String,
        required: 'Password can not be empty'
    },
    username: {
        type: String,
        required: 'username can not be empty'
    },
    email:  {
        type: String,
        required: 'email can not be empty'
    }
});

module.exports = mongoose.model('User_info',User_info);