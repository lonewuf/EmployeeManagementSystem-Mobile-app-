const mongoose = require('mongoose');

// User Schema
const ReportSchema = mongoose.Schema({
   
    employee: {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'User',
    },
    date_logged: {
      type: Date, 
      default: Date.now()
    },
    month_logged: {
      type: Number, 
    },
    year_logged: {
      type: Number, 
    },
    day_logged: {
      type: Number, 
    },
    month: {
      type: Number
    },
    day: {
      type: Number
    },
    year: {
      type: Number
    },
    report: {
      type: String
    },
    file: {
      type: String
    }

});

module.exports = mongoose.model('Report', ReportSchema);

