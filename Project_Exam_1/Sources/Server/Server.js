const express = require('express');
const cors = require('cors');
const  bodyParser  =  require('body-parser');
const config = require('./config.js');

const app = express();
const  router  =  express.Router();
app.use(cors());

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

// --------------------------------------------------------
// Router functions
// --------------------------------------------------------

router.post('/addFavorite', (req, res) => {
    const user = req.body.user;
    const venueName = req.body.name;
    const venueID = req.body.id;

    console.log(user + ' ' + venueName + ' ' + venueID);
});

router.get('/getFavorites/:user', (req, res) => {
    const user = req.params.user;

    console.log(user);
});
