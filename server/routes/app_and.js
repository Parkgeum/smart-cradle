var express = require('express');
var router = express.Router();
var mongoose = require('mongoose');
var con = require('./../con');
var mongo=con.mongo;

mongoose.connect(mongo, {useNewUrlParser: true});

let users = [
    {
      id: 1,
      name: 'alice'
    },
    {
      id: 2,
      name: 'bek'
    },
    {
      id: 3,
      name: 'chris'
    }
]
  
router.get('/users', (req, res) => {
     console.log('who get in here/users');
     res.json(users)
});
  
router.post('/post', (req, res) => {
     console.log('who get in here post /users');
     var inputData;
  
     req.on('data', (data) => {
       inputData = JSON.parse(data);
     });
  
     req.on('end', () => {
       console.log("user_id : "+inputData.user_id + " , name : "+inputData.name);
     });
  
     res.write("OK!");
     res.end();
});

module.exports = router;
