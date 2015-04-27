package quickstart.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="tea")
@XmlAccessorType(XmlAccessType.FIELD)
public class Tea {
	@Override
	public String toString() {
		return "Tea [name=" + name + ", origin=" + origin + ", country="
				+ country + ", year=" + year + "]";
	}
	@XmlElement
	private String name;
	@XmlElement
	private String origin;
	@XmlElement
	private String country;
	@XmlElement
	private int year;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}

}
