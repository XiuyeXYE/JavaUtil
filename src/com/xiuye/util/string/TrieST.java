package com.xiuye.util.string;

public class TrieST <V>{
    private static int R = 256;
    private Node root;

    private static class Node{
        private Object val;
        private Node[] next = new Node[R];
    }
    public V get(String key){
        Node x = get(root,key,0);
        if(x == null)return  null;
        return (V)x.val;
    }

    private Node get(Node x, String key, int d) {
        if(x == null){
            return null;
        }
        if(d == key.length()){
            return x;
        }
        char c = charAt(d);
        return get(x.next[c],key,d+1);
    }

    private char charAt(int d) {
        return 0;
    }

    public void put(String key,V val){
        root = put(root,key,val,0);
    }

    private Node put(Node x, String key, V val, int i) {
        if(x == null){
            return null;
        }
        if(i == key.length()){
            x.val = val;
            return x;
        }
        char c = key.charAt(i);
        x.next[c] = put(x.next[c],key,val,i+1);
        return x;
    }

    public  int size(){
        return size(root);
    }

    private int size(Node x) {
        if(x == null){
            return 0;
        }
        int cnt = 0;
        if(x.val != null){
            cnt ++;
        }
        for (char c=0;c<R;c++){
            cnt += size(x.next[c]);
        }
        return cnt;

    }

}
