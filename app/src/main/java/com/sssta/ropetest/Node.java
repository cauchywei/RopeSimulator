package com.sssta.ropetest;

/**
 * Created by cauchywei on 15/3/23.
 */
public class Node {

    public static  Vector GRIVATY = new Vector(0,10f);

    public Vector position = new Vector();
    private boolean isStable;

    private Vector velocity = new Vector();
    private Vector force = new Vector();

    private float mass = 5;

    private Node pre;
    private Node next;

    public Node(Vector position) {
        this.position = position;
    }

    public Node(float x,float y) {
        this.position = new Vector(x,y);
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public boolean isStable() {
        return isStable;
    }

    public void setStable(boolean isStable) {
        this.isStable = isStable;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    public float getX(){
        return getPosition().x;
    }

    public float getY(){
        return getPosition().y;
    }

    public void setX(float x){
        getPosition().x = x;
    }

    public void setY(float y){
        getPosition().y = y;
    }

    public void incDeltaX(float dx){
        setX(getX() + dx);
    }

    public void incDeltaY(float dy){
        setY(getY() + dy);
    }

    public float getSpeedX(){
        return getVelocity().x;
    }

    public float getSpeedY(){
        return getVelocity().y;
    }

    public void setSpeedX(float x){
        getVelocity().x = x;
    }

    public void setSpeedY(float y){
        getVelocity().y = y;
    }

    public void incSpeedDeltaX(int dx){
        setSpeedX(getSpeedX() + dx);
    }
    public void incSpeedDeltaY(int dy){
        setSpeedY(getSpeedY() + dy);
    }

    public Node getPre() {
        return pre;
    }

    public void setPre(Node pre) {
        this.pre = pre;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public void implicated() {
        if (isStable()){
//            Vector bforce = next.getPosition().minus(getPosition());
//            next.setForce(bforce);
        }else{
            if (next == null){
                pre.getForce().addQ(getVelocity());
            }else{
                Vector dv = pre.getVelocity().minus(next.getVelocity());
                pre.getForce().addQ(getVelocity().times(10));
                next.getForce().addQ(getVelocity().times(-1).times(10));
            }
        }
    }

    public Vector implicated(Node node, Vector ds) {
        Node implocatedNode = null;

        if (pre != node){
            implocatedNode = pre;
        }

        if(next != node){
            implocatedNode = next;
        }

        if (implocatedNode!=null){

        }

        return ds;
    }

    public Vector getForce() {
        return force;
    }

    public void setForce(Vector force) {
        this.force = force;
    }

    public void addForce(Vector force) {
        this.force.addQ(force);
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public void calulate(float dt){
        if (!isStable()){
            velocity.addQ(force.div(mass).times(dt));
            position.addQ(velocity.times(dt));
        }

    }


    public boolean contains(Vector vector) {
        return getPosition().distance(vector)<=Rope.SIZE;
    }
}
