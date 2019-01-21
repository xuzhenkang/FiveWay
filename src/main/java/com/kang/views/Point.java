package com.kang.views;

import java.awt.Color;
import java.util.LinkedList;

public class Point {
	private int x;
	private int y;
	private Color color;
	private boolean isSelected; // 有没有被选中
	private boolean isDead; // 有没有被吃掉，被吃掉为true
	public static final int DIAMETER = 30;
	private LinkedList<PointRecord> pointTrack = new LinkedList<PointRecord>(); // 该棋子轨迹
	private int index;// 在chessList中的索引

	public Point(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.isSelected = false;
		this.isDead = false;
		this.color = color;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getY() {
		return y;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public Color getColor() {
		return color;
	}

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		if (isDead == true) {
			this.isSelected = false;
		}
		this.isDead = isDead;
	}

	public LinkedList<PointRecord> getPointTrack() {
		return pointTrack;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return "[index:" + index + ",x:" + x + ",y:" + y + ",isDead:" + isDead + ",isSelected:" + isSelected + ",color:"
				+ color + "]";
	}

	// 每步数据记录
	class PointRecord {
		int x;
		int y;
		boolean isSelected;
		boolean isDead;

		public PointRecord(int x, int y, boolean isSelected, boolean isDead) {
			this.x = x;
			this.y = y;
			this.isSelected = isSelected;
			this.isDead = isDead;
		}
	}

	public void traversePointTrack() {
		if (pointTrack.size() != 0) {
			int n = 0;
			for (PointRecord c : pointTrack) {
				System.out.print("c[" + n + "](" + c.x + "," + c.y + "," + c.isDead + "," + c.isSelected + ")");
				n++;
			}
			System.out.println();
		} else {
			System.out.println("pointTrack.size = 0");
		}
	}

}
