const express = require('express');
const router = express.Router();
const fs = require('fs');
const exec = require('child_process').exec;


router.get('/', (req, res, next) => {
    res.render('index');
});

// for this request, execute the java program that generates the json files
router.get('/search', (req, res, next) => {
  exec('java GenerateJSONs vehicles.json', (error, stdout, stderr) => {
    if (error) {
      console.log(stderr);
      throw error;
    }
    res.render('result');
  });
});

// then for each kind of output, read the specific file and send is as
// a json string 
router.get('/search/nameAndPrice', (req, res, next) => {
  fs.readFile('./json/nameAndPrice.json', 'utf8', (readError,data) => {
    if (readError) {
      console.log("There was an error with this search, probably the JSON file" +
                  " is not valid.");
      res.send("There was an error with this search, it probably generated a " +
               "JSON file is not valid.");
    } else {
      const dataObj = JSON.parse(data);
      const dataStr = JSON.stringify(dataObj);
      res.render('nameAndPrice', {dataStr});
    }
  });
});

router.get('/search/nameAndSpecification', (req, res, next) => {
  fs.readFile('./json/nameAndSpecification.json', 'utf8', (readError, data) => {
    if (readError) {
      console.log("There was an error with this search, probably the JSON file" +
                  " is not valid.");
      res.send("There was an error with this search, it probably generated a " +
                "JSON file is not valid.");
    } else {
      const dataObj = JSON.parse(data);
      const dataStr = JSON.stringify(dataObj);
      res.render('nameAndSpecification', {dataStr});
    }
  });

});

router.get('/search/carTypeRatings', (req, res, next) => {
  fs.readFile('./json/carTypeRatings.json', 'utf8', (readError, data) => {
    if (readError) {
      console.log("There was an error with this search, probably the JSON file" +
                  " is not valid.");
      res.send("There was an error with this search, it probably generated a " +
               "JSON file is not valid.");
    } else {
      const dataObj = JSON.parse(data);
      const dataStr = JSON.stringify(dataObj);
      res.render('carTypeRatings', {dataStr});
    }
  });
});

router.get('/search/vehicleRatings', (req, res, next) => {
  fs.readFile('./json/vehicleRatings.json', 'utf8', (readError, data) => {
    if (readError) {
      console.log("There was an error with this search, probably the JSON file" +
                  " is not valid.");
      res.send("There was an error with this search, it probably generated a " +
               "JSON file is not valid.");
    } else {
      const dataObj = JSON.parse(data);
      const dataStr = JSON.stringify(dataObj);
      res.render('vehicleRatings', {dataStr});
    }
  });
});

module.exports = router;
