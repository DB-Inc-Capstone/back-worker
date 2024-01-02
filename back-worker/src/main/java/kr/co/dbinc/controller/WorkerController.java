package kr.co.dbinc.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.dbinc.api.WorkerService;
import kr.co.dbinc.model.WorkerVO;

@RequestMapping("/worker")
@Controller
public class WorkerController {
	
	private final WorkerService workerService;
	
	public WorkerController(WorkerService ws) {
		this.workerService = ws;
	}

	/**
	 * @return List<WorkerVO>
	 */
	@GetMapping("/selectWorkerTest")
	public ResponseEntity<List<WorkerVO>> selectWorkerTest() {
		List<WorkerVO> workerList = workerService.selectWorkerList();
		return new ResponseEntity<>(workerList, HttpStatus.OK);
	}
	
	/**
	 * @param workerVO
	 * @return 성공 여부 반환
	 */
	@PostMapping("/insertWorkerTest")
	public ResponseEntity<Integer> insertWorkerTest(@Valid @RequestBody WorkerVO workerVO) {
		Integer result = workerService.insertWorker(workerVO);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
