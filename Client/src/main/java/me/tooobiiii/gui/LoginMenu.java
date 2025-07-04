package me.tooobiiii.gui;

import me.tooobiiii.ChessClient;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class LoginMenu {

	public LoginMenu() {
		SwingUtilities.invokeLater(LoginMenu::createAndShowGUI);
	}

	public static void createAndShowGUI() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) (screenSize.width * 0.15);
		int height = (int) (screenSize.height * 0.15);

		JFrame frame = new JFrame("Chess Login");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width, height);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);

		// Plain gray background panel
		JPanel backgroundPanel = new JPanel();
		backgroundPanel.setBackground(new Color(200, 200, 200));
		backgroundPanel.setLayout(new BorderLayout(10, 10));
		backgroundPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		// Title with chess icon
		JLabel titleLabel = new JLabel("♔ Chess Login ♔", JLabel.CENTER);
		titleLabel.setFont(new Font("Serif", Font.BOLD, 28));
		titleLabel.setForeground(new Color(50, 50, 50));
		backgroundPanel.add(titleLabel, BorderLayout.NORTH);

		// Username input field
		JPanel inputPanel = new JPanel(new GridBagLayout());
		inputPanel.setFont(new Font("Serif", Font.PLAIN, 16));
		inputPanel.setOpaque(false);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5); // Add padding

		JLabel usernameLabel = new JLabel("Username");
		usernameLabel.setFont(new Font("Serif", Font.BOLD, 18));
		usernameLabel.setForeground(new Color(50, 50, 50));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST; // Align label to the right
		inputPanel.add(usernameLabel, gbc);

		JTextField usernameField = new JTextField(15);
		usernameField.setFont(new Font("Serif", Font.PLAIN, 16));
		usernameField.setHorizontalAlignment(JTextField.LEFT);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 1.0; // Allow horizontal expansion
		gbc.fill = GridBagConstraints.HORIZONTAL; // Fill horizontally
		inputPanel.add(usernameField, gbc);

		backgroundPanel.add(inputPanel, BorderLayout.CENTER);

		// Play button with hover effect
		JButton playButton = new JButton("Play");
		playButton.setFont(new Font("Serif", Font.BOLD, 20));
		playButton.setBackground(new Color(180, 180, 180)); // Slightly darker gray
		playButton.setForeground(Color.WHITE);
		playButton.setFocusPainted(false);
		playButton.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 2));
		playButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		playButton.setPreferredSize(new Dimension(100, 40)); // Increase width slightly
		playButton.addActionListener(e -> {
			String username = usernameField.getText().trim();
			if (username.isEmpty()) {
				JOptionPane.showMessageDialog(frame, "Username cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			frame.dispose();
			try {
				new ChessClient(username);
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		});

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		buttonPanel.setOpaque(false);
		buttonPanel.add(playButton);
		backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

		frame.add(backgroundPanel);
		frame.setVisible(true);
	}
}