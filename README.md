# Wii U PC Controller

[![Join the chat at https://gitter.im/jtf323/Wii-U-PC-Controller](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/jtf323/Wii-U-PC-Controller?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

Control your PC with your Wii U gamepad controller!

The goal of this project is to be able to control your computers mouse using your Wii U gamepad. Currently you can see some basic debugging information on both the PC server and Wii U gamepad as well as perform left and right clicks using ZL / ZR. Next, I will implement moving of the mouse using the analog sticks.

Once mouse control is fully implemented I will begin to explore the possibility of a virtual keyboard.

## Installation / Usage
* In the resources folder you'll find a file called `index.html`. Open this up and find the following line
`request.open("POST", "http://192.168.1.210:1337/Command", true);`
* Change `192.168.1.210` to the IP address of the computer running the server
* (TODO: This will be completed once the project is in a fully fucntional state)