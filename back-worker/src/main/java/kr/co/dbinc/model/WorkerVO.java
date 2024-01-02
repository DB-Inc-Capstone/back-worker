package kr.co.dbinc.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkerVO {
	private Integer worker_pk;
	private String id;
	private String pw;
	private String name;
	private String phone_number;
	private String email;
	private Boolean acc_status;
}
