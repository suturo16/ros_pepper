/** <module> suturo_storage_place
   

@author Sascha Jongebloed
@license BSD
*/

:- module(suturo_prolog_node,
    [
      createRosNode/1,
      changeNumber/4
    ]).

:- use_module(library('semweb/rdf_db')).
:- use_module(library('semweb/rdfs')).

:-  rdf_meta
    createRosNode(r),
    changeNumber(r,r,r,r).

createRosNode(Node) :-
  jpl_new('org.suturo.test.CallPointAtProlog', [], Node),
  jpl_list_to_array(['org.suturo.test.TestNode'], Arr),
  jpl_call('org.knowrob.utils.ros.RosUtilities', runRosjavaNode, [Node, Arr], _),
  nb_setval(currClass, Node).

changeNumber(XPostion, YPosition, ZPostion, AnswerObject) :-
  nb_getval(currClass, Node),
  jpl_call(Node, 'callPointAtServiceRaw', [XPostion, YPosition, ZPostion], AnswerObject).
  

