package by.htp.ts.bean;

import java.sql.Date;
import java.util.Arrays;

public class UserHasTestDTO {
	
	private String[] login;
	private String topic;
	private Date date;
	
	public UserHasTestDTO() {
	}

	public UserHasTestDTO(String[] login, String topic, Date date) {
		this.login = login;
		this.topic = topic;
		this.date = date;
	}

	public String[] getLogin() {
		return login;
	}

	public void setLogin(String[] login) {
		this.login = login;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + Arrays.hashCode(login);
		result = prime * result + ((topic == null) ? 0 : topic.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserHasTestDTO other = (UserHasTestDTO) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (!Arrays.equals(login, other.login))
			return false;
		if (topic == null) {
			if (other.topic != null)
				return false;
		} else if (!topic.equals(other.topic))
			return false;
		return true;
	}
	
}
