package fi.vamk.servlet;

public class Student {
	private String id = "";
	private String name = "";
	private String sex = "";
	private String birthday = "";
	private String nationality = "";
	private String telephone = "";
	private String major = "";

	public Student(String id, String name, String sex, String birthday, String nationality, String telephone, String major) {
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.birthday = birthday;
		this.nationality = nationality;
		this.telephone = telephone;
		this.major = major;
	}

	public Student() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	@Override
	public String toString() {
		return "<tr><td>" + id + "</td>" + "<td>" + name + "</td>" + "<td>" + sex + "</td>" + "<td>" + birthday + "</td>" + "<td>" + nationality + "</td>" + "<td>" + telephone
				+ "</td>" + "<td>" + major + "</td></tr>";
	}

}
