package exceptions;

public class CarrinhoException extends RuntimeException 
{
    public CarrinhoException(String mensagem) 
    {
        super(mensagem);
    }
}