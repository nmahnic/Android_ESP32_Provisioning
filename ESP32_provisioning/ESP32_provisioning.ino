//
//
// SDL_ESP32_BC24_GETIP Demo Program
// SwitchDoc Labs
//
// June 2018
//

#define BC24DEBUG

#include <WiFi.h>

#include <ArduinoJson.h>
#include <WiFiClientSecure.h>
#include <HTTPClient.h>
//#include <EEPROM.h>
#include <Preferences.h>

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

String ApiHost = "http://192.168.0.127:5000";

bool WiFiPresent = false;

Preferences preferences;

void setup() {

  Serial.begin(115200);

  Serial.println();
  Serial.println();
  Serial.println("--------------------");
  Serial.println("WiFi Provisioning ESP32 Software Demo");
  Serial.println("--------------------");
  Serial.println("Version: 1.0");

  preferences.begin("credentials", false);
  String ssid = preferences.getString("ssid", ""); 
  String passwd = preferences.getString("password", "");
  Serial.println("SSID guadado:" + ssid);
  Serial.println("PASSWD guadado:" + passwd);
  

  Serial.print("Compiled at:");
  Serial.print (__TIME__);
  Serial.print(" ");
  Serial.println(__DATE__);

  WiFiPresent = false;

  if(ssid != "" && passwd != ""){
    WiFiPresent = true;
    WiFi.mode(WIFI_STA);
    WiFi.begin(ssid.c_str(), passwd.c_str());
    Serial.print("Connecting to WiFi ..");
    while (WiFi.status() != WL_CONNECTED) {
      Serial.print('.');
      delay(1000);
    }
    Serial.println(WiFi.localIP());
  }

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

    preferences.putString("ssid", WiFi_SSID);
    preferences.putString("password", WiFi_psk);


    String ssid = preferences.getString("ssid", ""); 
    String passwd = preferences.getString("password", "");
    Serial.println("SSID guadado:" + ssid);
    Serial.println("PASSWD guadado:" + passwd);

  }else{
    Serial.println("-------------");
    Serial.println("WiFi NOT Connected");
    Serial.println("-------------");
  }

}

void loop() {
  // put your main code here, to run repeatedly:
  Serial.println("Get to server");
//  postWhoAmI(WiFi.macAddress());
  getUsers();
  delay(2000); 
}

void getRoot(){
  HTTPClient http;
  http.begin(ApiHost + "/");
  int httpCode = http.GET();
  processResponse(httpCode, http);
}

void getUsers(){
  HTTPClient http;
  http.begin(ApiHost + "/user/");
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
