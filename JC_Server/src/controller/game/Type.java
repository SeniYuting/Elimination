package controller.game;

public enum Type {
	normal,
	bomb, // tape explode the cross .   a line with five tiles can produce this prop.  remain the middle one
	blink, // tape and explode a round . and use to link.  random the place to produce this.
	time;
}
