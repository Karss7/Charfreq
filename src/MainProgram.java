import java.util.function.BiConsumer;

/**
 * Created by Karss on 24.10.2016.
 */
public class MainProgram{
    static String strOut="";

    static void delSpecSymbls(AVLTree tree){
        tree.delete(';');
        tree.delete(':');
        tree.delete('^');
        tree.delete('<');
        tree.delete('>');
        tree.delete('?');
        tree.delete('@');
        tree.delete('[');
        tree.delete(']');
        tree.delete('\\');
        tree.delete('^');
        tree.delete('`');
        tree.delete('_');
    }
    public static void main(String[] args) {
        AVLTree<Character, Integer> tree = new AVLTree<>();
        String str=ReadWriter.Read("input.txt");

        str=str.toLowerCase();
        str=str.replace(" ","");
        str=str.replace("\n","");
        for(int i=0; i<str.length(); ++i){
            Integer num=tree.get(str.charAt(i));
            if(num==null)
                tree.add(str.charAt(i),1);
            else {
                num=tree.delete(str.charAt(i))+1;
                tree.add(str.charAt(i), num);
            }
            if(i==9)
                System.out.print("");
        }
        delSpecSymbls(tree);

        BiConsumer<Character, Integer> visitor=(x, y)->{
            System.out.print(x+":"+y+" ");
            if((x>='0' && x<='9')||(x>='a'&&x<='z')) {
                strOut += x + ":" + y + " ";
                //System.out.println(" writed");
            }
            //else System.out.println(" NOT writed");
        };

        tree.traverse(visitor);

        ReadWriter.Write("output.txt", strOut);
    }
}
