// Middlewares

exports.isUser = function(req, res, next) {
  if (req.isAuthenticated()) {
      next();
  } else {
      req.flash('danger', 'Please log in.');
      res.redirect('/');
  }
}

exports.isLoggedIn = function(req, res, next) {
  if (req.isAuthenticated()) {
      res.redirect('/');
  } else {
      next();    
  }
}

exports.isAdmin = function(req, res, next) {
  if (req.isAuthenticated() && res.locals.user.admin == 1) {
      next();
  } else {
      req.flash('danger', 'Please log in as admin.');
      res.redirect('/profile/user');
  }
}

exports.secret = "Not so secret key"
