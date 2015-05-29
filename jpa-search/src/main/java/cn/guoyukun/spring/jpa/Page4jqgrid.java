package cn.guoyukun.spring.jpa;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class Page4jqgrid<T> extends PageImpl<T> {
	private static final long serialVersionUID = -4666797015040531454L;

	public Page4jqgrid(List<T> content, Pageable pageable, long total) {
		super(content, pageable, total);
	}

	public Page4jqgrid(List<T> content) {
		super(content);
	}
	
	public long getTotal4jqgrid(){
		long totalPages = this.getTotalPages();
		return totalPages == 0 ? 1 : totalPages;
	}

	public long getNumber4jqgrid(){
		long number = this.getNumber();
		return number == 0 ? 1 : number;
	}

}
