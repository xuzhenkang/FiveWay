package kang.views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.util.LinkedList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ChessBoard extends JPanel implements MouseListener {
	private static final long serialVersionUID = 1L;
	public static final int MARGIN = 40; // 边距
	public static final int GRID_SPAN = 50; // 网格间距
	public static final int ROWS = 4; // 棋盘行数
	public static final int COLS = 4; // 棋盘列数
	public static final int STEP = 1; // 棋子可以行走的步长

	private int blackChessNum;// 活着的黑棋数量
	private int whiteChessNum;// 活着的白棋数量
	private int endangeredBlackChessNum;// 当前受威胁的黑棋数量
	private int endangeredWhiteChessNum;// 当前受威胁的白棋数量
	
	
	Point[] chessList = new Point[2 * (COLS + 1)];
	boolean isBlack = true; // 默认黑棋优先,是否该黑棋走
	boolean gameOver = false; // 是否结束
	int xIndex, yIndex; // 当前移动的棋子索引
	// 下棋轨迹
	LinkedList<Point> track = new LinkedList<Point>();

	
	public int getBlackChessNum() {
		return blackChessNum;
	}

	private void setBlackChessNum() {
		int n = 0;
		for (Point p : chessList) {
			if (p.getColor() == Color.BLACK && !p.isDead()) n++;
		}
		this.blackChessNum = n;
	}

	public int getWhiteChessNum() {
		return whiteChessNum;
	}

	private void setWhiteChessNum() {
		int n = 0;
		for (Point p : chessList) {
			if (p.getColor() == Color.WHITE && !p.isDead()) n++;
		}
		this.blackChessNum = n;
	}

	public int getEndangeredBlackChessNum() {
		return endangeredBlackChessNum;
	}

	@SuppressWarnings("unused")
	private void setEndangeredBlackChessNum(int endangeredBlackChessNum) {
		this.endangeredBlackChessNum = endangeredBlackChessNum;
	}

	public int getEndangeredWhiteChessNum() {
		return endangeredWhiteChessNum;
	}

	@SuppressWarnings("unused")
	private void setEndangeredWhiteChessNum(int endangeredWhiteChessNum) {
		this.endangeredWhiteChessNum = endangeredWhiteChessNum;
	}

	/* 遍历下棋轨迹 */
	public void traverseTrack() {
		if (track.size() != 0) {
			int n = 0;
			for (Point c : track) {
				System.out.println(c);
				n++;
			}
			System.out.println("::: n = " + n);
		} else {
			System.out.println("track.size = 0");
		}
	}

	/* 初始化棋子数组 */
	public void initializeChessList() {
		for (int i = 0; i < chessList.length; i++) {
			if (i < chessList.length / 2) { // 前一半是白棋
				chessList[i] = new Point(i, 0, Color.WHITE);
			} else { // 后一半是黑棋
				chessList[i] = new Point(i - chessList.length / 2, 4,
						Color.BLACK);
			}
			chessList[i].setIndex(i);
		}
		this.blackChessNum = COLS + 1;
		this.whiteChessNum = COLS + 1;
		this.endangeredBlackChessNum = 0;
		this.endangeredWhiteChessNum = 0;
	}

	Image img;
	Image shadows;
	Color colortemp;

	public ChessBoard() {
		img = Toolkit.getDefaultToolkit().getImage("images/board.jpg");
		shadows = Toolkit.getDefaultToolkit().getImage("shadows.jpg");
		/* 初始化棋子 */
		initializeChessList();
		this.addMouseListener(this);
		this.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
//				int x1 = (e.getX() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
//				int y1 = (e.getY() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
			}
		});
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g); // 获得画笔

		/* 获得图片的宽度与高度 */
		int imgWidth = img.getWidth(this);
		int imgHeight = img.getHeight(this);
		/* 获得窗口宽度与高度 */
		int FWidth = this.getWidth();
		int FHeight = this.getHeight();

		int x = (FWidth - imgWidth) / 2;
		int y = (FHeight - imgHeight) / 2;

		/* 画背景 */
		g.drawImage(img, x, y, null);
		/* 画棋盘 */
		for (int i = 0; i <= ROWS; i++) {
			g.drawLine(MARGIN, MARGIN + i * GRID_SPAN, MARGIN + COLS
					* GRID_SPAN, MARGIN + i * GRID_SPAN);
		}
		for (int i = 0; i <= COLS; i++) {
			g.drawLine(MARGIN + i * GRID_SPAN, MARGIN, MARGIN + i * GRID_SPAN,
					MARGIN + ROWS * GRID_SPAN);
		}
		/* 画棋子 */
		for (int i = 0; i < chessList.length; i++) {
			if (chessList[i].isDead())
				continue;
			/* 网格交叉点x，y坐标 */
			int xPos = chessList[i].getX() * GRID_SPAN + MARGIN;
			int yPos = chessList[i].getY() * GRID_SPAN + MARGIN;
			g.setColor(chessList[i].getColor()); // 设置颜色
			colortemp = chessList[i].getColor();
			if (colortemp == Color.black) { // 画黑棋
				RadialGradientPaint paint = new RadialGradientPaint(xPos
						- Point.DIAMETER / 2 + 25, yPos - Point.DIAMETER / 2
						+ 10, 20, new float[] { 0f, 1f }, new Color[] {
						Color.WHITE, Color.BLACK });
				((Graphics2D) g).setPaint(paint);
				((Graphics2D) g).setRenderingHint(
						RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				((Graphics2D) g).setRenderingHint(
						RenderingHints.KEY_ALPHA_INTERPOLATION,
						RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);
			} else if (colortemp == Color.white) { // 画白棋
				RadialGradientPaint paint = new RadialGradientPaint(xPos
						- Point.DIAMETER / 2 + 25, yPos - Point.DIAMETER / 2
						+ 10, 70, new float[] { 0f, 1f }, new Color[] {
						Color.WHITE, Color.BLACK });
				((Graphics2D) g).setPaint(paint);
				((Graphics2D) g).setRenderingHint(
						RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				((Graphics2D) g).setRenderingHint(
						RenderingHints.KEY_ALPHA_INTERPOLATION,
						RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);
			}

			Ellipse2D e = new Ellipse2D.Float(xPos - Point.DIAMETER / 2, yPos
					- Point.DIAMETER / 2, 34, 35);
			((Graphics2D) g).fill(e);

			if (chessList[i].isSelected()) { // 如果被选中，画红矩形框
				g.setColor(Color.red);
				g.drawRect(xPos - Point.DIAMETER / 2,
						yPos - Point.DIAMETER / 2, 34, 35);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) { // 鼠标在组件上按下时调用
		// 游戏结束时，不再能下
		if (gameOver)
			return;
		String colorName = isBlack ? "黑棋" : "白棋";
		// 将鼠标点击的坐标位置转换成网格索引
		xIndex = (e.getX() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
		yIndex = (e.getY() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
		// System.out.println("xIndex:" + xIndex + ";yIndex:" + yIndex);
		// 落在棋盘外不能下
		if (xIndex < 0 || xIndex > ROWS || yIndex < 0 || yIndex > COLS)
			return;
		Point p;
		// 如果x，y位置已经有棋子存在, 并且该棋子为我方棋子，则该棋子被选中
		if ((p = findChess(xIndex, yIndex)) != null
				&& findSelectedPoint(chessList) == null
				&& (isBlack && p.getColor() == Color.black || !isBlack
						&& p.getColor() == Color.white)) {
			p.setSelected(true);
			this.repaint();
		}

		/* 在chessList中找到被选中的棋子，并返回 */
		Point selectedPoint = findSelectedPoint(chessList);
		/*
		 * 若点击的位置与已经被选中的棋子的位置横纵网格坐标之差的绝对值都等于步长STEP，并且该位置不可以有对方棋子则可以落子，
		 * 修改对应的横纵网格坐标
		 */
		if (selectedPoint != null
				&& (Math.abs(xIndex - selectedPoint.getX()) == STEP
						&& Math.abs(yIndex - selectedPoint.getY()) == 0 || Math
						.abs(yIndex - selectedPoint.getY()) == STEP
						&& Math.abs(xIndex - selectedPoint.getX()) == 0)
				&& findChess(xIndex, yIndex) == null) {
			// 记录上一步数据
			selectedPoint.getPointTrack().add(
					selectedPoint.new PointRecord(selectedPoint.getX(),
							selectedPoint.getY(), selectedPoint.isSelected(),
							selectedPoint.isDead()));
			// 设置该步数据
			selectedPoint.setX(xIndex);
			selectedPoint.setY(yIndex);
			// 在selectedPoint中的轨迹添加新坐标,注意此处只能是++stepCount,不能是stepCount++
			// selectedPoint.getTrack().add(selectedPoint.new Coordinate(xIndex,
			// yIndex, ++stepCount));
			track.add(selectedPoint);
			selectedPoint.setSelected(false); // 设置选中选项为非。
			eatOpposite(isBlack, chessList, selectedPoint.getX(),
					selectedPoint.getY(), xIndex, yIndex);
			this.repaint();
			if (isWin(isBlack)) {
				String msg = String.format("恭喜，%s赢了！", colorName);
				JOptionPane.showMessageDialog(this, msg);
				gameOver = true;
			}
			isBlack = !isBlack;
		} else if (selectedPoint != null
				&& (p = findChess(xIndex, yIndex)) != null
				&& isMySide(p, isBlack)) {
			/* 如果已经选中我方一子selectedPoint，又点击我方另一子p，则选中我方的另一子p，selectedPoint取消选中 */
			selectedPoint.setSelected(false);
			p.setSelected(true);
			this.repaint();
		}
	}

	// 在棋子数组中查找是否有索引为x，y的棋子存在，返回该棋子
	private Point findChess(int x, int y) {
		for (Point c : chessList) {
			if (c != null && c.getX() == x && c.getY() == y && !c.isDead()) {
				return c;
			}
		}
		return null;
	}

	/* 在chesses中找到被选中的棋子，并返回 */
	private Point findSelectedPoint(Point[] chesses) {
		/* 如果棋子中有被选中的，那么退出下面的循环，可以知道chesses[i]就是被选中的棋子 */
		int i;
		for (i = 0; i < chesses.length; i++) {
			if (chesses[i].isSelected()) {
				break;
			}
		}
		if (i == chesses.length)
			return null;
		else
			return chesses[i];
	}

	/* 判断该棋子是否为我方棋子 */
	private boolean isMySide(Point point, boolean isBlack) {

		if (isBlack) {
			if (point.getColor() == Color.BLACK)
				return true;
			else
				return false;
		} else {
			if (point.getColor() == Color.WHITE)
				return true;
			else
				return false;
		}
	}

	/*
	 * 是否吃掉对方一子,若是，返回吃掉的那个子，若否，返回空。 isBlack我方，
	 * chesses棋子数组，myPointIndex我的棋子在chesses中的索引，myXIndex,myYIndex我当前的棋子的横纵坐标，
	 * xIndex,yIndex我的棋子要落的位置网格索引
	 */
	private Point eatOpposite(boolean isBlack, Point[] chesses, int myXIndex,
			int myYIndex, int toXIndex, int toYIndex) {
		Color currentColor = isBlack ? Color.BLACK : Color.WHITE;
		Color oppositeColor = !isBlack ? Color.BLACK : Color.WHITE;
		Point p, q; // p为我方另一子， q为被吃掉的子

		/* 南北方向 */
		if ((p = findChess(myXIndex, myYIndex + 1)) != null
				&& p.getColor() == currentColor
				&& (q = findChess(myXIndex, myYIndex + 2)) != null
				&& q.getColor() == oppositeColor
				&& !colHasOtherChess(myYIndex, myYIndex + 1, myYIndex + 2,
						myXIndex, chesses)) {
			/* 判断如果该子下方的两个子都存在，且第一个是我方，第二个是彼方，并且该列无其他子，则吃掉彼方子 */
			q.setDead(true);
		} else if ((p = findChess(myXIndex, myYIndex - 1)) != null
				&& p.getColor() == currentColor
				&& (q = findChess(myXIndex, myYIndex - 2)) != null
				&& q.getColor() == oppositeColor
				&& !colHasOtherChess(myYIndex, myYIndex - 1, myYIndex - 2,
						myXIndex, chesses)) {
			/* 判断如果该子下方和上方的两个子都存在，且上方是我方，下方是彼方，并且该列无其他子，则吃掉彼方子 */
			q.setDead(true);
		} else if ((p = findChess(myXIndex, myYIndex - 1)) != null
				&& p.getColor() == currentColor
				&& (q = findChess(myXIndex, myYIndex + 1)) != null
				&& q.getColor() == oppositeColor
				&& !colHasOtherChess(myYIndex, myYIndex - 1, myYIndex + 1,
						myXIndex, chesses)) {
			/* 判断如果该子下方和上方的两个子都存在，且下方是我方，上方是彼方，并且该列无其他子，则吃掉彼方子 */
			q.setDead(true);
		} else if ((p = findChess(myXIndex, myYIndex + 1)) != null
				&& p.getColor() == currentColor
				&& (q = findChess(myXIndex, myYIndex - 1)) != null
				&& q.getColor() == oppositeColor
				&& !colHasOtherChess(myYIndex, myYIndex + 1, myYIndex - 1,
						myXIndex, chesses)) {
			/* 判断如果该子上方的两个子都存在，且最上方是彼方，次上方是我方，并且该列无其他子，则吃掉彼方子 */
			q.setDead(true);
		}
		/* 东西方向 */
		else if ((p = findChess(myXIndex + 1, myYIndex)) != null
				&& p.getColor() == currentColor
				&& (q = findChess(myXIndex + 2, myYIndex)) != null
				&& q.getColor() == oppositeColor
				&& !rowHasOtherChess(myXIndex, myXIndex + 1, myXIndex + 2,
						myYIndex, chesses)) {
			q.setDead(true);
		} else if ((p = findChess(myXIndex - 1, myYIndex)) != null
				&& p.getColor() == currentColor
				&& (q = findChess(myXIndex - 2, myYIndex)) != null
				&& q.getColor() == oppositeColor
				&& !rowHasOtherChess(myXIndex, myXIndex - 1, myXIndex - 2,
						myYIndex, chesses)) {
			/* 判断如果该子下方和上方的两个子都存在，且上方是我方，下方是彼方，并且该列无其他子，则吃掉彼方子 */
			q.setDead(true);
		} else if ((p = findChess(myXIndex - 1, myYIndex)) != null
				&& p.getColor() == currentColor
				&& (q = findChess(myXIndex + 1, myYIndex)) != null
				&& q.getColor() == oppositeColor
				&& !rowHasOtherChess(myXIndex, myXIndex - 1, myXIndex + 1,
						myYIndex, chesses)) {
			/* 判断如果该子下方和上方的两个子都存在，且下方是我方，上方是彼方，并且该列无其他子，则吃掉彼方子 */
			q.setDead(true);
		} else if ((p = findChess(myXIndex + 1, myYIndex)) != null
				&& p.getColor() == currentColor
				&& (q = findChess(myXIndex - 1, myYIndex)) != null
				&& q.getColor() == oppositeColor
				&& !rowHasOtherChess(myXIndex, myXIndex + 1, myXIndex - 1,
						myYIndex, chesses)) {
			/* 判断如果该子上方的两个子都存在，且最上方是彼方，次上方是我方，并且该列无其他子，则吃掉彼方子 */
			q.setDead(true);
		} else
			return null;
		setBlackChessNum();
		setWhiteChessNum();
		return q;
	}

	/* 我方为黑棋， 返回我方是否胜利 */
	private boolean isWin(boolean isBlack) {
		int n = 0;
		if (isBlack) {
			for (int i = 0; i < chessList.length / 2; i++) {
				if (!chessList[i].isDead()) {
					n++;
				}
			}
			if (1 == n) {
				return true;
			} else
				return false;
		} else {
			for (int i = chessList.length / 2; i < chessList.length; i++) {
				if (!chessList[i].isDead()) {
					n++;
				}
			}
			if (1 == n) {
				return true;
			} else
				return false;
		}
	}

	/* 判断该行除了这三个点（x1,y,x2,y,x3,y）外的其他位置没有棋子,若有返回真，否则返回假 */
	private boolean rowHasOtherChess(int x1, int x2, int x3, int y,
			Point[] chesses) {
		int n = 0;
		for (Point p : chesses) {
			if (p.getY() == y && p.getX() != x1 && p.getX() != x2
					&& p.getX() != x3 && !p.isDead()) {
				n++;
			}
		}
		if (0 == n)
			return false;
		else
			return true;
	}

	/* 判断该列除了这三个点（x,y1,y2,y3）外的其他位置没有棋子,若有返回真，否则返回假 */
	private boolean colHasOtherChess(int y1, int y2, int y3, int x,
			Point[] chesses) {
		int n = 0;
		for (Point p : chesses) {
			if (p.getX() == x && p.getY() != y1 && p.getY() != y2
					&& p.getY() != y3 && !p.isDead()) {
				n++;
			}
		}
		if (0 == n)
			return false;
		else
			return true;
	}

	/* 重新启动游戏 */
	public void restartGame() {
		/* 初始化棋子 */
		initializeChessList();
		// 恢复游戏相关的变量值
		isBlack = true;
		// stepCount = 0;
		track.clear();
		gameOver = false; // 游戏是否结束
		repaint();
	}

	/* 悔棋 */
	public void goBack() {
		if (gameOver) {
			JOptionPane.showMessageDialog(this, "游戏已经结束无法悔棋！");
			return;
		}

		if (track.size() > 0) {
			Point selectedPoint = findSelectedPoint(chessList);
			if (selectedPoint != null)
				selectedPoint.setSelected(false);
			// 恢复上一步数据
			Point last = track.getLast();
			last.setX(last.getPointTrack().getLast().x);
			last.setY(last.getPointTrack().getLast().y);
			last.setDead(last.getPointTrack().getLast().isDead);
			last.setSelected(last.getPointTrack().getLast().isSelected);
			if (last.getPointTrack().size() > 0)
				last.getPointTrack().removeLast();
			// last.traversePointTrack();
			if (track.size() > 0)
				track.removeLast();
			if (track.size() > 0 && track.getLast().isDead()) {
				track.getLast().setDead(false);
			}
			this.repaint();
			isBlack = !isBlack;
		} else {
			JOptionPane.showMessageDialog(this, "此时无法悔棋！");
			return;
		}
	}

	/* 退出 */
	public void exit() {
		int n = JOptionPane.showConfirmDialog(this, "真的 要退出吗？", "exit",
				JOptionPane.YES_NO_OPTION);
		if (n == JOptionPane.YES_OPTION) {
			System.exit(0);
		} else if (n == JOptionPane.NO_OPTION) {
			return;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	// 坐标
	class Coordinate {
		int x, y;
		Point p; // 该棋子

		public Coordinate(int x, int y, Point p) {
			this.x = x;
			this.y = y;
			this.p = p;
		}
	}

}
