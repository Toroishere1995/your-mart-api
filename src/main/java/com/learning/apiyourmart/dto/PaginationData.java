package com.learning.apiyourmart.dto;

/**
 * Class which is wrapped in another class. This class holds pagination data.
 * 
 * @author ayushsaxena
 *
 */
public class PaginationData {
	private boolean nextPage;
	private int currentPage;

	public int getCurrentPage() {
		return currentPage;
	}

	public boolean isNextPage() {
		return nextPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public void setNextPage(boolean nextPage) {
		this.nextPage = nextPage;
	}
}
