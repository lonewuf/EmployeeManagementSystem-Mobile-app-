const express = require('express');
const router = express.Router();
const passport = require('passport');
const bcrypt = require('bcryptjs');
const nodemailer = require('nodemailer');
const mkdirp    = require('mkdirp');
const fs        = require('fs-extra');
const resizeImg = require('resize-img');
const userAuth = require('../config/userauth');
const auth = require('../config/auth');
const helpers = require('../config/helpers');
const jwt = require('jsonwebtoken');
const keys = require('../config/keys');

var multer, storage, path, crypto;
multer = require('multer')
path = require('path');
crypto = require('crypto');


// // Get Models
const User = require('../models/users');
// const Sale = require('../models/sales');
const ForgotPassword = require('../models/forgotpassword');
const VariableStore = require('../models/variablestore')
const CompanyNum = require('../models/companyNum')


storage = multer.diskStorage({
    destination: './uploads/',
    filename: function(req, file, cb) {
      return crypto.pseudoRandomBytes(16, function(err, raw) {
        if (err) {
          return cb(err);
        }
        return cb(null, "" + (raw.toString('hex')) + (path.extname(file.originalname)));
      });
    }
  });



router.post(
  '/upload',
  multer({
    storage: storage
  }).single('upload'), 
  function(req, res) {
    console.log('sadd')
    console.log(req.file, "fileee");
    console.log(req.body, "bodyyy");
    var obj = JSON.parse(req.body.upload);
    console.log(obj, obj.name);
  fs.copyFile(`${__dirname}/uploads/${req.file.filename}`, `${__dirname}/uploads/try/${req.file.filename}`, (err) => {
    if (err) throw err;
    console.log('source.txt was copied to destination.txt');
  });
    res.redirect("/uploads/" + req.file.filename);
    console.log(req.file.filename);
    return res.status(200).end();
  });


router.get('/sample', async(req, res) => {
  console.log("sampleeee")
  res.json({message: "hi biiitttchh"});
})
//////////// Mobile app jsonwebtoken
// @route   POST
// @desc    Login user
// @access  Public
router.post('/login', async(req, res) => { 
  // Error Validation
  console.log(req.body.email, " saddd")
  try {
    const email = req.body.email; 
    const password = req.body.password;
    const userCount = await User.findOne({ email: req.body.email }).countDocuments();
    const user = await User.findOne({ email: req.body.email })
    if(userCount === 0) { 
      console.log(user)
      res.json({
          success: false,
          admin: '',
          token: ``,
          message:'Email is not registered'
        });
    } else {
      const isMatch = await bcrypt.compare(password, user.password)
      if(isMatch) {
        const payload = { id: user.id, name: user.name, email: user.email, admin: user.admin};
        const token = await jwt.sign(payload, keys.secret, { expiresIn: 3600 });
        console.log(token, 'saddd')
        res.json({
          success: true,
          admin: user.admin,
          token: `Bearer ${token}`,
          message:''
        });
      } else {
        res.json({
          success: false,
          admin: '',
          token: ``,
          message:'Wrong password'
        });
      }
    }
  } catch(err) {
    console.log(err);
  }
})

//  @route  GET
//  @desc   Return current user details
//  @access Private
router.get('/current', passport.authenticate('jwt', { session: false }), (req, res) => {
  res.json({
    id: req.user.id,
    email: req.user.email,
    name: req.user.name,
    admin: req.user.admin
  });
})


//////////////////





// router.get('/register', auth.isLoggedIn, function (req, res) {

//   res.render('register', {
//       title: 'Register'
//   }); 

// });

// Mobile register
router.post('/register', async (req, res) => {
  const email = req.body.email;
  const name = req.body.name;
  const plainPass = req.body.password
  const company_num = "100"

  const salt = await bcrypt.genSalt(10)
  const password = await bcrypt.hash(plainPass, salt);

  const newEmployee = {
    email,
    name,
    password,
    company_num
  }

  try {
    const user = await User.find({email}).count();
    if(user) {
      res.json('Email already exist');
      console.log('Email exist');
    } else {
      const createdEmployee = await User.create(newEmployee);
      res.json('Registration success');
      console.log('Registration success');
    }
  } catch(err) {
    console.log(err)
  } 

})


router.post('/registerr',
  passport.authenticate('jwt', { session: false }), 
  function (req, res) {
  // console.log(req.body)
  // var obj = JSON.parse(req.body.upload)
  // console.log(obj);
  var imageF = "";
  var name = req.body.name; 
  var email = req.body.email;
  var address = req.body.address;
  var phone_number = req.body.phone_number;
  var company_num = req.body.company_num;
  // var rCompany = /^[0-9]${6}/

  // req.checkBody('name', 'Name is required!').notEmpty();
  // req.checkBody('address', 'Address is required!').notEmpty();
  // req.checkBody('company_num', 'Address is required!').notEmpty();
  // req.checkBody('company_num', 'Company Number is required!').notEmpty();
  // req.checkBody('email', 'Email is required!').isEmail();
  // req.checkBody('image', 'You must upload an image').isImage(imageF);
  //   req.checkBody('pswd', 'Password is required!').notEmpty();
  //   req.checkBody('pswd2', 'Passwords do not match!').equals(password);

  var errors = req.validationErrors();

  if (errors) {
		CompanyNum.find({})
    .then(foundCompanyID => {
      Report.find({})
        .then(foundReports => {
          Leave.find({})
            .then(foundLeaves => {
							User.find({})
                .populate('reports')
                .populate('leaves')
                .then(foundUsers => {
                  res.render('admin/index', {
                    foundCompanyID, 
                    foundReports,
                    foundLeaves,
                    user: req.user,
                    foundUsers,
										user: req.user,
										errors
                  })
                })
                .catch(err => console.log(err))})
            .catch(err => console.log(err))
        })
        .catch(err => console.log(err))})
    .catch(err => console.log(err))
  } else {
  User.findOne({email: email}, function (err, user) {
    if (err)
    console.log(err);

    if (user) {
    res.send('Email exists, choose another!');
    } else {
      console.log("aaa")
			var password = helpers.randomPassword(8)
			var tempPassword = password
			
			bcrypt.genSalt(10, function (err, salt) {
				bcrypt.hash(password, salt, function (err, hash) {
				if (err)
					console.log(err);
				
				password = hash;

				User.create({
					name,
					email,
					address,
					phone_number,
					company_num,
					password,
					image: imageF
				})
				.then(createdEmp => {
          console.log("dddd")
					mkdirp(`public/employee_images/${createdEmp._id}`, err => {
					if(err)
						throw (err);
					});
					mkdirp(`public/employee_images/${createdEmp._id}/files`, err => {
						if(err)
							throw (err);
					});
					if(imageF == "") {
						// var productImage = req.files.image;
						// var path = `public/employee_images/${createdEmp._id}/${imageF}`
					
						// productImage.mv(path, err => {
						// 	console.log(err);
						// });

            // fs.copyFile(`public/uploads/${req.file.filename}`, `public/employee_images/${createdEmp._id}/${req.file.filename}`, (err) => {
            //   if (err) throw err;
            //   console.log('source.txt was copied to destination.txt');
            // });
            console.log("zzz")
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
							subject: 'ERMSCORE - Employee account', // Subject line
							html: `<h2>Hi <strong>${createdEmp.name}</strong></h2>
							<br><br>
							<p>Welcome to Ermscore</p>
							<br>
							<br>
							<br> 
							<p>Your username and temporary password is listed below. Please change your password provided in the link below</p>
							<p>Username/Email: <strong>${createdEmp.email}</strong></p>
							<p>Password: <strong>${tempPassword}</strong></p>
							<br>
							<br>
							<a href="${userAuth.hostDev}/change-password/${createdEmp._id}">Click here to change your password</a>`
							};
							transporter.sendMail(mailOptions)
							.then(info => {
								res.send(`Employee is successfully created`)
								// res.redirect('/admin');
							})
							.catch(err => console.log(err))
					}
				})
				.catch(err => console.log(err))
				});
			});  
    }
  });
  }

});

// router.get('/login', auth.isLoggedIn, function (req, res) {

//   if (res.locals.user) res.redirect('/');
  
//   res.render('login', {
//       title: 'Log in'
//   });

// });

router.post('/loginnn', async (req, res) => {
  const email = req.body.email;
  const password = req.body.password;

  try {
    const userNum = await User.find({email}).count();
    const user = await User.findOne({email})
    console.log(user)
    if(!userNum) {
      res.json('Email does not exist')
      console.log('Email does not exist')
    } else {

      const isMatch = await bcrypt.compare(password, user.password)
      
      if (isMatch) {
        res.json('Login success')
        console.log('Login success') 
      } else {
        res.json('Wrong password')
        console.log('Wrong password')
      }
    }
    
    
  } catch(err) {
    console.log(err)
  }
});

/*
 * GET login  
 */
router.post('/loginn', auth.isLoggedIn, function (req, res, next) {

  passport.authenticate('local', {
  successRedirect: '/profile',
  failureRedirect: '/',
  failureFlash: true
  })(req, res, next);
  
});

// router.post('/login-from-billing', auth.isLoggedIn, function (req, res, next) {

//   passport.authenticate('local', {
//       successRedirect: '/cart/payment-method',
//       failureRedirect: '/cart/payment-method',
//       failureFlash: true
//   })(req, res, next);
  
// });

/*
 * GET logout
 */
router.get('/logout', function (req, res) {

  req.logout();
  req.flash('success', 'You are logged out!');
  res.redirect('/');

});

// Forgot password - POST
router.post('/forgot-password', (req, res) => {
  
  const email = req.body.email

  User.findOne({email: email}, (err, foundUser) => {
  if(err) {
    throw(err)
  } else {

    if(!foundUser) {
    req.flash('success', 'We will send you an email if your email is in our system')
    res.redirect('/');
    } else {

    var transporter = nodemailer.createTransport({
			service: userAuth.tMail,
			auth: {
				user: userAuth.uName,
				pass: userAuth.pW
			}
    });

    ForgotPassword.create({email: email})
    .then(createdFoundPassword => {
      const mailOptions = {
      from: userAuth.uName, // sender address
      to: email, // list of receivers
      subject: 'ERMSCORE - Forgot Password', // Subject line
      html: `<h2>Hi ${foundUser.name}</h2>
      <br><br>
      <p>Click the link to change your password.</p>
      <br>
      <a href="${userAuth.hostDev}/forgot-password/${foundUser._id}/${createdFoundPassword._id}">Change password</a>`// plain text body
      };
      transporter.sendMail(mailOptions)
      .then(info => {
        req.flash('success', 'We will send you an email if your email is in our system')
        res.redirect('/');
      })
      .catch(err => console.log(err))
    })
    .catch(err => console.log(err))
  }
  }
  })

})

router.get('/forgot-password/:id/:change_pass_id', (req, res) => {
  
  const id = req.params.id
  const change_pass_id = req.params.change_pass_id

  ForgotPassword.findById(change_pass_id)
  .then(foundForgotPW => {
    User.findById(id)
    .then(foundUser => {
      res.render('forgot_password_submit', {
      title: 'Forgot Password',
      id,
      change_pass_id,
      user: req.user
      })
    })
    .catch(err => console.log(err))
  })
  .catch(err => console.log(err))
})

router.post('/forgot-password/:id/:change_pass_id', (req, res) => {
  
  const id = req.params.id
  const change_pass_id = req.params.change_pass_id
  var password = req.body.password;
  const password2 = req.body.password2;

  console.log('errors')

  req.checkBody('password', 'Password is required!').notEmpty();
  req.checkBody('password2', 'Passwords do not match!').equals(password);

  var errors = req.validationErrors();

  ForgotPassword.findById(change_pass_id)
  .then(foundForgotPW => {
    if (errors) {
    req.flash('danger', 'Make sure that fill up all the and Passwords should match')
    res.redirect(`/forgot-password/${id}/${change_pass_id}`);
    }
    bcrypt.genSalt(10, function (err, salt) {
    bcrypt.hash(password, salt, function (err, hash) {
      if (err)
      console.log(err);

      password = hash;

      User.updateOne({_id: id}, {$set: {"password": password}})
      .then(foundUser => {
        ForgotPassword.findByIdAndDelete(change_pass_id)
        .then(() => {
          console.log('changes')
          req.flash('success', 'Your Password is changed')
          res.redirect('/');
        })
        .catch(err => console.log(err))
      })
      .catch(err => console.log(err))
      
    });
    });

    
  })
  .catch(err => console.log(err))
  
})

router.get('/', (req, res) => {
  if(req.user) {
  	res.redirect('/profile')
  } else {
		res.render('index')
	}
  
});

router.get('/delete-emp/:id', (req, res) => {
	User.findByIdAndDelete(req.params.id)
		.then(() => {
			req.flash('success', 'Employee is deleted')
			res.redirect('back');
		})
})




// Exports
module.exports = router;