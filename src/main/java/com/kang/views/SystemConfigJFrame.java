package com.kang.views;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import com.kang.entities.GlobalSystemConfig;
import com.kang.utils.I18NUtil;

public class SystemConfigJFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private I18NUtil i18nUtil;
	
	private StartChessJFrame parentFrame; // 父窗口
	private SystemConfigPanel systemConfigPanel;

	public SystemConfigJFrame(StartChessJFrame parentFrame) {

		this.parentFrame = parentFrame;
		this.i18nUtil = I18NUtil.getInstance("SystemConfig", GlobalSystemConfig.getInstance().getSystemConfig().getLanguage());
		
		systemConfigPanel = new SystemConfigPanel(this);
		this.add(systemConfigPanel);
		this.setTitle(i18nUtil.getText("settings"));
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setSize(320, 220);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				SystemConfigJFrame.this.parentFrame.setEnabled(true);
				SystemConfigJFrame.this.dispose();
			}
		});
		this.setVisible(true);
	}
	public StartChessJFrame getParentFrame() {
		return parentFrame;
	}
}
