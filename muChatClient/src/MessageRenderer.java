import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

public class MessageRenderer extends JLabel implements ListCellRenderer<Message> {

	@Override
	public Component getListCellRendererComponent(JList<? extends Message> list, Message message, int index,
			boolean isSelected, boolean arg4) {
		String from = message.getFrom();
		String msgString = message.getMessage();
		this.setOpaque(true);
		if (from == "me") {
			this.setBackground(Color.LIGHT_GRAY);
			this.setHorizontalAlignment(SwingConstants.RIGHT);
		} else {
			this.setBackground(Color.WHITE);
			this.setHorizontalAlignment(SwingConstants.LEFT);
		}
		this.setText(msgString);
		return this;
	}
}
