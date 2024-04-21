package kr.co.dbinc.model;

import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@AllArgsConstructor
@SuperBuilder
public class WorkerVO {
	private Long id;
	private String username;
	private String password;
	private String nickname;
	private String phone_number;
	private String email;
}
