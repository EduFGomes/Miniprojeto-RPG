public class TipoArmadura
{
    private String tipoArmadura;
    private int defesaExtra;

    public TipoArmadura(String tipoArmadura, int defesaExtra) throws Exception
    {
        if (tipoArmadura == null || defesaExtra == 0) throw new Exception ("Parâmetro(s) inválido(s).");

        this.tipoArmadura = tipoArmadura;
        this.defesaExtra = defesaExtra;
    }
}