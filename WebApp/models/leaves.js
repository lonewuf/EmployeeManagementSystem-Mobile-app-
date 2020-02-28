const mongoose = require('mongoose');

// User Schema
const LeavesSchema = mongoose.Schema({
   
    employee: {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'User'
    },  
    type_leave: {
      type: String
    },
    date: {
      type: Date,
      default: Date.now()
    },
    date_from: {
      type: Date,
      require: true
    },
    date_to: {
      type: Date,
      require: true
    },
    days_leave: {
      type: Number,
    },
    reason: {
      type: String,
      require: true
    },
    status: {
      type: String,
      default: '1' // 2 - approved  1 - pending  0 - cancelled
    }

});

module.exports = mongoose.model('Leave', LeavesSchema);

