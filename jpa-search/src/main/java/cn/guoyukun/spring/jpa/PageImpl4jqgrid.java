package cn.guoyukun.spring.jpa;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class PageImpl4jqgrid<T> extends PageImpl<T> {
	private static final long serialVersionUID = -4666797015040531454L;

	public PageImpl4jqgrid(List<T> content, Pageable pageable, long total) {
		super(content, pageable, total);
	}

	public PageImpl4jqgrid(List<T> content) {
		super(content);
	}
	
	public long getPage4jqgrid(){
		long totalPages = this.getTotalPages();
		return totalPages == 0 ? 1 : totalPages;
	}

	public long getTotal4jqgrid(){
		long number = this.getNumber();
		return number == 0 ? 1 : number;
	}

}
