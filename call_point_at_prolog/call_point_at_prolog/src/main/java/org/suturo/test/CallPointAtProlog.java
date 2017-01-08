package org.suturo.test;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.ros.exception.RemoteException;
import org.ros.exception.RosRuntimeException;
import org.ros.exception.ServiceNotFoundException;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.service.ServiceClient;
import org.ros.node.service.ServiceResponseListener;
import org.ros.message.Time;

import geometry_msgs.PoseStamped;
import geometry_msgs.Pose;
import geometry_msgs.Point;

import pepper_point_at_iai.PepperPointAtRequest;
import pepper_point_at_iai.PepperPointAtResponse;

import pepper_text_to_speech.*;



/**
 * Service client to call the PointAt Service for Pepper
 * 
 * @author Sascha
 */
public class CallPointAtProlog extends AbstractNodeMain {

  ServiceClient<PepperPointAtRequest, pepper_point_at_iai.PepperPointAtResponse> serviceClient;
  ConnectedNode node;  
  
  @Override
  public GraphName getDefaultNodeName() {
    return GraphName.of("PointAt/client");
  }


  @Override
  public void onStart(final ConnectedNode connectedNode) {
    
    // save reference to the ROS node
    this.node = connectedNode;

  }

  public boolean callPointAtService(geometry_msgs.PoseStamped point, String description){
    return callPointAtServiceRaw(point.getPose().getPosition().getX(), point.getPose().getPosition().getY(), point.getPose().getPosition().getZ(), point.getHeader().getFrameId(), description);
  }

  /**
   * Call the pepper_point_at service and return the successs
   * 
   * @return An ObjectDetection with the pose and type of the detected object
   */
  public boolean callPointAtServiceRaw(double xPosition, 
    double yPosition, double zPosition, String frameID, String description) {
    
    // wait for node to be ready
    try {
      while(node == null) {
        Thread.sleep(200);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    
    // start service client
    try {
      serviceClient = node.newServiceClient("pepper_point_at", pepper_point_at_iai.PepperPointAt._TYPE);
      
    } catch (ServiceNotFoundException e) {
      throw new RosRuntimeException(e);
    }
    
    final pepper_point_at_iai.PepperPointAtRequest req = serviceClient.newMessage();

    req.getPoint().getHeader().setFrameId(frameID);
    req.getPoint().getPose().getPosition().setX(xPosition);
    req.getPoint().getPose().getPosition().setY(yPosition);
    req.getPoint().getPose().getPosition().setZ(zPosition);   
    req.setDescription(description); 
    // call the service and 
    serviceClient.call(req, new ServiceResponseListener<pepper_point_at_iai.PepperPointAtResponse>() {
      
      @Override
      public void onSuccess(pepper_point_at_iai.PepperPointAtResponse response) {
          node.getLog().info(response.getReturn());

      }

      @Override
      public void onFailure(RemoteException e) {
        throw new RosRuntimeException(e);
      }
    });
    
    return true;
  }

    public boolean callSayService(String description){
    // wait for node to be ready
    try {
      while(node == null) {
        Thread.sleep(200);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    
    ServiceClient<PepperSayRequest, pepper_text_to_speech.PepperSayResponse> serviceClientSay;
    // start service client
    try {
      serviceClientSay = node.newServiceClient("pepper_say", pepper_text_to_speech.PepperSay._TYPE);
      
    } catch (ServiceNotFoundException e) {
      throw new RosRuntimeException(e);
    }
    
    final pepper_text_to_speech.PepperSayRequest req = serviceClientSay.newMessage();
 
    req.setStr(description); 
    // call the service and 
    serviceClientSay.call(req, new ServiceResponseListener<pepper_text_to_speech.PepperSayResponse>() {
      
      @Override
      public void onSuccess(pepper_text_to_speech.PepperSayResponse response) {
          node.getLog().info(response.getReturn());

      }

      @Override
      public void onFailure(RemoteException e) {
        throw new RosRuntimeException(e);
      }
    });
    
    return true;
  }




}
