package kr.co.dbinc.model;

import lombok.Data;

@Data
public class WorkerVO {
	private Integer id;
	private String username;
	private String password;
	private String nickname;
	private String phoneNumber;
	private String email;
}
