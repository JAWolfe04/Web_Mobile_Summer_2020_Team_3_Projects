const express = require('express');
const cors = require('cors');
const bodyParser  =  require('body-parser');
const config = require('./Config');
var mongoose = require('mongoose');

const app = express();
const  router  =  express.Router();
app.use(cors());

mongoose.connect(config.collection, { useNewUrlParser: true, useUnifiedTopology: true, useFindAndModify: false })
    .then(() => console.log('connection successful'))
    .catch((err) => console.error(err));

router.use(bodyParser.urlencoded({ extended:  false }));
router.use(bodyParser.json());

// Message from pinging server
router.get('/', (req, res) => {
    res.status(200).send('This is an authentication server');
});

// Creates listener for http requests
app.use(router);
const  port  =  process.env.PORT  ||  3000;
app.listen(port, () => {
    console.log('Server listening at http://localhost:'  +  port);
});

var UserSchema = new mongoose.Schema({
    user: String,
    venueNames: [String],
    venueIDs: [String]
});

const Users = mongoose.model('Users', UserSchema);

// --------------------------------------------------------
// Router functions
// --------------------------------------------------------

router.post('/addFavorite/:id', (req, res, next) => {
    Users.findByIdAndUpdate(req.params.id,
        {
            $push: {venueNames: req.body.name, venueIDs: req.body.id}
        },
        (err, post) => {
        if (err) return next(err);
        res.json(post);
    });
});

router.get('/getFavorites/:id', (req, res, next) => {
    Users.findById(req.params.id,
        (err, post) => {
        if (err) return next(err);
        res.json(post);});
});

router.get('/loginUser/:user', (req, res, next) => {
    Users.findOneAndUpdate({ user: req.params.user },
        {},
        {new: true, upsert:true},
        (err, post) => {
            if (err) return next(err);
            res.json(post);});
});
