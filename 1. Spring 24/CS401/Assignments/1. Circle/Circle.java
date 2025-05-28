package com.chaitanya.cs401;
													//user defined class
													
public class Circle {
	private static double PI = 3.14; 				//defining pi value
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    public double getRadius() { 					//getting instance radius variable
        return radius;
    }

    public void setRadius(double radius) { 			//using set 
        this.radius = radius;
    }

    @Override
    public String toString() {
        return "Circle{" + "radius=" + radius + '}'; //Concatenation of string
    }

    public double getArea() { 						//calculating area of circle
        return PI * this.radius * this.radius;
    }
    
}