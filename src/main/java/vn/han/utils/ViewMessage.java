package vn.han.utils;

import lombok.Data;

@Data
public class ViewMessage {
	public String type;
	public String content;
	
	public ViewMessage(String type, String content) {
		this.type = type;
		this.content = content;
	}

}
