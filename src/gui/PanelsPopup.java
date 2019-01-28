package gui;

import java.awt.Component;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

class PanelsPopup extends JPanel {
	private static PanelsPopup inst = null;
	private final static int column_height = 32;

	private JFrame frame;
	private Rectangle frame_bounds;

	private JPanel content;

	private static ArrayList<JPanel> panels;
	private static ArrayList<Rectangle> panels_bounds;

	private static ArrayList<Component> comps_init;
	private static ArrayList<Rectangle> comps_init_bounds;

	public PanelsPopup(JFrame frame, JPanel content) {
		this.inst = this;
		this.frame = frame;
		this.frame_bounds = frame.getBounds();

		this.content = content;

		this.panels = new ArrayList<JPanel>();
		this.panels_bounds = new ArrayList<Rectangle>();

		this.comps_init = new ArrayList<Component>();
		this.comps_init_bounds = new ArrayList<Rectangle>();
	}

	public void init(Component... components) {
		// 팝업 밑에 위치한 컴포넌트는 따로 리스트 저장
		for (Component comp : components) {
			comps_init.add(comp);
			comps_init_bounds.add(comp.getBounds());
			content.add(comp);
		}
	}

	public static void delete(JPanel panel) {
		panel.removeAll();
		panel.setVisible(false);
		panels.remove(panel);

		inst.resize();
	}

	public void add(JPanel panel, Component... components) {
		for (Component comp : components)
			panel.add(comp);
		content.add(panel);

		panels.add(panel);
		panels_bounds.add(panel.getBounds());

		inst.resize();
	}

	public void resize() {
		frame.setBounds(frame.getX(), frame.getY(), frame.getWidth(), frame_bounds.height + getOffset(panels.size()));

		// comps setBounds
		for (int i = 0; i < comps_init.size(); i++) {
			Rectangle bounds = comps_init_bounds.get(i);
			comps_init.get(i).setBounds(bounds.x, bounds.y + getOffset(panels.size()), bounds.width, bounds.height);
		}

		// panel setBounds
		for (int i = 0; i < panels.size(); i++) {
			Rectangle bounds = panels_bounds.get(i);
			panels.get(i).setBounds(bounds.x, bounds.y + getOffset(i), bounds.width, bounds.height);
		}

		content.revalidate();
		content.repaint();
	}

	public static int getOffset(int index) {
		return index * column_height;
	}
}
