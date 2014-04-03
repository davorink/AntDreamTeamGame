/**
 * condition.java represents the condition for the Ant Game. 
 * @author Davorin Kopic
 * @version 31/3/2014
 */
public class Condition {
  Friend,             /* cell contains an ant of the same color */
  Foe,                /* cell contains an ant of the other color */
  FriendWithFood,     /* cell contains an ant of the same color carrying food */
  FoeWithFood,        /* cell contains an ant of the other color carrying food */
  Food,               /* cell contains food (not being carried by an ant) */
  Rock,               /* cell is rocky */
  Marker(marker),     /* cell is marked with a marker of this ant's color */
  FoeMarker,          /* cell is marked with *some* marker of the other color */
  Home,               /* cell belongs to this ant's anthill */
  FoeHome             /* cell belongs to the other anthill */
}
