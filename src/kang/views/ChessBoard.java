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
	public static final int MARGIN = 40; // �߾�
	public static final int GRID_SPAN = 50; // ������
	public static final int ROWS = 4; // ��������
	public static final int COLS = 4; // ��������
	public static final int STEP = 1; // ���ӿ������ߵĲ���

	private int blackChessNum;// ���ŵĺ�������
	private int whiteChessNum;// ���ŵİ�������
	private int endangeredBlackChessNum;// ��ǰ����в�ĺ�������
	private int endangeredWhiteChessNum;// ��ǰ����в�İ�������
	
	
	Point[] chessList = new Point[2 * (COLS + 1)];
	boolean isBlack = true; // Ĭ�Ϻ�������,�Ƿ�ú�����
	boolean gameOver = false; // �Ƿ����
	int xIndex, yIndex; // ��ǰ�ƶ�����������
	// ����켣
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

	/* ��������켣 */
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

	/* ��ʼ���������� */
	public void initializeChessList() {
		for (int i = 0; i < chessList.length; i++) {
			if (i < chessList.length / 2) { // ǰһ���ǰ���
				chessList[i] = new Point(i, 0, Color.WHITE);
			} else { // ��һ���Ǻ���
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
		/* ��ʼ������ */
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
		super.paintComponent(g); // ��û���

		/* ���ͼƬ�Ŀ����߶� */
		int imgWidth = img.getWidth(this);
		int imgHeight = img.getHeight(this);
		/* ��ô��ڿ����߶� */
		int FWidth = this.getWidth();
		int FHeight = this.getHeight();

		int x = (FWidth - imgWidth) / 2;
		int y = (FHeight - imgHeight) / 2;

		/* ������ */
		g.drawImage(img, x, y, null);
		/* ������ */
		for (int i = 0; i <= ROWS; i++) {
			g.drawLine(MARGIN, MARGIN + i * GRID_SPAN, MARGIN + COLS
					* GRID_SPAN, MARGIN + i * GRID_SPAN);
		}
		for (int i = 0; i <= COLS; i++) {
			g.drawLine(MARGIN + i * GRID_SPAN, MARGIN, MARGIN + i * GRID_SPAN,
					MARGIN + ROWS * GRID_SPAN);
		}
		/* ������ */
		for (int i = 0; i < chessList.length; i++) {
			if (chessList[i].isDead())
				continue;
			/* ���񽻲��x��y���� */
			int xPos = chessList[i].getX() * GRID_SPAN + MARGIN;
			int yPos = chessList[i].getY() * GRID_SPAN + MARGIN;
			g.setColor(chessList[i].getColor()); // ������ɫ
			colortemp = chessList[i].getColor();
			if (colortemp == Color.black) { // ������
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
			} else if (colortemp == Color.white) { // ������
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

			if (chessList[i].isSelected()) { // �����ѡ�У�������ο�
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
	public void mousePressed(MouseEvent e) { // ���������ϰ���ʱ����
		// ��Ϸ����ʱ����������
		if (gameOver)
			return;
		String colorName = isBlack ? "����" : "����";
		// �������������λ��ת������������
		xIndex = (e.getX() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
		yIndex = (e.getY() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
		// System.out.println("xIndex:" + xIndex + ";yIndex:" + yIndex);
		// ���������ⲻ����
		if (xIndex < 0 || xIndex > ROWS || yIndex < 0 || yIndex > COLS)
			return;
		Point p;
		// ���x��yλ���Ѿ������Ӵ���, ���Ҹ�����Ϊ�ҷ����ӣ�������ӱ�ѡ��
		if ((p = findChess(xIndex, yIndex)) != null
				&& findSelectedPoint(chessList) == null
				&& (isBlack && p.getColor() == Color.black || !isBlack
						&& p.getColor() == Color.white)) {
			p.setSelected(true);
			this.repaint();
		}

		/* ��chessList���ҵ���ѡ�е����ӣ������� */
		Point selectedPoint = findSelectedPoint(chessList);
		/*
		 * �������λ�����Ѿ���ѡ�е����ӵ�λ�ú�����������֮��ľ���ֵ�����ڲ���STEP�����Ҹ�λ�ò������жԷ�������������ӣ�
		 * �޸Ķ�Ӧ�ĺ�����������
		 */
		if (selectedPoint != null
				&& (Math.abs(xIndex - selectedPoint.getX()) == STEP
						&& Math.abs(yIndex - selectedPoint.getY()) == 0 || Math
						.abs(yIndex - selectedPoint.getY()) == STEP
						&& Math.abs(xIndex - selectedPoint.getX()) == 0)
				&& findChess(xIndex, yIndex) == null) {
			// ��¼��һ������
			selectedPoint.getPointTrack().add(
					selectedPoint.new PointRecord(selectedPoint.getX(),
							selectedPoint.getY(), selectedPoint.isSelected(),
							selectedPoint.isDead()));
			// ���øò�����
			selectedPoint.setX(xIndex);
			selectedPoint.setY(yIndex);
			// ��selectedPoint�еĹ켣���������,ע��˴�ֻ����++stepCount,������stepCount++
			// selectedPoint.getTrack().add(selectedPoint.new Coordinate(xIndex,
			// yIndex, ++stepCount));
			track.add(selectedPoint);
			selectedPoint.setSelected(false); // ����ѡ��ѡ��Ϊ�ǡ�
			eatOpposite(isBlack, chessList, selectedPoint.getX(),
					selectedPoint.getY(), xIndex, yIndex);
			this.repaint();
			if (isWin(isBlack)) {
				String msg = String.format("��ϲ��%sӮ�ˣ�", colorName);
				JOptionPane.showMessageDialog(this, msg);
				gameOver = true;
			}
			isBlack = !isBlack;
		} else if (selectedPoint != null
				&& (p = findChess(xIndex, yIndex)) != null
				&& isMySide(p, isBlack)) {
			/* ����Ѿ�ѡ���ҷ�һ��selectedPoint���ֵ���ҷ���һ��p����ѡ���ҷ�����һ��p��selectedPointȡ��ѡ�� */
			selectedPoint.setSelected(false);
			p.setSelected(true);
			this.repaint();
		}
	}

	// �����������в����Ƿ�������Ϊx��y�����Ӵ��ڣ����ظ�����
	private Point findChess(int x, int y) {
		for (Point c : chessList) {
			if (c != null && c.getX() == x && c.getY() == y && !c.isDead()) {
				return c;
			}
		}
		return null;
	}

	/* ��chesses���ҵ���ѡ�е����ӣ������� */
	private Point findSelectedPoint(Point[] chesses) {
		/* ����������б�ѡ�еģ���ô�˳������ѭ��������֪��chesses[i]���Ǳ�ѡ�е����� */
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

	/* �жϸ������Ƿ�Ϊ�ҷ����� */
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
	 * �Ƿ�Ե��Է�һ��,���ǣ����سԵ����Ǹ��ӣ����񣬷��ؿա� isBlack�ҷ���
	 * chesses�������飬myPointIndex�ҵ�������chesses�е�������myXIndex,myYIndex�ҵ�ǰ�����ӵĺ������꣬
	 * xIndex,yIndex�ҵ�����Ҫ���λ����������
	 */
	private Point eatOpposite(boolean isBlack, Point[] chesses, int myXIndex,
			int myYIndex, int toXIndex, int toYIndex) {
		Color currentColor = isBlack ? Color.BLACK : Color.WHITE;
		Color oppositeColor = !isBlack ? Color.BLACK : Color.WHITE;
		Point p, q; // pΪ�ҷ���һ�ӣ� qΪ���Ե�����

		/* �ϱ����� */
		if ((p = findChess(myXIndex, myYIndex + 1)) != null
				&& p.getColor() == currentColor
				&& (q = findChess(myXIndex, myYIndex + 2)) != null
				&& q.getColor() == oppositeColor
				&& !colHasOtherChess(myYIndex, myYIndex + 1, myYIndex + 2,
						myXIndex, chesses)) {
			/* �ж���������·��������Ӷ����ڣ��ҵ�һ�����ҷ����ڶ����Ǳ˷������Ҹ����������ӣ���Ե��˷��� */
			q.setDead(true);
		} else if ((p = findChess(myXIndex, myYIndex - 1)) != null
				&& p.getColor() == currentColor
				&& (q = findChess(myXIndex, myYIndex - 2)) != null
				&& q.getColor() == oppositeColor
				&& !colHasOtherChess(myYIndex, myYIndex - 1, myYIndex - 2,
						myXIndex, chesses)) {
			/* �ж���������·����Ϸ��������Ӷ����ڣ����Ϸ����ҷ����·��Ǳ˷������Ҹ����������ӣ���Ե��˷��� */
			q.setDead(true);
		} else if ((p = findChess(myXIndex, myYIndex - 1)) != null
				&& p.getColor() == currentColor
				&& (q = findChess(myXIndex, myYIndex + 1)) != null
				&& q.getColor() == oppositeColor
				&& !colHasOtherChess(myYIndex, myYIndex - 1, myYIndex + 1,
						myXIndex, chesses)) {
			/* �ж���������·����Ϸ��������Ӷ����ڣ����·����ҷ����Ϸ��Ǳ˷������Ҹ����������ӣ���Ե��˷��� */
			q.setDead(true);
		} else if ((p = findChess(myXIndex, myYIndex + 1)) != null
				&& p.getColor() == currentColor
				&& (q = findChess(myXIndex, myYIndex - 1)) != null
				&& q.getColor() == oppositeColor
				&& !colHasOtherChess(myYIndex, myYIndex + 1, myYIndex - 1,
						myXIndex, chesses)) {
			/* �ж���������Ϸ��������Ӷ����ڣ������Ϸ��Ǳ˷������Ϸ����ҷ������Ҹ����������ӣ���Ե��˷��� */
			q.setDead(true);
		}
		/* �������� */
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
			/* �ж���������·����Ϸ��������Ӷ����ڣ����Ϸ����ҷ����·��Ǳ˷������Ҹ����������ӣ���Ե��˷��� */
			q.setDead(true);
		} else if ((p = findChess(myXIndex - 1, myYIndex)) != null
				&& p.getColor() == currentColor
				&& (q = findChess(myXIndex + 1, myYIndex)) != null
				&& q.getColor() == oppositeColor
				&& !rowHasOtherChess(myXIndex, myXIndex - 1, myXIndex + 1,
						myYIndex, chesses)) {
			/* �ж���������·����Ϸ��������Ӷ����ڣ����·����ҷ����Ϸ��Ǳ˷������Ҹ����������ӣ���Ե��˷��� */
			q.setDead(true);
		} else if ((p = findChess(myXIndex + 1, myYIndex)) != null
				&& p.getColor() == currentColor
				&& (q = findChess(myXIndex - 1, myYIndex)) != null
				&& q.getColor() == oppositeColor
				&& !rowHasOtherChess(myXIndex, myXIndex + 1, myXIndex - 1,
						myYIndex, chesses)) {
			/* �ж���������Ϸ��������Ӷ����ڣ������Ϸ��Ǳ˷������Ϸ����ҷ������Ҹ����������ӣ���Ե��˷��� */
			q.setDead(true);
		} else
			return null;
		setBlackChessNum();
		setWhiteChessNum();
		return q;
	}

	/* �ҷ�Ϊ���壬 �����ҷ��Ƿ�ʤ�� */
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

	/* �жϸ��г����������㣨x1,y,x2,y,x3,y���������λ��û������,���з����棬���򷵻ؼ� */
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

	/* �жϸ��г����������㣨x,y1,y2,y3���������λ��û������,���з����棬���򷵻ؼ� */
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

	/* ����������Ϸ */
	public void restartGame() {
		/* ��ʼ������ */
		initializeChessList();
		// �ָ���Ϸ��صı���ֵ
		isBlack = true;
		// stepCount = 0;
		track.clear();
		gameOver = false; // ��Ϸ�Ƿ����
		repaint();
	}

	/* ���� */
	public void goBack() {
		if (gameOver) {
			JOptionPane.showMessageDialog(this, "��Ϸ�Ѿ������޷����壡");
			return;
		}

		if (track.size() > 0) {
			Point selectedPoint = findSelectedPoint(chessList);
			if (selectedPoint != null)
				selectedPoint.setSelected(false);
			// �ָ���һ������
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
			JOptionPane.showMessageDialog(this, "��ʱ�޷����壡");
			return;
		}
	}

	/* �˳� */
	public void exit() {
		int n = JOptionPane.showConfirmDialog(this, "��� Ҫ�˳���", "exit",
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

	// ����
	class Coordinate {
		int x, y;
		Point p; // ������

		public Coordinate(int x, int y, Point p) {
			this.x = x;
			this.y = y;
			this.p = p;
		}
	}

}
