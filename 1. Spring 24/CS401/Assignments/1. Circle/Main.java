package com.chaitanya.cs401;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		System.out.println("Enter the radius of a circle:");
        Scanner scanner = new Scanner(System.in);
        double radius = scanner.nextDouble();
        Circle circle = new Circle(radius);
        System.out.println(String.format("Area of a circle is: %.0f", circle.getArea())); //printing double value with zero precision
	
	}

}