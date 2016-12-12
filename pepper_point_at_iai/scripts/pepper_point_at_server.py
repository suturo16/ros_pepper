#! /usr/bin/env python
# -*- encoding: UTF-8 -*-
"""
This module is for the Point at function from Pepper inside a ROS Environment.
It was created by Rafael Miranda in December 2016.
"""
from pepper_point_at_iai.srv import *
from geometry_msgs.msg import Point
import rospy
import qi
import argparse
import sys

# This is just a variable which says which arm pepper will use.
ARM = "RArm" # can be RArm, LArm or Arms
# The frame, can be 0=torso, 1=world or 2=robot.
FRAME = 2
# Fraction max speed, no idea what it is or what it does
MAX_SPEED = 0.8
# The Text To Speech Service of Pepper, hopefully it is not None when we need it.
trackerService = None
tts = None

# This method gets the String from our SRV File and uses the Text to Speech from Pepper
# to say stuff. Until now we will only return Complete.
def pepper_point_at(req):
    point = [float (req.pointAtPoint.x), float(req.pointAtPoint.y), float(req.pointAtPoint.z)]
    print "Got something to do! Pointing at (" +\
          str(req.pointAtPoint.x) + ", " + str(req.pointAtPoint.y) + str(req.pointAtPoint.z) + ")"
    # so that pepper will say something before pointing at it. this will only work if the service is not available.
    tts.say("Look at this shit!")
    # now let's point somewhere!
    #pointAt(const std::string& Effector, const std::vector<float>& Position, const int& Frame, const float& FractionMaxSpeed)
    trackerService.pointAt(ARM, point, FRAME, MAX_SPEED)
    return PepperPointAtResponse(PepperPointAtResponse.COMPLETED)


# The server announces the service to our ROSCORE and stays open until the Core is closed or the Node is manually
# terminated.
def pepper_point_at_server():
    rospy.init_node('pepper_point_at_server')
    s = rospy.Service('pepper_point_at', PepperPointAt, pepper_point_at)
    rospy.spin()


# We are using the NAO API from Aldebaran, we start the session with the given IP address. And save the TTS as
# a global variable in this script. That is the best way of getting it inside the Say Method.
if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument("--ip", type=str, default="127.0.0.1",
                        help="Robot IP address. On robot or Local Naoqi: use '127.0.0.1'.")
    parser.add_argument("--port", type=int, default=9559,
                        help="Naoqi port number")
    args = parser.parse_args()
    session = qi.Session()
    try:
        session.connect("tcp://" + args.ip + ":" + str(args.port))
    # this will give us some light if something breaks while starting the service.
    except RuntimeError:
        print ("Can't connect to Naoqi at ip \"" + args.ip + "\" on port " + str(args.port)
               + ".\n" + "Please check your script arguments. Run with -h option for help.")
        sys.exit(1)
    global tts
    global trackerService
    # Get the service ALTracker.
    trackerService = session.service("ALTracker")
    # Get the text to speech.
    tts = session.service("ALTextToSpeech")
    pepper_point_at_server()