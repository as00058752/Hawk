#Author: Anthony Lopez
#Contributors: Anh (Steven) Nguyen
#Last update: 05/03/2020 by Anh (Steven) Nguyen

import socket
import serial
import time

HOST = "localhost"
PORT = 8190

sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
sock.connect((HOST, PORT))

arduino = serial.Serial('COM3', 115200)
time.sleep(2)
verify = 0

def getDataFromJava():
    data = int(sock.recv(1024).decode("utf-8").rstrip("\n"))
    sock.send("0".encode("utf-8"))
    return data

def sendDataToArduino(int data):
    arduino.write(str(data).encode() + '.'.encode())
        verify = arduino.readline()
        if verify == 0:
            print("Communication with Arduino interupted")

while True:
    try:
        panData = getDataFromJava()
        tiltData = getDataFromJava()
        engageData = getDataFromJava()
        
        sendDataToArduino(panData)
        sendDataToArduino(tiltData)
        sendDataToArduino(engageData)
        
        print (panData , tiltData , engageData)

        ########################################################
        # Code to send data to Arduino goes here               #
        # panData: pan angle as an int                         #
        # tiltData: tilt angle as an int                       #
        # engageData: engage flag as an int (0 is off, 1 is on)#
        ########################################################

    except ValueError:
        print("Value Error")

    if panData == -1:
        sendDataToArduino(-1)
        sendDataToArduino(-1)
        sendDataToArduino(0)
        sock.close()
        print("Socket closed")
        break
