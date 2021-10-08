//
// SDL_ESP_BC24_GETIP
// Collection of routines to get IP from  AP Access Point
// SwitchDoc
// June 2018 Version 1.0
//
//
// 1) SmartConfig
// 3) WPS Button
// 4) Local AP

// definitions

// BC24 - define if you have a BC24 (that has an ESP32 on board)
// BC24DEBUG - define if you want debugging output


void WiFiEvent(WiFiEvent_t event, system_event_info_t info);

int WPSReconnectCount = 0;   // for timeout on WPS

//
// Use Local AP  (192.168.4.1)
//


bool localAPGetIP(long apTimeOutSeconds)
{

  bool myWiFiPresent;
  myWiFiPresent = false;

  // set up AP point for reading ssid/password since SmartConfig didn't work
  // Set up Wifi


#define WL_MAC_ADDR_LENGTH 6
  // Append the last two bytes of the MAC (HEX'd) to string to make unique
  uint8_t mac[WL_MAC_ADDR_LENGTH];
  WiFi.softAPmacAddress(mac);
  APssid = "NM-ESP32-" + WiFi.macAddress().substring(9);


  //WiFiManager
  //Local intialization. Once its business is done, there is no need to keep it around
  WiFiManager wifiManager;
//#ifdef BC24DEBUG
  wifiManager.setDebugOutput(true);
//#else
//  wifiManager.setDebugOutput(false);
//#endif
  //reset saved settings
  //wifiManager.resetSettings();

  vTaskDelay(1000 / portTICK_PERIOD_MS);

  //set callback that gets called when connecting to previous WiFi fails, and enters Access Point mode
  wifiManager.setAPCallback(configModeCallback);
  //fetches ssid and pass and tries to connect
  //if it does not connect it starts an access point with the specified name
  wifiManager.setTimeout(apTimeOutSeconds);
  //and goes into a blocking loop awaiting configuration
  if (!wifiManager.autoConnect(APssid.c_str())) {
    Serial.println("failed to connect and hit timeout");
#ifdef BC24
    blinkLED(4, 300);  // blink 4, failed to connect
    BC24OneBlink(Red, 1000);
#else
  vTaskDelay(1000 / portTICK_PERIOD_MS);
#endif
    //reset and try again, or maybe put it to deep sleep
    //ESP.reset();
    //delay(1000);
    myWiFiPresent = false;
  }
#ifdef BC24
  xSemaphoreTake( xSemaphoreSingleBlink, 10);   // Turn off single blink
#endif
  if (WiFi.status()  == WL_CONNECTED)
  {
    myWiFiPresent = true;
#ifdef BC24
    BC24OneBlink(Green, 1000);
#else
  vTaskDelay(1000 / portTICK_PERIOD_MS);
#endif
  }


  return myWiFiPresent;
}
