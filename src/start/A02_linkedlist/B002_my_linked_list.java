package start.A02_linkedlist;

public class B002_my_linked_list {

//    单向链表
//    fd
/*    static class ListNode{
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) {
            this.val = val;
        }
    }

    static class MyLinkedList {
        int size;
        ListNode head;

        public MyLinkedList() {
            size = 0;
            head = new ListNode(0);
        }

        public int get(int num) {
            if (num <= 0 || num > size) {
                return -1;
            }
            ListNode cur = head;
            for (int i = 0; i < num; i++) {
                cur = cur.next;
            }
            return cur.val;
        }

        public void addAtHead(int val) {
            addAtIndex(0, val);
        }

        public void addAtTail(int val) {
            addAtIndex(size, val);
        }

        public void addAtIndex(int index, int val){
            if (index < 0){
                index = 0;
            }
            if (index > size){
                return;
            }
            size++;
            ListNode pre = head;
            for (int i = 0; i < index; i++) {
                pre = pre.next;
            }
            ListNode addV = new ListNode(val);
            addV.next = pre.next;
            pre.next = addV;
        }

        public void deleteAtIndex(int index) {
            if (index >= size || index < 0){
                return;
            }
            size--;
            ListNode pre = head;
            for (int i = 0; i < index; i++) {
                pre = pre.next;
            }
            pre.next = pre.next.next;
        }
    }*/

//    双向链表

    static class MyLinkedList {
         class ListNode {
            int val;
            ListNode prev, next;
            ListNode(int val) {
                this.val = val;
            }
        }
        int size;
        ListNode head, tail;

        public MyLinkedList() {
            size = 0;
            head = new  ListNode(0);
            tail = new ListNode(0);
            head.next = tail;
            tail.prev = head;
        }

        public int get(int index) {
            if (index < 0 || index >= size){
                return -1;
            }
            ListNode cur = head;
            if (index < (size - 1) / 2){
                for (int i = 0; i <=index ; i++) {
                    cur = cur.next;
                }
            }else {
                cur = tail;
                for (int i = 0; i < size - index - 1; i++) {
                    cur = cur.prev;
                }
            }
            return cur.val;
        }

        public void addAtHead(int val) {
            ListNode cur = head;
            ListNode node = new ListNode(val);
            node.next = cur.next;
            cur.next.prev = node;
            cur.next = node;
            node.prev = cur;
            size++;
        }

        public void addAtTail(int val) {
            ListNode cur = tail;
            ListNode node = new ListNode(val);
            node.next = tail;
            node.prev = cur.prev;
            cur.prev.next = node;
            cur.prev = node;
            size++;
        }

        public void addAtIndex(int index, int val) {
            if (index > size){
                return;
            }
            if (index < 0) {
                index = 0;
            }
            ListNode cur = head;
            for (int i = 0; i <= index ; i++) {
                cur = cur.next;
            }
            ListNode node = new ListNode(val);
            node.next = cur.next;
            cur.next.prev = node;
            cur.next = node;
            node.prev = cur;
            size++;
        }

        public void deleteAtIndex(int index) {
            if (index >= size || index < 0) {
                return;
            }
            ListNode cur = head;
            for (int i = 0; i <= index; i++) {
                cur = cur.next;
            }
            cur.next.next.prev = cur;
            cur.next = cur.next.next;
            size--;
        }
    }

    public static void main(String[] args) {
        MyLinkedList mll = new MyLinkedList();
//        mll.addAtIndex(0,2);
//        mll.addAtTail(1);
//        mll.addAtTail(4);
//        mll.addAtHead(7);
        printListNode(mll.head.next);
        
    }

    private static void printListNode(MyLinkedList.ListNode head) {
        if (head == null){
            System.out.println(-1);
            return;
        }
        while (head != null){
            System.out.print(head.val + " ");
            head = head.next;
        }
    }

}
