public class TipoArmadura
{
    private String tipoArmadura;
    private int defesaExtra;

    public TipoArmadura(tipoArmadura, defesaExtra) throws Exceptions
    {
        if (tipoArmadura == null || defesaExtra == null) throw new Exception ("Parâmetro(s) inválido(s).");

        this.tipoArmadura = tipoArmadura;
        this.defesaExtra = defesaExtra;
    }
}