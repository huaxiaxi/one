package start.A02_linkedlist;

public class ListNode {
    int val;
    ListNode next;

    public ListNode(){
    }

    public ListNode(int val){
        this.val = val;
    }
    public ListNode(int val, ListNode next){
        this.val = val;
        this.next = next;
    }
    public static void printListNode(ListNode head) {
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