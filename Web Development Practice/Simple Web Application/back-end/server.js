const express = require('express')
const app = express()
const port = 3000
const https = require('https')
const cors = require('cors');
const appId = "3e2d927d4f28b456c6bc662f34350957";

app.listen(port, () => {
  console.log(`App listening from http://localhost:${port}`)
})

app.get('/', (req, res) => {
  res.send('Hello World!')
})

app.get('/getWeather', cors(), async (req, res) => {
  await getWeather(req, res).then(async weatherResult => {
    await getAirPollution(weatherResult.lat, weatherResult.long, res).then(airPollutionResult => {
      let finalResult = {weatherResult, airPollutionResult}
      res.send(finalResult)
    })
  })
})

async function getWeather(req, res) {
  var location = ""
  if(req.query.city){
    location = req.query.city;
  } 
  else{
    res.send("No city parameter sent.");
    return
  }
  const url = "https://api.openweathermap.org/data/2.5/forecast?q=" + location + "&appid=" + appId + "&units=metric";
  return new Promise((resolve) => {
      https.get(url, (response) => {
        if (response.statusCode === 200) {
          let resultData = '';
          response.on('data', data => resultData += data);
          response.on('end', () => {
              const weatherData = JSON.parse(resultData);
              let latitude = weatherData.city.coord.lat;
              let longitude = weatherData.city.coord.lon;
    
              let rainExpectedBool = checkForRain(weatherData);
              let temperatureRange = checkTemperature(weatherData);
              let summaryTable = parseSummaryTable(weatherData);
              resolve({
                rainExpected: rainExpectedBool, 
                temperature: temperatureRange, 
                summaryTable: summaryTable,
                long: longitude,
                lat: latitude
              });
          });
        } else {
          res.send("Invalid city parameter specified.")
        }
      })
  })
}

async function getAirPollution(latitude, longitude, res){
  const url = "https://api.openweathermap.org/data/2.5/air_pollution/forecast?lat=" + latitude + "&lon=" + longitude + "&appid="+ appId;
  return new Promise((resolve) => {
    https.get(url, (response) => {
      if (response.statusCode === 200) {
        let resultData = '';
        response.on('data', data => resultData += data);
        response.on('end', () => {
            const airPollutionData = JSON.parse(resultData);
            let wearMaskBool = checkAirPollution(airPollutionData);
            resolve(wearMaskBool);
        });
      } else {
        res.send("Invalid coordinates specified.")
      }
    })
  })
}

var checkAirPollution = function(airPollutionData){
  let sumPM2_5 = 0;
  for(let x = 0; x < airPollutionData.list.length; x++){
    sumPM2_5 += airPollutionData.list[x].components.pm2_5
  }
  let averagePM2_5 = sumPM2_5/106;
  if(averagePM2_5>=10){
    return true
  }
  else{
    return false;
  }
}

var checkForRain = function(weatherData){
  for(let x = 0; x < weatherData.list.length; x++){
    if(weatherData.list[x].weather[0].main == "Rain"){
      return true;
    }
  }
  return false;
}

var checkTemperature = function(weatherData){
  let sumOfTemps = 0;
  for(let x = 1; x < weatherData.list.length; x++){
    sumOfTemps += weatherData.list[x].main.temp;
  }
  let averageTemp = sumOfTemps/40;
  if(averageTemp <= 10){
    return "Cold";
  }
  else if(averageTemp >= 20){
    return "Hot";
  }
  else{
    return "Warm";
  }
}

var parseSummaryTable = function(weatherData){
  let summaryTable = [];
  for(let x = 0; x < weatherData.list.length; x+=8){
    let rainfallLevel = null;
    if(weatherData.list[x].rain){
      rainfallLevel = weatherData.list[x].rain['3h'];
    }
    summaryTable.push({
      temperature: weatherData.list[x].main.temp,
      windSpeed: weatherData.list[x].wind.speed,
      rainfallLevel: rainfallLevel
    })
  }
  return summaryTable;
}