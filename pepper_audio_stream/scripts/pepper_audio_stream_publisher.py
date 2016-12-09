#! /usr/bin/env python
# -*- encoding: UTF-8 -*-
"""This script will enable audio streaming over ROS.
You will of course need the IP Adress from the Robot, after getting that we will get a stream with the sounds comming
in the microphones in Pepper.
"""
import rospy
import qi
import argparse
import sys


# This will build the connection with Pepper or output an error message.
# after the connection is built we can start the ROS Node and probably start publishing Audio.
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