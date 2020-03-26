#Author: Anthony Lopez
#Contributors: Anh (Steven) Nguyen
#Last update: 03/26/2020 by Anh (Steven) Nguyen

import socket

HOST = "localhost"
PORT = 8080

i = 0
xdata = 0
ydata = 0
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
sock.connect((HOST, PORT))

while True:
    xdata = sock.recv(5)
    ydata = sock.recv(5)
    try:
        xdata = int(xdata)
        ydata = int(ydata)
    except ValueError:
        sock.close()
        print("Socket closed")
        break
    print (xdata , ydata)
