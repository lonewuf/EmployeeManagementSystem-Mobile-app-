const mongoose = require('mongoose');

// User Schema
const UserSchema = mongoose.Schema({
   
  name: {
    type: String,
    required: true
  },
  email: {
    type: String,
    required: true,
  },
  task_category: {
    type: String,
    default: ''
  }, 
  tempPassword: {
    type: String 
  },
  company_num: {
    type: String,
    required: true,
  },
  password: {
    type: String,
    required: true
  },
  image: {
    type: String
  },
  admin: {
    type: Number,
    default: 0
  },
  phone_number: {
    type: String,
    // required: true
  },
  address: {
    type: String,
    //required: true
  },
  disabled: {
    type: Boolean,
    default: false  
  },
  skill: [],
  reports: [
    {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'Report'
    }
  ],
  leaves: [
    {
      type:  mongoose.Schema.Types.ObjectId,
      ref: 'Leave'
    }
  ],
  sick_leave: {
    type: Number,
    default: 30
  },
  vacation_leave: {
    type: Number,
    default: 30
  },

  
});

module.exports = mongoose.model('User', UserSchema);


 