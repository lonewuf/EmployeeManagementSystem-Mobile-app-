const mongoose = require('mongoose');

// User Schema
const ForgotPasswordSchema = mongoose.Schema({
   
    email: {
      type: String
    },
    date: {
      type: Date,
      default: Date.now()
    }
    
});

module.exports = mongoose.model('Forgotpassword', ForgotPasswordSchema);


