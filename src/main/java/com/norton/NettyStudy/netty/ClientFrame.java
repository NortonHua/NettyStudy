package com.norton.NettyStudy.netty;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * 窗口类
 * @author Artificial Intellige
 *
 */
public class ClientFrame extends Frame{

	TextArea ta=new TextArea();
	TextField tf=new TextField();
	
	FrameClient c=null;
	
	public ClientFrame() {
		this.setSize(600,400);
		this.setLocation(100,20);
		this.add(ta,BorderLayout.CENTER);
		this.add(tf,BorderLayout.SOUTH);
		tf.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				c.send(tf.getText());
				ta.setText(ta.getText()+tf.getText());
				tf.setText("");
			}
		});
		
		this.setVisible(true);
		connectToServer();
	}

	private void connectToServer() {
		// TODO Auto-generated method stub
		c=new FrameClient();
		c.connect();
	}
	
	public static void main(String[] args) {
		new ClientFrame();
	}
	
}
