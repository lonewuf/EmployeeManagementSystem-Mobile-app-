const mongoose = require('mongoose');

// User Schema
const VariableStoreSchema = mongoose.Schema({
   
  counter: {
    type: Number,
    default: 0
  },
  target: {
    type: String
  }
    
});




module.exports = mongoose.model('VariableStore', VariableStoreSchema);


 