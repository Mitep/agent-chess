package model.chess;

import model.agent.AID;

public class FigureCalculation {

	private AID figure;
	private int current_position;
	private int new_position;
	private double efficiency = 0;
	
	public FigureCalculation() {}

	public AID getFigure() {
		return figure;
	}

	public void setFigure(AID figure) {
		this.figure = figure;
	}

	public int getCurrent_position() {
		return current_position;
	}

	public void setCurrent_position(int current_position) {
		this.current_position = current_position;
	}

	public int getNew_position() {
		return new_position;
	}

	public void setNew_position(int new_position) {
		this.new_position = new_position;
	}

	public double getEfficiency() {
		return efficiency;
	}

	public void setEfficiency(double efficiency) {
		this.efficiency = efficiency;
	}
	
}
