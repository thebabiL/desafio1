package exceptions;

public class ProdutoException extends RuntimeException 
{
    public ProdutoException(String mensagem) 
    {
        super(mensagem);
    }
}
