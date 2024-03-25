package kr.co.dbinc.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkerDTO {
	private Integer id;
	private String username;
	private String password;
	private String nickname;
	private String phoneNumber;
	private String email;
}
