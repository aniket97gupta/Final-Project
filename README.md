# MOBILE APPLICATION ACCESS CONTROL
### The face is one of the easiest ways to distinguish the individual identity of each other. Face recognition is a personal identification system that uses personal characteristics of a person to identify the person's identity.The area of this project face detection system with face recognition is Image processing. After the face is recognized as a valid user, the application will send a Bluetooth signal to the circuit and the door will unlock.
## SCREENSHOTS
![Screenshot_2019-06-14-14-05-35-106_com miui gallery](https://user-images.githubusercontent.com/50113128/60082838-88d39b80-96bf-11e9-9375-50015bbfd3b8.png)
![Screenshot_2019-06-14-14-05-56-944_com miui gallery](https://user-images.githubusercontent.com/50113128/60082956-c7695600-96bf-11e9-9f27-f299356edbcb.png)
![Screenshot_2019-06-14-14-06-33-876_com miui gallery](https://user-images.githubusercontent.com/50113128/60082957-c7695600-96bf-11e9-989f-ac388c8bd445.png)
![Screenshot_2019-06-14-14-06-45-348_com miui gallery](https://user-images.githubusercontent.com/50113128/60082958-c801ec80-96bf-11e9-98a5-417ef71fb6f2.png)
![Screenshot_2019-06-14-14-06-58-196_com miui gallery](https://user-images.githubusercontent.com/50113128/60082961-c801ec80-96bf-11e9-8060-a271de770eba.png)
![Screenshot_2019-06-14-14-07-13-391_com miui gallery](https://user-images.githubusercontent.com/50113128/60082962-c89a8300-96bf-11e9-888b-f0404822296b.png)
![Screenshot_2019-06-14-14-07-22-467_com miui gallery](https://user-images.githubusercontent.com/50113128/60082963-c89a8300-96bf-11e9-8ac9-b13b8fb723ef.png)
## USAGE
#### Clone this repository
#### Open project in android studio
#### Optional: Configure firebase if required. Check out comments in ReviewResults activity
#### Compile, install the Apk
#### Go to training, set an ID and capture a face to train. Repeat this a couple of times with different people and IDs
#### Go to recognition, click scan and try to capture everyone in the video stream. The detected faces will be recognized and shown.
#### Once done, stop scanning and click submit to review capture results. It will send a bluetooth siggnal to the arduino board and the led on it will turn on.
## KNOWN ISSUES AND TODO
#### Face recognition is not accurate. Far from it
#### Recognition model gets created each time "Recognize" tab is clicked. Slows down as number of training images increase.
## DIRECTORIES
#### /sdcard/facerecogOCV - Training images
