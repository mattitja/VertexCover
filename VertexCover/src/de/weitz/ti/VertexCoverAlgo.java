// Copyright (c) 2014, Dr. Edmund Weitz.  All rights reserved.

package de.weitz.ti;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

class VertexCoverAlgo {
	static String server = "weitz.de";
	static String submitURL = "submit";

	// please enter your name here - see also the class
	// VertexCoverAlgoPassword for the password
	static String name = "miro";
	// start with problem level 1 - see PDF for details
	static int problemLevel = 2;
	// change to true if you want to repeat the last exercise
	static boolean repeatLast = false;

	boolean[][] edges;
	int max;

	VertexCoverAlgo(boolean edges[][]) {
		this.edges = edges;
		this.max = edges.length - 1;
	}

	ArrayList<Integer> input;
	int[] countArray;
	boolean[][] edgesCopy;
	Set<Integer> finalSolution;
	boolean finish = false;

	int[] solve() {
		input = createInput();
		System.out.println("Input: " + input);

		for (int dimension = 1; dimension < input.size(); dimension++) {
			getSubsets(dimension, 0, new HashSet<Integer>());
			if (finish) {
				break;
			}
		}

		return toInt(finalSolution);
	}

	private ArrayList<Integer> createInput() {
		ArrayList<Integer> input = new ArrayList<>();
		for (int i = 1; i < edges.length; i++) {
			input.add(i);
		}
		return input;
	}

	private void getSubsets(int dimension, int id, Set<Integer> current) {
		if (finish) {
			return;
		}
		if (current.size() == dimension) {
			finish = test(new HashSet<>(current));
			if (finish) {
				finalSolution = new HashSet<>(current);
			}
			return;
		}

		if (id == input.size())
			return;
		Integer x = input.get(id);
		current.add(x);
		getSubsets(dimension, id + 1, current);
		current.remove(x);
		getSubsets(dimension, id + 1, current);
	}

	private boolean test(Set<Integer> set) {
		edgesCopy = cloneDeepBooleanArray(edges);
		countArray = new int[edgesCopy.length + 1];
		for (int value : set) {
			falsefy(value);
		}
		refreshCount();
		return solutionFound();
	}

	private void refreshCount() {
		for (int i = 1; i < edgesCopy.length; i++) {
			countArray[i] = 0;
			for (int j = 1; j < edgesCopy[i].length; j++) {
				if (edgesCopy[i][j]) {
					countArray[i]++;
				}
			}
		}
	}

	private boolean solutionFound() {
		int sum = 0;
		for (int i = 1; i < countArray.length; i++) {
			sum += countArray[i];
		}
		return sum == 0;
	}

	private void falsefy(int highest) {
		for (int i = 1; i < edgesCopy[highest].length; i++) {
			if (edgesCopy[highest][i]) {
				edgesCopy[highest][i] = false;
				edgesCopy[i][highest] = false;
			}
		}
	}

	public int[] toInt(Set<Integer> set) {
		int[] a = new int[set.size()];
		int i = 0;
		for (Integer val : set)
			a[i++] = val;
		return a;
	}

	public boolean[][] cloneDeepBooleanArray(boolean[][] array) {
		int rows = array.length;
		boolean[][] newArray = (boolean[][]) array.clone();
		for (int row = 0; row < rows; row++) {
			newArray[row] = (boolean[]) array[row].clone();
		}
		return newArray;
	}

}