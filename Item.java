public class Item implements Comparable<Item>
{
    private String nome;
    private String descricao;
    private String tipoEfeito;
    private int forcaEfeito;
    private int quantidade;

    public Item (String nome, String descricao, String tipoEfeito,
                 int valorEfeito, int quantidade) throws Exception
    {
        if (nome == null ||
            descricao == null ||
            tipoEfeito == null ||
            valorEfeito <= 0 ||
            quantidade <= 0) throw new Exception("Parametros Invalidos");

        this.nome = nome;
        this.descricao = descricao;
        this.tipoEfeito = tipoEfeito.toUpperCase();
        this.forcaEfeito = valorEfeito;
        this.quantidade = quantidade;
    }

    public Item(Item i) throws Exception
    {
        if (i == null) throw new Exception("Item vazio.");

        this.nome = i.nome;
        this.descricao = i.descricao;
        this.tipoEfeito = i.tipoEfeito;
        this.forcaEfeito = i.forcaEfeito;
        this.quantidade = i.quantidade;
    }

    public String getNome() { return this.nome; }
    public String getDescricao() { return this.descricao; }
    public String getTipoEfeito() { return this.tipoEfeito; }
    public int getForcaEfeito() { return this.forcaEfeito; }
    public int getQuantidade() { return this.quantidade; }

    public void setQuantidade(int quantidade) throws Exception
    {
        if (quantidade < 0) throw new Exception("Quantidade nao pode ser negativa.");
        this.quantidade = quantidade;
    }

    public void adicionarQuantidade(int quantidade) throws Exception
    {
        if (quantidade <= 0) throw new Exception("Quantidade deve ser positiva.");
        this.quantidade += quantidade;
    }

    public void usar()
    {
        if (this.quantidade > 0) this.quantidade--;
    }

    @Override
    public String toString()
    {
        return ("Item: " + this.nome +
                "\nDescricao: " + this.descricao +
                "\nTipo Efeito: " + this.tipoEfeito +
                "\nForca do Efeito: " + this.forcaEfeito +
                "\nQuantidade: " + this.quantidade);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == this) return true;
        if (obj == null) return false;
        if (obj.getClass() != this.getClass()) return false;
        Item i =  (Item)obj;
        if (!this.nome.equals(i.nome)             ||
            !this.tipoEfeito.equals(i.tipoEfeito) ||
            this.forcaEfeito != i.forcaEfeito)
            return false;
        return true;
    }

    @Override
    public int hashCode()
    {
        int retorno = 1;
        retorno = retorno * 31 + this.nome.hashCode();
        retorno = retorno * 31 + this.tipoEfeito.hashCode();
        retorno = retorno * 31 + (Integer.hashCode(this.forcaEfeito));

        if (retorno < 0) retorno = -retorno;
        return retorno;
    }

    @Override
    public int compareTo(Item i) {
        if (this == i) return 0;

        int compNome = this.nome.compareTo(i.getNome());
        if (compNome != 0) return compNome;

        int compEfeito = this.tipoEfeito.compareTo(i.getTipoEfeito());
        if (compEfeito != 0) return compEfeito;

        return (Integer.compare(this.forcaEfeito, i.getForcaEfeito()));
    }
}
