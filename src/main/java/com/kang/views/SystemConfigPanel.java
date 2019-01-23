package com.kang.views;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.kang.entities.GlobalSystemConfig;
import com.kang.entities.SystemConfig;
import com.kang.util.I18NUtil;

public class SystemConfigPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private SystemConfigJFrame parentJFrame;
	private I18NUtil i18nUtil;
	private SystemConfig systemConfig;
	
	private JPanel ipAddrPanel;
	private JLabel ipAddrLabel;
	private JTextField ipAddrField;

	private JPanel patternPanel;
	private JLabel patternLabel;
	private ButtonGroup patternRadioButtonGroup;
	private JRadioButton[] versusButtons;
	
	private JPanel languagePanel;
	private JLabel languageLabel;
	private ButtonGroup languageRadioButtonGroup;
//	private JRadioButton chinese, english, autoLanguage;
	private JRadioButton[] languageButtons;

	private JPanel buttonPanel;
	private JButton saveButton, cancelButton;

	public SystemConfigPanel(SystemConfigJFrame parentJFrame) {

		/* initialize */
		this.init(parentJFrame);
		
		/* initialize ActionListener */
		ButtonActionListener buttonListener = new ButtonActionListener();

		/* initialize components */
		ipAddrPanel = new JPanel();
		ipAddrLabel = new JLabel(i18nUtil.getText("IPAddress"));
		ipAddrField = new JTextField(15);
		ipAddrField.setText(systemConfig.getIpAddr());
		if ("networkModel".equals(systemConfig.getVersus())) {
			ipAddrField.setEnabled(true);
		} else if ("localModel".equals(systemConfig.getVersus())) {
			ipAddrField.setEnabled(false);
		}
		ipAddrPanel.add(ipAddrField);

		/* 对战模式 */
		patternPanel = new JPanel();
		patternLabel = new JLabel(i18nUtil.getText("versus"));
		patternRadioButtonGroup = new ButtonGroup();
		versusButtons = new JRadioButton[systemConfig.getVersusValues().length];
		for (int i = 0; i < versusButtons.length; i++) {
			versusButtons[i] = new JRadioButton(i18nUtil.getText(systemConfig.getVersusValues()[i]));
			versusButtons[i].setName(systemConfig.getVersusValues()[i]);
			versusButtons[i].addActionListener(buttonListener);
			patternRadioButtonGroup.add(versusButtons[i]);
			patternPanel.add(versusButtons[i]);
		}
		versusButtons[systemConfig.getVersusIndex()].setSelected(true);
		
		/* languages */
		languagePanel = new JPanel();
		languagePanel.setToolTipText(i18nUtil.getText("restartToEffect"));
		languageLabel = new JLabel(i18nUtil.getText("languages"));
		languageRadioButtonGroup = new ButtonGroup();
		
		languageButtons = new JRadioButton[systemConfig.getLanguageValues().length];
		for (int i = 0; i < languageButtons.length; i++) {
			languageButtons[i] = new JRadioButton(i18nUtil.getText(systemConfig.getLanguageValues()[i]));
			if ("chinese".equals(systemConfig.getLanguageValues()[i])) {
				languageButtons[i].setText(i18nUtil.getText("chinese", Locale.SIMPLIFIED_CHINESE));
			} else if ("english".equals(systemConfig.getLanguageValues()[i])) {
				languageButtons[i].setText(i18nUtil.getText("english", Locale.US));
			}
			languageButtons[i].setName(systemConfig.getLanguageValues()[i]);
			languageButtons[i].addActionListener(buttonListener);
			languageButtons[i].setToolTipText(i18nUtil.getText("restartToEffect"));
			languageRadioButtonGroup.add(languageButtons[i]);
			languagePanel.add(languageButtons[i]);
		}
		languageButtons[systemConfig.getLanguageIndex()].setSelected(true);
		
		/* buttons */
		buttonPanel = new JPanel();
		saveButton = new JButton(i18nUtil.getText("save"));
		saveButton.addActionListener(buttonListener);
		cancelButton = new JButton(i18nUtil.getText("cancel"));
		cancelButton.addActionListener(buttonListener);
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);

		/* set Layout */
		GridBagLayout gridBagLayout = new GridBagLayout();
		this.setLayout(gridBagLayout);
		
		/* add components */
		this.add(ipAddrLabel);
		this.add(ipAddrPanel);
		this.add(patternLabel);
		this.add(patternPanel);
		this.add(languageLabel);
		this.add(languagePanel);
		this.add(buttonPanel);
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.BOTH;
		gridBagLayout.setConstraints(ipAddrLabel, constraints);
		constraints.gridwidth = 0;
		gridBagLayout.setConstraints(ipAddrPanel, constraints);
		constraints.gridwidth = 1;
		gridBagLayout.setConstraints(patternLabel, constraints);
		constraints.gridwidth = 0;
		gridBagLayout.setConstraints(patternPanel, constraints);
		constraints.gridwidth = 1;
		gridBagLayout.setConstraints(languageLabel, constraints);
		constraints.gridwidth = 0;
		gridBagLayout.setConstraints(languagePanel, constraints);
		constraints.gridwidth = 0;
		gridBagLayout.setConstraints(buttonPanel, constraints);
	}
	
	/**
	 * 初始化
	 */
	private void init(SystemConfigJFrame parentJFrame) {
		this.parentJFrame = parentJFrame;
		this.systemConfig = GlobalSystemConfig.getInstance().getSystemConfig();
		this.i18nUtil = I18NUtil.getInstance("SystemConfig", this.systemConfig.getLanguage());
	}

	class ButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();
			AbstractButton buttonObj = (AbstractButton) obj;
			if ("networkModel".equals(buttonObj.getName()) || "localModel".equals(buttonObj.getName())) {
				JRadioButton versusRadioButton = (JRadioButton) buttonObj;
				if (versusRadioButton.isSelected() && "networkModel".equals(versusRadioButton.getName())) {
					ipAddrField.setEnabled(true);
					SystemConfigPanel.this.systemConfig.setVersus("networkModel");
				} else if (versusRadioButton.isSelected() && "localModel".equals(versusRadioButton.getName())) {
					ipAddrField.setEnabled(false);
					SystemConfigPanel.this.systemConfig.setVersus("localModel");
				}
			} else if (obj == SystemConfigPanel.this.saveButton) {
				SystemConfigPanel.this.systemConfig.setVersus(this.getSelected(SystemConfigPanel.this.versusButtons).getName());
				SystemConfigPanel.this.systemConfig.setIpAddr(SystemConfigPanel.this.ipAddrField.getText());;
				SystemConfigPanel.this.systemConfig.setLanguage(this.getSelected(SystemConfigPanel.this.languageButtons).getName());
				GlobalSystemConfig.getInstance().saveSystemConfig();
				this.exit();
			} else if (obj == SystemConfigPanel.this.cancelButton) {
				this.exit();
			}
		}
		private void exit() {
			SystemConfigPanel.this.parentJFrame.getParentFrame().setEnabled(true);
			SystemConfigPanel.this.parentJFrame.dispose();
		}
		/**
		 * 获取选中的JRadioButton
		 * @param jRadioButtons
		 * @return
		 */
		private JRadioButton getSelected(JRadioButton[] jRadioButtons) {
			for (JRadioButton jRadioButton : jRadioButtons) {
				if (jRadioButton.isSelected()) {
					return jRadioButton;
				}
			}
			return null;
		}
	}
}
