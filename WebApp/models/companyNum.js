const mongoose = require('mongoose');

// Import variable storage
const VariableStore = require('./variablestore');

// User Schema
const CompanyNumberSchema = mongoose.Schema({
   
  number: {
    type: Number,
    default: 0
  },
  date: {
    type: Date,
    default: Date.now()
  },
  used: {
    type: Boolean,
    default: false
  }
    
});

module.exports = mongoose.model('CompanyNum', CompanyNumberSchema);


 