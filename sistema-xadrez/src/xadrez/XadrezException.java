package xadrez;

public class XadrezException extends RuntimeException {

	// nº serial PADRÃO
	private static final long serialVersionUID = 1L;
	
	//CONSTRUTOR C/ ARGUMENTO String msg
	public XadrezException (String msg) {
		super(msg);
	}

}
