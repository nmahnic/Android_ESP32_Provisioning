const express = require("express");

const app = express();
app.use(express.json());

app.listen(3000,() => {
    console.log("Started on PORT 3000");
})

app.post('/User',function(req,res){
    console.log(req.body);
    res.json(
        {
            responseCode:0,
            responseMessage:"hola"
        }
    )
});

app.post('/Measurement',function(req,res){
    console.log(req.body);
    res.json(
        {
            responseCode:0,
            responseMessage:"hola"
        }
    )
});

app.post('/WhoAmI',function(req,res){
    console.log(req.body);
    res.json(
        {
            responseCode:0,
            responseMessage:"hola"
        }
    )
});

app.get('/',function(req,res){
    res.sendStatus(200)
});