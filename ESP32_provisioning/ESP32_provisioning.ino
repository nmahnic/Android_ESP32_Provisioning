//
//
// SDL_ESP32_BC24_GETIP Demo Program
// SwitchDoc Labs
//
// June 2018
//


#if defined(ARDUINO) && ARDUINO >= 100
// No extras
#elif defined(ARDUINO) // pre-1.0
// No extras
#elif defined(ESP_PLATFORM)
#include "arduinoish.hpp"
#endif

#define BC24DEBUG

#include <WiFi.h>

#include <ArduinoJson.h>
#include <WiFiClientSecure.h>
#include <HTTPClient.h>

// AP Variables

#include "WiFiManager.h"          //https://github.com/tzapu/WiFiManager

//gets called when WiFiManager enters configuration mode


void configModeCallback (WiFiManager *myWiFiManager)
//void configModeCallback ()
{

  Serial.println("Entered config mode Mash");

  Serial.println(WiFi.softAPIP());

}

#define WEB_SERVER_PORT 80
String APssid;

String Wssid;
String WPassword;

WiFiServer server(WEB_SERVER_PORT);

// include GET IP routines
#include "SDL_ESP32_BC24_GETIP.h"


String WiFi_SSID = "";
String WiFi_psk = "";

String ApiHost = "http://192.168.68.136:3000";

bool WiFiPresent = false;

void setup() {

  Serial.begin(115200);

  Serial.println();
  Serial.println();
  Serial.println("--------------------");
  Serial.println("WiFi Provisioning ESP32 Software Demo");
  Serial.println("--------------------");
  Serial.print("Version: 1.0");


  Serial.print("Compiled at:");
  Serial.print (__TIME__);
  Serial.print(" ");
  Serial.println(__DATE__);

  WiFiPresent = false;

  if (WiFiPresent != true){
#define APTIMEOUTSECONDS 60
    WiFiPresent = localAPGetIP(APTIMEOUTSECONDS);
  }


  if (WiFiPresent == true){
    Serial.println("-------------");
    Serial.println("WiFi Connected");
    Serial.println("-------------");
    WiFi_SSID = WiFi.SSID();
    WiFi_psk = WiFi.psk();
    Serial.print("SSID=");
    Serial.println(WiFi_SSID);

    Serial.print("psk=");
    Serial.println(WiFi_psk);
  }else{
    Serial.println("-------------");
    Serial.println("WiFi NOT Connected");
    Serial.println("-------------");
  }

}

void loop() {
  // put your main code here, to run repeatedly:
  Serial.println("Get to server");
  postWhoAmI(WiFi.macAddress());
  delay(2000); 
}

void getRoot(){
  HTTPClient http;
  http.begin(ApiHost + "/");
  int httpCode = http.GET();
  processResponse(httpCode, http);
}

void postWhoAmI(String newData){
  HTTPClient http;
  http.begin(ApiHost + "/WhoAmI");
  http.addHeader("Content-Type", "application/json");
  
  String message = "";
  StaticJsonDocument<300> jsonDoc;
  jsonDoc["data"] = newData;
  serializeJson(jsonDoc, message);
  
  int httpCode = http.POST(message);
  processResponse(httpCode, http);
}

void processResponse(int httpCode, HTTPClient& http){
  if (httpCode > 0) {
    Serial.printf("Response code: %d\t", httpCode);

    if (httpCode == HTTP_CODE_OK) {
      String payload = http.getString();
      Serial.println(payload);
    }
  }else {
    Serial.printf("Request failed, error: %s\n", http.errorToString(httpCode).c_str());
  }
  http.end();
}
