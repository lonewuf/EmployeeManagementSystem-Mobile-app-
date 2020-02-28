// Import all packages needed from node_modules
const express           = require('express'),
      bodyParser        = require('body-parser'),
      mongoose          = require('mongoose'),
      path              = require('path'),
      flash             = require('connect-flash'),
      session           = require('express-session'),
      fileUpload        = require('express-fileupload'),
      expressValidator  = require('express-validator'),
      passport          = require('passport');
const app = express();

var multer, storage, crypto;
multer = require('multer')
crypto = require('crypto');





const auth = require('./config/auth');``

// Setup Database
const myDb = require('./config/database');
mongoose.connect(myDb.database, { useNewUrlParser: true });
mongoose.connection
  .then(() => console.log('Database is running'))
  .catch(err => console.long(`Database Error: ${err}`));

// Setup Middlewares and other settings
app.use(express.static(path.join(__dirname, 'public')));
// app.use(express.static(__dirname + '/public'));
app.set('view engine', 'ejs');
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));

app.use(fileUpload());

app.use(session({
  secret: auth.secret,
  resave: true,
  saveUninitialized: true
//  cookie: { secure: true }
}));

// Set global variable errors to null
app.locals.errors = null;

app.use(expressValidator({
  errorFormatter: function (param, msg, value) {
      var namespace = param.split('.')
              , root = namespace.shift()
              , formParam = root;

      while (namespace.length) {
          formParam += '[' + namespace.shift() + ']';
      }
      return {
          param: formParam,
          msg: msg, 
          value: value
      };
  },
  customValidators: {
    isImage: function (value, filename) {
        var extension = (path.extname(filename)).toLowerCase();
        switch (extension) {
            case '.jpg':
                return '.jpg';
            case '.jpeg':
                return '.jpeg';
            case '.png':
                return '.png';
            case '':
                return '.jpg';
            default:
                return false;
        }
    },
    isValidFile: function (value, filename) {
      var extension = (path.extname(filename)).toLowerCase();
      switch (extension) {
          case '.txt':
              return '.txt';
          case '.csv':
              return '.csv';
          case '.docx':
              return '.docx';
          case '.doc':
              return '.doc';
          default:
              return false;
      }
  }
  }
}));

app.use(flash());
app.use(function (req, res, next) {
    res.locals.messages = require('express-messages')(req, res);
    next();
});

require('./config/passport2')(passport);


app.use((req, res, next) => {
  res.locals.user = req.user || null;
  next();
});



// storage = multer.diskStorage({
//     destination: './uploads/',
//     filename: function(req, file, cb) {
//       return crypto.pseudoRandomBytes(16, function(err, raw) {
//         if (err) {
//           return cb(err);
//         }
//         return cb(null, "" + (raw.toString('hex')) + (path.extname(file.originalname)));
//       });
//     }
//   });


// // Post files
// app.post(
//   "/upload",
//   multer({
//     storage: storage
//   }).single('upload'), function(req, res) {
//     console.log(req.file);
//     console.log(req.body);
//   var obj = JSON.parse(req.body.upload);
//     console.log(obj, obj.name);
//   fs.copyFile(`${__dirname}/uploads/${req.file.filename}`, `${__dirname}/uploads/try/${req.file.filename}`, (err) => {
//     if (err) throw err;
//     console.log('source.txt was copied to destination.txt');
//   });
//     res.redirect("/uploads/" + req.file.filename);
//     console.log(req.file.filename);
//     return res.status(200).end();
//   });


// Import all routes
const usersRoutes           = require('./routes/users'),
      usersProfileRoutes    = require('./routes/user-profile'),
      adminRoutes           = require('./routes/admin')

app.use('/profile', usersProfileRoutes)
app.use('/admin', adminRoutes)
app.use('/', usersRoutes)


// Choose Server
const server_host = process.env.YOUR_HOST || '0.0.0.0';

// Choose Port
const port = process.env.PORT || 4000 ;

// Start Server
app.listen(port, '192.168.43.209' || server_host,() => {
  console.log(`Server started on ${port}`);
});