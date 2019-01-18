package kang.views;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import kang.util.I18NUtil;
import kang.util.NetWorkUtil;

public class SystemConfigPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private I18NUtil i18nUtil = I18NUtil.getInstance("SystemConfig");
	
	private JPanel ipAddrPanel;
	private JLabel ipAddrLabel;
	private JTextField ipAddrField;

	private JPanel patternPanel;
	private JLabel patternLabel;
	private ButtonGroup patternRadioButtonGroup;
	private JRadioButton netPattern, localPattern;
	
	private JPanel languagePanel;
	private JLabel languageLabel;
	private ButtonGroup languageRadioButtonGroup;
	private JRadioButton chinese, english, autoLanguage;

	private JPanel buttonPanel;
	private JButton saveButton, cancelButton;

	public SystemConfigPanel() {

		/* initialize ActionListener */
		ButtonItemListener lis = new ButtonItemListener();

		/* initialize components */
		ipAddrPanel = new JPanel();
		ipAddrLabel = new JLabel(i18nUtil.getText("IPAddress"));
		ipAddrField = new JTextField(15);
		ipAddrField.setText(NetWorkUtil.getIPAddr());
		ipAddrField.setEnabled(false);
		ipAddrPanel.add(ipAddrField);

		/* 对战模式 */
		patternPanel = new JPanel();
		patternLabel = new JLabel(i18nUtil.getText("versus"));
		netPattern = new JRadioButton(i18nUtil.getText("networkModel"));
		netPattern.addActionListener(lis);
		localPattern = new JRadioButton(i18nUtil.getText("localModel"));
		localPattern.addActionListener(lis);
		localPattern.setSelected(true);
		patternRadioButtonGroup = new ButtonGroup();
		patternRadioButtonGroup.add(netPattern);
		patternRadioButtonGroup.add(localPattern);
		patternPanel.add(netPattern);
		patternPanel.add(localPattern);
		
		/* languages */
		languagePanel = new JPanel();
		languagePanel.setToolTipText(i18nUtil.getText("restartToEffect"));
		languageLabel = new JLabel(i18nUtil.getText("languages"));
		languageRadioButtonGroup = new ButtonGroup();
		chinese = new JRadioButton(i18nUtil.getText("chinese", Locale.SIMPLIFIED_CHINESE));
		chinese.setToolTipText(i18nUtil.getText("restartToEffect"));
		english = new JRadioButton(i18nUtil.getText("english", Locale.US));
		english.setToolTipText(i18nUtil.getText("restartToEffect"));
		
		autoLanguage = new JRadioButton(i18nUtil.getText("autoLanguage"));
		autoLanguage.setSelected(true);
		autoLanguage.setToolTipText(i18nUtil.getText("restartToEffect"));
		languageRadioButtonGroup.add(chinese);
		languageRadioButtonGroup.add(english);
		languageRadioButtonGroup.add(autoLanguage);
		languagePanel.add(chinese);
		languagePanel.add(english);
		languagePanel.add(autoLanguage);

		/* buttons */
		buttonPanel = new JPanel();
		saveButton = new JButton(i18nUtil.getText("save"));
		cancelButton = new JButton(i18nUtil.getText("cancel"));
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
	
	private void init() {
		
	}

	class ButtonItemListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();
			if (obj == SystemConfigPanel.this.netPattern || obj == SystemConfigPanel.this.localPattern) {
				if (SystemConfigPanel.this.netPattern.isSelected()) {
					ipAddrField.setEnabled(true);
				} else if (SystemConfigPanel.this.localPattern.isSelected()) {
					ipAddrField.setEnabled(false);
				}
			}
		}
	}
}
