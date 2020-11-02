package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class TestDemoTextPane extends JFrame {
 JTextArea area = null;

 TextPaneDemo t = null;
    int i=0;
 private TestDemoTextPane() {
  setTitle("Test");
  t = new TextPaneDemo();

  t.append(Color.RED, "test");
  t.append(Color.BLUE, "333");
  t.replaceSelection("text");
  t.insertIcon(new ImageIcon("logo.png"));
  t.insertComponent(new JButton("Click Me"));

  Container pane = getContentPane();
  pane.add(new JScrollPane(t));
  JPanel panel = new JPanel();
  area = new JTextArea();
  area.setRows(6);
  JButton button = new JButton("·¢ËÍ");
  button.addActionListener(new ActionListener() {
   public void actionPerformed(ActionEvent e) {
    i++;
    t.append(ranColor(i), area.getText()+"\n");
   }
  });
  panel.setLayout(new BorderLayout());
  panel.add(area);
  panel.add(button, BorderLayout.NORTH);
  pane.add(panel, BorderLayout.SOUTH);

  setBounds(300, 200, 500, 500);
  setVisible(true);

 }

 public Color ranColor(int n) {
  switch (n % 5) {
  case 0:
   return Color.RED;
  case 1:
   return Color.BLACK;
  case 2:
   return Color.BLUE;
  case 3:
   return Color.YELLOW;
  case 4:
   return Color.GREEN;
  default:
   return Color.orange;
  }
 }

 public static void main(String[] args) {
  new TestDemoTextPane();
 }

}
class TextPaneDemo extends JTextPane{

public void append(Color c,String s){
 StyleContext sc=StyleContext.getDefaultStyleContext();
 AttributeSet aset=sc.addAttribute(SimpleAttributeSet.EMPTY,StyleConstants.Foreground,c);

 int len=getDocument().getLength();
 setCaretPosition(len);
 setCharacterAttributes(aset,false);
 replaceSelection(s);
}
}

