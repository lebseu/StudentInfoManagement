package fi.vamk.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import fi.vamk.servlet.Student;

public class XMLOperation {
	private File dbXML;

	public XMLOperation(File dbXML) {
		this.dbXML = dbXML;
		if (!dbXML.exists()) {
			try {
				dbXML.createNewFile();
				Document document = DocumentHelper.createDocument();
				document.addElement("school");
				Writer fileWriter = new FileWriter(dbXML);
				XMLWriter xmlWriter = new XMLWriter(fileWriter, OutputFormat.createPrettyPrint());
				xmlWriter.write(document);
				xmlWriter.flush();
				xmlWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void create(Student student) {
		try {
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(dbXML);
			Element root = document.getRootElement();
			Element studentElement = root.addElement("student").addAttribute("id", student.getId());
			studentElement.addElement("name").addText(student.getName());
			studentElement.addElement("sex").addText(student.getSex());
			studentElement.addElement("birthday").addText(student.getBirthday());
			studentElement.addElement("nationality").addText(student.getNationality());
			studentElement.addElement("telephone").addText(student.getTelephone());
			studentElement.addElement("major").addText(student.getMajor());
			Writer fileWriter = new FileWriter(dbXML);
			XMLWriter xmlWriter = new XMLWriter(fileWriter, OutputFormat.createPrettyPrint());
			xmlWriter.write(document);
			xmlWriter.flush();
			xmlWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public boolean validate(File dbXML, Student student) {

		// ID and Name cannot be blank;
		if (!student.getId().equals("") && !student.getName().equals("")) {
			// prevent the duplicate ID;
			try {
				SAXReader reader = new SAXReader();
				Document document = reader.read(dbXML);
				List<Node> nodes = document.selectNodes("/school/student");
				for (Node node : nodes) {
					Student nodeStudent = nodeToStudent(node);
					if (nodeStudent.getId().equals(student.getId())) {
						return false;
					}
				}
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;

	}

	@SuppressWarnings("unchecked")
	public ArrayList<Student> search(Student student) {

		ArrayList<Student> students = new ArrayList<Student>();
		try {

			SAXReader reader = new SAXReader();
			Document document = reader.read(dbXML);
			List<Node> nodes = document.selectNodes("/school/student");
			for (Node node : nodes) {
				Student nodeStudent = nodeToStudent(node);
				if (nodeStudent.getId().equals(student.getId()) || student.getId().equals(""))
					if (nodeStudent.getName().equals(student.getName()) || student.getName().equals(""))
						if (nodeStudent.getSex().equals(student.getSex()) || student.getSex().equals(""))
							if (nodeStudent.getBirthday().equals(student.getBirthday()) || student.getBirthday().equals(""))
								if (nodeStudent.getNationality().equals(student.getNationality()) || student.getNationality().equals(""))
									if (nodeStudent.getTelephone().equals(student.getTelephone()) || student.getTelephone().equals(""))
										if (nodeStudent.getMajor().equals(student.getMajor()) || student.getMajor().equals("")) {
											students.add(nodeStudent);
										}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return students;
	}

	public Student nodeToStudent(Node node) {

		Student NodeStudent = new Student();
		NodeStudent.setId(node.valueOf("@id"));
		NodeStudent.setName(node.selectSingleNode("name").getText());
		NodeStudent.setSex(node.selectSingleNode("sex").getText());
		NodeStudent.setBirthday(node.selectSingleNode("birthday").getText());
		NodeStudent.setNationality(node.selectSingleNode("nationality").getText());
		NodeStudent.setTelephone(node.selectSingleNode("telephone").getText());
		NodeStudent.setMajor(node.selectSingleNode("major").getText());
		return NodeStudent;
	}

	public Student getStudentById(String id) {
		Student student = new Student();
		student.setId(id);
		if(search(student).size()==0){
			return null;
		}else {
			return search(student).get(0);
		}
		
	}

	public boolean delete(Student student) {
		String id = student.getId();
		if (getStudentById(id) == null) {
			return false;
		} else {
			try {
				SAXReader reader = new SAXReader();
				Document document = reader.read(dbXML);
				Element root = document.getRootElement();
				Node node = document.selectSingleNode("/school/student[@id='" + id + "']");
				root.remove(node);
				Writer fileWriter = new FileWriter(dbXML);
				XMLWriter xmlWriter = new XMLWriter(fileWriter, OutputFormat.createPrettyPrint());
				xmlWriter.write(document);
				xmlWriter.flush();
				xmlWriter.close();
			} catch (DocumentException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}

	}

}
