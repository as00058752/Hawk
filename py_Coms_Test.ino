#include "SerialTransfer.h"
#include <Servo.h>

Servo servoX;
Servo servoY;
SerialTransfer myTransfer;

void setup()
{
  servoX.attach(3); //X-axis servo
  smooth_write(servoX, 90);
  servoY.attach(5); //Y-axis servo
  smooth_write(servoY, 90);
  pinMode(8, OUTPUT); //Laser output
  digitalWrite(8, LOW);
  Serial.begin(115200);
  myTransfer.begin(Serial);
}


void loop()
{
  if(Serial.available() > 0){
    int dataX = Serial.parseInt();
    if(dataX == -1)
      dataX = 90;
    smooth_write(servoX, dataX);
    Serial.println("Recieved");
    delay(5);
    Serial.read();
    int dataY = Serial.parseInt();
    if(dataY == -1)
      dataY = 90;
    smooth_write(servoY, dataY);
    Serial.println("Recieved");
    Serial.read();
    int dataLaser = Serial.parseInt();
    if(dataLaser == 1)
      digitalWrite(8, HIGH);
    else
      digitalWrite(8,LOW);
    Serial.println("Recieved");
    Serial.read();
  }
}

void smooth_write(Servo servo, int ang) {
  int cur_ang = servo.read();
  if(ang > cur_ang + 5) {
    for(int i = cur_ang; i <= ang; i += 1) {
      servo.write(i);
      delay(5);
    }
  }  
  else if(ang < cur_ang - 5) {
    for(int i = cur_ang; i >= ang; i -= 1) {
      servo.write(i);
      delay(5);
    }
  }
  else {
    servo.write(ang);
    delay(10);
  }
}
