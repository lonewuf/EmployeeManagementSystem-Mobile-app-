const express = require('express');
const router = express.Router();
const passport = require('passport');
const nodemailer = require('nodemailer');
const bcrypt = require('bcryptjs');
const auth = require('../config/auth');
const userAuth = require('../config/userauth');

// // Get Models
const User = require('../models/users');
const VariableStore = require('../models/variablestore')
const CompanyNum = require('../models/companyNum')
const Leave = require('../models/leaves')
const Report = require('../models/reports')

router.get('/', passport.authenticate('jwt', { session: false }), (req, res) => {
  console.log("reeeeeeeeeeeeeeeeiiiiiii")
  CompanyNum.find({})
    .then(foundCompanyID => { 
      Report.find({})
        .sort({date: -1})
        .populate('employee')
        .then(foundReports => {
          Leave.find({status: '1'})
            .populate('employee')
            .sort({date: -1})
            .then(foundLeaves => {
              User.find({})
                .populate('reports')
                .populate('leaves')
                .then(foundUsers => {

                  res.json({
                    foundCompanyID,
                    foundReports,
                    foundLeaves,
                    user: req.user,
                    foundUsers,
                    success: true,
                    message: 'Admin data recieved successfully'
                  })
                })
                .catch(err => console.log(err))

              
              
            })
            .catch(err => console.log(err))
        })
        .catch(err => console.log(err))
      
    })
    .catch(err => console.log(err))
  
});

router.post('/approve-leave/:id', (req, res) => {
  const id = req.params.id;
  console.log("aaaaaa", id)
  Leave.updateOne({_id: id}, {$set: {status: '2'}})
    .then(updatedLeave => {
      Leave.findById(id)
        .populate('employee')
        .then(foundLeave => {
          var typeLeave = foundLeave.type_leave == 'sick_leave' ? 'sick_leave' : 'vacation_leave';

          if(typeLeave == 'sick_leave') {
            User.updateOne({_id: foundLeave.employee._id}, {$inc: {sick_leave: -parseInt(foundLeave.days_leave)}})
              .then(updatedL => {
                  console.log("ccccc")

                res.send('Sick Leave approved')
              })
              .catch(err => console.log(err))
          } else if(typeLeave == 'vacation_leave') {
            User.updateOne({_id: foundLeave.employee._id}, {$inc: {vacation_leave: -parseInt(foundLeave.days_leave)}})
              .then(updatedL => {
                res.send( 'Vacation Leave approved')
              })
              .catch(err => console.log(err))
          }
          
        })
        .catch(err => console.log(err))
    })
    .catch(err => console.log(err))
})

router.post('/disapprove-leave/:id', (req, res) => {
  const id = req.params.id;

  Leave.updateOne({_id: id}, {$set: {status: '0'}})
    .then(updatedLeave => {
      res.send("Leave Disapproved")
    })
    .catch(err => console.log(err))
})

router.get('/generate-employee-number', auth.isAdmin,(req, res) => {

  VariableStore.updateOne({target: 'secret-target'}, {$inc: {counter: 1}})
    .then(updatedCounter => {
      const company_num = new CompanyNum({
        number: updatedCounter.counter
      })

      CompanyNum.create({number: updatedCounter.counter})
        .then(createdCompanyNum => {
          console.log(company_num)
          req.flash('success', `New company number is generated: ${createdCompanyNum._id}`)
          res.redirect('/admin');
        })
        .catch(err => console.log(err))
    })
    .catch(err => console.log(err))
})

// router.get('/user', auth.isUser, (req, res) => {
//   User.findOne({email: req.user.email})
//     .then(foundUser => {
//       res.render('user-profile/home', {
//         foundUser
//       })
//     })
//     .catch(err => console.log(err))
// });


// router.get('/profile/:id', auth.isAdmin, (req, res) => {
//   User.findOne({_id: req.params.id})
//       .then(foundUser => {

//           res.render('profile', {
//               foundUser,
//               title: `${foundUser.name}'s Profile`
//           })
//       })
//       .catch(err => {
//           req.flash('danger', 'User is not present')
//           res.redirect('/');
//       });
// });

// // Forgot password - GET
// router.get('/forgot-password', (req, res) => {
//   res.render('forgot_password', {
//       title: 'Forgot Password'
//   })
// })

router.post('/send-company-id/:id', auth.isAdmin,(req, res) => {
    
  const email = req.body.email
  const id = req.params.id

  var transporter = nodemailer.createTransport({
  service: userAuth.tMail,
  auth: {
          user: userAuth.uName,
          pass: userAuth.pW
      }
  });

  const mailOptions = {
    from: userAuth.uName, // sender address
    to: email, // list of receivers
    subject: 'ERMSCORE - Company ID', // Subject line
    html: `<h2>Hi Mr./Mrs./Ms. </h2>
    <br><br>
    <p>This is your Company ID for your registration in our website: <strong>${id}</strong></p>
    <br>`
  };

  transporter.sendMail(mailOptions)
    .then(info => {
        req.flash('success', 'Email sent')
        res.redirect('back');
    })
    .catch(err => console.log(err))
  })

// Exports
module.exports = router;