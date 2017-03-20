import java.util.Random;
import java.util.function.BiConsumer;

/**
 * Created by Karss on 20.10.2016.
 */

public class AVLTree<K extends Comparable, V> {

    private Node<K,V> root;

    static private class Node<Key extends Comparable,Val>{
        Key k;
        Val v;
        int height;

        Node<Key, Val> p;
        Node<Key, Val> l;
        Node<Key, Val> r;

        Node(Key _k, Val _v, Node<Key, Val> _p){
            p=_p;
            k= _k;
            v= _v;
            height=1;
            l=r=null;
        }
    }

    private int height(Node n){
        return n==null?0:n.height;
    }
    private int balanceFact(Node n){
        return height(n.r)-height(n.l);
    }
    private void updateHeight(Node n){
        int hl=height(n.l);
        int hr=height(n.r);
        n.height=1+(hl>hr?hl:hr);
    }

    private Node<K,V> rotateRight(Node<K,V> a){
        Node<K,V> b=a.l;
        a.l=b.r;

        if(b.r!=null)
            b.r.p=a;
        b.r=a;

        b.p=a.p;
        a.p=b;

        updateHeight(a);
        updateHeight(b);
        return b;
    }
    private Node<K,V> rotateLeft(Node<K,V> a){
        Node<K,V> b=a.r;
        a.r=b.l;

        if(b.l!=null)
            b.l.p=a;
        b.l=a;

        b.p=a.p;
        a.p=b;

        updateHeight(a);
        updateHeight(b);
        return b;
    }

    private Node<K,V> balance(Node<K,V> n){
        updateHeight(n);
        if(balanceFact(n)>=2){
            if(balanceFact(n.r)<0)
                n.r=rotateRight(n.r);
            return rotateLeft(n);
        }
        if(balanceFact(n)<=-2){
            if(balanceFact(n.l)>0)
                n.l=rotateLeft(n.l);
            return rotateRight(n);
        }

        return n;
    }

    private Node<K,V> insert(Node<K,V> n,K key, V val, Node<K,V> par){
        if(n==null) return new Node(key, val, par);
        if(key.compareTo(n.k)<0)
            n.l=insert(n.l, key, val, n);
        else
            n.r=insert(n.r, key,val, n);
        return balance(n);
    }

    private Node<K,V> findMin(Node<K,V> n){
        if(n==null)
            return null;
        return n.l==null?n:findMin(n.l);
    }
    private Node<K,V> findMax(Node<K,V> n){
        if(n==null)
            return null;
        return n.r==null?n:findMax(n.r);
    }

    private Node<K,V> removeMin(Node<K,V> n){
        if(n.l==null)
            return n.r;
        n.l=removeMin(n.l);
        if(n.l!=null)
            n.l.p=n;
        return balance(n);
    }

    private Node<K,V> remove(Node<K,V> n, K key){
        if(n==null) return null;
        if(key.compareTo(n.k)<0)
            n.l=remove(n.l, key);
        else if(key.compareTo(n.k)>0)
            n.r=remove(n.r,key);
        else{
            Node<K,V> l=n.l;
            Node<K,V> r=n.r;

            if(n.r!=null) {
                n.r.p = n.p;
            }

            //delete p

            if(r==null){
                return l;
            }

            Node<K,V> min=findMin(r);

            min.r=removeMin(r);

            min.l=l;
            if(min.l!=null)
                min.l.p=min.p;
            min.p=n.p;
            return balance(min);
        }
        return balance(n);
    }



    private Node<K,V> successor(Node<K,V> n){
        //return findMin(n.r);
        if(n.r!=null)
            return findMin(n.r);
        else if(n.p!=null) {
            if(n.p.l==n)
                return n.p;
            else {
                Node<K,V> tmp=n.p;

                while(tmp.r==n){
                    n=tmp;
                    tmp=tmp.p;
                    if(tmp==null)
                        return null;
                }
                return tmp;
            }
        }
        else
            return null;
    }
    private Node<K,V> predecessor(Node<K,V> n){
        //return findMax(n.l);
        if(n.l!=null)
            return findMax(n.l);
        else if(n.p!=null) {
            if(n.p.r==n)
                return n.p;
            else {
                Node<K,V> tmp=n.p;

                while(tmp.l==n){
                    n=tmp;
                    tmp=tmp.p;
                    if(tmp==null)
                        return null;
                }
                return tmp;
            }
        }
        else
            return null;
    }
    private Node<K,V> find(K key, Node<K,V> n){
        if(n==null)
            return null;
        if(key.compareTo(n.k)==0)
            return n;
        else if(key.compareTo(n.k)>0)
            return find(key, n.r);
        else return find(key, n.l);
    }

    private void fixParents(Node<K,V> n, Node<K,V> p){
        n.p=p;
        if(n.l!=null)
            fixParents(n.l,n);
        if(n.r!=null)
            fixParents(n.r,n);
    }

    public void add(K key, V val){
        root = insert(root, key, val, null);
    }
    public V get(K key){
        Node<K,V> tmp= find(key, root);
        if(tmp==null)
            return null;
        else
            return tmp.v;
    }
    public V delete(K key){
        V tmp= get(key);
        root =remove(root, key);
        return tmp;
    }

    public void traverse(BiConsumer bc){
        fixParents(root,null);
        Node<K,V> tmp=findMin(root);
        while(tmp!=null){
            bc.accept(tmp.k,tmp.v);
            tmp=successor(tmp);
        }
    }

    public void traverse(BiConsumer bc, K from, K to){ //обход от до
        fixParents(root, null);
        Node<K,V> _from = find(from, root);
        Node<K,V> _to = find(to, root);
        if(_from==null || _to==null)
            throw new IllegalArgumentException("from or to is null");

        Node<K, V> tmp = _from;
        while (true) {
            bc.accept(tmp.k, tmp.v);

            if(tmp == _to) break;

            if(_from.k.compareTo(_to.k)<0)
                tmp = successor(tmp);
            else
                tmp=predecessor(tmp);
        }

    }

    AVLTree(){
        root =null;
    }
}

