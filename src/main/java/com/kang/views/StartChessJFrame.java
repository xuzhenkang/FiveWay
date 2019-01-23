package com.kang.views;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.kang.entities.GlobalSystemConfig;
import com.kang.util.I18NUtil;

public class StartChessJFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private I18NUtil i18nUtil;

	private ChessBoard chessBoard;
	private JPanel toolbar;
	private JButton startButton, backButton, exitButton, showButton;
	private JMenu sysMenu;
	private JMenuBar menuBar;
	private JMenuItem startMenuItem, exitMenuItem, backMenuItem, showMenuItem, configMenuItem;

	private void init() {
		this.i18nUtil = I18NUtil.getInstance("StartChess", GlobalSystemConfig.getInstance().getSystemConfig().getLanguage());
	}

	public StartChessJFrame() {
		/* initialize Configuration */
		this.init();
		chessBoard = new ChessBoard(this);
		Container contentPane = this.getContentPane();
		contentPane.add(chessBoard);
		chessBoard.setOpaque(true); // 设置是否不透明，true不透明，false透明

		/* initialize ActionListener */
		ButtonItemListener lis = new ButtonItemListener();

		/* initialize MenuItems */
		startMenuItem = new JMenuItem(i18nUtil.getText("start"));
		exitMenuItem = new JMenuItem(i18nUtil.getText("exit"));
		backMenuItem = new JMenuItem(i18nUtil.getText("back"));
		showMenuItem = new JMenuItem(i18nUtil.getText("show"));
		configMenuItem = new JMenuItem(i18nUtil.getText("config"));

		/* add ActionListeners to MenuItems */
		startMenuItem.addActionListener(lis);
		exitMenuItem.addActionListener(lis);
		backMenuItem.addActionListener(lis);
		showMenuItem.addActionListener(lis);
		configMenuItem.addActionListener(lis);

		/* initialize JMenu and add MenuItems to it */
		sysMenu = new JMenu(i18nUtil.getText("sysMenu"));
		sysMenu.add(startMenuItem);
		sysMenu.add(exitMenuItem);
		sysMenu.add(backMenuItem);
		sysMenu.add(showMenuItem);
		sysMenu.add(configMenuItem);

		/* initialize JMenuBar and add JMenu to it */
		menuBar = new JMenuBar();
		menuBar.add(sysMenu);

		/* initialize JButtons */
		startButton = new JButton(i18nUtil.getText("start"));
		backButton = new JButton(i18nUtil.getText("back"));
		exitButton = new JButton(i18nUtil.getText("exit"));
		showButton = new JButton(i18nUtil.getText("show"));

		/* add ActionListener to JButtons */
		startButton.addActionListener(lis);
		backButton.addActionListener(lis);
		exitButton.addActionListener(lis);
		showButton.addActionListener(lis);

		/* initialize JPanel and add JButtons to it after setting layout */
		toolbar = new JPanel();
		toolbar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolbar.add(startButton);
		toolbar.add(backButton);
		toolbar.add(exitButton);
		toolbar.add(showButton);

		/* set the windows */
		this.setTitle(i18nUtil.getText("title"));
		this.setJMenuBar(menuBar);
		this.add(toolbar, BorderLayout.SOUTH);
		this.add(chessBoard);// 将面板添加到窗体上

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					if (JOptionPane.showConfirmDialog(null, i18nUtil.getText("exitMessage"), i18nUtil.getText("alert"),
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
						System.exit(0);
					}
				} catch (Exception ee) {
					ee.printStackTrace();
				}
			}
		});

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(290, 380);
		this.setResizable(false);
		// this.pack();
		this.setLocationRelativeTo(null);
	}

	class ButtonItemListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource(); // to get source of action
			if (obj == StartChessJFrame.this.startMenuItem || obj == StartChessJFrame.this.startButton) {
				chessBoard.restartGame();
			} else if (obj == StartChessJFrame.this.backMenuItem || obj == StartChessJFrame.this.backButton) {
				chessBoard.goBack();
			} else if (obj == StartChessJFrame.this.exitMenuItem || obj == StartChessJFrame.this.exitButton) {
				chessBoard.exit();
			} else if (obj == StartChessJFrame.this.showMenuItem || obj == StartChessJFrame.this.showButton) {
				chessBoard.traverseTrack();
				// System.out.println(chessBoard.track.getLast().p);
				// System.out.println(chessBoard.track.size());
			} else if (obj == StartChessJFrame.this.configMenuItem) {
				StartChessJFrame.this.setEnabled(false);
				new SystemConfigJFrame(StartChessJFrame.this);
			}
		}
	}

	public static void main(String[] args) {
		StartChessJFrame f = new StartChessJFrame();
		f.setVisible(true);
	}
}
