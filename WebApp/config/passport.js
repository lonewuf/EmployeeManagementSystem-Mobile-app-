const LocalStrategy = require('passport-local').Strategy;
const User = require('../models/users');
const bcrypt = require('bcryptjs');

module.exports =  passport => {

    passport.use(new LocalStrategy( (username, password, done) => {

        User.findOne({email: username},  (err, user) => {
            if (err)
                console.log(err);

            if (!user) {
                return done(null, false, {message: 'Incorrect username or password'});
            }

            bcrypt.compare(password, user.password,  (err, isMatch) => {
                if (err)
                    console.log(err);

                if (isMatch) {
                    return done(null, user);
                } else {
                    return done(null, false, {message: 'Incorrect username or password'});
                }
            });
        });

    }));

    passport.serializeUser( (user, done) => {
        done(null, user.id);
    });

    passport.deserializeUser( (id, done) => {
        User.findById(id,  (err, user) => {
            done(err, user);
        });
    });

}

