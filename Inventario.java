import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Inventario implements Cloneable
{
    private ArrayList<Item> itens;

    public Inventario(){
        this.itens = new ArrayList<>();
    }

    public Inventario(Inventario i) throws Exception
    {
        this.itens = new ArrayList<>();

        for (Item itemOriginal : i.itens)
        {
            this.itens.add(new Item(itemOriginal));
        }
    }

    public void adicionarItem(Item i) throws Exception
    {
        for (Item itemExistente : this.itens)
        {
            if (itemExistente.equals(i))
            {
                itemExistente.adicionarQuantidade(i.getQuantidade());
                return;
            }
        }

        this.itens.add(new Item(i));
    }

    public Item buscarItem(String nome)
    {
        for (Item i :  this.itens)
        {
            if (i.getNome().equalsIgnoreCase(nome))
            {
                return i;
            }
        }
        return null;
    }

    public void usarItem(Item i)
    {
        Iterator<Item> iterator = this.itens.iterator();

        while (iterator.hasNext())
        {
            Item item = iterator.next();

            if  (item.equals(i))
            {
                item.usar();
                if (item.getQuantidade() == 0)
                {
                    iterator.remove();
                }
                return;
            }
        }
    }

    public void listarItems()
    {
        if (this.itens.isEmpty())
        {
            System.out.println("Inventario vazio.");
            return;
        }

        Collections.sort(this.itens);

        System.out.println("INVENTARIO");
        for (Item item : this.itens)
        {
            System.out.println(item.getNome() + " (x" +  item.getQuantidade() + ")");
        }
        System.out.println("------------------");
    }

    @Override
    public Inventario clone()
    {
        try
        {
            return new Inventario(this);
        }
        catch (Exception e)
        {
            System.err.println("Erro ao tentar clonar o objeto: " + e.getMessage());
            return null;
        }
    }
}
