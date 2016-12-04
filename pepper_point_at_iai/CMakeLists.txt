cmake_minimum_required(VERSION 2.8.3)
project(pepper_point_at_iai)

## Find catkin macros and libraries
## if COMPONENTS list like find_package(catkin REQUIRED COMPONENTS xyz)
## is used, also find other catkin packages
find_package(catkin REQUIRED COMPONENTS
   rospy
   message_generation
   std_msgs
   geometry_msgs
)

## Generate services in the 'srv' folder
add_service_files(
  FILES
  PepperPointAt.srv
)

## Generate added messages and services with any dependencies listed here
generate_messages(
  DEPENDENCIES
  geometry_msgs  # Or other packages containing msgs
)

####
## catkin specific configuration ##
###################################
## The catkin_package macro generates cmake config files for your package
## Declare things to be passed to dependent projects
## INCLUDE_DIRS: uncomment this if you package contains header files
## LIBRARIES: libraries you create in this project that dependent projects also need
## CATKIN_DEPENDS: catkin_packages dependent projects also need
## DEPENDS: system dependencies of this project that dependent projects also need
catkin_package(
#  INCLUDE_DIRS include
#  LIBRARIES text_to_speech
#  CATKIN_DEPENDS other_catkin_pkg
#  DEPENDS system_lib
)
