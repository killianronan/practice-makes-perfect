const express = require('express')
const app = express()
const port = 3000
//AWS CONFIG
const AWS = require("aws-sdk");
AWS.config.update({
  region: "us-east-1",
  accessKeyId: "",
  secretAccessKey: ""
});
const dynamodb = new AWS.DynamoDB();
const docClient = new AWS.DynamoDB.DocumentClient();
const s3 = new AWS.S3();
//CLIENT PATH
const path = require("path");
let publicPath = path.resolve(__dirname, "public")
app.use(express.static(publicPath))

app.listen(port, () => {
  console.log(`App listening from http://localhost:${port}`)
})

app.get('/createDatabase',  async (req, res) => {
  await createDatabase().then(async result => {
    res.send({result})
  })
})

app.get('/destroyDatabase',  async (req, res) => {
  await destroyDatabase().then(async result => {
    res.send({result})
  })
})

app.get('/queryDatabase', async (req, res) => {
  await queryDatabase(req).then(async result => {
    res.send({result})
  })
})

async function createDatabase() {
  // Check to see if table already exists
  return new Promise((resolve) => {
    dynamodb.describeTable({TableName:"Movies"}, function(error) {
      if(!error){
        console.log("Table has already been created.");
        resolve("Table has already been created.");
      }
      else{
        console.log("Table is not already created");
        var createTableParams = {
          TableName : "Movies",
          KeySchema: [       
              { AttributeName: "year", KeyType: "HASH"},// Partition key
              { AttributeName: "title", KeyType: "RANGE" }// Sort key
          ],
          AttributeDefinitions: [       
              { AttributeName: "year", AttributeType: "N" },
              { AttributeName: "title", AttributeType: "S" }
          ],
          ProvisionedThroughput: {       
              ReadCapacityUnits: 1, 
              WriteCapacityUnits: 1
          }
        };
        dynamodb.createTable(createTableParams, function(err, data) {
          if (err) {
            console.error("Unable to create table. Error JSON:", JSON.stringify(err, null, 2));
            resolve("Cannot create database, unable to create table.")
          } else {
            console.log("Created table. Table description JSON:", JSON.stringify(data, null, 2));
          }
          let s3Params = {
            Bucket: "csu44000assign2useast20", 
            Key: "moviedata.json"
          };
          // Get moviedata.json
          s3.getObject(s3Params, function(err, data) {
            if (err) {
              console.log(err, err.stack);
            }
            else{
              console.log("got movies data",data);
              let result = JSON.parse(data.Body)
              result.forEach(function (movie) {
                var putParams = {
                  TableName: "Movies",
                  Item: {
                    "year": movie.year,
                    "title": movie.title,
                    "info": movie.info
                  }
                };
                docClient.put(putParams, function (err, data) {
                  if (err) {
                    console.error("Unable to populate DB. Error JSON:", JSON.stringify(err, null, 2));
                  } else {
                    resolve("Database created.");
                  }
                });
              });
            }
          });
        });
      }
    });
  })
}

async function destroyDatabase() {
  return new Promise((resolve) => {
    // Check table exists
    dynamodb.describeTable({TableName:"Movies"}, function(error) {
      if(!error){
        var params = {
          TableName : "Movies"
        };
        dynamodb.deleteTable(params, function(err, data) {
          if (err) {
            console.error("Unable to delete table. Error JSON:", JSON.stringify(err, null, 2));
            resolve("Unable to delete table.");
          } else {
            console.log("Deleted table. Table description JSON:", JSON.stringify(data, null, 2));
            resolve("Database destroyed.");
          }
        })
      }
      else{
        console.log("Table does not exist.");
        resolve("Cannot destroy database, the table does not exist.");
      }
    });
  });
}

async function queryDatabase(req) {
  return new Promise((resolve) => {
    dynamodb.describeTable({TableName:"Movies"}, function(error) {
      if(!error){
        var year = req.query.year;
        var name = req.query.name;//find movies that start with the passed string
        var rating = req.query.rating;//find movies with ratings higher than passed rating
        var params = {
          TableName : "Movies",
          ProjectionExpression:"#yr, title, info",
          KeyConditionExpression: "#yr = :yyyy and begins_with(title, :t)", //Only a max of 2 conditions allowed
          ExpressionAttributeNames:{
            "#yr": "year"
          },
          ExpressionAttributeValues: {
            ":yyyy": parseInt(year),
            ":t": name
          },
        };
        docClient.query(params, function(err, data) {
            if (err) {
              console.log("Unable to query. Error:", JSON.stringify(err, null, 2));
            } else {
              console.log("Query succeeded.");
              console.log(data.Items)
              let finalResult = [];
              data.Items.forEach((function (movie) {
                if(movie.info.rating>=rating){
                  finalResult.push(movie)
                }
              }));
              resolve(finalResult)
            }
        });
      }
      else{
        resolve("Table not created.");
      }
    });
  });
}