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
//IRQ
#define window_ticks 2500 //Fmuestreo IRQ=2,5KHz y Ventaneo= 1seg --> Ventaneo / Tm = 2500 = window_ticks

#define LED_PIN 32
#define CURRENT 34              //ADC1 porque el ADC2 no funciona con el WIFI
#define VOLTAGE 35

hw_timer_t * timer = NULL;
portMUX_TYPE timerMux = portMUX_INITIALIZER_UNLOCKED;



//vector de muestras de corriente
unsigned int i=0,a=0;
unsigned int adc_current[window_ticks];
unsigned int adc_voltage[window_ticks];
unsigned char flag_ventaneo=0,flag_tx=0;
volatile int count;    // Trigger 
int totalInterrupts;   // counts the number of triggering of the alarm


// Code with critica section
void IRAM_ATTR onTime() { 

   portENTER_CRITICAL_ISR(&timerMux);
   count++;
   portEXIT_CRITICAL_ISR(&timerMux);
}

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
//  preferences.putString("ssid", "");
//  preferences.putString("password", "");
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

  pinMode(CURRENT,INPUT);
  pinMode(VOLTAGE,INPUT);

  pinMode(LED_PIN, OUTPUT);
  digitalWrite(LED_PIN, LOW);

  // Configure the Prescaler at 80 the quarter of the ESP32 is cadence at 80Mhz
  // 80000000 / 80 = 1000000 tics por segundo
  timer = timerBegin(0, 80, true);                
  timerAttachInterrupt(timer, &onTime, true);    

  
  //Interrumpe cada 2,5KHz = 400uSeg --> X ticks * 1Meg = 400uS --> ticks= 400 
  timerAlarmWrite(timer, 400, true);           
  timerAlarmEnable(timer);

}

//Main
void loop(){
//  Serial.println("Inicio de LOOP");
//  delay(10000);
  if (count == 1) {
  // Comment out enter / exit to deactivate the critical section 
    portENTER_CRITICAL(&timerMux);
    count--;
    portEXIT_CRITICAL(&timerMux);

    digitalWrite(LED_PIN, HIGH);
    digitalWrite(LED_PIN, LOW);

    //Guardo lecturas cada T = 1 / 5KHz mientras el ventaneo este activo
    if(flag_ventaneo != 1){
      adc_current[i] = analogRead(CURRENT); //leo ADC
      adc_voltage[i] = analogRead(VOLTAGE); //leo ADC
      i++;
    }  
    
    totalInterrupts++;  //contador de tiempo de IRQ 

    if(totalInterrupts == window_ticks){ //si pasaron 0,5segundos --> accion de fin de ventaneo
      totalInterrupts=0;      //reseteo contador de tiempo
      flag_ventaneo=1;        //hago que deje de leer el adc
      flag_tx=1;              //flag para tx por serie
      i=0;                    //reseteo posicion del vector
    }

  }

  if (flag_tx == 1){
    buildMessage();
  }
}

void buildMessage(){
  String message = "";
  DynamicJsonDocument doc(81000);
//  JsonArray currentList = doc.to<JsonArray>();
//  JsonArray voltageList = doc.to<JsonArray>();
  JsonArray currentList = doc.createNestedArray("current");
  JsonArray voltageList = doc.createNestedArray("voltage");
  
  doc["mac"] = WiFi.macAddress();
  Serial.println("Inicio de Txx......");
  Serial.println("Current;Voltage");
  for (a=0;a<window_ticks;a++){
    currentList.add(adc_current[a]);
//    Serial.print(adc_current[a]);
//    Serial.print(";");
    voltageList.add(adc_voltage[a]);
//    Serial.println(adc_voltage[a]);
  }

//  Serial.println("Largo de currentList: "+currentList.size());
  Serial.println("Largo de currentList: ");
  Serial.println("Fin de Txx......");
  

  serializeJson(doc, message);
//  Serial.println(message);
  postMeasure(message);
  doc.clear();
  flag_ventaneo=0;        //hago que vuelva a leer el adc
  flag_tx=0;              //flag para apagar tx
  delay(2000);
//  delay(10000);
//  delay(10000);
//  delay(10000);
//  delay(10000);
  ESP.restart();
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
  http.begin(ApiHost + "/whoami/");
  http.addHeader("Content-Type", "application/json");
  
  String message = "";
  StaticJsonDocument<300> jsonDoc;
  jsonDoc["data"] = newData;
  serializeJson(jsonDoc, message);

  Serial.println(message);
  int httpCode = http.POST(message);
  processResponse(httpCode, http);
}

void postMeasure(String message){
  HTTPClient http;
  http.begin(ApiHost + "/measure/");
  http.addHeader("Content-Type", "application/json");
  
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
