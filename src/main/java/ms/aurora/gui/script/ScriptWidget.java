package ms.aurora.gui.script;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.UIManager;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;

import ms.aurora.api.script.Script;
import ms.aurora.api.script.ScriptManifest;

public class ScriptWidget extends JPanel {
	private static final long serialVersionUID = 414689352878859224L;
	private final Script script;

	/**
	 * Create the panel.
	 */
	public ScriptWidget(Script script) {
		setBackground(UIManager.getColor("Panel.background"));
		setBorder(BorderFactory.createLineBorder(Color.black));
		// 630, 150
		setMinimumSize(new Dimension(630, 150));
		setPreferredSize(getMinimumSize());
		setMaximumSize(getPreferredSize());
		setLayout(null);
		
		ScriptManifest manifest = script.getManifest();		
		JButton btnStart = new JButton("Start");
		btnStart.setBounds(538, 115, 84, 29);
		add(btnStart);
		
		JButton btnMoreInfo = new JButton("More info");
		btnMoreInfo.setBounds(428, 115, 117, 29);
		add(btnMoreInfo);
		
		JLabel lblScriptHeader = new JLabel(manifest.name() + " by " + manifest.author());
		lblScriptHeader.setForeground(new Color(0, 153, 204));
		lblScriptHeader.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		lblScriptHeader.setBounds(19, 6, 603, 23);
		add(lblScriptHeader);
		
		JLabel lblDescription = new JLabel(manifest.shortDescription());
		lblDescription.setVerticalAlignment(SwingConstants.TOP);
		lblDescription.setBounds(29, 41, 581, 71);
		add(lblDescription);
		this.script = script;
	}
	
	public Script getScript() {
		return script;
	}
}
