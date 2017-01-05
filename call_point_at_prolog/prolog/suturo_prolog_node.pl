/** <module> suturo_storage_place
   

@author Sascha Jongebloed
@license BSD
*/

:- module(suturo_prolog_node,
    [
      pepper_point_at_client/5
    ]).

:- use_module(library('semweb/rdf_db')).
:- use_module(library('semweb/rdfs')).

:-  rdf_meta
    pepper_point_at_client(r,r,r,r,r).

pepper_point_at_client(XPostion, YPosition, ZPostion, FrameID, AnswerObject) :-
  jpl_new('org.suturo.test.CallPointAtProlog', [], Node),
  jpl_list_to_array(['org.suturo.test.TestNode'], Arr),
  jpl_call('org.knowrob.utils.ros.RosUtilities', runRosjavaNode, [Node, Arr], _),
  jpl_call(Node, 'callPointAtServiceRaw', [XPostion, YPosition, ZPostion, FrameID], AnswerObject).



  

