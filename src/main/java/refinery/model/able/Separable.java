package refinery.model.able;

public interface Separable<T> {
	
	/*
	 *   1: 분리
	 *   0: 분리안함
	 *  -1: 버림
	 */
	int separateFrom(T previous);
}
