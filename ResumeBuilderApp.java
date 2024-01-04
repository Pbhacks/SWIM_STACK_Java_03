import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class ResumeBuilderApp extends JFrame {

    private JTextField nameField, emailField, experienceField;
    private JTextArea educationArea, skillsArea;

    public ResumeBuilderApp() {
        // Set up the frame
        setTitle("Resume Builder");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Create components
        JLabel nameLabel = new JLabel("Name:");
        JLabel emailLabel = new JLabel("Email:");
        JLabel educationLabel = new JLabel("Education:");
        JLabel experienceLabel = new JLabel("Experience:");
        JLabel skillsLabel = new JLabel("Skills:");

        nameField = new JTextField();
        emailField = new JTextField();
        educationArea = new JTextArea();
        experienceField = new JTextField();
        skillsArea = new JTextArea();

        JButton generateButton = new JButton("Generate Resume");
        JButton editButton = new JButton("Edit Resume");
        JButton viewButton = new JButton("View Resume");

        // Set component positions and sizes
        nameLabel.setBounds(10, 20, 80, 25);
        emailLabel.setBounds(10, 50, 80, 25);
        educationLabel.setBounds(10, 80, 80, 25);
        experienceLabel.setBounds(10, 180, 80, 25);
        skillsLabel.setBounds(10, 250, 80, 25);

        nameField.setBounds(100, 20, 200, 25);
        emailField.setBounds(100, 50, 200, 25);
        educationArea.setBounds(100, 80, 350, 90);
        experienceField.setBounds(100, 180, 350, 60);
        skillsArea.setBounds(100, 250, 350, 60);

        generateButton.setBounds(150, 320, 150, 25);
        editButton.setBounds(310, 320, 90, 25);
        viewButton.setBounds(410, 320, 90, 25);

        // Add components to the frame
        add(nameLabel);
        add(emailLabel);
        add(educationLabel);
        add(experienceLabel);
        add(skillsLabel);
        add(nameField);
        add(emailField);
        add(educationArea);
        add(experienceField);
        add(skillsArea);
        add(generateButton);
        add(editButton);
        add(viewButton);

        // Set up action listeners
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateResume();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editResume();
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewResume();
            }
        });
    }

    private void generateResume() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Resume.txt"))) {
            writer.write("Name: " + nameField.getText() + "\n");
            writer.write("Email: " + emailField.getText() + "\n");
            writer.write("Education:\n" + educationArea.getText() + "\n");
            writer.write("Experience: " + experienceField.getText() + "\n");
            writer.write("Skills:\n" + skillsArea.getText() + "\n");

            JOptionPane.showMessageDialog(this, "Resume generated successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error generating resume.");
        }
    }

    private void editResume() {
        try (BufferedReader reader = new BufferedReader(new FileReader("Resume.txt"))) {
            String line;
            StringBuilder resumeContent = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                resumeContent.append(line).append("\n");
            }

            String[] resumeFields = resumeContent.toString().split("\n");

            // Set the fields in the UI
            nameField.setText(getFieldValue(resumeFields, "Name:"));
            emailField.setText(getFieldValue(resumeFields, "Email:"));
            educationArea.setText(getFieldValue(resumeFields, "Education:"));
            experienceField.setText(getFieldValue(resumeFields, "Experience:"));
            skillsArea.setText(getFieldValue(resumeFields, "Skills:"));

            JOptionPane.showMessageDialog(this, "Resume loaded for editing.");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading resume for editing.");
        }
    }

    private String getFieldValue(String[] fields, String prefix) {
        for (String field : fields) {
            if (field.startsWith(prefix)) {
                return field.substring(prefix.length()).trim();
            }
        }
        return "";
    }

    private void viewResume() {
        try (BufferedReader reader = new BufferedReader(new FileReader("Resume.txt"))) {
            StringBuilder resumeContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                resumeContent.append(line).append("\n");
            }

            JTextArea viewArea = new JTextArea(resumeContent.toString());
            viewArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(viewArea);
            scrollPane.setPreferredSize(new java.awt.Dimension(500, 400));
            JOptionPane.showMessageDialog(this, scrollPane, "View Resume", JOptionPane.PLAIN_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error viewing resume.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ResumeBuilderApp().setVisible(true);
            }
        });
    }
}
